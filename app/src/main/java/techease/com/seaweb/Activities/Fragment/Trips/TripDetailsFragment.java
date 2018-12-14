package techease.com.seaweb.Activities.Fragment.Trips;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.comix.overwatch.HiveProgressView;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Activities.FullscreenActivityTrip;
import techease.com.seaweb.Activities.Activities.TripBottomNavigationActivity;
import techease.com.seaweb.Activities.Models.Trip.MakeFavrtTripResponseModel;
import techease.com.seaweb.Activities.Models.Trip.TripDetailResponseModel;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;


public class TripDetailsFragment extends Fragment {

    ImageView ivBack,ivTripImage,ivUser,ivHeart;
    TextView tvTitle,tvDuration,tvLocation,tvStartDate,tvEndDate,tvStartTime,tvEndTime,tvListedBy,tvPriceList,tvSeats;
    HiveProgressView hiveProgressView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String id,userid,type = "trip";
    String isFvrt,message;
    Button btnBook;
    public static boolean bookNow,tripDetailPriceList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  =  inflater.inflate(R.layout.fragment_trip_details, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userid = sharedPreferences.getString("userid","");
        id = sharedPreferences.getString("tripid","");
        tvSeats = view.findViewById(R.id.tvRemainingSeats);
        btnBook = view.findViewById(R.id.btnBookNowTrip);
        ivHeart = view.findViewById(R.id.ivHeartTripDetails);
        tvPriceList = view.findViewById(R.id.tvPriceListTripDetails);
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

                Intent intent=new Intent(getActivity(), TripBottomNavigationActivity.class);
                getActivity().overridePendingTransition(R.animator.fade_out,R.animator.fade_in);
                getActivity().startActivity(intent);
                getActivity().finish();

            }
        });

        ivHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiveProgressView.showContextMenu();
                makeItFavrtApiCall();
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bookNow = true;
                Intent intent=new Intent(getActivity(), FullscreenActivityTrip.class);
                getActivity().overridePendingTransition(R.animator.fade_out,R.animator.fade_in);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
        tvPriceList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tripDetailPriceList = true;
                Intent intent=new Intent(getActivity(), FullscreenActivityTrip.class);
                getActivity().overridePendingTransition(R.animator.fade_out,R.animator.fade_in);
                getActivity().startActivity(intent);
                getActivity().finish();
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
                    isFvrt = response.body().getData().getIsFavorite();
                    tvSeats.setText(response.body().getData().getSeats());
                    editor.putString("tripid",response.body().getData().getPid().toString()).commit();
                    editor.putString("seats",tvSeats.getText().toString()).commit();
                    editor.putString("tfrom",tvStartTime.getText().toString()).commit();
                    editor.putString("tto",tvEndTime.getText().toString()).commit();
                    editor.putString("dfrom",tvStartDate.getText().toString()).commit();
                    editor.putString("dto",tvEndDate.getText().toString()).commit();
                    editor.putString("child",response.body().getData().getPriceChild()).commit();
                    editor.putString("adult",response.body().getData().getPriceAdult()).commit();
                    if (isFvrt.equals("true"))
                    {
                        ivHeart.setBackgroundResource(R.drawable.fillheart);
                    }
                    else
                    {
                        ivHeart.setBackgroundResource(R.drawable.white_heart);
                    }
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

    private void makeItFavrtApiCall() {
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<MakeFavrtTripResponseModel> call = services.makeItFavrt(id,userid,type);
        call.enqueue(new Callback<MakeFavrtTripResponseModel>() {
            @Override
            public void onResponse(Call<MakeFavrtTripResponseModel> call, Response<MakeFavrtTripResponseModel> response) {
                hiveProgressView.clearAnimation();
                hiveProgressView.setVisibility(View.GONE);
                if (response.isSuccessful())
                {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    message = response.body().getMessage();
                    if (message.contains("Added"))
                    {
                        ivHeart.setBackgroundResource(R.drawable.fillheart);
                    }
                    else
                    {
                        ivHeart.setBackgroundResource(R.drawable.white_heart);
                    }


                }
                else
                {
                    hiveProgressView.clearAnimation();
                    hiveProgressView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<MakeFavrtTripResponseModel> call, Throwable t) {

                hiveProgressView.clearAnimation();
                hiveProgressView.setVisibility(View.GONE);
            }
        });
    }

}
