package techease.com.seaweb.Activities.Fragment;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.comix.overwatch.HiveProgressView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Activities.BottomActivity;
import techease.com.seaweb.Activities.Models.BookingBoatResponseModel;
import techease.com.seaweb.Activities.Models.LoginResponseModel;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;


public class BookingFragment extends Fragment {

    TextView tvPrice,tvSdate,tvEdate,tveTime,tvsTime;
    EditText etTextArea;
    Button btnSend;
    ImageView ivBack;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String price,sDate,eDate,message,user_id,proid,type;
    SimpleDateFormat sdf;
    Date date1,date2;
    String totalDays;
    HiveProgressView hiveProgressView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        user_id = sharedPreferences.getString("userid","");
        proid = sharedPreferences.getString("proid","");
        sDate = sharedPreferences.getString("sdate","");
        eDate = sharedPreferences.getString("edate","");
        price = sharedPreferences.getString("total","");
        type = sharedPreferences.getString("type","");
        hiveProgressView = view.findViewById(R.id.progressMessage);
        ivBack = view.findViewById(R.id.ivBackMessageBooking);
        tveTime = view.findViewById(R.id.tvEtime);
        tvsTime = view.findViewById(R.id.tvStime);
        tvEdate = view.findViewById(R.id.tvEdate);
        tvSdate = view.findViewById(R.id.tvSDate);
        tvPrice = view.findViewById(R.id.tvAmount);
        etTextArea = view.findViewById(R.id.etMessageBoat);
        btnSend = view.findViewById(R.id.btnBookBoat);

        hiveProgressView.setVisibility(View.GONE);
        sdf = new SimpleDateFormat("yyyy/MM/dd");

        tvEdate.setText(eDate);
        tvSdate.setText(sDate);

        try {

            date1 = sdf.parse(sDate);
            date2 = sdf.parse(eDate);


            if (sDate.equals(eDate))
            {

                tvPrice.setText(price+"$");
            }
            else
            {
                long difference = Math.abs(date1.getTime() - date2.getTime());
                long differenceDates = difference / (24 * 60 * 60 * 1000);

                totalDays = Long.toString(differenceDates);
                int check = Integer.parseInt(totalDays)+1;
                check = check*100;

                tvPrice.setText(String.valueOf(check)+"$");

            }

        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("zmadiff",e.getMessage());
        }




        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = etTextArea.getText().toString();
                if (message.equals(""))
                {
                    etTextArea.setError("Please enter any message here");
                }
                else
                {
                    hiveProgressView.setVisibility(View.VISIBLE);
                    apiCall();
                }


            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new DatePickerBookingFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                getFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            }
        });
        return view;
    }
    private void apiCall() {

        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<BookingBoatResponseModel> call = services.bookBoat(user_id,proid,sDate,eDate,price,type,message,
                "5555555555554444", "03", "2023", "123");
        call.enqueue(new Callback<BookingBoatResponseModel>() {
            @Override
            public void onResponse(Call<BookingBoatResponseModel> call, Response<BookingBoatResponseModel> response) {

                hiveProgressView.setVisibility(View.GONE);
                if (response.isSuccessful())
                {
                    Toast.makeText(getActivity(), String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();

                    BottomActivity.whichOne = "Bookings";

                    startActivity(new Intent(getActivity(),BottomActivity.class));
                    getActivity().overridePendingTransition(R.animator.fade_in,R.animator.fade_out);
                    getActivity().finish();

                }
                else if (response.body() == null)
                {
                    Toast.makeText(getActivity(), "something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookingBoatResponseModel> call, Throwable t) {

                hiveProgressView.setVisibility(View.GONE);
                Log.d("zmaBookingexp",t.getMessage());
            }
        });

    }



}
