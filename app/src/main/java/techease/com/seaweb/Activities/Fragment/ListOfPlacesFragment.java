package techease.com.seaweb.Activities.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Adapters.GetAllPlacesAdapter;
import techease.com.seaweb.Activities.Adapters.ListOfPlacesNamesAdapter;
import techease.com.seaweb.Activities.Adapters.PlacesAdapter;
import techease.com.seaweb.Activities.Models.GetAllPlacesDataModel;
import techease.com.seaweb.Activities.Models.GetAllPlacesResponseModel;
import techease.com.seaweb.Activities.Utils.AlertsUtils;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;


public class ListOfPlacesFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener{


    public  static RecyclerView recyclerView,rvNames;
    List<GetAllPlacesDataModel> dataModelList;
    public  static GetAllPlacesAdapter getAllPlacesAdapter;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<String> svSuggestions;
    ListOfPlacesNamesAdapter namesAdapter;
    AutoCompleteTextView etSearch;
    public static boolean searchFlag = false ;

    GoogleApiClient googleApiClient;
    PlacesAdapter placesAdapter;
    private static final LatLngBounds latlng = new LatLngBounds( new LatLng(-40,-168),new LatLng(71,136));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listofplaces, container, false);

//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        if (view != null) {
//            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }

        googleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(),this)
                .build();

        placesAdapter = new PlacesAdapter(getActivity(), googleApiClient,latlng,null);



        alertDialog = null;


        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        etSearch = view.findViewById(R.id.autoCompleteTextView1);
        etSearch.setSelection(0);
        recyclerView=view.findViewById(R.id.rvPlaces);
        rvNames=view.findViewById(R.id.rvListOfPlaces);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager.setStackFromEnd(false);
        recyclerView.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager2.setStackFromEnd(false);
        rvNames.setLayoutManager(linearLayoutManager2);


        dataModelList=new ArrayList<>();
        svSuggestions=new ArrayList<>();

        etSearch.setAdapter(placesAdapter);

        if (alertDialog == null) {
            alertDialog = AlertsUtils.createProgressDialog(getActivity());
            alertDialog.show();
        }
        getAllPlacesAdapter=new GetAllPlacesAdapter(getActivity(),dataModelList);
        recyclerView.setAdapter(getAllPlacesAdapter);

        namesAdapter = new ListOfPlacesNamesAdapter(getActivity(),dataModelList);
        rvNames.setAdapter(namesAdapter);
        apiCall();


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
                List<GetAllPlacesDataModel> newData = new ArrayList<>();
                for (int j = 0; j < dataModelList.size(); j++) {
                    final String test2 = dataModelList.get(j).getName().toLowerCase();
                    if (test2.startsWith(String.valueOf(query))) {
                        newData.add(dataModelList.get(j));
                        searchFlag = true;
                    }
                }
                ListOfPlacesFragment.getAllPlacesAdapter = new GetAllPlacesAdapter(getActivity(), newData);
                ListOfPlacesFragment.recyclerView.setAdapter(ListOfPlacesFragment.getAllPlacesAdapter);
                ListOfPlacesFragment.getAllPlacesAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;

    }
    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(getActivity());
        googleApiClient.disconnect();
    }

    private void apiCall() {
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<GetAllPlacesResponseModel> call = services.getAllPlaces();
        call.enqueue(new Callback<GetAllPlacesResponseModel>() {
            @Override
            public void onResponse(Call<GetAllPlacesResponseModel> call, Response<GetAllPlacesResponseModel> response) {

                if (response.isSuccessful())
                {
                    if (alertDialog != null)
                        alertDialog.dismiss();
                    alertDialog = null;
                    Log.d("zmagetAllPlace",response.toString());

                    dataModelList.addAll(response.body().getData());

                    getAllPlacesAdapter.notifyDataSetChanged();
                    namesAdapter.notifyDataSetChanged();
                    if (alertDialog != null)
                        alertDialog.dismiss();
                }
                else
                {
                    if (alertDialog != null)
                    alertDialog.dismiss();
                    alertDialog = null;
                }

            }

            @Override
            public void onFailure(Call<GetAllPlacesResponseModel> call, Throwable t) {
                if (alertDialog != null)
                    alertDialog.dismiss();
                alertDialog = null;
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
