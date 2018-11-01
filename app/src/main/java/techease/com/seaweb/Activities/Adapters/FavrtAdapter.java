package techease.com.seaweb.Activities.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        FavrtDataModel model=dataModelList.get(i);

        holder.tvLocation.setText(model.getLocation());
        Glide.with(activity).load(model.getBoatImage()).into(holder.ivPlaceImage);
        holder.tvPrice.setText(model.getPrice());
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPlaceImage=itemView.findViewById(R.id.ivBoatOnLocImage);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvLocation=itemView.findViewById(R.id.tvLoc);
            ivFvrt=itemView.findViewById(R.id.ivBoatOnLocImage);

        }
    }
}
