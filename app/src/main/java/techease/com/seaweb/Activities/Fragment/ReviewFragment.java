package techease.com.seaweb.Activities.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.whinc.widget.ratingbar.RatingBar;

import techease.com.seaweb.R;


public class ReviewFragment extends Fragment {

    RatingBar ratingBar1,ratingBar2,ratingBar3,ratingBar4,ratingBar5;
    TextView tvComfortValue,tvCleaningValue,tvPriceValue,tvStaffValue,tvServicesValue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);


        tvComfortValue = view.findViewById(R.id.tvComfortValue);
        tvCleaningValue = view.findViewById(R.id.tvCleaningValue);
        tvPriceValue = view.findViewById(R.id.tvPriceValue);
        tvStaffValue = view.findViewById(R.id.tvStaffValue);
        tvServicesValue = view.findViewById(R.id.tvServicesValue);

        ratingBar1 = view.findViewById(R.id.ratingBar);
        ratingBar2 = view.findViewById(R.id.ratingBar2);
        ratingBar3 = view.findViewById(R.id.ratingBar3);
        ratingBar4 = view.findViewById(R.id.ratingBar4);
        ratingBar5 = view.findViewById(R.id.ratingBar5);


        ratingBar1.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(RatingBar ratingBar, int preCount, int CurCount) {

                tvComfortValue.setText(String.valueOf(CurCount));
            }
        });

        ratingBar2.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(RatingBar ratingBar, int preCount, int CurCount) {

                tvCleaningValue.setText(String.valueOf(CurCount));
            }
        });

        ratingBar3.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(RatingBar ratingBar, int preCount, int CurCount) {

                tvPriceValue.setText(String.valueOf(CurCount));
            }
        });

        ratingBar4.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(RatingBar ratingBar, int preCount, int CurCount) {

                tvStaffValue.setText(String.valueOf(CurCount));
            }
        });

        ratingBar5.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(RatingBar ratingBar, int preCount, int CurCount) {

                tvServicesValue.setText(String.valueOf(CurCount));
            }
        });



        return view;
    }

}
