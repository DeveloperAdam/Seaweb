package techease.com.seaweb.Activities.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Fragment.BoatDetailFragment;
import techease.com.seaweb.Activities.Models.AddToFavrtResponseModel;
import techease.com.seaweb.Activities.Models.BoatOnLocationModel;
import techease.com.seaweb.Activities.Utils.AlertsUtils;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;

public class BoatsOnLocationAdapter extends RecyclerView.Adapter<BoatsOnLocationAdapter.ViewHolder> {

    android.support.v7.app.AlertDialog alertDialog;
    String boatid,isFvrt;
    boolean flag=false;
    String userid;
    Activity activity;
    List<BoatOnLocationModel> boatsOnLocationDataModels;


    public BoatsOnLocationAdapter(Activity activity, List<BoatOnLocationModel> boatOnLocationModel) {
        this.activity = activity;
        this.boatsOnLocationDataModels = boatOnLocationModel;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_list_service, viewGroup, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final BoatOnLocationModel model=boatsOnLocationDataModels.get(i);
        viewHolder.tvLocation.setText(model.getLocation());
        Picasso.get().load(model.getFile()).into(viewHolder.ivPlaceImage);
        Picasso.get().load(model.getUserImg()).into(viewHolder.ivUserImage);

        viewHolder.tvPrice.setText(model.getFullPrice());
        viewHolder.tvTitle.setText(model.getTitle());
        isFvrt=model.getIs_fvrt();
        if (isFvrt.equals("true"))
        {
            viewHolder.ivFavrt.setBackgroundResource(R.drawable.fillheart);
        }
        else
        {
            viewHolder.ivFavrt.setBackgroundResource(R.drawable.white_heart);
        }
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boatid=model.getPid().toString();
                viewHolder.editor.putString("boatid",boatid).commit();
                Fragment fragment = new BoatDetailFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack("abc").commit();


            }
        });

        
        viewHolder.ivFavrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFvrt=model.getIs_fvrt();
                userid=viewHolder.sharedPreferences.getString("userid","");
                boatid=model.getPid().toString();
                if (alertDialog == null) {
                   alertDialog = AlertsUtils.createProgressDialog(activity);
                    alertDialog.show();
                }
                apicall();
                if (isFvrt.equals("true"))
                {
                    viewHolder.ivFavrt.setBackgroundResource(R.drawable.white_heart);
                }
                else
                {
                    viewHolder.ivFavrt.setBackgroundResource(R.drawable.fillheart);
                }

            }
        });

    }

    private void apicall() {

        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<AddToFavrtResponseModel> call = services.addToFavrt(boatid,String.valueOf(userid));
        call.enqueue(new Callback<AddToFavrtResponseModel>() {
            @Override
            public void onResponse(Call<AddToFavrtResponseModel> call, Response<AddToFavrtResponseModel> response) {

                if (response.isSuccessful())
                {
                    if (alertDialog != null)
                        alertDialog.dismiss();
                    Log.d("zmaAddFavrtBoats",response.toString());
                    Toast.makeText(activity, response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();

                }
                else

                {
                    if (alertDialog != null)
                        alertDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AddToFavrtResponseModel> call, Throwable t) {
                if (alertDialog != null)
                    alertDialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return boatsOnLocationDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        CardView cardView;
        ImageView ivPlaceImage,ivUserImage,ivFavrt;
        TextView tvTitle,tvPrice,tvLocation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPlaceImage=itemView.findViewById(R.id.ivBoatOnLocImage);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvLocation=itemView.findViewById(R.id.tvLoc);
            cardView=itemView.findViewById(R.id.cvBoat);
            ivFavrt= itemView.findViewById(R.id.ivFvrt);
            ivUserImage= itemView.findViewById(R.id.ivUserImageBoatonLocation);
            sharedPreferences = activity.getSharedPreferences("abc", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
    }
}
