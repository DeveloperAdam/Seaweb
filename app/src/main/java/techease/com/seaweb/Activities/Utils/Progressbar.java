package techease.com.seaweb.Activities.Utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import cc.cloudist.acplibrary.ACProgressPie;
import techease.com.seaweb.R;

public class Progressbar {

    public static Button button;
    public static TextView textView;
    public static GridView gridView;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static RequestQueue mRequestQueue;
    public static String deviceToken;
    public static String SERVER_MSG = "Server not responding";
    public static String CONNECTION_MSG = "No internet connection";
    public static String WRONG_MSG = "Something went wrong!";
    public static String SUCCESS = "Success";
    public static ACProgressPie progressPie;
    public static Fragment connectFragment(Context context, Fragment fragment) {
        ((AppCompatActivity) context).getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("true").commit();
        return fragment;
    }
    public static Fragment conn(Context context, Fragment fragment) {
        ((AppCompatActivity) context).getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("true").commit();
        return fragment;
    }
    public static Fragment connectFragmentWithOutBackStack(Context context, Fragment fragment) {
        ((AppCompatActivity) context).getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        return fragment;
    }
    public static SharedPreferences.Editor putStringValueInEditor(Context context, String key, String value) {
        sharedPreferences = getSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.putString(key, value).commit();
        return editor;
    }
    public static SharedPreferences.Editor putIntegerValueInEditor(Context context, String key, int value) {
        sharedPreferences = getSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.putInt(key, value).commit();
        return editor;
    }
    public static SharedPreferences.Editor putBooleanValueInEditor(Context context, String key, boolean value) {
        sharedPreferences = getSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.putBoolean(key, value).commit();
        return editor;
    }
    public static SharedPreferences getSharedPreferences(Context context) {
        //sharedPreferences = context.getSharedPreferences(Configuration.MY_PREF, 0);
        return context.getSharedPreferences("abc", 0);
    }
    public static Request<String> setRequest(Context context, StringRequest stringRequest) {
        mRequestQueue = Volley.newRequestQueue(context);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return mRequestQueue.add(stringRequest);
    }
    public static String getAPIToken(Context context) {
        return getSharedPreferences(context).getString("api_token", "");
    }

    public static int getUserID(Context context) {
        return getSharedPreferences(context).getInt("user_id", 0);
    }
    public static boolean isLoggedIn(Context context) {
        return getSharedPreferences(context).getBoolean("loggedIn", false);
    }

    public static ACProgressPie acProgressPieDialog(Context context) {
        progressPie = new ACProgressPie.Builder(context)
                .ringColor(Color.WHITE)
                .pieColor(Color.WHITE)
                .updateType(ACProgressConstant.PIE_AUTO_UPDATE)
                .build();
        progressPie.show();
        return progressPie;
    }

}
