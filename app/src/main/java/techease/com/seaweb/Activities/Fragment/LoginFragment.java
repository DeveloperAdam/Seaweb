package techease.com.seaweb.Activities.Fragment;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Activities.BottomActivity;
import techease.com.seaweb.Activities.Activities.LoginSignupActivity;
import techease.com.seaweb.Activities.Models.LoginResponseModel;
import techease.com.seaweb.Activities.Utils.AlertsUtils;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;

public class LoginFragment extends Fragment {

    TextView tvForgetPass,tvGotoSignUp;
    Button btnsignIn;
    ImageView ivBack;
    EditText etEmail,etPass;
    String pass,email,fullName,token;
    String user_id;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        tvForgetPass=view.findViewById(R.id.tvForgotPass);
        tvGotoSignUp=view.findViewById(R.id.tvGotoSignUp);
        btnsignIn=view.findViewById(R.id.btnSignIn);
        etEmail=view.findViewById(R.id.etEmailLogin);
        etPass=view.findViewById(R.id.etPassLogin);
        ivBack=view.findViewById(R.id.ivBack);


        tvGotoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           Fragment fragment=new SignUpFragment();
           getFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();

            }
        });

        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment=new ForgotPassFragment();
                getFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), LoginSignupActivity.class));
                getActivity().finish();
            }
        });

        btnsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=etEmail.getText().toString();
                pass=etPass.getText().toString();


                if (email.isEmpty() || !email.contains("@") || !email.contains(".com"))
                {
                    etEmail.setError("please enter the valid email");
                }
                else
                    if (pass.isEmpty())
                {
                    etPass.setError("please fill this field");
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
        Log.d("zmaLoginData",email+pass);
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<LoginResponseModel> call = services.login(email,pass);
        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {

                if (response.isSuccessful())
                {
                                    if (alertDialog != null)
                    alertDialog.dismiss();
                    Log.d("zmaLoginResp",response.toString());

                    fullName=response.body().getUser().getFullName();
                    email=response.body().getUser().getEmail();
                    token=response.body().getUser().getToken();
                    user_id=response.body().getUser().getUserId().toString();



                    editor.putString("username",fullName).commit();
                    editor.putString("email",email).commit();
                    editor.putString("userid",user_id).commit();
                    editor.putString("token",token).commit();
                    editor.putString("login","login").commit();

                    startActivity(new Intent(getActivity(), BottomActivity.class));
                    getActivity().finish();
                }
                else
                {
                                    if (alertDialog != null)
                    alertDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                if (alertDialog != null)
                    alertDialog.dismiss();
            }
        });

    }
}
