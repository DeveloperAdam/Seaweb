package techease.com.seaweb.Activities.Fragment.Profile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import techease.com.seaweb.R;


public class ContactUsFragment extends Fragment {

    ImageView ivBack;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        ivBack = view.findViewById(R.id.ivBackContact);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Fragment  fragment = new ProfileFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).setCustomAnimations(R.animator.slide_out_up,R.animator.slide_in_up).addToBackStack("back").commit();
            }
        });
        return view;
    }

}
