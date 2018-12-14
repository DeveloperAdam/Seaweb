package techease.com.seaweb.Activities.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.security.MessageDigest;

import techease.com.seaweb.Activities.Utils.Network;
import techease.com.seaweb.R;

public class MainActivity extends AppCompatActivity {

    String token;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ((AppCompatActivity) this).getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        token=sharedPreferences.getString("login","");

      //  getHasKey();
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    Log.d("zmatoken",token);
                    if (token.equals("login"))
                    {
                        if ( Network.checkInternetConnection(MainActivity.this)==true)
                        {
                            Log.d("what","if");
                            startActivity(new Intent(MainActivity.this,BottomActivity.class));
                            overridePendingTransition(R.animator.fade_out,R.animator.fade_in);
                            finish();

                        }
                        else {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(getIntent());
                                }
                            });
                        }


                    }
                    else
                    {
                        if ( Network.checkInternetConnection(MainActivity.this)==true) {
                            Log.d("what","else");
                            startActivity(new Intent(MainActivity.this,LoginSignupActivity.class));
                            overridePendingTransition(R.animator.fade_out,R.animator.fade_in);
                            finish();
                        }
                        else {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(getIntent());
                                }
                            });
                        }

                    }



                }
            }
        };
        timer.start();



    }


    void getHasKey()
    {
        //Get Has Key
        try
        {
            PackageInfo info = getPackageManager().getPackageInfo("techease.com.seaweb", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
