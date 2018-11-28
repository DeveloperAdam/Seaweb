package techease.com.seaweb.Activities.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.comix.overwatch.HiveProgressView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import techease.com.seaweb.Activities.Adapters.BoatDetailsAdapter;
import techease.com.seaweb.Activities.Adapters.PriceListAdapter;
import techease.com.seaweb.Activities.Models.ImageModelBoatDetails;
import techease.com.seaweb.Activities.Models.PriceListModel;
import techease.com.seaweb.Activities.Utils.CircleIndicator;
import techease.com.seaweb.R;


public class PriceListFragment extends Fragment {

    ImageView ivBack;
    RecyclerView recyclerView;
    List<PriceListModel> priceListModelList;
    PriceListAdapter priceListAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    HiveProgressView hiveProgressView;
    String boatid,userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_price_list, container, false);

        hiveProgressView = view.findViewById(R.id.progressPriceList);
        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        ivBack=view.findViewById(R.id.ivClosePriceList);
        userId=sharedPreferences.getString("userid","");
        boatid=sharedPreferences.getString("boatid","");
        recyclerView = view.findViewById(R.id.rvPriceList);
        priceListModelList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        priceListAdapter = new PriceListAdapter(getActivity(),priceListModelList);
        recyclerView.setAdapter(priceListAdapter);
        hiveProgressView.showContextMenu();
        apicall();


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new BoatDetailFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.animator.slide_out_up, R.animator.slide_in_up, R.animator.slide_in_up, R.animator.slide_out_up);
                transaction.replace(R.id.container, fragment).commit();
            }
        });
        return view;
    }
    private void apicall() {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://divergense.com/boat/App/getBoatDetails"
                , new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ZmaImages", response);
                hiveProgressView.clearAnimation();
                hiveProgressView.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject object = jsonObject.getJSONObject("data");
                    JSONArray array = object.getJSONArray("price_list");
                    for (int i = 0; i<array.length(); i++)
                    {
                        JSONObject obj = array.getJSONObject(i);
                        PriceListModel model = new PriceListModel();
                        model.setFrom(obj.getString("from"));
                        model.setTo(obj.getString("to"));
                        model.setType(obj.getString("type"));
                        model.setPrice(obj.getString("price"));

                        priceListModelList.add(model);

                    }
                    priceListAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    hiveProgressView.clearAnimation();
                    hiveProgressView.setVisibility(View.GONE);
                    e.printStackTrace();
                    Log.d("error", String.valueOf(e.getCause()));
                }


            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hiveProgressView.clearAnimation();
                hiveProgressView.setVisibility(View.GONE);
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
                params.put("id",boatid);
                params.put("userid",userId);
                Log.d("zmaParm",params.toString());
                return params;
            }
        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }
}
