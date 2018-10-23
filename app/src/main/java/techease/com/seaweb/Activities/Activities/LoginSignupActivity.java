package techease.com.seaweb.Activities.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Models.SocialLoginResponseModel;
import techease.com.seaweb.Activities.Utils.AlertsUtils;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;

public class LoginSignupActivity extends AppCompatActivity {

    LoginButton btnfb;
    Button UpperFbbtn,upperGooglebtn;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    CallbackManager callbackManager;
    private static final String EMAIL = "email";
    int RC_SIGN_IN=1;
    AccessToken accessToken;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String socialToken,name,email,provider,device_id;
    int user_id;
    String fullname,strEmail,strLoc,img;
    boolean flag=false;
    android.support.v7.app.AlertDialog alertDialog;
    Button btnCreateAcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        ((AppCompatActivity) this).getSupportActionBar().hide();
        setContentView(R.layout.activity_login_signup);

        sharedPreferences = this.getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        device_id= Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        upperGooglebtn=findViewById(R.id.btngoogle);
        signInButton = findViewById(R.id.sign_in_button);
        btnfb=findViewById(R.id.login_button);
        UpperFbbtn=findViewById(R.id.btnFB);
        btnfb.setReadPermissions(Arrays.asList(EMAIL));
        btnCreateAcc=findViewById(R.id.btnCreateAccount);

        btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginSignupActivity.this,FullscreenActivity.class));
                finish();
            }
        });


        UpperFbbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnfb.performClick();
                flag=true;
                callbackManager = CallbackManager.Factory.create();
                LoginManager.getInstance().logInWithReadPermissions(LoginSignupActivity.this, Arrays.asList("email"));
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                final GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.d("zmaFbRes", String.valueOf(response));


                                        try {
                                            socialToken=object.getString("id");
                                            name=object.getString("first_name")+object.getString("last_name");
                                            email=object.getString("email");
                                            provider="Facebook";

                                            Bundle bundle=getFacebookData(object);
                                            Log.d("zmaSocialDetail",socialToken+name+email+provider+device_id);

                                            socialLoginApiCall();




                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Log.d("zmaFbE",e.getMessage().toString());
                                        }

                                    }
                                });
                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location");                         request.setParameters(parameters);
                                request.setParameters(parameters);
                                request.executeAsync();
                                // App code
                            }

                            @Override
                            public void onCancel() {
                                // App code
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                Log.d("zmaFbE",exception.getMessage().toString());
                                // App code
                            }
                        });

            }
        });

        //Fb access token
        accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();




        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        upperGooglebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog == null) {
                    alertDialog = AlertsUtils.createProgressDialog(LoginSignupActivity.this);
                    alertDialog.show();
                }
                signInButton.performClick();
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


        //check for existing login
    //    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);

    }
    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");
            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());
                editor.putString("img",profile_pic.toString()).commit();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("zmaE",String.valueOf(e.getCause()));
                return null;
            }
            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));

            fullname = object.getString("first_name")+" " + object.getString("last_name");
            editor.putString("username",fullname).commit();
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            strEmail = object.getString("email");

            editor.putString("email",strEmail).commit();
            provider = "facebook";
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));
                strLoc=object.getString("location");
                editor.putString("loc",strLoc).commit();
            return bundle;
        }
        catch(JSONException e) {
            Log.d("Error","Error parsing JSON");
        }
        return null;
    }
    private void socialLoginApiCall() {
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<SocialLoginResponseModel> call = services.socialLogin(email,name,device_id,socialToken,provider);
        call.enqueue(new Callback<SocialLoginResponseModel>() {
            @Override
            public void onResponse(Call<SocialLoginResponseModel> call, Response<SocialLoginResponseModel> response) {

                if (response.isSuccessful())
                {
                    if (alertDialog != null)
                    alertDialog.dismiss();
                    Log.d("zmaSocialResp",response.toString());

                    name=response.body().getUser().getFullName();
                    email=response.body().getUser().getEmail();
                    socialToken=response.body().getUser().getToken();
                    user_id=response.body().getUser().getUserId();


                    editor.putString("name",name).commit();
                    editor.putString("email",email).commit();
                    editor.putInt("userid",user_id).commit();
                    editor.putString("token",socialToken).commit();
                    editor.putString("login","login").commit();
                    editor.putString("deviceid",device_id).commit();


                    startActivity(new Intent(LoginSignupActivity.this,BottomActivity.class));
                    finish();
                }
                else
                {
                    if (alertDialog != null)
                        alertDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<SocialLoginResponseModel> call, Throwable t) {
                if (alertDialog != null)
                    alertDialog.dismiss();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (flag==true)
        {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        else
        {
            if (requestCode == RC_SIGN_IN) {
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);



    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.d("zmagoogleresp",account.toString());
            name=account.getDisplayName();
            editor.putString("username",name).commit();
            email=account.getEmail();
            provider="Google";
           socialToken=account.getId();
           img=account.getPhotoUrl().toString();
           editor.putString("img",img).commit();

            Log.d("zmaSocialDetail",socialToken+name+email+provider+device_id);

            if (alertDialog == null) {
                alertDialog = AlertsUtils.createProgressDialog(LoginSignupActivity.this);
                alertDialog.show();
            }
            socialLoginApiCall();

        } catch (ApiException e) {
            if (alertDialog != null)
                alertDialog.dismiss();
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("zmaGoogleExp",e.getMessage().toString());

        }
    }
}
