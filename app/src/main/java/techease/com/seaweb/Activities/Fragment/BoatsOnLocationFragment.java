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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.comix.overwatch.HiveProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import techease.com.seaweb.Activities.Activities.BottomActivity;
import techease.com.seaweb.Activities.Adapters.BoatsOnLocationAdapter;
import techease.com.seaweb.Activities.Models.BoatOnLocationModel;
import techease.com.seaweb.R;


public class BoatsOnLocationFragment extends Fragment {

    RecyclerView recyclerView;
    ImageView ivback;
    BoatsOnLocationAdapter boatsOnLocationAdapter;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String locId;
    String userId;
    HiveProgressView hiveProgressView;
    AutoCompleteTextView etSearch;
    List<BoatOnLocationModel> boatOnLocationModel;
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

        alertDialog = null;
        hiveProgressView = view.findViewById(R.id.progress2);
        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        etSearch = view.findViewById(R.id.svBoatsList);
        etSearch.setSelection(0);
        ivback=view.findViewById(R.id.ivBackListofBoats);
        recyclerView=view.findViewById(R.id.rvBoatsOnLocation);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        boatOnLocationModel = new ArrayList<>();

        locId=sharedPreferences.getString("placeid","");
        userId=sharedPreferences.getString("userid","");

        hiveProgressView.showContextMenu();
        boatsOnLocationAdapter=new BoatsOnLocationAdapter(getActivity(),boatOnLocationModel);
        recyclerView.setAdapter(boatsOnLocationAdapter);

        apiCall();



        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListOfPlacesFragment.searchFlag =  false;
                startActivity(new Intent(getActivity(), BottomActivity.class));
                getActivity().overridePendingTransition(R.animator.fade_out,R.animator.fade_in);
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
                List<BoatOnLocationModel> newData = new ArrayList<>();
                for (int j = 0; j < boatOnLocationModel.size(); j++) {
                    final String test2 = boatOnLocationModel.get(j).getTitle().toLowerCase();
                    if (test2.startsWith(String.valueOf(query))) {
                        newData.add(boatOnLocationModel.get(j));
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
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://divergense.com/boat/App/getAndroidBoatData"
                , new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hiveProgressView.setVisibility(View.GONE);

                Log.d("zmaBonLocImg", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i<jsonArray.length(); i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        BoatOnLocationModel model = new BoatOnLocationModel();
                        model.setTitle(object.getString("title"));
                        model.setLocation(object.getString("location"));
                        model.setFullPrice(object.getString("fullday_price"));
                        model.setIs_fvrt(object.getString("is_favorite"));
                        model.setPid(object.getString("pid"));
                        model.setFile(object.getString("files"));
                        model.setUserImg(object.getString("user_picture"));
                        boatOnLocationModel.add(model);

                    }
                    boatsOnLocationAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                    if (alertDialog != null)
                        alertDialog.dismiss();
                    alertDialog = null;
                }

            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (alertDialog != null)
                    alertDialog.dismiss();
                alertDialog = null;
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
                params.put("location",locId);
                params.put("userid",userId);
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
    public void onResume() {
        super.onResume();
        boatsOnLocationAdapter.notifyDataSetChanged();
    }
}
