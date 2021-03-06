package techease.com.seaweb.Activities.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Activities.BottomActivity;
import techease.com.seaweb.Activities.Adapters.BoatDetailsAdapter;
import techease.com.seaweb.Activities.Adapters.GetAllPlacesAdapter;
import techease.com.seaweb.Activities.Models.GetAllPlacesDataModel;
import techease.com.seaweb.Activities.Models.GetAllPlacesResponseModel;
import techease.com.seaweb.Activities.Models.ImageModelBoatDetails;
import techease.com.seaweb.Activities.Utils.AlertsUtils;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.Activities.Utils.CircleIndicator;
import techease.com.seaweb.R;


public class TabFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    Fragment fragment;

    List<GetAllPlacesDataModel> dataModelList;
    ArrayList<String> suggestions;
    String[] some;
    android.support.v7.app.AlertDialog alertDialog;



    @Override
    public void onStart() {
        super.onStart();

        placesCall();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab, container, false);

        dataModelList = new ArrayList<>();
        suggestions = new ArrayList<>();


        tabLayout = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);


//        etSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.select_dialog_singlechoice, suggestions);
//                etSearch.setThreshold(1);
//                etSearch.setAdapter(adapter);
//            }
//        });


        return view;
    }

    private void setupViewPager(final ViewPager viewPager) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (alertDialog == null) {
            alertDialog = AlertsUtils.createProgressDialog(getActivity());
            alertDialog.show();
        }
        tabApiCall();


    }

    private void tabApiCall() {

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://divergense.com/boat/App/getAllLocations"
                , new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ZmaImages", response);
                if (alertDialog != null)
                    alertDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i =0 ; i<jsonArray.length(); i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        some = new String[] {object.getString("name").toString()};
                        for(int z = 0; z<some.length; z++)
                        {
                            tabLayout.addTab(tabLayout.newTab().setText(some[z].toString()));
                        }
                        viewPager.setAdapter(new PagerAdapter(((FragmentActivity)getActivity()).getSupportFragmentManager(), tabLayout.getTabCount()));
                        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                viewPager.setCurrentItem(tab.getPosition());
                            }

                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {

                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {

                            }
                        });
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    if (alertDialog != null)
                        alertDialog.dismiss();
                }

            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("error", String.valueOf(error.getCause()));
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);

    }



    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(android.support.v4.app.FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }



        @Override
        public android.support.v4.app.Fragment getItem(int position) {

            switch (position) {
                case 0:
                    fragment=new ListOfPlacesFragment();
                    return fragment;
                case 1:
//                    fragment=new ListOfPlacesFragment();
                    return null;
                case 2:
                  //  fragment=new ListOfPlacesFragment();
                    return null;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }


    }


    private void placesCall() {
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<GetAllPlacesResponseModel> call = services.getAllPlaces();
        call.enqueue(new Callback<GetAllPlacesResponseModel>() {
            @Override
            public void onResponse(Call<GetAllPlacesResponseModel> call, Response<GetAllPlacesResponseModel> response) {

                if (response.isSuccessful())
                {
                    if (alertDialog != null)
                        alertDialog.dismiss();
                    Log.d("zmagetAllPlace",response.toString());

                    dataModelList.addAll(response.body().getData());

                }
                else
                {
                    if (alertDialog != null)
                        alertDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<GetAllPlacesResponseModel> call, Throwable t) {
                if (alertDialog != null)
                    alertDialog.dismiss();
            }
        });
    }


}
