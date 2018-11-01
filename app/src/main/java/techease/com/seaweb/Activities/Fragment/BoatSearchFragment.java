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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Activities.BottomActivity;
import techease.com.seaweb.Activities.Adapters.SearchedBoatsAdapter;
import techease.com.seaweb.Activities.Models.BoatTypeResponseModel;
import techease.com.seaweb.Activities.Models.SearchedBoatsDataModel;
import techease.com.seaweb.Activities.Models.SearchedBoatsResponseModel;
import techease.com.seaweb.Activities.Utils.AlertsUtils;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;

public class BoatSearchFragment extends Fragment {

    ImageView ivBack;
    RecyclerView recyclerView;
    List<SearchedBoatsDataModel> searchedBoatsDataModelList;
    SearchedBoatsAdapter adapter;
    android.support.v7.app.AlertDialog alertDialog;
    String type,location,startDate,endDate;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_boat_search, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        ivBack = view.findViewById(R.id.ivBackSearchedBoats);
        recyclerView = view.findViewById(R.id.rvSearchedBoats);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchedBoatsDataModelList = new ArrayList<>();

        type = sharedPreferences.getString("type","");
        location = sharedPreferences.getString("placeid","");
        startDate = sharedPreferences.getString("startdate","");
        endDate = sharedPreferences.getString("enddate","");


        if (alertDialog == null) {
            alertDialog = AlertsUtils.createProgressDialog(getActivity());
            alertDialog.show();
        }
        adapter = new SearchedBoatsAdapter(getActivity(),searchedBoatsDataModelList);
        recyclerView.setAdapter(adapter);
        apiCall();


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Fragment fragment = new BoatTypeFragment();
               getFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            }
        });


        return view;
    }

    private void apiCall() {
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Log.d("zmaParamsss",location+" "+type+" "+startDate+" "+endDate);
        Call<SearchedBoatsResponseModel> call = services.getSearchedBoats(location,type,startDate,endDate);
        call.enqueue(new Callback<SearchedBoatsResponseModel>() {
            @Override
            public void onResponse(Call<SearchedBoatsResponseModel> call, Response<SearchedBoatsResponseModel> response) {

                if (response.isSuccessful())
                {
                    if (alertDialog != null)
                        alertDialog.dismiss();
                    Log.d("zmaSearchedBoats",response.toString());
                    searchedBoatsDataModelList.addAll(response.body().getData()) ;
                    Toast.makeText(getActivity(), String.valueOf(searchedBoatsDataModelList.size()), Toast.LENGTH_SHORT).show();
                    if (searchedBoatsDataModelList.size()<1)
                    {
                        Toast.makeText(getActivity(), "No Suggestion", Toast.LENGTH_SHORT).show();
                    }

                    adapter.notifyDataSetChanged();


                }
                else
                {
                    if (alertDialog != null)
                        alertDialog.dismiss();
                    Toast.makeText(getActivity(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SearchedBoatsResponseModel> call, Throwable t) {
                if (alertDialog != null)
                    alertDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}
