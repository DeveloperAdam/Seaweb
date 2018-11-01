package techease.com.seaweb.Activities.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import techease.com.seaweb.Activities.Models.BoatBookingResponseModel;
import techease.com.seaweb.Activities.Models.LoginResponseModel;
import techease.com.seaweb.Activities.Utils.AlertsUtils;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;


public class AddBookingDetailFragment extends Fragment implements View.OnClickListener {

    TextView tvStartDate,tvEndDate,tvAdults,tvChildren,tvCharges;
    ImageView ivAddAdults,ivMinusAdults,ivAddChildren,ivMinusChildren,ivBack;
    Button btnBook;
    EditText etMessage;
    String startDate,endDate,adults,children,userid,pro_id,message,resp;
    android.support.v7.app.AlertDialog alertDialog;
    Calendar calendar1,calendar2;
    long difference;
    Date date1,date2;
    SimpleDateFormat sdf;
    int day,month,year,days;
    DatePickerDialog datePickerDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int charges,totalAdults=0,totalChildren=0,totalDays;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_booking_detail, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userid=sharedPreferences.getString("userid","");
        pro_id = sharedPreferences.getString("proid","");
        ivBack=view.findViewById(R.id.ivCrossBack);
        etMessage=view.findViewById(R.id.etMessage);
        tvStartDate=view.findViewById(R.id.tvStartDate);
        tvEndDate=view.findViewById(R.id.tvEndDate);
        tvAdults=view.findViewById(R.id.tvAdults);
        tvChildren=view.findViewById(R.id.tvChildren);
        tvCharges=view.findViewById(R.id.tvCharges);
        ivAddAdults=view.findViewById(R.id.ivAddAdult);
        ivMinusAdults=view.findViewById(R.id.ivMinusAdults);
        ivAddChildren=view.findViewById(R.id.ivAddChildren);
        ivMinusChildren=view.findViewById(R.id.ivMinusChildren);
        btnBook=view.findViewById(R.id.btnBook);

         sdf = new SimpleDateFormat("yyyy/MM/dd");

        ivAddAdults.setOnClickListener(this);
        ivMinusAdults.setOnClickListener(this);
        ivAddChildren.setOnClickListener(this);
        ivMinusChildren.setOnClickListener(this);
        tvStartDate.setOnClickListener(this);
        tvEndDate.setOnClickListener(this);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new BoatDetailFragment();
                getFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                message = etMessage.getText().toString();
                adults = tvAdults.getText().toString();
                children = tvChildren.getText().toString();

                try {
                    date1 = sdf.parse(startDate);
                    date2 = sdf.parse(endDate);

                    long diff = date1.getTime() - date2.getTime();

                    String abc = String.valueOf(Math.abs(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
                    totalDays = Integer.parseInt(abc);
                    if (totalAdults == 0 && totalChildren == 0)
                    {
                        calculate(totalDays,0,0);
                    }
                    else
                        if (totalAdults == 0)
                        {
                            calculate(totalDays,0,totalChildren);
                        }
                        else
                            if (totalChildren == 0)
                            {
                                calculate(totalDays,totalAdults,0);
                            }
                            else
                            {
                                calculate(totalDays,totalAdults,totalChildren);
                            }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (alertDialog == null) {
                    alertDialog = AlertsUtils.createProgressDialog(getActivity());
                    alertDialog.show();
                }
                apiCall();




            }
        });

        return view;
    }


    private void apiCall() {
        Log.d("zmaParams",pro_id+" "+userid+" "+startDate+" "+endDate+" "+adults+" "+children+" "+String.valueOf(charges)+" "+message);
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<BoatBookingResponseModel> call = services.boatBooking(pro_id,userid,startDate,endDate,adults,children,String.valueOf(charges),"0","123",message);
        call.enqueue(new Callback<BoatBookingResponseModel>() {
            @Override
            public void onResponse(Call<BoatBookingResponseModel> call, Response<BoatBookingResponseModel> response) {

                if (response.isSuccessful())
                {
                    if (alertDialog != null)
                        alertDialog.dismiss();
                    Log.d("zmaBooking",response.toString());

                    resp = response.body().getMessage();
                    Toast.makeText(getActivity(), resp, Toast.LENGTH_SHORT).show();

                    if (resp.equals("Boat Booked"))
                    {
                        etMessage.setText("");
                        tvAdults.setText("0");
                        tvCharges.setText("0");
                        tvCharges.setText("$ 0");
                        tvStartDate.setText("Start Date");
                        tvEndDate.setText("End Date");

                        Fragment fragment = new BookedBoatsFragment();
                        getFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack("back").commit();
                    }
                }
                else
                {
                    if (alertDialog != null)
                        alertDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<BoatBookingResponseModel> call, Throwable t) {
                if (alertDialog != null)
                    alertDialog.dismiss();
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
                if (totalDays == 0 && totalChildren == 0)
                {
                    calculate(0,totalAdults,0);
                }
                else
                if (totalChildren == 0)
                {
                    calculate(0,totalAdults,0);
                }
                else
                if (totalDays == 0)
                {
                    calculate(0,totalAdults,totalChildren);
                }

                break;
            case R.id.ivMinusAdults:

                totalAdults--;
                if (totalAdults <= 0)
                {
                    totalAdults = 0;
                }
                tvAdults.setText(String.valueOf(totalAdults));
                if (totalDays == 0 && totalChildren == 0)
                {
                    calculate(0,totalAdults,0);
                }
                else
                if (totalChildren == 0)
                {
                    calculate(0,totalAdults,0);
                }
                else
                if (totalDays == 0)
                {
                    calculate(0,totalAdults,totalChildren);
                }

                break;
            case R.id.ivAddChildren:
                totalChildren++;
                tvChildren.setText(String.valueOf(totalChildren));
                if (totalDays == 0 && totalAdults == 0)
                {
                    calculate(0,0,totalChildren);
                }
                else
                if (totalAdults == 0)
                {
                    calculate(totalDays,0,totalChildren);
                }
                else
                if (totalDays == 0)
                {
                    calculate(0,totalAdults,totalChildren);
                }
                break;
            case R.id.ivMinusChildren:
                totalChildren--;
                if (totalChildren <= 0)
                {
                    totalChildren = 0;
                }

                tvChildren.setText(String.valueOf(totalChildren));
                if (totalDays == 0 && totalAdults == 0)
                {
                    calculate(0,0,totalChildren);
                }
                else
                if (totalAdults == 0)
                {
                    calculate(totalDays,0,totalChildren);
                }
                else
                if (totalDays == 0)
                {
                    calculate(0,totalAdults,totalChildren);
                }
                break;
            case R.id.tvStartDate:

                calendar1 = Calendar.getInstance();
                day = calendar1.get(Calendar.DAY_OF_MONTH);
                month = calendar1.get(Calendar.MONTH);
                year = calendar1.get(Calendar.YEAR);

                String myFormat = "yyyy-MM-dd";
                sdf = new SimpleDateFormat(myFormat, Locale.US);

                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener()  {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                        tvStartDate.setText( year+"-"+month+"-"+dayOfMonth);
                        startDate = year+"-"+month+"-"+dayOfMonth;


                    }
                },year,month,day);
                datePickerDialog.show();
                break;
            case R.id.tvEndDate:

                calendar2 = Calendar.getInstance();

                 day = calendar2.get(Calendar.DAY_OF_MONTH);
                 month = calendar2.get(Calendar.MONTH);
                 year = calendar2.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        tvEndDate.setText( year+"-"+month+"-"+dayOfMonth);
                        endDate = year+"-"+month+"-"+dayOfMonth;


                    }
                },year,month,day);
                datePickerDialog.show();

                break;

        }
    }

    public void calculate(int days,int adults, int children)
    {

        charges = (days+adults+children)*10;

        tvCharges.setText("$ "+String.valueOf(charges));

    }
}
