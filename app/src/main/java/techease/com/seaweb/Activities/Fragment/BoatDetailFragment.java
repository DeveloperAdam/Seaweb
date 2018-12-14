package techease.com.seaweb.Activities.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.comix.overwatch.HiveProgressView;
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator;
import com.squareup.picasso.Picasso;

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
import techease.com.seaweb.Activities.Adapters.BoatDetailsAdapter;
import techease.com.seaweb.Activities.Adapters.FacilitiesAdapter;
import techease.com.seaweb.Activities.Fragment.Chatting.MessagesListFragment;
import techease.com.seaweb.Activities.Fragment.Chatting.PriceRequestFragment;
import techease.com.seaweb.Activities.Models.BoatDetail.BoatDetailChildModel;
import techease.com.seaweb.Activities.Models.BoatDetail.BoatDetailResponseModel;
import techease.com.seaweb.Activities.Models.BoatDetail.BoatFacilityParentModel;
import techease.com.seaweb.Activities.Models.FacilitiesGroupModel;
import techease.com.seaweb.Activities.Models.FacilitiesModel;
import techease.com.seaweb.Activities.Models.FacilitiesSubItemModel;
import techease.com.seaweb.Activities.Models.ImageModelBoatDetails;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;


public class BoatDetailFragment extends Fragment {

    RecyclerView recyclerViewImages;
    ExpandableListView expandableListView;
    TextView tvTitle,tvPrice,tvBirths,tvCabinets,tvSkipper,tvBoattype,tvPlace,tvPeople,tvDes,tvPerDayPrice;
    String userId;
    LinearLayout llPriceList;
    Button btnGotoAddBookDetail,btnMessage;
    IndefinitePagerIndicator indefinitePagerIndicator;
    ImageView ivfvrt,ivBack,ivProfile;
    String boatid,proid,pricePerDay;
    List<ImageModelBoatDetails> boatDetailsDataModelList;
    String isFvrt,owenerid;
    List<FacilitiesModel> facilitiesModels;
    FacilitiesAdapter facilitiesAdapter;
    BoatDetailsAdapter boatDetailsAdapter;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    HiveProgressView hiveProgressView;
    ScrollView scrollView;

    List<FacilitiesGroupModel> facilitiesGroupModels;
    HashMap<FacilitiesGroupModel, List<FacilitiesSubItemModel>> listDataChild;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_boat_detail, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userId=sharedPreferences.getString("userid","");
        boatid=sharedPreferences.getString("boatid","");
        tvPerDayPrice = view.findViewById(R.id.tvPerDayPrice);
        ivfvrt=view.findViewById(R.id.ivFvrtBdetails);
        btnMessage = view.findViewById(R.id.btnMessageScreen);
        hiveProgressView = view.findViewById(R.id.progressBoatDetail);
        expandableListView = view.findViewById(R.id.lvExp);
        ivProfile = view.findViewById(R.id.profile_image);
        tvDes = view.findViewById(R.id.tvDes);
        scrollView = view.findViewById(R.id.scrollview);
        ivBack = view.findViewById(R.id.ivB);
        indefinitePagerIndicator = view.findViewById(R.id.recyclerview_pager_indicator);
        llPriceList = view.findViewById(R.id.llPriceList);
        recyclerViewImages=view.findViewById(R.id.rvBoatDetailsImage);
        tvTitle=view.findViewById(R.id.tvBoatDetailsTitle);
        tvPrice=view.findViewById(R.id.tvPriceBoatDetails);
        tvBirths=view.findViewById(R.id.tvBirthsBoatDetails);
        tvCabinets=view.findViewById(R.id.tvCabinetsBoatDetails);
        tvSkipper=view.findViewById(R.id.tvSkipperBoatDetails);
        tvBoattype=view.findViewById(R.id.tvBoatTypeBoatDetails);
        tvPlace=view.findViewById(R.id.tvBoatDetailsPlace);
        tvPeople =view.findViewById(R.id.tvPeopleBoatDetails);
        btnGotoAddBookDetail=view.findViewById(R.id.btnGotoAddBookDetail);



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager.setStackFromEnd(false);
        recyclerViewImages.setLayoutManager(linearLayoutManager);
        boatDetailsDataModelList=new ArrayList<>();
        facilitiesModels = new ArrayList<>();
        facilitiesGroupModels = new ArrayList<>();
        listDataChild = new HashMap<>();
        boatDetailsAdapter = new BoatDetailsAdapter(getActivity(),boatDetailsDataModelList);
        recyclerViewImages.setAdapter(boatDetailsAdapter);
        indefinitePagerIndicator.attachToRecyclerView(recyclerViewImages);
        facilitiesAdapter = new FacilitiesAdapter(getActivity(), facilitiesGroupModels, listDataChild);
        expandableListView.setAdapter(facilitiesAdapter);
        hiveProgressView.showContextMenu();
        imageApiCall();
        apicall();


        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editor.putString("perday",pricePerDay).commit();
                editor.putString("ownerid",owenerid).commit();
                Fragment fragment = new MessagesListFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                getFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();

            }
        });
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {

                expandableListView.performClick();
            }
        });


        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                setListViewHeight(expandableListView, i);
                return false;
            }
        });



        btnGotoAddBookDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("proid",proid).commit();
                editor.putString("perday",pricePerDay).commit();
                Fragment fragment = new DatePickerBookingFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                getFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new BoatsOnLocationFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                getFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            }
        });

        llPriceList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new PriceListFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.animator.slide_in_up, R.animator.slide_out_up, R.animator.slide_out_up, R.animator.slide_in_up);
                transaction.replace(R.id.container, fragment).addToBackStack("back").commit();

            }
        });
        return view;
    }

    private void imageApiCall() {

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://divergense.com/boat/App/getBoatDetails"
                , new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hiveProgressView.setVisibility(View.GONE);

                Log.d("zmaBDetailImg", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject object = jsonObject.getJSONObject("data");
                    JSONArray jsonArray = object.getJSONArray("files");
                    for (int i=0; i<jsonArray.length(); i++)
                    {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        ImageModelBoatDetails model = new ImageModelBoatDetails();
                        model.setFile(obj.getString("file"));
                        boatDetailsDataModelList.add(model);
                    }

                    boatDetailsAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    if (alertDialog != null)
                        alertDialog.dismiss();
                    alertDialog = null;
                }

            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (alertDialog != null)
                    alertDialog.dismiss();
                alertDialog = null;
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

    public  void apicall()
        {
            ApiService services = ApiClient.getClient().create(ApiService.class);
            Call<BoatDetailResponseModel> call = services.getBoatDetail(boatid,userId);
            call.enqueue(new Callback<BoatDetailResponseModel>() {
                @Override
                public void onResponse(Call<BoatDetailResponseModel> call, Response<BoatDetailResponseModel> response) {

                    if (response.isSuccessful())
                    {
                        hiveProgressView.clearAnimation();
                        hiveProgressView.setVisibility(View.GONE);
                        Log.d("zma detail",response.toString());

                        tvTitle.setText(response.body().getBoatDetailDataModel().getTitle());
                        tvPlace.setText(response.body().getBoatDetailDataModel().getLocation());
                        tvPrice.setText(response.body().getBoatDetailDataModel().getFulldayPrice()+"$ Per Day");
                        tvBoattype.setText(response.body().getBoatDetailDataModel().getType());
                        editor.putString("type",tvBoattype.getText().toString()).commit();
                        tvSkipper.setText(response.body().getBoatDetailDataModel().getSkipper());
                        tvPeople.setText(response.body().getBoatDetailDataModel().getPeople());
                        tvBirths.setText(response.body().getBoatDetailDataModel().getBirths());
                        tvCabinets.setText(response.body().getBoatDetailDataModel().getCabinats());
                        tvDes.setText(response.body().getBoatDetailDataModel().getDescription());
                        proid = response.body().getBoatDetailDataModel().getPid().toString();
                        editor.putString("proid",proid).commit();
                        owenerid = response.body().getBoatDetailDataModel().getOwnerId();
                        isFvrt = response.body().getBoatDetailDataModel().getIsFavorite();
                        pricePerDay = response.body().getBoatDetailDataModel().getFulldayPrice();
                        tvPerDayPrice.setText(pricePerDay+"$");

                        if (!response.body().getBoatDetailDataModel().getUserPicture().equals(""))
                        {
                            Picasso.get().load(response.body().getBoatDetailDataModel().getUserPicture()).into(ivProfile);
                        }
                        if (isFvrt.equals("true"))
                        {
                            ivfvrt.setBackgroundResource(R.drawable.fillheart);
                        }
                        else
                            if (isFvrt.equals("false"))
                            {
                                ivfvrt.setBackgroundResource(R.drawable.white_heart);
                            }
                    setGroupChild(response.body().getBoatDetailDataModel().getFacilities());
                    }
                    else
                    {
                        Log.d("zmadetail",response.toString());
                        hiveProgressView.clearAnimation();
                        hiveProgressView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<BoatDetailResponseModel> call, Throwable t) {
                    hiveProgressView.clearAnimation();
                    hiveProgressView.setVisibility(View.GONE);
                    Log.d("zmaLoginexp",t.getMessage());
                }
            });
        }


    private void setGroupChild(List<BoatFacilityParentModel> facilitiesModels) {
        for(int i =0; i<facilitiesModels.size(); i++) {
            FacilitiesGroupModel facilitiesGroupModel = new FacilitiesGroupModel();
            facilitiesGroupModel.setHeading(facilitiesModels.get(i).getHeading());
            facilitiesGroupModels.add(facilitiesGroupModel);

            List<FacilitiesSubItemModel> servicesChild = new ArrayList<>();
            for (BoatDetailChildModel childServices : facilitiesModels.get(i).getData()) {
                FacilitiesSubItemModel notaryServicesChildModel = new FacilitiesSubItemModel();
                notaryServicesChildModel.setName(childServices.getName());
                servicesChild.add(notaryServicesChildModel);

            }
            listDataChild.put(facilitiesGroupModel, servicesChild);
        }
        facilitiesAdapter.notifyDataSetChanged();
    }
    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        FacilitiesAdapter listAdapter = (FacilitiesAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 100;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

}
