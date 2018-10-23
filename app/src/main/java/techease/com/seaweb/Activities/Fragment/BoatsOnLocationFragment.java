package techease.com.seaweb.Activities.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Activities.BottomActivity;
import techease.com.seaweb.Activities.Adapters.BoatsOnLocationAdapter;
import techease.com.seaweb.Activities.Models.BoatsOnLocationDataModel;
import techease.com.seaweb.Activities.Models.BoatsOnLocationResponseModel;
import techease.com.seaweb.Activities.Utils.AlertsUtils;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;


public class BoatsOnLocationFragment extends Fragment {

    RecyclerView recyclerView;
    ImageView ivback;
    List<BoatsOnLocationDataModel> boatsOnLocationDataModels;
    BoatsOnLocationAdapter boatsOnLocationAdapter;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String locId;
    int userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_boats_on_location, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        ivback=view.findViewById(R.id.ivBackListofBoats);
        recyclerView=view.findViewById(R.id.rvBoatsOnLocation);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        boatsOnLocationDataModels=new ArrayList<>();

        locId=getArguments().getString("placeid");
        userId=sharedPreferences.getInt("userid",0);
        if (alertDialog == null) {
            alertDialog = AlertsUtils.createProgressDialog(getActivity());
            alertDialog.show();
        }
        apiCall();



        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), BottomActivity.class));
                getActivity().finish();

            }
        });

        return view;
    }

    private void apiCall() {
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<BoatsOnLocationResponseModel> call = services.boatsOnLocation(locId,String.valueOf(userId));
        call.enqueue(new Callback<BoatsOnLocationResponseModel>() {
            @Override
            public void onResponse(Call<BoatsOnLocationResponseModel> call, Response<BoatsOnLocationResponseModel> response) {

                if (response.isSuccessful())
                {
                    if (alertDialog != null)
                        alertDialog.dismiss();
                    Log.d("zmaPlaceBoats",response.toString());

                    boatsOnLocationDataModels.addAll(response.body().getData());

                    boatsOnLocationAdapter=new BoatsOnLocationAdapter(getActivity(),boatsOnLocationDataModels);
                    recyclerView.setAdapter(boatsOnLocationAdapter);
                    boatsOnLocationAdapter.notifyDataSetChanged();

                }
                else
                {
                    if (alertDialog != null)
                        alertDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<BoatsOnLocationResponseModel> call, Throwable t) {
                if (alertDialog != null)
                    alertDialog.dismiss();
            }
        });
    }


}