package techease.com.seaweb.Activities.Fragment.Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import techease.com.seaweb.R;

public class AccountDetailFragment extends Fragment {

    ImageView imageView,ivBack;
    TextView tvFname,tvLname,tvEmail;
    EditText etFname,etLname,etEmail;
    String img,fname,lname,name,email;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_detail, container, false);


        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        img = sharedPreferences.getString("img","");
        email = sharedPreferences.getString("email","");
        name = sharedPreferences.getString("username","");
        String[] splitStr = name.split("\\s+");
        fname = splitStr[0];
        lname = splitStr[1];
        imageView = view.findViewById(R.id.ivAccountPic);
        ivBack = view.findViewById(R.id.ivbackAccount);
        tvFname = view.findViewById(R.id.tvFnameAccount);
        tvLname = view.findViewById(R.id.tvLnameAccount);
        etFname = view.findViewById(R.id.etFnameAccount);
        etLname = view.findViewById(R.id.etLnameAccount);
        tvEmail = view.findViewById(R.id.tvEmailAccount);
        etEmail = view.findViewById(R.id.etEmailAccount);


        etEmail.setText(email);
        etLname.setText(lname);
        etFname.setText(fname);

        Glide.with(getActivity()).load(img).into(imageView);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment=new ProfileFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).addToBackStack("back").commit();
            }
        });

        return view;
    }

}
