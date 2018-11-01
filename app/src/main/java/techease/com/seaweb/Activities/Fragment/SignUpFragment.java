package techease.com.seaweb.Activities.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Activities.BottomActivity;
import techease.com.seaweb.Activities.Activities.LoginSignupActivity;
import techease.com.seaweb.Activities.Models.RegisterResponseModel;
import techease.com.seaweb.Activities.Utils.AlertsUtils;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;


public class SignUpFragment extends Fragment implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String device_id;
    ImageView ivback;
    TextView tvOwner,tvUser,tvAlreadyHaveAnAccount;
    Button btnRegister;
    String type;
    EditText etfname,etLname,etArea,etCpass,etPass,etEmail;
    String email,fname,lname,area,pass,fullName,token,cpass;
    String user_id;
    android.support.v7.app.AlertDialog alertDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);


        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        device_id=sharedPreferences.getString("device-id","");

        ivback=view.findViewById(R.id.ivBackSignUp);
        tvOwner=view.findViewById(R.id.tvOwner);
        tvUser=view.findViewById(R.id.tvUser);
        tvAlreadyHaveAnAccount=view.findViewById(R.id.tvGotoLogin);
        btnRegister=view.findViewById(R.id.btnSignUp);
        etArea=view.findViewById(R.id.etAreaname);
        etfname=view.findViewById(R.id.etFirstname);
        etLname=view.findViewById(R.id.etLastname);
        etEmail=view.findViewById(R.id.etEmail);
        etCpass=view.findViewById(R.id.etCpass);
        etPass=view.findViewById(R.id.etPass);

        tvOwner.setOnClickListener(this);
        tvUser.setOnClickListener(this);
        tvAlreadyHaveAnAccount.setOnClickListener(this);
        btnRegister.setOnClickListener(this);


        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), LoginSignupActivity.class));
                getActivity().finish();
            }
        });

        return view;
    }


    @Override
    public void onClick(View v) {
        int id=v.getId();


        switch (id)
        {
            case R.id.tvOwner:
                type="Owner";
                break;
            case R.id.tvUser:
                type="User";
                break;
            case R.id.tvGotoLogin:
             Fragment fragment=new LoginFragment();
             getFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
             break;
            case R.id.btnSignUp:

                area=etArea.getText().toString();
                fname=etfname.getText().toString();
                lname=etLname.getText().toString();
                pass=etPass.getText().toString();
                email=etEmail.getText().toString();
                cpass=etCpass.getText().toString();

                if (area.isEmpty())
                {
                    etArea.setError("Please fill this field");
                }
                else
                if (fname.isEmpty())
                {
                    etfname.setError("Please fill this field");
                }
                else
                if (lname.isEmpty())
                {
                    etLname.setError("Please fill this field");
                }
                else
                if (email.isEmpty() || !email.contains("@") && !email.contains(".com"))
                {
                    etEmail.setError("Please enter the valid email");
                }
                else
                if (pass.isEmpty() ||  !pass.equals(cpass))
                {
                    etCpass.setError("Password does not match");
                }
                else
                if (cpass.isEmpty() || !cpass.equals(pass))
                {
                    etCpass.setError("Password does not match");
                }
                else{

                    if (alertDialog == null) {
                        alertDialog = AlertsUtils.createProgressDialog(getActivity());
                        alertDialog.show();
                    }
                    apiCall();
                }



        }

    }

    private void apiCall() {
        Log.d("zmaRegData",email+fname+lname+type+area+pass+device_id);
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<RegisterResponseModel> call = services.register(email,fname,lname,type,area,pass,device_id);
        call.enqueue(new Callback<RegisterResponseModel>() {
            @Override
            public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {

                if (response.isSuccessful())
                {
                    if (alertDialog != null)
                    alertDialog.dismiss();
                    Log.d("zmaRegisterResp",response.toString());

                    fullName=response.body().getUser().getFullName();
                    email=response.body().getUser().getEmail();
                    token=response.body().getUser().getToken();
                    user_id=response.body().getUser().getUserId().toString();

                    Toast.makeText(getActivity(), email, Toast.LENGTH_SHORT).show();

                    editor.putString("username",fullName).commit();
                    editor.putString("email",email).commit();
                    editor.putString("userid",user_id).commit();
                    editor.putString("token",token).commit();
                    editor.putString("device_id",device_id).commit();
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
            public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
                if (alertDialog != null)
                    alertDialog.dismiss();
            }
        });

    }
}
