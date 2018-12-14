package techease.com.seaweb.Activities.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Adapters.BoatTypesAdapter;
import techease.com.seaweb.Activities.Models.BoatTypeDataModel;
import techease.com.seaweb.Activities.Models.BoatTypeResponseModel;
import techease.com.seaweb.Activities.Utils.AlertsUtils;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;

public class BoatTypeFragment extends Fragment {

    Button btnSave;
    ImageView ivBack;
    RecyclerView recyclerView;
    List<BoatTypeDataModel> boatTypeDataModelList;
    BoatTypesAdapter adapter;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_boat_type, container, false);

        alertDialog = null;
        recyclerView = view.findViewById(R.id.rvBoatTypes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        boatTypeDataModelList = new ArrayList<>();
        ivBack = view.findViewById(R.id.ivBackBoatTypes);
        btnSave = view.findViewById(R.id.btnSaveBoatType);

        if (alertDialog == null) {
            alertDialog = AlertsUtils.createProgressDialog(getActivity());
            alertDialog.show();
        }
        adapter=new BoatTypesAdapter(getActivity(),boatTypeDataModelList);
        recyclerView.setAdapter(adapter);
        apiCall();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (BoatTypesAdapter.boatType != null)
                {
                    Fragment fragment = new DatePickerFilterFragment();
                    fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                    fragment.setExitTransition(new Slide(Gravity.LEFT));
                    getFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack("back").commit();
                }
                else
                {
                    Toast.makeText(getActivity(), "Please select one of the boat type", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Fragment  fragment = new SearchFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
               getFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            }
        });


        return view;
    }
    private void apiCall() {
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<BoatTypeResponseModel> call = services.getBoatTypes();
        call.enqueue(new Callback<BoatTypeResponseModel>() {
            @Override
            public void onResponse(Call<BoatTypeResponseModel> call, Response<BoatTypeResponseModel> response) {

                if (response.isSuccessful())
                {
                    if (alertDialog != null)
                        alertDialog.dismiss();
                    alertDialog = null;
                    Log.d("zmaFavrtBoats",response.toString());
                    boatTypeDataModelList.addAll(response.body().getData()) ;
                    adapter.notifyDataSetChanged();


                }
                else

                {
                    if (alertDialog != null)
                        alertDialog.dismiss();
                    alertDialog = null;
                }

            }

            @Override
            public void onFailure(Call<BoatTypeResponseModel> call, Throwable t) {
                if (alertDialog != null)
                    alertDialog.dismiss();
                alertDialog = null;

            }
        });
    }
}
