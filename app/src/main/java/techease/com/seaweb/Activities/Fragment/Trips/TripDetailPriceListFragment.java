package techease.com.seaweb.Activities.Fragment.Trips;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import techease.com.seaweb.R;


public class TripDetailPriceListFragment extends Fragment {

    TextView tvChild,tvAdults;
    ImageView ivBack;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String child,adult;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip_detail_price_list, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        child = sharedPreferences.getString("child","");
        adult = sharedPreferences.getString("adult","");

        ivBack = view.findViewById(R.id.ivClosePriceListTrip);
        tvChild = view.findViewById(R.id.tvChildPrice);
        tvAdults = view.findViewById(R.id.tvAdultPrice);

        tvChild.setText(child+"$");
        tvAdults.setText(adult+"$");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new TripDetailsFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.animator.slide_out_up, R.animator.slide_in_up, R.animator.slide_in_up, R.animator.slide_out_up);
                transaction.replace(R.id.container2, fragment).addToBackStack("back").commit();
            }
        });

        return view;
    }

}
