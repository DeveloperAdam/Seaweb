package techease.com.seaweb.Activities.Fragment.Trips;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.comix.overwatch.HiveProgressView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Adapters.TripsAdapter.TripListAdapter;
import techease.com.seaweb.Activities.Models.Trip.AllTripsDataModel;
import techease.com.seaweb.Activities.Models.Trip.AllTripsResponseModel;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;


public class TripsFragment extends Fragment {

    RecyclerView recyclerView;
    List<AllTripsDataModel> tripListModels;
    TripListAdapter tripListAdapter;
    HiveProgressView hiveProgressView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trips, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        hiveProgressView = view.findViewById(R.id.progressTrip);
        recyclerView = view.findViewById(R.id.rvTrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tripListModels = new ArrayList<>();
        tripListAdapter = new TripListAdapter(getActivity(),tripListModels);
        recyclerView.setAdapter(tripListAdapter);
        hiveProgressView.showContextMenu();
        apicall();


        return view;
    }

    private void apicall() {
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<AllTripsResponseModel> call = services.getAllTrips();
        call.enqueue(new Callback<AllTripsResponseModel>() {
            @Override
            public void onResponse(Call<AllTripsResponseModel> call, Response<AllTripsResponseModel> response) {

                if (response.isSuccessful()) {
                    hiveProgressView.clearAnimation();
                    hiveProgressView.setVisibility(View.GONE);
                    Log.d("zmaTrips", response.toString());

                    tripListModels.addAll(response.body().getData());
                    tripListAdapter.notifyDataSetChanged();
                }
                else
                {
                    Log.d("zmaTrips", response.toString());
                    hiveProgressView.clearAnimation();
                    hiveProgressView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<AllTripsResponseModel> call, Throwable t) {
                hiveProgressView.clearAnimation();
                hiveProgressView.setVisibility(View.GONE);
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();

        tripListAdapter.notifyDataSetChanged();
    }
}
