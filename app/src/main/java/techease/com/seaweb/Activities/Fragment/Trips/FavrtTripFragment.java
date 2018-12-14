package techease.com.seaweb.Activities.Fragment.Trips;

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
import android.widget.Toast;

import com.comix.overwatch.HiveProgressView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Adapters.TripsAdapter.FavrtTripsAdapter;
import techease.com.seaweb.Activities.Models.FavrtResponseModel;
import techease.com.seaweb.Activities.Models.Trip.FavrtTripsDataModel;
import techease.com.seaweb.Activities.Models.Trip.FavrtTripsResponseModel;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;


public class FavrtTripFragment extends Fragment {

    RecyclerView recyclerView;
    List<FavrtTripsDataModel> favrtTripsDataModels;
    FavrtTripsAdapter adapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    HiveProgressView hiveProgressView;
    String userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favrt_trip, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        hiveProgressView = view.findViewById(R.id.progressFvrtTrips);
        userId=sharedPreferences.getString("userid","");
        recyclerView = view.findViewById(R.id.rvFvrtTrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        favrtTripsDataModels = new ArrayList<>();
        adapter = new FavrtTripsAdapter(getActivity(),favrtTripsDataModels);
        recyclerView.setAdapter(adapter);

        hiveProgressView.setVisibility(View.VISIBLE);
        apiCall();


        return view;
    }

    private void apiCall() {
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<FavrtTripsResponseModel> call = services.getFavrtTrips(String.valueOf(userId));
        call.enqueue(new Callback<FavrtTripsResponseModel>() {
            @Override
            public void onResponse(Call<FavrtTripsResponseModel> call, Response<FavrtTripsResponseModel> response) {

                hiveProgressView.setVisibility(View.GONE);
                if (response.isSuccessful())
                {
                    Log.d("zmaFavrtTrips",response.toString());


                    if(response.body().getData() != null)
                    {
                        favrtTripsDataModels.addAll(response.body().getData());
                        if (favrtTripsDataModels.size()>=0)
                        {

                        }
                        else
                        {
                            Toast.makeText(getActivity(), "No", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "No Favourite Trips", Toast.LENGTH_SHORT).show();
                    }

                    adapter.notifyDataSetChanged();



                }
                else

                {

                }
            }

            @Override
            public void onFailure(Call<FavrtTripsResponseModel> call, Throwable t) {
                hiveProgressView.setVisibility(View.GONE);
            }
        });
    }

}
