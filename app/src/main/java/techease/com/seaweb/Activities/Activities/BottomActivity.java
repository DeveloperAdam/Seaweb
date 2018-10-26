package techease.com.seaweb.Activities.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import techease.com.seaweb.Activities.Fragment.BookedBoatsFragment;
import techease.com.seaweb.Activities.Fragment.FavoriteFragment;
import techease.com.seaweb.Activities.Fragment.ListOfPlacesFragment;
import techease.com.seaweb.Activities.Fragment.ProfileFragment;
import techease.com.seaweb.R;

public class BottomActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    Fragment fragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:

                    startActivity(new Intent(BottomActivity.this,BottomActivity.class));
                    finish();
                    return true;
                case R.id.favrt:
                     fragment = new FavoriteFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
                    return true;
                case R.id.booked:
                    fragment = new BookedBoatsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
                    return true;
                case R.id.profile:
                    fragment = new ProfileFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ((AppCompatActivity) this).getSupportActionBar().hide();
        setContentView(R.layout.activity_bottom);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);

        setupViewPager(viewPager);
       // tabLayout.setupWithViewPager(viewPager);


//        fragment = new ListOfPlacesFragment();
//        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();




    }

    private void setupViewPager(final ViewPager viewPager) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        tabLayout.addTab(tabLayout.newTab().setText("CUBA"));
        tabLayout.addTab(tabLayout.newTab().setText("CANNES"));
        tabLayout.addTab(tabLayout.newTab().setText("SPLIT"));
        viewPager.setAdapter(new PagerAdapter(((FragmentActivity)this).getSupportFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(android.support.v4.app.FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }



        @Override
        public android.support.v4.app.Fragment getItem(int position) {

            switch (position) {
                case 0:
                    fragment=new ListOfPlacesFragment();
                    return fragment;
                case 1:
                    fragment=new ListOfPlacesFragment();
                    return fragment;
                case 2:
                    fragment=new ListOfPlacesFragment();
                    return fragment;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}
