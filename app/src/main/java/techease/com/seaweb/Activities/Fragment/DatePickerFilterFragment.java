package techease.com.seaweb.Activities.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import techease.com.seaweb.Activities.Activities.BottomActivity;
import techease.com.seaweb.R;


public class DatePickerFilterFragment extends Fragment {

    DateRangeCalendarView  dateRangeCalendarView;
    Button btnSave;
    String sDate,eDate;
    ImageView ivBack;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView tvCheckIn,tvCheckOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date_picker, container, false);


        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        tvCheckIn = view.findViewById(R.id.tvcheckInFilterDate);
        tvCheckOut = view.findViewById(R.id.tvCheckoutFilterDate);
        btnSave = view.findViewById(R.id.btnSaveDate);
        ivBack = view.findViewById(R.id.ivBackDate);
        dateRangeCalendarView = view.findViewById(R.id.calendar);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new BoatSearchFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                getFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack("back").commit();
            }
        });


        dateRangeCalendarView.setCalendarListener(new DateRangeCalendarView.CalendarListener() {
            @Override
            public void onFirstDateSelected(Calendar startDate) {
                convertStartDateToString(startDate);
                eDate = sDate;

                tvCheckIn.setText(sDate);
                tvCheckOut.setText(eDate);
                editor.putString("startdate",sDate).commit();
                editor.putString("enddate",eDate).commit();
            }

            @Override
            public void onDateRangeSelected(Calendar startDate, Calendar endDate) {

                convertEndDateToString(endDate);

                tvCheckIn.setText(sDate);
                tvCheckOut.setText(eDate);

                editor.putString("startdate",sDate).commit();
                editor.putString("enddate",eDate).commit();

            }
        });



        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Fragment fragment = new BoatTypeFragment();
               getFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            }
        });
        return view;
    }


    private void convertStartDateToString(Calendar calendar) {

        calendar.add(Calendar.DATE, 0);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(calendar.getTime());
        System.out.println(formatted);
        sDate = formatted;
        editor.putString("startdate",sDate).commit();
        try {
            System.out.println(format1.parse(formatted));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void convertEndDateToString(Calendar endDate) {
        endDate.add(Calendar.DATE, 0);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(endDate.getTime());
        System.out.println(formatted);
        eDate = formatted;
        editor.putString("enddate",eDate).commit();
        try {
            System.out.println(format1.parse(formatted));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }




}
