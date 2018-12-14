package techease.com.seaweb.Activities.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

import techease.com.seaweb.Activities.Fragment.BookedBoatsFragment;
import techease.com.seaweb.Activities.Fragment.FavoriteFragment;
import techease.com.seaweb.Activities.Fragment.ListOfPlacesFragment;
import techease.com.seaweb.Activities.Fragment.Profile.ProfileFragment;
import techease.com.seaweb.Activities.Fragment.Trips.TripsFragment;
import techease.com.seaweb.Activities.Utils.BottomNavigationHelper;
import techease.com.seaweb.R;

public class BottomActivity extends AppCompatActivity {


    Fragment fragment;
    public static String whichOne="";
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    fragment = new ListOfPlacesFragment();
                    fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                    fragment.setExitTransition(new Slide(Gravity.LEFT));
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
                    return true;
                case R.id.favrt:
                     fragment = new FavoriteFragment();
                    fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                    fragment.setExitTransition(new Slide(Gravity.LEFT));
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
                    return true;
                case R.id.booked:
                    fragment = new BookedBoatsFragment();
                    fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                    fragment.setExitTransition(new Slide(Gravity.LEFT));
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
                    return true;
                case R.id.profile:
                    fragment = new ProfileFragment();
                    fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                    fragment.setExitTransition(new Slide(Gravity.LEFT));
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
                    return true;
                case R.id.trips:
//                    fragment = new TripsFragment();
//                    fragment.setEnterTransition(new Slide(Gravity.RIGHT));
//                    fragment.setExitTransition(new Slide(Gravity.LEFT));
//                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
                    startActivity(new Intent(BottomActivity.this,TripBottomNavigationActivity.class));
                    overridePendingTransition(R.animator.fade_out,R.animator.fade_in);
                    finish();
                    return true;
            }

            return false;
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) this).getSupportActionBar().hide();
        setContentView(R.layout.activity_bottom);

      //  ahBottomNavigation = findViewById(R.id.bottom_navigation);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomNavigationHelper.disableShiftMode(navigation);


        if (whichOne.equals("Bookings"))
        {
            fragment = new BookedBoatsFragment();
            fragment.setEnterTransition(new Slide(Gravity.RIGHT));
            fragment.setExitTransition(new Slide(Gravity.LEFT));
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
            whichOne = "";
        }
        else
            if (whichOne.equals("Favourite"))
            {
                fragment = new FavoriteFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
                whichOne ="";
            }
            else
            if (whichOne.equals(""))
            {
                fragment = new ListOfPlacesFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
                whichOne = "";
            }
            else
                if (whichOne.equals("Profile"))
                {
                    fragment = new ProfileFragment();
                    fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                    fragment.setExitTransition(new Slide(Gravity.LEFT));
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();

                    whichOne = "";
                }


    }


}
