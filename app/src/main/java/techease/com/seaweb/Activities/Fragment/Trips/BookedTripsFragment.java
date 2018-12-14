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
import android.widget.Toast;

import com.comix.overwatch.HiveProgressView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Adapters.TripsAdapter.BookedTripsAdapter;
import techease.com.seaweb.Activities.Models.BookedBoatsResponseModel;
import techease.com.seaweb.Activities.Models.GetAllPlacesResponseModel;
import techease.com.seaweb.Activities.Models.Trip.BookedTripsDataModel;
import techease.com.seaweb.Activities.Models.Trip.BookedTripsResponseModel;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;


public class BookedTripsFragment extends Fragment {

    RecyclerView recyclerView;
    List<BookedTripsDataModel> tripsDataModels;
    BookedTripsAdapter adapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    HiveProgressView hiveProgressView;
    String userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booked_trips, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userId = sharedPreferences.getString("userid","");
        hiveProgressView = view.findViewById(R.id.progressBookedTrips);
        recyclerView = view.findViewById(R.id.rvBookedTrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        tripsDataModels = new ArrayList<>();
        adapter = new BookedTripsAdapter(getActivity(),tripsDataModels);
        recyclerView.setAdapter(adapter);

        hiveProgressView.setVisibility(View.VISIBLE);
        apiCall();
        return view;
    }


    private void apiCall() {
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<BookedTripsResponseModel> call = services.getBookedTrips(userId,"Trip");
        call.enqueue(new Callback<BookedTripsResponseModel>() {
            @Override
            public void onResponse(Call<BookedTripsResponseModel> call, Response<BookedTripsResponseModel> response) {

                hiveProgressView.setVisibility(View.GONE);
                if (response.isSuccessful())
                {

                    Log.d("zmaBookedTrips",response.toString());


                    if (response.body().getData() !=null)
                    {
                        tripsDataModels.addAll(response.body().getData());

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "No Booked Trips", Toast.LENGTH_SHORT).show();
                    }

                    adapter.notifyDataSetChanged();


                }
                else

                {
                    Log.d("zmaBookedBoats",response.toString());

                    hiveProgressView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<BookedTripsResponseModel> call, Throwable t) {

                Log.d("zmaBookedBoats",t.getCause().toString());
                hiveProgressView.setVisibility(View.GONE);
            }
        });
    }


}
