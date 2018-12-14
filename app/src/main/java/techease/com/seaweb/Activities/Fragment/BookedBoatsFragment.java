package techease.com.seaweb.Activities.Fragment;

import android.content.Context;
import android.content.Intent;
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
import techease.com.seaweb.Activities.Activities.BottomActivity;
import techease.com.seaweb.Activities.Adapters.BookedBoatsAdapter;
import techease.com.seaweb.Activities.Adapters.FavrtAdapter;
import techease.com.seaweb.Activities.Models.BookedBoatsDataModel;
import techease.com.seaweb.Activities.Models.BookedBoatsResponseModel;
import techease.com.seaweb.Activities.Models.FavrtResponseModel;
import techease.com.seaweb.Activities.Utils.AlertsUtils;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;


public class BookedBoatsFragment extends Fragment {

    RecyclerView recyclerView;
    List<BookedBoatsDataModel> bookedBoatsDataModelList;
    BookedBoatsAdapter adapter;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userId;
    HiveProgressView hiveProgressView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booked_boats, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userId=sharedPreferences.getString("userid","");
        hiveProgressView = view.findViewById(R.id.progressBookedBoat);
        recyclerView=view.findViewById(R.id.rvBooking);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bookedBoatsDataModelList = new ArrayList<>();

        hiveProgressView.setVisibility(View.VISIBLE);
        adapter=new BookedBoatsAdapter(getActivity(),bookedBoatsDataModelList);
        recyclerView.setAdapter(adapter);
        apiCall();


        return view;
    }

    private void apiCall() {

        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<BookedBoatsResponseModel> call = services.getBookedBoats(userId,"Boat");
        call.enqueue(new Callback<BookedBoatsResponseModel>() {
            @Override
            public void onResponse(Call<BookedBoatsResponseModel> call, Response<BookedBoatsResponseModel> response) {

                hiveProgressView.setVisibility(View.GONE);
                if (response.isSuccessful())
                {

                    Log.d("zmaBookedBoats",response.toString());


                    if (response.body().getData() !=null)
                    {
                        bookedBoatsDataModelList.addAll(response.body().getData());

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "NO BOOKED BOATS", Toast.LENGTH_SHORT).show();
                    }

                        adapter.notifyDataSetChanged();


                }
                else

                {
                    Log.d("zmaBookedBoats",response.toString());

                }
            }

            @Override
            public void onFailure(Call<BookedBoatsResponseModel> call, Throwable t) {

                Log.d("zmaBookedBoats",t.getCause().toString());
                hiveProgressView.setVisibility(View.GONE);
            }
        });
    }


}
