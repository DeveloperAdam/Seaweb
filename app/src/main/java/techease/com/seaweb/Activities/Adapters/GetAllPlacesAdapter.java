package techease.com.seaweb.Activities.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import java.util.List;

import techease.com.seaweb.Activities.Activities.FullscreenActivity;
import techease.com.seaweb.Activities.Fragment.TabFragment;
import techease.com.seaweb.Activities.Models.GetAllPlacesDataModel;
import techease.com.seaweb.R;

public class GetAllPlacesAdapter extends RecyclerView.Adapter<GetAllPlacesAdapter.ViewHolder> {

    Activity activity;
    List<GetAllPlacesDataModel> dataModelList;
    public static boolean boatOnLoc ;

    public GetAllPlacesAdapter(Activity activity, List<GetAllPlacesDataModel> dataModelList) {
        this.activity=activity;
        this.dataModelList=dataModelList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_ship_types, viewGroup, false);


        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final GetAllPlacesDataModel model=dataModelList.get(i);

        //Picasso.get().load(model.getPicture()).into(viewHolder.imageView);
        viewHolder.progressBar.setVisibility(View.VISIBLE);
        Glide.with(activity).load(model.getPicture()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                viewHolder.progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(viewHolder.imageView);
        viewHolder.tvPlacename.setText(model.getName());
         viewHolder.placeId=model.getId();

         boatOnLoc = false;
        viewHolder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boatOnLoc = true;
                viewHolder.placeId=model.getId();
                viewHolder.editor.putString("placeid",viewHolder.placeId).commit();

                Intent intent=new Intent(activity, FullscreenActivity.class);
                intent.putExtra("placeid",viewHolder.placeId);
                viewHolder.editor.putString("placeid",viewHolder.placeId).commit();
                activity.overridePendingTransition(R.animator.fade_out,R.animator.fade_in);
                activity.startActivity(intent);
                activity.finish();

            }
        });



    }


    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvPlacename;
        FrameLayout frameLayout;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        ProgressBar progressBar;
        String placeId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.ivPlaceImage);
            tvPlacename=itemView.findViewById(R.id.tvPlaceName);
            frameLayout=itemView.findViewById(R.id.f);
            progressBar = itemView.findViewById(R.id.pbHeaderProgress);
            sharedPreferences = activity.getSharedPreferences("abc", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();

        }
    }
}
