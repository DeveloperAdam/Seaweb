package techease.com.seaweb.Activities.Fragment.Trips;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.comix.overwatch.HiveProgressView;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Models.Trip.AllTripsResponseModel;
import techease.com.seaweb.Activities.Models.Trip.TripDetailResponseModel;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;


public class TripDetailsFragment extends Fragment {

    ImageView ivBack,ivTripImage,ivUser;
    TextView tvTitle,tvDuration,tvLocation,tvStartDate,tvEndDate,tvStartTime,tvEndTime,tvListedBy;
    HiveProgressView hiveProgressView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String id,userid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  =  inflater.inflate(R.layout.fragment_trip_details, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userid = sharedPreferences.getString("userid","");
        id = sharedPreferences.getString("tripid","");
        hiveProgressView = view.findViewById(R.id.progressTripDetails);
        ivBack = view.findViewById(R.id.ivBackTripDetail);
        ivTripImage = view.findViewById(R.id.ivTripDetails);
        ivUser = view.findViewById(R.id.ivUserTripDetail);
        tvTitle = view.findViewById(R.id.tvTripTitleDetails);
        tvDuration = view.findViewById(R.id.tvDurationTripDetails);
        tvLocation = view.findViewById(R.id.tvLocationTripDetail);
        tvStartDate = view.findViewById(R.id.tvStartDateTripDetail);
        tvEndDate = view.findViewById(R.id.tvEndDateTripDetails);
        tvStartTime = view.findViewById(R.id.tvStartTimeTripDetail);
        tvEndTime = view.findViewById(R.id.tvEndTimeTripDetails);
        tvListedBy = view.findViewById(R.id.tvListedBy);


        hiveProgressView.showContextMenu();
        apicall();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new TripsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
            }
        });


        return view;
    }
    private void apicall() {
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<TripDetailResponseModel> call = services.getTripDetails(id,userid);
        call.enqueue(new Callback<TripDetailResponseModel>() {
            @Override
            public void onResponse(Call<TripDetailResponseModel> call, Response<TripDetailResponseModel> response) {

                if (response.isSuccessful()) {
                    hiveProgressView.clearAnimation();
                    hiveProgressView.setVisibility(View.GONE);
                    Log.d("zmaTripDetails", response.toString());

                    Picasso.get().load(response.body().getData().getImage()).into(ivTripImage);
                    Picasso.get().load(response.body().getData().getUserPicture()).into(ivUser);
                    tvDuration.setText(response.body().getData().getDuration());
                    tvTitle.setText(response.body().getData().getTitle());
                    tvStartDate.setText(response.body().getData().getFromDate());
                    tvEndDate.setText(response.body().getData().getToDate());
                    tvStartTime.setText(response.body().getData().getTimeFrom());
                    tvEndTime.setText(response.body().getData().getTimeTo());
                    tvListedBy.setText(response.body().getData().getUsername());
                    tvLocation.setText(response.body().getData().getLocation());
                }
                else
                {
                    Log.d("zmaTrips", response.toString());
                    hiveProgressView.clearAnimation();
                    hiveProgressView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<TripDetailResponseModel> call, Throwable t) {
                hiveProgressView.clearAnimation();
                hiveProgressView.setVisibility(View.GONE);
            }
        });

    }

}
