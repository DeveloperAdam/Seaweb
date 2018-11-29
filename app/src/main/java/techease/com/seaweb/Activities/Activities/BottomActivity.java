package techease.com.seaweb.Activities.Activities;

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
                    fragment = new TripsFragment();
                    fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                    fragment.setExitTransition(new Slide(Gravity.LEFT));
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
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


//        AHBottomNavigationItem item1 = new AHBottomNavigationItem("", R.drawable.home);
//        AHBottomNavigationItem item2 = new AHBottomNavigationItem("", R.drawable.hrt);
//        AHBottomNavigationItem item3 = new AHBottomNavigationItem("", R.drawable.label);
//        AHBottomNavigationItem item4 = new AHBottomNavigationItem("", R.drawable.profi);
//
//        ahBottomNavigation.setBehaviorTranslationEnabled(false);
//
//
//        ahBottomNavigation.addItem(item1);
//        ahBottomNavigation.addItem(item2);
//        ahBottomNavigation.addItem(item3);
//        ahBottomNavigation.addItem(item4);
//
//        ahBottomNavigation.setDefaultBackgroundColor(Color.WHITE);
//        ahBottomNavigation.setAccentColor(fetchColor(R.color.orangecolor));
//        ahBottomNavigation.setInactiveColor(fetchColor(R.color.graycolor));


        if (whichOne.equals("Bookings"))
        {
            fragment = new BookedBoatsFragment();
            fragment.setEnterTransition(new Slide(Gravity.RIGHT));
            fragment.setExitTransition(new Slide(Gravity.LEFT));
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
        }
        else
            if (whichOne.equals("Favourite"))
            {
                fragment = new FavoriteFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
            }
            else
            if (whichOne.equals(""))
            {
                fragment = new ListOfPlacesFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
            }

     //   ahBottomNavigation.setCurrentItem(0);


//        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(int position, boolean wasSelected) {
//
//                if (position == 0)
//                {
//                    fragment = new ListOfPlacesFragment();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
//                }
//                else
//                    if (position == 1)
//                    {
//
//                        fragment = new FavoriteFragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
//                    }
//                    else
//                    if (position == 2)
//                    {
//                        fragment = new BookedBoatsFragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
//                    }
//                    else
//                    if (position == 3)
//                    {
//                        fragment = new ProfileFragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
//                    }
//            }
//        });


    }


}
