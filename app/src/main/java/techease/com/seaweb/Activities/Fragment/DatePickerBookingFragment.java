package techease.com.seaweb.Activities.Fragment;

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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import techease.com.seaweb.R;

public class DatePickerBookingFragment extends Fragment {


    DateRangeCalendarView dateRangeCalendarView;
    Button btnSave;
    String sDate,eDate,pro_id;
    ImageView ivBack;
    TextView tvSdate,tvEdate,tvCheckIn,tvCheckOut;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Switch aSwitch;
    List<String> dates;
    LinearLayout linearLayoutSwitch;
    String price;
    int intPrice;
    JSONArray jsonArray;
    float total;
    int length;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date_picker_booking, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        pro_id = sharedPreferences.getString("proid","");
        tvCheckIn = view.findViewById(R.id.tvcheckin);
        tvCheckOut = view.findViewById(R.id.tvcheckout);
        price = sharedPreferences.getString("perday","");
        if (price.contains("."))
        {
            String parsePrice = String.valueOf(price.split("."));
            price = parsePrice;
            total = Float.parseFloat(price);
        }
        else
        {
            intPrice = Integer.parseInt(price);
            total = intPrice;
        }

        tvSdate = view.findViewById(R.id.tvcheckin);
        tvEdate = view.findViewById(R.id.tvcheckout);
        linearLayoutSwitch = view.findViewById(R.id.ll2);
        aSwitch = view.findViewById(R.id.halfydaySwitch);
        btnSave = view.findViewById(R.id.btnSaveDateBooking);
        ivBack = view.findViewById(R.id.ivBackBookingDate);
        dateRangeCalendarView = view.findViewById(R.id.calendarBooking);


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true)
                {
                    total = intPrice/2;
                    editor.putString("total",String.valueOf(total)).commit();
                }
                else
                {
                    total = Float.parseFloat(price);
                    editor.putString("total",String.valueOf(total)).commit();
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editor.putString("total",String.valueOf(total)).commit();
                Fragment fragment = new BookingFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                getFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack("back").commit();
            }
        });

        dateRangeCalendarView.setCalendarListener(new DateRangeCalendarView.CalendarListener() {
            @Override
            public void onFirstDateSelected(Calendar startDate) {
                linearLayoutSwitch.setVisibility(View.VISIBLE);
                convertStartDateToString(startDate);
                eDate = sDate;
                editor.putString("edate",eDate).commit();
                tvCheckIn.setText(sDate);
                tvCheckOut.setText(eDate);
                Log.d("zmaDates",sDate+"   "+eDate);


                checkDate(sDate,eDate);


            }

            @Override
            public void onDateRangeSelected(Calendar startDate, Calendar endDate) {
                linearLayoutSwitch.setVisibility(View.GONE);
                convertEndDateToString(endDate);

                tvCheckIn.setText(sDate);
                tvCheckOut.setText(eDate);

                checkDate(sDate,eDate);

            }
        });



        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new BoatDetailFragment();
                getFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            }
        });
        return view;
    }
    private void convertStartDateToString(Calendar calendar) {

        calendar.add(Calendar.DATE, 0);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        String formatted = format1.format(calendar.getTime());
        System.out.println(formatted);
        sDate = formatted;
        editor.putString("sdate",sDate).commit();
        try {
            System.out.println(format1.parse(formatted));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void convertEndDateToString(Calendar endDate) {
        endDate.add(Calendar.DATE, 0);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        String formatted = format1.format(endDate.getTime());
        System.out.println(formatted);
        eDate = formatted;
        editor.putString("edate",eDate).commit();
        try {
            System.out.println(format1.parse(formatted));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public  void apicall()
    { final StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://divergense.com/boat/App/boat_dates"
            , new com.android.volley.Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            Log.d("zmaDatesList", response);

            try {
                JSONObject jsonObject = new JSONObject(response);
                jsonArray = jsonObject.getJSONArray("data");
                length = jsonArray.length();
                for (int i =0; i<jsonArray.length(); i++)
                {
                    JSONObject object = jsonArray.getJSONObject(i);
                    dates.add(object.getString("date")) ;
                }

            } catch (JSONException e) {
                e.printStackTrace();

            }

        }

    }, new com.android.volley.Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            Log.d("error", String.valueOf(error.getCause()));
        }
    }) {
        @Override
        public String getBodyContentType() {
            return "application/x-www-form-urlencoded;charset=UTF-8";
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("pro_id",pro_id);
            params.put("date",sDate);
            Log.d("zmaParm",params.toString());
            return params;
        }
    };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }

    public void checkDate(String date1,String date2)
    {
        if (length>0)
        {
            for (int z=0; z<length; z++)
            {
                if (date1.equals(dates))
                {
                    Toast.makeText(getActivity(), "Sorry boat is booked on these dates", Toast.LENGTH_SHORT).show();
                    btnSave.setEnabled(false);
                }
                else
                if (date2.equals(dates))
                {
                    Toast.makeText(getActivity(), "Sorry boat is booked on these dates", Toast.LENGTH_SHORT).show();
                    btnSave.setEnabled(false);
                }
                else
                {
                    btnSave.setEnabled(true);
                }
            }
        }


    }


}
