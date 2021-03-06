package techease.com.seaweb.Activities.Fragment.Account;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.comix.overwatch.HiveProgressView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Models.ForgotPassResponseModel;
import techease.com.seaweb.Activities.Utils.AlertsUtils;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;


public class ForgotPassFragment extends Fragment {

    EditText etEmail;
    Button btnSentCode;
    String email,message;
    boolean success;
    ImageView ivBack;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    HiveProgressView hiveProgressView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_pass, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        alertDialog = null;

        hiveProgressView = view.findViewById(R.id.progressForgot);
        ivBack=view.findViewById(R.id.ivbackForgot);
        etEmail=view.findViewById(R.id.etEmailForgot);
        btnSentCode=view.findViewById(R.id.btnSentCode);

        hiveProgressView.setVisibility(View.GONE);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment=new LoginFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                getFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack("back").commit();
            }
        });

        btnSentCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=etEmail.getText().toString();


                if (email.isEmpty() || !email.contains("@") || !email.contains(".com"))
                {
                    etEmail.setError("Please enter the valid email");
                }
                else
                {
                   hiveProgressView.setVisibility(View.VISIBLE);
                    apiCall();
                }
            }
        });

        return view;
    }

    private void apiCall() {
        Log.d("zmaForgotData",email);
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<ForgotPassResponseModel> call = services.forgot(email);
        call.enqueue(new Callback<ForgotPassResponseModel>() {
            @Override
            public void onResponse(Call<ForgotPassResponseModel> call, Response<ForgotPassResponseModel> response) {

                if (response.isSuccessful())
                {
                    hiveProgressView.setVisibility(View.GONE);
                    Log.d("zmaForgotResp",response.toString());

                    success = response.body().getSuccess();
                    if (success==true)
                    {
                        email=response.body().getMessage();

                        Fragment fragment=new CodeFragment();
                        fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                        fragment.setExitTransition(new Slide(Gravity.LEFT));
                        getFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack("back").commit();
                    }
                    else
                    {
                        message = response.body().getMessage().toString();
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }




                }
                else
                {
                    hiveProgressView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ForgotPassResponseModel> call, Throwable t) {
                hiveProgressView.setVisibility(View.GONE);
            }
        });

    }

}
