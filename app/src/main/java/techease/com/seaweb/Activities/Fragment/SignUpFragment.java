package techease.com.seaweb.Activities.Fragment;

import android.content.Context;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    View view;
   // private RadioGroup radioSexGroup;
   // private RadioButton radioSexButton;
    ImageView ivback;
    TextView tvAlreadyHaveAnAccount;
    Button btnRegister;
    String type,message;
    EditText etfname,etLname,etArea,etCpass,etPass,etEmail;
    String email,fname,lname,area,pass,fullName,token,cpass;
    String user_id;
    android.support.v7.app.AlertDialog alertDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        alertDialog = null;
        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        device_id=sharedPreferences.getString("device-id","");


      //  radioSexGroup = view.findViewById(R.id.radioSex);
        ivback=view.findViewById(R.id.ivBackSignUp);
        tvAlreadyHaveAnAccount=view.findViewById(R.id.tvGotoLogin);
        btnRegister=view.findViewById(R.id.btnSignUp);
        etArea=view.findViewById(R.id.etAreaname);
        etfname=view.findViewById(R.id.etFirstname);
        etLname=view.findViewById(R.id.etLastname);
        etEmail=view.findViewById(R.id.etEmail);
        etCpass=view.findViewById(R.id.etCpass);
        etPass=view.findViewById(R.id.etPass);
        tvAlreadyHaveAnAccount.setOnClickListener(this);
        btnRegister.setOnClickListener(this);


        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Fragment fragment = new LoginFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
               getFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack("back").commit();
            }
        });

        return view;
    }


    @Override
    public void onClick(View v) {
        int id=v.getId();


        switch (id)
        {
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
                if (email.isEmpty() || !email.contains("@")  )
                {
                    etEmail.setError("Please enter the valid email");
                }
                else
                if (pass.isEmpty() || pass.length() < 6)
                {
                    etPass.setError("Please enter the valid password more than 6 characters");
                }
                else if (  !pass.equals(cpass) ) {
                        etCpass.setError("Password does not match");
                    }
                else
                if (cpass.isEmpty() || !cpass.equals(pass))
                {
                    etCpass.setError("Password does not match");
                }
                else{
                    type = "User";
                  //  int selectedId = radioSexGroup.getCheckedRadioButtonId();
                  //  radioSexButton = view.findViewById(selectedId);
                  //  type = radioSexButton.getText().toString();

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
                    alertDialog = null;
                    Log.d("zmaRegisterResp",response.toString());

                    message = response.body().getMessage();
                    if (message.equals("Registered Successfully"))
                    {
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
                    if (alertDialog != null)
                    alertDialog.dismiss();
                    alertDialog = null;
                }
            }

            @Override
            public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
                if (alertDialog != null)
                    alertDialog.dismiss();
                alertDialog = null;
            }
        });

    }
}
