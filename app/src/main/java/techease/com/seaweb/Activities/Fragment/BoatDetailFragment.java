package techease.com.seaweb.Activities.Fragment;

import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import techease.com.seaweb.Activities.Activities.BottomActivity;
import techease.com.seaweb.Activities.Adapters.BoatDetailsAdapter;
import techease.com.seaweb.Activities.Adapters.FacilitiesAdapter;
import techease.com.seaweb.Activities.Models.BoatDetail.BoatDetailResponseModel;
import techease.com.seaweb.Activities.Models.BoatDetail.Data;
import techease.com.seaweb.Activities.Models.BoatDetail.Datum;
import techease.com.seaweb.Activities.Models.BoatDetail.Facility;
import techease.com.seaweb.Activities.Models.FacilitiesGroupModel;
import techease.com.seaweb.Activities.Models.FacilitiesModel;
import techease.com.seaweb.Activities.Models.FacilitiesSubItemModel;
import techease.com.seaweb.Activities.Models.ImageModelBoatDetails;
import techease.com.seaweb.Activities.Models.LoginResponseModel;
import techease.com.seaweb.Activities.Utils.AlertsUtils;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.Activities.Utils.CircleIndicator;
import techease.com.seaweb.R;


public class BoatDetailFragment extends Fragment  {

    RecyclerView recyclerViewImages;
    ExpandableListView expandableListView;
    TextView tvTitle,tvPrice,tvBirths,tvCabinets,tvSkipper,tvBoattype,tvPlace,tvPeople;
    String userId,des;
    LinearLayout llPriceList;
    Button btnGotoAddBookDetail;
    IndefinitePagerIndicator indefinitePagerIndicator;
    ImageView ivfvrt,ivBack,ivProfile;
    String boatid,births,title,price,fullDay,halfDay,place,people,cabinets,skipper,boattype,isFvrt,proid,user_Img;
    List<ImageModelBoatDetails> boatDetailsDataModelList;
    List<FacilitiesModel> facilitiesModels;
    FacilitiesAdapter facilitiesAdapter;
    BoatDetailsAdapter adapter;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    HiveProgressView hiveProgressView;

    List<FacilitiesGroupModel> facilitiesGroupModels;
    HashMap<FacilitiesGroupModel, List<FacilitiesSubItemModel>> listDataChild;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_boat_detail, container, false);

        List<String> categories = new ArrayList<String>();
        categories.add("Price List");
        categories.add("Full Day");
        categories.add("Half Day");
        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userId=sharedPreferences.getString("userid","");
        boatid=sharedPreferences.getString("boatid","");
        ivfvrt=view.findViewById(R.id.ivFvrtBdetails);
       // hiveProgressView = view.findViewById(R.id.progressBoatDetail);
        expandableListView = view.findViewById(R.id.lvExp);
        ivProfile = view.findViewById(R.id.profile_image);
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
     //   hiveProgressView.showContextMenu();
        facilitiesModels = new ArrayList<>();
        facilitiesGroupModels = new ArrayList<FacilitiesGroupModel>();
        listDataChild = new HashMap<>();
        facilitiesAdapter = new FacilitiesAdapter(getActivity(), facilitiesGroupModels, listDataChild);
        expandableListView.setAdapter(facilitiesAdapter);
       // imageCall();
        apicall();



        btnGotoAddBookDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("proid",proid);
                Fragment fragment = new AddBookingDetailFragment();
                fragment.setArguments(bundle);
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
        public  void apicall()
        {
            ApiService services = ApiClient.getClient().create(ApiService.class);
            Call<Facility> call = services.getBoatDetail(boatid,userId);
            call.enqueue(new Callback<Facility>() {
                @Override
                public void onResponse(Call<Facility> call, Response<Facility> response) {

                    if (response.isSuccessful())
                    {
                        if (alertDialog != null)
                            alertDialog.dismiss();
                        alertDialog = null;
                        Log.d("zma detail",response.toString());

              //      setGroupChild(response.body().getData());
                    }
                    else
                    {
                        Log.d("zmadetail",response.toString());
                        if (alertDialog != null)
                            alertDialog.dismiss();
                        alertDialog = null;
                    }
                }

                @Override
                public void onFailure(Call<Facility> call, Throwable t) {
                    if (alertDialog != null)
                        alertDialog.dismiss();
                    alertDialog = null;
                    Log.d("zmaLoginexp",t.getMessage());
                }
            });
        }


    private void setGroupChild(List<Data> facilitiesModels) {
        for(int i =0; i<facilitiesModels.size(); i++) {
            FacilitiesGroupModel facilitiesGroupModel = new FacilitiesGroupModel();
            facilitiesGroupModel.setHeading(facilitiesModels.get(i).getFacilities().get(i).getHeading());
            facilitiesGroupModels.add(facilitiesGroupModel);

            List<FacilitiesSubItemModel> servicesChild = new ArrayList<>();
            for (Datum childServices : facilitiesModels.get(i).getFacilities().get(i).getData()) {
                FacilitiesSubItemModel notaryServicesChildModel = new FacilitiesSubItemModel();
                notaryServicesChildModel.setName(childServices.getName());
                servicesChild.add(notaryServicesChildModel);

            }
            listDataChild.put(facilitiesGroupModel, servicesChild);
        }
        facilitiesAdapter.notifyDataSetChanged();
    }

}
