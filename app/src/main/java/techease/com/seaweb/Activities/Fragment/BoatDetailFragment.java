package techease.com.seaweb.Activities.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Adapters.BoatDetailsAdapter;
import techease.com.seaweb.Activities.Adapters.FavrtAdapter;
import techease.com.seaweb.Activities.Models.BoatDetailsDataModel;
import techease.com.seaweb.Activities.Models.BoatDetailsFileModel;
import techease.com.seaweb.Activities.Models.BoatDetailsResponseModel;
import techease.com.seaweb.Activities.Models.FavrtResponseModel;
import techease.com.seaweb.Activities.Utils.AlertsUtils;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;


public class BoatDetailFragment extends Fragment {

    RecyclerView recyclerViewImages;
    TextView tvTitle,tvPrice,tvBirths,tvCabinets,tvSkipper,tvBoattype,tvPlace,tvPeople;
    int userId;
    ImageView ivfvrt;
    String boatid,births,title,price,place,people,cabinets,skipper,boattype,isFvrt;
    List<BoatDetailsFileModel> boatDetailsDataModelList;
    BoatDetailsAdapter adapter;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_boat_detail, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userId=sharedPreferences.getInt("userid",0);
        boatid=getArguments().getString("boatid","");
        ivfvrt=view.findViewById(R.id.ivFvrtBdetails);
        recyclerViewImages=view.findViewById(R.id.rvBoatDetailsImage);
        tvTitle=view.findViewById(R.id.tvBoatDetailsTitle);
        tvPrice=view.findViewById(R.id.tvPriceBoatDetails);
        tvBirths=view.findViewById(R.id.tvBirthsBoatDetails);
        tvCabinets=view.findViewById(R.id.tvCabinetsBoatDetails);
        tvSkipper=view.findViewById(R.id.tvSkipperBoatDetails);
        tvBoattype=view.findViewById(R.id.tvBoatTypeBoatDetails);
        tvPlace=view.findViewById(R.id.tvBoatDetailsPlace);
        tvPeople =view.findViewById(R.id.tvPeopleBoatDetails);

        recyclerViewImages.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayout.HORIZONTAL,true));
        boatDetailsDataModelList=new ArrayList<>();
        if (alertDialog == null) {
            alertDialog = AlertsUtils.createProgressDialog(getActivity());
            alertDialog.show();
        }
        apicall();

        return view;
    }

    private void apicall() {

        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<BoatDetailsResponseModel> call = services.getBoatDetails(boatid,String.valueOf(userId));
        call.enqueue(new Callback<BoatDetailsResponseModel>() {
            @Override
            public void onResponse(Call<BoatDetailsResponseModel> call, Response<BoatDetailsResponseModel> response) {

                if (response.isSuccessful())
                {
                    if (alertDialog != null)
                        alertDialog.dismiss();
                    Log.d("zmaBoatDetails",response.toString());

                    title=response.body().getData().getTitle();
                    price=response.body().getData().getWholePrice();
                    place=response.body().getData().getLocation();
                    skipper=response.body().getData().getSkipper();
                    people=response.body().getData().getPeople();
                    births=response.body().getData().getBirths();
                    cabinets=response.body().getData().getCabinats();
                    boattype=response.body().getData().getType();
                    isFvrt=response.body().getData().getIsFavorite();
                  //  boatDetailsDataModelList.addAll(response.body().getData().getFiles());


                    if (isFvrt.equals("true"))
                    {
                        ivfvrt.setBackgroundResource(R.drawable.fillheart);
                    }
                    else
                    {
                        ivfvrt.setBackgroundResource(R.drawable.white_heart);
                    }

                    tvTitle.setText(title);
                    tvPeople.setText("People "+people);
                    tvPrice.setText(price);
                    tvPlace.setText(place);
                    tvBirths.setText("Births "+births);
                    tvBoattype.setText(boattype);
                    tvSkipper.setText("Skipper "+skipper);
                    tvCabinets.setText("Cabinets "+cabinets);

                }
                else

                {
                    if (alertDialog != null)
                        alertDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<BoatDetailsResponseModel> call, Throwable t) {
                if (alertDialog != null)
                    alertDialog.dismiss();
            }
        });

    }

}
