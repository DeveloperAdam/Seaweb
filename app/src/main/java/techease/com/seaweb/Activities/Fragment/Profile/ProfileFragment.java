package techease.com.seaweb.Activities.Fragment.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import de.hdodenhof.circleimageview.CircleImageView;
import techease.com.seaweb.Activities.Activities.BottomActivity;
import techease.com.seaweb.Activities.Activities.FullscreenActivity;
import techease.com.seaweb.Activities.Activities.LoginSignupActivity;
import techease.com.seaweb.Activities.Fragment.BookedBoatsFragment;
import techease.com.seaweb.Activities.Fragment.DatePickerFragment;
import techease.com.seaweb.Activities.Fragment.ListOfPlacesFragment;
import techease.com.seaweb.R;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    ImageView ivProfile;
    LinearLayout llProfile,llMessages,llBookings,llFavourites,llSetting,llLogout,llContactUs;
    TextView tvUsername;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userName,img;
    GoogleApiClient googleApiClient;
    Fragment fragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);




        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        llProfile = view.findViewById(R.id.llProfile);
        llMessages = view.findViewById(R.id.llMessages);
        llBookings = view.findViewById(R.id.llBookings);
        llFavourites = view.findViewById(R.id.llfavourites);
        llSetting = view.findViewById(R.id.llSetting);
        llLogout = view.findViewById(R.id.llLogout);
        llContactUs = view.findViewById(R.id.llContactUs);
        ivProfile = view.findViewById(R.id.ivProfile);

        userName=sharedPreferences.getString("username","");
        img=sharedPreferences.getString("img","");


        tvUsername=view.findViewById(R.id.tvUsername);


        Glide.with(getActivity()).load(img).into(ivProfile);

        tvUsername.setText(userName);


        llContactUs.setOnClickListener(this);
        llLogout.setOnClickListener(this);
        llBookings.setOnClickListener(this);
        llProfile.setOnClickListener(this);
        llMessages.setOnClickListener(this);
        llFavourites.setOnClickListener(this);
        llSetting.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id)
        {
            case R.id.llProfile:
         fragment=new AccountDetailFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack("back").commit();
                break;
            case R.id.llBookings:
                BottomActivity.whichOne = "Bookings";
                startActivity(new Intent(getActivity(), BookedBoatsFragment.class));
                getActivity().finish();
                break;
            case R.id.llMessages:
                break;
            case R.id.llSetting:
                break;
            case R.id.llContactUs:
                fragment = new ContactUsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).setCustomAnimations(R.animator.slide_in_up,R.animator.slide_out_up).addToBackStack("back").commit();
                break;
            case R.id.llLogout:
                editor.putString("token","").commit();
                editor.putString("login","").commit();
                FullscreenActivity.flag = false;
                ListOfPlacesFragment.searchFlag = false;
                LoginManager.getInstance().logOut();
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // ...


                            }
                        });
                editor.clear();
                Toast.makeText(getActivity(),"Logged Out",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), LoginSignupActivity.class));
                getActivity().finish();
                break;
            case R.id.llfavourites:
                BottomActivity.whichOne = "Favourite";
                startActivity(new Intent(getActivity(), BookedBoatsFragment.class));
                getActivity().finish();
                break;
        }
    }
}
