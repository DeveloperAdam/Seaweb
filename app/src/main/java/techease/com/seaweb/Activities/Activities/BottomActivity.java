package techease.com.seaweb.Activities.Activities;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
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
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    fragment = new ListOfPlacesFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
                    return true;
                case R.id.favrt:
                     fragment = new FavoriteFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
                    return true;
                case R.id.booked:
                    fragment = new BookedBoatsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
                    return true;
                case R.id.profile:
                    fragment = new ProfileFragment();
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


        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationHelper.disableShiftMode(navigation);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        fragment = new ListOfPlacesFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();



    }

}
