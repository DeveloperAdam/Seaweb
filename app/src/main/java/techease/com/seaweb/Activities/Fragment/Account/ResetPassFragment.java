package techease.com.seaweb.Activities.Fragment.Account;

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
import android.widget.Toast;

import com.comix.overwatch.HiveProgressView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Activities.LoginSignupActivity;
import techease.com.seaweb.Activities.Models.ResetPassResponseModel;
import techease.com.seaweb.Activities.Utils.AlertsUtils;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;


public class ResetPassFragment extends Fragment {

    EditText etNewPass,etCnewPass;
    Button btnReset;
    ImageView ivBack;
    String pass,cpass,code;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    HiveProgressView hiveProgressView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_pass, container, false);


        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        ivBack=view.findViewById(R.id.ivBackReset);
        code=sharedPreferences.getString("code","");
        etNewPass=view.findViewById(R.id.etNewPass);
        etCnewPass=view.findViewById(R.id.etCnewPass);
        btnReset=view.findViewById(R.id.btnReset);

        hiveProgressView = view.findViewById(R.id.progressReset);
        hiveProgressView.setVisibility(View.GONE);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Fragment fragment=new CodeFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
               getFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pass=etNewPass.getText().toString();
                cpass=etCnewPass.getText().toString();

                if (pass.isEmpty())
                {
                    etNewPass.setError("Please fill this field");
                }
                else
                    if (cpass.isEmpty() || !cpass.equals(pass))
                    {
                        etCnewPass.setError("Password does not match");
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
        Log.d("zmaResetData",code+cpass);
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<ResetPassResponseModel> call = services.resetPassword(code,pass);
        call.enqueue(new Callback<ResetPassResponseModel>() {
            @Override
            public void onResponse(Call<ResetPassResponseModel> call, Response<ResetPassResponseModel> response) {

                if (response.isSuccessful())
                {
                    hiveProgressView.setVisibility(View.GONE);
                    Log.d("zmaResetResp",response.toString());

                    code=response.body().getMessage();

                    Toast.makeText(getActivity(), code, Toast.LENGTH_SHORT).show();

                   startActivity(new Intent(getActivity(), LoginSignupActivity.class));
                    getActivity().overridePendingTransition(R.animator.fade_out,R.animator.fade_in);
                   getActivity().finish();
                }

                else

                {
                    hiveProgressView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResetPassResponseModel> call, Throwable t) {
                hiveProgressView.setVisibility(View.GONE);
            }
        });

    }
}
