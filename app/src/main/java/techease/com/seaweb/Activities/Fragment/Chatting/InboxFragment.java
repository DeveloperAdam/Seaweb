package techease.com.seaweb.Activities.Fragment.Chatting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.comix.overwatch.HiveProgressView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Activities.BottomActivity;
import techease.com.seaweb.Activities.Activities.FullscreenActivity;
import techease.com.seaweb.Activities.Adapters.Chat.InboxAdapter;
import techease.com.seaweb.Activities.Fragment.BoatDetailFragment;
import techease.com.seaweb.Activities.Fragment.Profile.ProfileFragment;
import techease.com.seaweb.Activities.Models.Chat.InboxDataModel;
import techease.com.seaweb.Activities.Models.Chat.InboxResponseModel;
import techease.com.seaweb.Activities.Models.FavrtResponseModel;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;


public class InboxFragment extends Fragment {

    ImageView ivBack;
    RecyclerView recyclerView;
    List<InboxDataModel> inboxDataModels;
    InboxAdapter inboxAdapter;
    HiveProgressView hiveProgressView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userId = sharedPreferences.getString("userid","");
        hiveProgressView = view.findViewById(R.id.progressInbox);
        ivBack = view.findViewById(R.id.ivBackInbox);
        recyclerView = view.findViewById(R.id.rvInbox);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        inboxDataModels = new ArrayList<>();
        inboxAdapter = new InboxAdapter(getActivity(),inboxDataModels);
        recyclerView.setAdapter(inboxAdapter);
        apiCall();


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (BottomActivity.whichOne.equals("Profile"))
                {
                    startActivity(new Intent(getActivity(), BottomActivity.class));
                    getActivity().overridePendingTransition(R.animator.fade_in,R.animator.fade_out);
                    getActivity().finish();
                }
                else
                {
                    Fragment fragment = new BoatDetailFragment();
                    fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                    fragment.setExitTransition(new Slide(Gravity.LEFT));
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
                }



            }
        });
        return view;
    }


    private void apiCall() {
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<InboxResponseModel> call = services.getInbox(String.valueOf(userId));
        call.enqueue(new Callback<InboxResponseModel>() {
            @Override
            public void onResponse(Call<InboxResponseModel> call, Response<InboxResponseModel> response) {

                hiveProgressView.clearAnimation();
                hiveProgressView.setVisibility(View.GONE);
                if (response.isSuccessful())
                {
                    Log.d("zmaInbox",response.toString());

                    inboxDataModels.addAll(response.body().getData());
                    inboxAdapter.notifyDataSetChanged();

                }
                else

                {
                    hiveProgressView.clearAnimation();
                    hiveProgressView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<InboxResponseModel> call, Throwable t) {

                hiveProgressView.clearAnimation();
                hiveProgressView.setVisibility(View.GONE);
            }
        });
    }
}
