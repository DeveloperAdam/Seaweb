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
import techease.com.seaweb.Activities.Fragment.Account.ForgotPassFragment;
import techease.com.seaweb.Activities.Fragment.Account.ResetPassFragment;
import techease.com.seaweb.Activities.Models.CodeResponseModel;
import techease.com.seaweb.Activities.Utils.AlertsUtils;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;


public class CodeFragment extends Fragment {

    EditText etCode;
    Button btnVerify;
    String code,message;
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
        View view = inflater.inflate(R.layout.fragment_code, container, false);


        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        hiveProgressView = view.findViewById(R.id.progressCode);
        ivBack=view.findViewById(R.id.ivBackCode);
        etCode=view.findViewById(R.id.etCode);
        btnVerify=view.findViewById(R.id.btnVerify);
        alertDialog = null;


        hiveProgressView.setVisibility(View.GONE);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment=new ForgotPassFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                getFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            }
        });
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                code=etCode.getText().toString();

                if (code.isEmpty())
                {
                    etCode.setError("Please enter the code");
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
        Log.d("zmaForgotData",code);
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<CodeResponseModel> call = services.checkCode(code);
        call.enqueue(new Callback<CodeResponseModel>() {
            @Override
            public void onResponse(Call<CodeResponseModel> call, Response<CodeResponseModel> response) {

                if (response.isSuccessful())
                {
                    hiveProgressView.setVisibility(View.GONE);
                    Log.d("zmaForgotResp",response.toString());

                    message=response.body().getMessage();
                    success = response.body().getSuccess();
                    if (success ==  true)
                    {

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        editor.putString("code",etCode.getText().toString()).commit();
                        Fragment fragment = new ResetPassFragment();
                        fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                        fragment.setExitTransition(new Slide(Gravity.LEFT));
                        getFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack("back").commit();
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
            public void onFailure(Call<CodeResponseModel> call, Throwable t) {
                hiveProgressView.setVisibility(View.GONE);
            }
        });

    }
}
