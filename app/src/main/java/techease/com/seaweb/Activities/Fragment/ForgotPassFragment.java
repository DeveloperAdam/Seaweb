package techease.com.seaweb.Activities.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
    String email;
    ImageView ivBack;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_pass, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        ivBack=view.findViewById(R.id.ivbackForgot);
        etEmail=view.findViewById(R.id.etEmailForgot);
        btnSentCode=view.findViewById(R.id.btnSentCode);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment=new LoginFragment();
                getFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
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
                    if (alertDialog == null) {
                        alertDialog = AlertsUtils.createProgressDialog(getActivity());
                        alertDialog.show();
                    }
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
                                    if (alertDialog != null)
                    alertDialog.dismiss();
                    Log.d("zmaForgotResp",response.toString());

                    email=response.body().getMessage();

                    Fragment fragment=new CodeFragment();
                    getFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();


                }
                else
                {
                                    if (alertDialog != null)
                    alertDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ForgotPassResponseModel> call, Throwable t) {
                if (alertDialog != null)
                    alertDialog.dismiss();
            }
        });

    }

}
