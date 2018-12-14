package techease.com.seaweb.Activities.Adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import techease.com.seaweb.Activities.Models.BoatTypeDataModel;
import techease.com.seaweb.Activities.Models.FavrtDataModel;
import techease.com.seaweb.R;

public class FavrtAdapter extends RecyclerView.Adapter<FavrtAdapter.ViewHolder> {

    Activity activity;
    List<FavrtDataModel> dataModelList;
    String isFvrt;
    public FavrtAdapter(Activity activity, List<FavrtDataModel> dataModelList) {
        this.activity=activity;
        this.dataModelList=dataModelList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_list_service, viewGroup, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {

        FavrtDataModel model=dataModelList.get(i);

        holder.tvLocation.setText(model.getLocation());
        if (!model.getBoatImage().equals(""))
        {
            holder.progressBar.setVisibility(View.VISIBLE);
            Glide.with(activity).load(model.getBoatImage()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    holder.progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    holder.progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(holder.ivPlaceImage);
        }

        if (!model.getUserPicture().equals(""))
        {
            Picasso.get().load(model.getUserPicture()).into(holder.ivUserImage);
        }

        holder.tvPrice.setText(model.getPriceDay());
        holder.tvTitle.setText(model.getTitle());

        isFvrt=model.getIsFavorite();
        Log.d("isFvrt",isFvrt);
        if (isFvrt.equals("true"))
        {
            holder.ivFvrt.setBackgroundResource(R.drawable.fillheart);
        }
        else
        {
            holder.ivFvrt.setBackgroundResource(R.drawable.white_heart);
        }

    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPlaceImage,ivUserImage,ivFvrt;
        TextView tvTitle,tvPrice,tvLocation;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPlaceImage=itemView.findViewById(R.id.ivBoatOnLocImage);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvLocation=itemView.findViewById(R.id.tvLoc);
            ivUserImage = itemView.findViewById(R.id.ivUserImageBoatonLocation);
            ivFvrt=itemView.findViewById(R.id.ivFvrt);
            progressBar = itemView.findViewById(R.id.innerProgressBookedBoats);

        }
    }
}
