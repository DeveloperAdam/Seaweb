package techease.com.seaweb.Activities.Activities;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import techease.com.seaweb.Activities.Fragment.BoatDetailFragment;
import techease.com.seaweb.Activities.Fragment.BoatsOnLocationFragment;
import techease.com.seaweb.Activities.Fragment.DatePickerFragment;
import techease.com.seaweb.Activities.Fragment.ListOfPlacesFragment;
import techease.com.seaweb.Activities.Fragment.LoginFragment;
import techease.com.seaweb.Activities.Fragment.TabFragment;
import techease.com.seaweb.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
   public static boolean flag =false;
    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)

        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) this).getSupportActionBar().hide();
        setContentView(R.layout.activity_fullscreen);

        sharedPreferences = this.getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String token=sharedPreferences.getString("login","");
        if (ListOfPlacesFragment.searchFlag == true)
        {
            android.support.v4.app.Fragment fragment=new DatePickerFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
        }
        else
        if (flag == true)
        {

            String boatid=getIntent().getExtras().getString("boatid");
            Bundle bundle=new Bundle();
            bundle.putString("boatid",boatid);
            android.support.v4.app.Fragment fragment=new BoatDetailFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
        }
        else
        if (token.equals("login"))
        {
            String placeid=sharedPreferences.getString("placeid","");
            Bundle bundle=new Bundle();
            bundle.putString("placeid",placeid);
            Fragment fragment=new BoatsOnLocationFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();

        }
        else
        {

            android.support.v4.app.Fragment fragment=new LoginFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
        }




    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {

        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
