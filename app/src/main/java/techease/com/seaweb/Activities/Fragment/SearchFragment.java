package techease.com.seaweb.Activities.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.comix.overwatch.HiveProgressView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import techease.com.seaweb.Activities.Activities.BottomActivity;
import techease.com.seaweb.Activities.Activities.FullscreenActivity;
import techease.com.seaweb.Activities.Adapters.GetAllPlacesAdapter;
import techease.com.seaweb.Activities.Adapters.PlacesAdapter;
import techease.com.seaweb.Activities.Adapters.SuggestedPlaceAdapter;
import techease.com.seaweb.Activities.Models.BoatOnLocationModel;
import techease.com.seaweb.Activities.Models.GetAllPlacesDataModel;
import techease.com.seaweb.Activities.Models.SuggestedPlaceModel;
import techease.com.seaweb.R;


public class SearchFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener{

    ImageView ivBack;
    RecyclerView recyclerView;
    List<SuggestedPlaceModel> suggestedPlaceModels;
    SuggestedPlaceAdapter suggestedPlaceAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    HiveProgressView hiveProgressView;
    PlacesAdapter placesAdapter;
    GoogleApiClient googleApiClient;
    AutoCompleteTextView etSearch;
    private static final LatLngBounds latlng = new LatLngBounds( new LatLng(-40,-168),new LatLng(71,136));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        googleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(),this)
                .build();

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        ivBack = view.findViewById(R.id.ivCloseSearch);
        etSearch = view.findViewById(R.id.search);
        etSearch.setSelection(0);
        placesAdapter = new PlacesAdapter(getActivity(), googleApiClient,latlng,null);
       // etSearch.setAdapter(placesAdapter);
        hiveProgressView = view.findViewById(R.id.progressSuggested);
        recyclerView = view.findViewById(R.id.rvSuggested);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        suggestedPlaceModels = new ArrayList<>();
        hiveProgressView.showContextMenu();
        suggestedPlaceAdapter = new SuggestedPlaceAdapter(getActivity(),suggestedPlaceModels);
        recyclerView.setAdapter(suggestedPlaceAdapter);

        apicall();


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(), BottomActivity.class);
                getActivity().overridePendingTransition(R.animator.slide_out_up,R.animator.slide_in_up);
                getActivity().startActivity(intent);
                getActivity().finish();

            }
        });



        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                View decorView = getActivity().getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN;
                decorView.setSystemUiVisibility(uiOptions);


            }

            @Override
            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();
                List<SuggestedPlaceModel> newData = new ArrayList<>();
                for (int j = 0; j < suggestedPlaceModels.size(); j++) {
                    final String test2 = suggestedPlaceModels.get(j).getName().toLowerCase();
                    if (test2.startsWith(String.valueOf(query))) {
                        newData.add(suggestedPlaceModels.get(j));
                    }
                }
                suggestedPlaceAdapter = new SuggestedPlaceAdapter(getActivity(), newData);
                recyclerView.setAdapter(suggestedPlaceAdapter);
                suggestedPlaceAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return view;
    }

    private void apicall() {
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://divergense.com/boat/App/getSuggestionLocations"
                , new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hiveProgressView.clearAnimation();
                hiveProgressView.setVisibility(View.GONE);

                Log.d("zmaBonLocImg", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i<jsonArray.length(); i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        SuggestedPlaceModel model = new SuggestedPlaceModel();
                        model.setId(object.getString("location_id"));
                        model.setName(object.getString("location_name"));
                        suggestedPlaceModels.add(model);
                    }

                        suggestedPlaceAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    hiveProgressView.clearAnimation();
                    hiveProgressView.setVisibility(View.GONE);
                }

            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                hiveProgressView.clearAnimation();
                hiveProgressView.setVisibility(View.GONE);
                Log.d("error", String.valueOf(error.getCause()));
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                Log.d("zmaParm",params.toString());
                return params;
            }
        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }

    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(getActivity());
        googleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onResume() {
        super.onResume();

        suggestedPlaceAdapter.notifyDataSetChanged();
    }
}
