package techease.com.seaweb.Activities.Fragment.Chatting;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.comix.overwatch.HiveProgressView;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Fragment.BoatDetailFragment;
import techease.com.seaweb.Activities.Models.BoatDetail.BoatDetailResponseModel;
import techease.com.seaweb.Activities.Models.Chat.PriceRequestResponseModel;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;

public class PriceRequestFragment extends Fragment {

    TextView tvAmount;
    Button btnSent;
    EditText etMesg;
    ImageView ivBack;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    HiveProgressView hiveProgressView;
    String user_id,pro_id,message,owner_id,price;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_price_request, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        user_id = sharedPreferences.getString("userid","");
        pro_id = sharedPreferences.getString("proid","");
        price = sharedPreferences.getString("perday","");
        owner_id = sharedPreferences.getString("ownerid","");
        ivBack = view.findViewById(R.id.ivBackPriceRequest);
        tvAmount = view.findViewById(R.id.tvAmountPriceRequest);
        btnSent = view.findViewById(R.id.btnSentRequest);
        etMesg = view.findViewById(R.id.etTextAreaPriceReq);
        hiveProgressView = view.findViewById(R.id.progressPriceRequest);

        hiveProgressView.setVisibility(View.GONE);
        tvAmount.setText(price+"$");

        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                message = etMesg.getText().toString();

                if (message.equals(""))
                {
                    etMesg.setError("Please type a message here");
                }
                else
                {
                    hiveProgressView.setVisibility(View.VISIBLE);
                    apicall();

                }
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new BoatDetailFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                getFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();

            }
        });

        return view;
    }

    public  void apicall()
    {
        Log.d("zmaPReqParams",price+"  "+pro_id+"  "+message+"  "+user_id);
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<PriceRequestResponseModel> call = services.sentRequest(user_id,message,owner_id,pro_id);
        call.enqueue(new Callback<PriceRequestResponseModel>() {
            @Override
            public void onResponse(Call<PriceRequestResponseModel> call, Response<PriceRequestResponseModel> response) {

                if (response.isSuccessful())
                {
                    hiveProgressView.clearAnimation();
                    hiveProgressView.setVisibility(View.GONE);
                    Log.d("zmaPReq",response.toString());

                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    etMesg.setText("");

                }
                else
                {
                    Log.d("zmaPReq",response.toString());
                    hiveProgressView.clearAnimation();
               //     Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    hiveProgressView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<PriceRequestResponseModel> call, Throwable t) {
                hiveProgressView.clearAnimation();
                hiveProgressView.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("zmaPReq",t.getMessage());
            }
        });
    }


}
