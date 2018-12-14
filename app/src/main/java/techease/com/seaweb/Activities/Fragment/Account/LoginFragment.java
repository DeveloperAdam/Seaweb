package techease.com.seaweb.Activities.Fragment.Account;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.comix.overwatch.HiveProgressView;

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
    String pass,email,fullName,token,message;
    String user_id;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    HiveProgressView hiveProgressView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_login, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        setupWindowAnimations();
        alertDialog = null;



        hiveProgressView = view.findViewById(R.id.progressLogin);
        tvForgetPass=view.findViewById(R.id.tvForgotPass);
        tvGotoSignUp=view.findViewById(R.id.tvGotoSignUp);
        btnsignIn=view.findViewById(R.id.btnSignIn);
        etEmail=view.findViewById(R.id.etEmailLogin);
        etPass=view.findViewById(R.id.etPassLogin);
        ivBack=view.findViewById(R.id.ivBack);


        hiveProgressView.setVisibility(View.GONE);


        tvGotoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           Fragment fragment=new SignUpFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
           getFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack("back").commit();

            }
        });

        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View sharedView = tvForgetPass;
                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), sharedView, "fade");


                Fragment fragment=new ForgotPassFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                getFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack("back").commit();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(),LoginSignupActivity.class));
                getActivity().overridePendingTransition(R.animator.fade_out,R.animator.fade_in);
                getActivity().finish();
            }
        });

        btnsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=etEmail.getText().toString();
                pass=etPass.getText().toString();


                if (email.isEmpty() || !email.contains("@")|| email.length() < 6)
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
                        hiveProgressView.setVisibility(View.VISIBLE);
                        apiCall();
                    }
            }
        });
        return view;
    }

    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setEnterTransition(fade);
        }
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
                    hiveProgressView.setVisibility(View.GONE);
                    Log.d("zmaLoginResp",response.toString());
                    message = response.body().getMessage().toString();
                    if (message.equals("Logged in"))
                    {
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
                        getActivity().overridePendingTransition(R.animator.fade_out,R.animator.fade_in);
                        getActivity().finish();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    hiveProgressView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                hiveProgressView.setVisibility(View.GONE);
                Log.d("zmaLoginexp",t.getMessage());
            }
        });

    }
}
