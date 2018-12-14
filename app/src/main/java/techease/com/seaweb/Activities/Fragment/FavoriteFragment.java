package techease.com.seaweb.Activities.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.comix.overwatch.HiveProgressView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Activities.BottomActivity;
import techease.com.seaweb.Activities.Adapters.FavrtAdapter;
import techease.com.seaweb.Activities.Models.FavrtDataModel;
import techease.com.seaweb.Activities.Models.FavrtResponseModel;
import techease.com.seaweb.Activities.Utils.AlertsUtils;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;


public class FavoriteFragment extends Fragment {

    RecyclerView recyclerView;
    List<FavrtDataModel> dataModelList;
    FavrtAdapter favrtAdapter;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userId;
    TextView tvNoFvrtBoats;
    HiveProgressView hiveProgressView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userId=sharedPreferences.getString("userid","");
        Toast.makeText(getActivity(), userId, Toast.LENGTH_SHORT).show();
        tvNoFvrtBoats = view.findViewById(R.id.tvNofavrtboats);
        recyclerView=view.findViewById(R.id.rvFavrt);
        hiveProgressView = view.findViewById(R.id.progressFvrtBoat);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataModelList=new ArrayList<>();

        hiveProgressView.setVisibility(View.VISIBLE);
        favrtAdapter=new FavrtAdapter(getActivity(),dataModelList);
        recyclerView.setAdapter(favrtAdapter);
        apiCall();




        return view;
    }

    private void apiCall() {
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<FavrtResponseModel> call = services.favrtBooking(String.valueOf(userId));
        call.enqueue(new Callback<FavrtResponseModel>() {
            @Override
            public void onResponse(Call<FavrtResponseModel> call, Response<FavrtResponseModel> response) {

                hiveProgressView.setVisibility(View.GONE);
                if (response.isSuccessful())
                {

                    Log.d("zmaFavrtBoats",response.toString());

                    if(response.body().getData() != null)
                    {
                        dataModelList.addAll(response.body().getData());
                        if (dataModelList.size()>=0)
                        {

                        }
                        else
                        {
                            tvNoFvrtBoats.setVisibility(View.VISIBLE);
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "NO FAVOURITES", Toast.LENGTH_SHORT).show();
                    }

                    favrtAdapter.notifyDataSetChanged();



                }
                else

                {
                    hiveProgressView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<FavrtResponseModel> call, Throwable t) {
                hiveProgressView.setVisibility(View.GONE);
            }
        });
    }

}
