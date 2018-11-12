package techease.com.seaweb.Activities.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Fragment.BookedBoatsFragment;
import techease.com.seaweb.Activities.Fragment.FavoriteFragment;
import techease.com.seaweb.Activities.Fragment.ListOfPlacesFragment;
import techease.com.seaweb.Activities.Fragment.ProfileFragment;
import techease.com.seaweb.Activities.Fragment.TabFragment;
import techease.com.seaweb.Activities.Models.LoginResponseModel;
import techease.com.seaweb.Activities.Models.SuggestedPlacesResponseModel;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.Activities.Utils.BottomNavigationHelper;
import techease.com.seaweb.R;

public class BottomActivity extends AppCompatActivity {


    Fragment fragment;
    BottomNavigationView navigation;
    AHBottomNavigation ahBottomNavigation;
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

        fragment = new ListOfPlacesFragment();
        fragment.setEnterTransition(new Slide(Gravity.RIGHT));
        fragment.setExitTransition(new Slide(Gravity.LEFT));
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
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


    private int fetchColor(@ColorRes int color)
    {
        return ContextCompat.getColor(this, color);
    }

}
