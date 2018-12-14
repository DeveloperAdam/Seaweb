package techease.com.seaweb.Activities.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;

import techease.com.seaweb.Activities.Fragment.ListOfPlacesFragment;
import techease.com.seaweb.Activities.Fragment.Profile.ProfileFragment;
import techease.com.seaweb.Activities.Fragment.Trips.BookedTripsFragment;
import techease.com.seaweb.Activities.Fragment.Trips.FavrtTripFragment;
import techease.com.seaweb.Activities.Fragment.Trips.TripsFragment;
import techease.com.seaweb.Activities.Utils.BottomNavigationHelper;
import techease.com.seaweb.R;

public class TripBottomNavigationActivity extends AppCompatActivity {

    Fragment fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home2:
//                    fragment = new ListOfPlacesFragment();
//                    fragment.setEnterTransition(new Slide(Gravity.RIGHT));
//                    fragment.setExitTransition(new Slide(Gravity.LEFT));
//                    getSupportFragmentManager().beginTransaction().replace(R.id.container_trip,fragment).commit();
                    startActivity(new Intent(TripBottomNavigationActivity.this,BottomActivity.class));
                    overridePendingTransition(R.animator.fade_out,R.animator.fade_in);
                    finish();
                    return true;
                case R.id.trips2:
                    fragment = new TripsFragment();
                    fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                    fragment.setExitTransition(new Slide(Gravity.LEFT));
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_trip,fragment).commit();
                    return true;
                case R.id.favrt2:
                    fragment = new FavrtTripFragment();
                    fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                    fragment.setExitTransition(new Slide(Gravity.LEFT));
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_trip,fragment).commit();
                    return true;
                case R.id.profile2:
                    fragment = new ProfileFragment();
                    fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                    fragment.setExitTransition(new Slide(Gravity.LEFT));
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_trip,fragment).commit();
                    return true;
                case R.id.booked2:
                    fragment = new BookedTripsFragment();
                    fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                    fragment.setExitTransition(new Slide(Gravity.LEFT));
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_trip,fragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) this).getSupportActionBar().hide();
        setContentView(R.layout.activity_trip_bottom_navigation);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomNavigationHelper.disableShiftMode(navigation);

        fragment = new TripsFragment();
        fragment.setEnterTransition(new Slide(Gravity.RIGHT));
        fragment.setExitTransition(new Slide(Gravity.LEFT));
        getSupportFragmentManager().beginTransaction().replace(R.id.container_trip,fragment).commit();
    }

}
