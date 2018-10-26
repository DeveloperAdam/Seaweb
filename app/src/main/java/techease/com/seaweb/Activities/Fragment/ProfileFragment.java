package techease.com.seaweb.Activities.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;

import de.hdodenhof.circleimageview.CircleImageView;
import techease.com.seaweb.Activities.Activities.LoginSignupActivity;
import techease.com.seaweb.R;


public class ProfileFragment extends Fragment {

    ImageView ivProfile;
    TextView tvLoc,tvEmail,tvUsername;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userName,email,loc,img;
    Button btnLogout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userName=sharedPreferences.getString("username","");
        email=sharedPreferences.getString("email","");
      //  loc=sharedPreferences.getString("loc","");
        img=sharedPreferences.getString("img","");



        ivProfile=view.findViewById(R.id.ivProfile);
       // tvLoc=view.findViewById(R.id.tvLoc);
        tvEmail=view.findViewById(R.id.tvEmail);
        tvUsername=view.findViewById(R.id.tvUsername);
        btnLogout=view.findViewById(R.id.btnLogout);


        Glide.with(getActivity()).load(img).into(ivProfile);

        tvUsername.setText(userName);
//        tvLoc.setText(loc);
        tvEmail.setText(email);






        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("login","logout").commit();
                editor.putString("token","").commit();
                editor.remove("login").commit();
                editor.remove("userid").commit();
                editor.clear().commit();

                LoginManager.getInstance().logOut();

                startActivity(new Intent(getActivity(), LoginSignupActivity.class));
                getActivity().finish();

            }
        });

        return view;
    }
}
