package techease.com.seaweb.Activities.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Activities.BottomActivity;
import techease.com.seaweb.Activities.Adapters.BoatsOnLocationAdapter;
import techease.com.seaweb.Activities.Adapters.GetAllPlacesAdapter;
import techease.com.seaweb.Activities.Models.BoatsOnLocationDataModel;
import techease.com.seaweb.Activities.Models.BoatsOnLocationResponseModel;
import techease.com.seaweb.Activities.Models.GetAllPlacesDataModel;
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
    String userId;
    AutoCompleteTextView etSearch;

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
        etSearch = view.findViewById(R.id.svBoatsList);
        etSearch.setSelection(0);
        ivback=view.findViewById(R.id.ivBackListofBoats);
        recyclerView=view.findViewById(R.id.rvBoatsOnLocation);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        boatsOnLocationDataModels=new ArrayList<>();


        locId=sharedPreferences.getString("placeid","");
        userId=sharedPreferences.getString("userid","");
        if (alertDialog == null) {
            alertDialog = AlertsUtils.createProgressDialog(getActivity());
            alertDialog.show();
        }

        boatsOnLocationAdapter=new BoatsOnLocationAdapter(getActivity(),boatsOnLocationDataModels);
        recyclerView.setAdapter(boatsOnLocationAdapter);

        apiCall();



        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), BottomActivity.class));
                getActivity().finish();

            }
        });


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();
                List<BoatsOnLocationDataModel> newData = new ArrayList<>();
                for (int j = 0; j < boatsOnLocationDataModels.size(); j++) {
                    final String test2 = boatsOnLocationDataModels.get(j).getTitle().toLowerCase();
                    if (test2.startsWith(String.valueOf(query))) {
                        newData.add(boatsOnLocationDataModels.get(j));
                        Toast.makeText(getActivity(), "aya", Toast.LENGTH_SHORT).show();
                    }
                }
                boatsOnLocationAdapter = new BoatsOnLocationAdapter(getActivity(), newData);
                ListOfPlacesFragment.recyclerView.setAdapter(boatsOnLocationAdapter);
                boatsOnLocationAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private void apiCall() {
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<BoatsOnLocationResponseModel> call = services.boatsOnLocation(locId,userId);
        call.enqueue(new Callback<BoatsOnLocationResponseModel>() {
            @Override
            public void onResponse(Call<BoatsOnLocationResponseModel> call, Response<BoatsOnLocationResponseModel> response) {

                if (response.isSuccessful())
                {
                    if (alertDialog != null)
                        alertDialog.dismiss();
                    Log.d("zmaPlaceBoats",response.toString());

                    if (response.body().getData() != null)
                    {
                        boatsOnLocationDataModels.addAll(response.body().getData());
                    }


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
