package techease.com.seaweb.Activities.Fragment.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import techease.com.seaweb.Activities.Activities.BottomActivity;
import techease.com.seaweb.Activities.Activities.FullscreenActivity;
import techease.com.seaweb.Activities.Activities.LoginSignupActivity;
import techease.com.seaweb.Activities.Activities.TripBottomNavigationActivity;
import techease.com.seaweb.Activities.Fragment.Chatting.InboxFragment;
import techease.com.seaweb.Activities.Fragment.ListOfPlacesFragment;
import techease.com.seaweb.Activities.Fragment.Chatting.MessagesListFragment;
import techease.com.seaweb.R;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    ImageView ivProfile;
    LinearLayout llProfile,llBookings,llFavourites,llSetting,llLogout,llContactUs,llMessages;
    TextView tvUsername;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userName,img;
    GoogleApiClient googleApiClient;
    Fragment fragment;
    public static boolean messages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);




        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        llProfile = view.findViewById(R.id.llProfile);
        llBookings = view.findViewById(R.id.llBookings);
        llFavourites = view.findViewById(R.id.llfavourites);
        llSetting = view.findViewById(R.id.llSetting);
        llLogout = view.findViewById(R.id.llLogout);
        llMessages = view.findViewById(R.id.llMessages);
        llContactUs = view.findViewById(R.id.llContactUs);
        ivProfile = view.findViewById(R.id.ivProfile);

        userName=sharedPreferences.getString("username","");
        img=sharedPreferences.getString("img","");


        tvUsername=view.findViewById(R.id.tvUsername);

        if (!img.equals(""))
        {
            Glide.with(getActivity()).load(img).into(ivProfile);
        }



        tvUsername.setText(userName);


        llContactUs.setOnClickListener(this);
        llLogout.setOnClickListener(this);
        llBookings.setOnClickListener(this);
        llProfile.setOnClickListener(this);
        llFavourites.setOnClickListener(this);
        llSetting.setOnClickListener(this);
        llMessages.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id)
        {
            case R.id.llProfile:
         fragment=new AccountDetailFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).setCustomAnimations(R.animator.slide_in_up,R.animator.slide_out_up).addToBackStack("back").commit();
                break;
            case R.id.llBookings:
                BottomActivity.whichOne = "Bookings";
                startActivity(new Intent(getActivity(), BottomActivity.class));
                getActivity().overridePendingTransition(R.animator.fade_in,R.animator.fade_out);
                getActivity().finish();
                break;
            case R.id.llSetting:
                fragment = new SettingFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).setCustomAnimations(R.animator.slide_in_up,R.animator.slide_out_up).addToBackStack("back").commit();
                break;
            case R.id.llMessages:
                messages = true;
                BottomActivity.whichOne = "Profile";
                startActivity(new Intent(getActivity(), FullscreenActivity.class));
                getActivity().overridePendingTransition(R.animator.fade_in,R.animator.fade_out);
                getActivity().finish();

                break;
            case R.id.llContactUs:
                fragment = new ContactUsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).setCustomAnimations(R.animator.slide_in_up,R.animator.slide_out_up).addToBackStack("back").commit();
                break;
            case R.id.llLogout:

                editor.putString("token","").commit();
                editor.putString("login","").commit();
                editor.clear().commit();
                editor.commit();

                FullscreenActivity.flag = false;
                ListOfPlacesFragment.searchFlag = false;

                LoginManager.getInstance().logOut();
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // ...

                                Log.d("zmaSignOut",status.toString());
                            }
                        });


                Toast.makeText(getActivity(),"Logged Out",Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getActivity(), LoginSignupActivity.class));
                getActivity().finish();

                break;
            case R.id.llfavourites:

                BottomActivity.whichOne = "Favourite";
                startActivity(new Intent(getActivity(), BottomActivity.class));
                break;
        }
    }


    @Override
    public void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        googleApiClient.connect();
        super.onStart();
    }
}
