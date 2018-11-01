package techease.com.seaweb.Activities.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Adapters.GetAllPlacesAdapter;
import techease.com.seaweb.Activities.Models.GetAllPlacesDataModel;
import techease.com.seaweb.Activities.Models.GetAllPlacesResponseModel;
import techease.com.seaweb.Activities.Utils.AlertsUtils;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;


public class ListOfPlacesFragment extends Fragment {


    public  static RecyclerView recyclerView;
    List<GetAllPlacesDataModel> dataModelList;
    public  static GetAllPlacesAdapter getAllPlacesAdapter;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<String> svSuggestions;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listofplaces, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        recyclerView=view.findViewById(R.id.rvPlaces);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager.setStackFromEnd(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        dataModelList=new ArrayList<>();
        svSuggestions=new ArrayList<>();

//

        if (alertDialog == null) {
            alertDialog = AlertsUtils.createProgressDialog(getActivity());
            alertDialog.show();
        }
        apiCall();

        return view;

    }

    private void apiCall() {
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<GetAllPlacesResponseModel> call = services.getAllPlaces();
        call.enqueue(new Callback<GetAllPlacesResponseModel>() {
            @Override
            public void onResponse(Call<GetAllPlacesResponseModel> call, Response<GetAllPlacesResponseModel> response) {

                if (response.isSuccessful())
                {

                    Log.d("zmagetAllPlace",response.toString());

                    dataModelList.addAll(response.body().getData());
                    getAllPlacesAdapter=new GetAllPlacesAdapter(getActivity(),dataModelList);
                    recyclerView.setAdapter(getAllPlacesAdapter);
                    getAllPlacesAdapter.notifyDataSetChanged();
                    if (alertDialog != null)
                        alertDialog.dismiss();
                }
                else
                {
                    if (alertDialog != null)
                    alertDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<GetAllPlacesResponseModel> call, Throwable t) {
                if (alertDialog != null)
                    alertDialog.dismiss();
            }
        });
    }

}
