package techease.com.seaweb.Activities.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import techease.com.seaweb.R;


public class DatePickerFragment extends Fragment {

    DateRangeCalendarView  dateRangeCalendarView;

    String sDate,eDate;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date_picker, container, false);


        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        dateRangeCalendarView = view.findViewById(R.id.calendar);

        dateRangeCalendarView.setCalendarListener(new DateRangeCalendarView.CalendarListener() {
            @Override
            public void onFirstDateSelected(Calendar startDate) {
                convertStartDateToString(startDate);
            }

            @Override
            public void onDateRangeSelected(Calendar startDate, Calendar endDate) {

                convertEndDateToString(endDate);

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
        Toast.makeText(getActivity(), formatted, Toast.LENGTH_SHORT).show();
        sDate = formatted;
        editor.putString("sDate",sDate).commit();
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
        Toast.makeText(getActivity(), formatted, Toast.LENGTH_SHORT).show();
        eDate = formatted;
        editor.putString("eDate",eDate).commit();
        try {
            System.out.println(format1.parse(formatted));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }




}
