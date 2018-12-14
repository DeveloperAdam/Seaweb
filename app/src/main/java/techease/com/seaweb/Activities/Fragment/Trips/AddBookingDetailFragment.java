package techease.com.seaweb.Activities.Fragment.Trips;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.comix.overwatch.HiveProgressView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Activities.BottomActivity;
import techease.com.seaweb.Activities.Fragment.BoatDetailFragment;
import techease.com.seaweb.Activities.Models.BoatBookingResponseModel;
import techease.com.seaweb.Activities.Models.LoginResponseModel;
import techease.com.seaweb.Activities.Models.Trip.TripBookingResponseModel;
import techease.com.seaweb.Activities.Utils.AlertsUtils;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;


public class AddBookingDetailFragment extends Fragment implements View.OnClickListener {

    TextView tvAdults,tvChildren,tvCharges,tvSeats,tvYourSeats,tvSdate,tvEdate,tvStime,tvEtime;
    ImageView ivAddAdults,ivMinusAdults,ivAddChildren,ivMinusChildren,ivBack;
    Button btnBook;
    EditText etMessage;
    String startDate,endDate,adults,children,userid,pro_id,message,resp,whole="0",seats,type,perChild,perAdult,
    timeFrom,timeTo,dateFrom,dateTo;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int charges,totalAdults=0,totalChildren=0,yourSeats;
    HiveProgressView hiveProgressView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_booking_detail, container, false);

        alertDialog = null;
        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        perAdult = sharedPreferences.getString("adult","");
        perChild = sharedPreferences.getString("child","");
        userid=sharedPreferences.getString("userid","");
        pro_id = sharedPreferences.getString("tripid","");
        seats = sharedPreferences.getString("seats","");
        timeFrom = sharedPreferences.getString("tfrom","");
        timeTo = sharedPreferences.getString("tto","");
        dateFrom  = sharedPreferences.getString("dfrom","");
        dateTo = sharedPreferences.getString("dto","");

        tvSdate = view.findViewById(R.id.tvDfrom);
        tvEdate = view.findViewById(R.id.tvDto);
        tvStime = view.findViewById(R.id.tvTfrom);
        tvEtime = view.findViewById(R.id.tvTto);
        hiveProgressView = view.findViewById(R.id.progressAddBookingDetail);
        tvYourSeats = view.findViewById(R.id.tvBookingSeats);
        ivBack=view.findViewById(R.id.ivCrossBack);
        etMessage=view.findViewById(R.id.etMessage);
        tvSeats = view.findViewById(R.id.tvSeats);
        tvAdults=view.findViewById(R.id.tvAdults);
        tvChildren=view.findViewById(R.id.tvChildren);
        tvCharges=view.findViewById(R.id.tvCharges);
        ivAddAdults=view.findViewById(R.id.ivAddAdult);
        ivMinusAdults=view.findViewById(R.id.ivMinusAdults);
        ivAddChildren=view.findViewById(R.id.ivAddChildren);
        ivMinusChildren=view.findViewById(R.id.ivMinusChildren);
        btnBook=view.findViewById(R.id.btnBook);
        ivBack = view.findViewById(R.id.ivCrossBack);

        tvSeats.setText(seats);
        tvStime.setText(timeFrom);
        tvEtime.setText(timeTo);
        tvSdate.setText(dateFrom);
        tvEdate.setText(dateTo);

        hiveProgressView.setVisibility(View.GONE);

        ivAddAdults.setOnClickListener(this);
        ivMinusAdults.setOnClickListener(this);
        ivAddChildren.setOnClickListener(this);
        ivMinusChildren.setOnClickListener(this);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new TripDetailsFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                getFragmentManager().beginTransaction().replace(R.id.container2,fragment).commit();
            }
        });


        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                message = etMessage.getText().toString();
                adults = tvAdults.getText().toString();
                children = tvChildren.getText().toString();

                if (message.equals(""))
                {
                    Toast.makeText(getActivity(), "Please Type a message ", Toast.LENGTH_SHORT).show();
                }
                else
                    if (adults.equals(""))
                    {
                                adults = "0";
                                whole = "1"; }
                            else
                                if (children.equals(""))
                                {
                                    children = "0";
                                    whole = "1";

                                }else
                                {
                                    hiveProgressView.setVisibility(View.VISIBLE);
                                    apiCall();
                                }






            }
        });

        return view;
    }


    private void apiCall() {
        Log.d("zmaTripBookingP",userid+ " "+pro_id+" "+message+" "+adults+" "+children+" "+String.valueOf(charges)+" "+seats);
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<TripBookingResponseModel> call = services.bookTrip(userid,pro_id,adults,children,seats,String.valueOf(charges),"Trip","BDAJ2323BJ2",message);
        call.enqueue(new Callback<TripBookingResponseModel>() {
            @Override
            public void onResponse(Call<TripBookingResponseModel> call, Response<TripBookingResponseModel> response) {

                hiveProgressView.setVisibility(View.GONE);
                if (response.isSuccessful())
                {
                    Log.d("zmaTripBooking",response.toString());

                    resp = response.body().getMessage();
                    Toast.makeText(getActivity(), resp, Toast.LENGTH_SHORT).show();

                    if (resp.equals("Booking Completed"))
                    {
                        etMessage.setText("");
                        tvAdults.setText("0");
                        tvChildren.setText("0");
                        tvCharges.setText("$ 0");
                        tvYourSeats.setText("0");

                        Fragment fragment = new BookedTripsFragment();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.animator.slide_out_up, R.animator.slide_in_up, R.animator.slide_in_up, R.animator.slide_out_up);
                        transaction.replace(R.id.container2, fragment).addToBackStack("back").commit();
                    }
                }
                else
                {

                }
            }

            @Override
            public void onFailure(Call<TripBookingResponseModel> call, Throwable t) {
                hiveProgressView.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id)
        {

            case R.id.ivAddAdult:
                totalAdults++;
                tvAdults.setText(String.valueOf(totalAdults));
                if ( totalChildren == 0)
                {
                    calculate(totalAdults,0);
                }
                else
                {
                    calculate(totalChildren,totalAdults);
                }


                break;
            case R.id.ivMinusAdults:

                totalAdults--;
                if (totalAdults <= 0)
                {
                    totalAdults = 0;
                }
                tvAdults.setText(String.valueOf(totalAdults));
                if (totalChildren == 0)
                {
                    calculate(totalAdults,0);
                }
                else
                if (totalChildren == 0)
                {
                    calculate(0,totalChildren);
                }
                else
                {
                    calculate(totalAdults,totalChildren);
                }

                break;
            case R.id.ivAddChildren:
                totalChildren++;
                tvChildren.setText(String.valueOf(totalChildren));
                if (totalAdults == 0)
                {
                    calculate(0,totalChildren);
                }
                else
                {
                    calculate(totalChildren,totalAdults);
                }


                break;
            case R.id.ivMinusChildren:
                totalChildren--;
                if (totalChildren <= 0)
                {
                    totalChildren = 0;
                }

                tvChildren.setText(String.valueOf(totalChildren));
                if ( totalAdults == 0)
                {
                    calculate(0,totalChildren);
                }
                else
                    if (totalChildren == 0)
                    {
                        calculate(totalAdults,0);
                    }
                else
                {
                    calculate(totalAdults,totalChildren);
                }
                break;


        }
    }

    public void calculate(int adults, int children)
    {

        yourSeats = adults+children;
        adults = adults * Integer.parseInt(perAdult);
        children = children * Integer.parseInt(perChild);
        tvYourSeats.setText(String.valueOf(yourSeats));
        charges = (adults+children);
        tvCharges.setText("$ "+String.valueOf(charges));

    }
}
