package techease.com.seaweb.Activities.Adapters.TripsAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import techease.com.seaweb.Activities.Adapters.BookedBoatsAdapter;
import techease.com.seaweb.Activities.Models.Trip.BookedTripsDataModel;
import techease.com.seaweb.R;

public class BookedTripsAdapter extends RecyclerView.Adapter<BookedTripsAdapter.ViewHolder> {

    Activity activity;
    List<BookedTripsDataModel> tripsDataModels;


    public BookedTripsAdapter(Activity activity, List<BookedTripsDataModel> tripsDataModels) {

        this.activity = activity;
        this.tripsDataModels = tripsDataModels;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_service, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        BookedTripsDataModel model = tripsDataModels.get(position);

        holder.tvLocation.setText(model.getLocation());
        if (!model.getImage().equals(""))
        {
            holder.progressBar.setVisibility(View.VISIBLE);
            Glide.with(activity).load(model.getImage()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    holder.progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    holder.progressBar.setVisibility(View.VISIBLE);
                    return false;
                }
            }).into(holder.ivPlaceImage);
        }

        if (!model.getUserPicture().equals(""))
        {
            Glide.with(activity).load(model.getUserPicture()).into(holder.ivUserImage);
        }

        holder.tvPrice.setText(model.getPrice()+"$");
        holder.tvTitle.setText(model.getTitle());

    }

    @Override
    public int getItemCount() {
        return tripsDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPlaceImage,ivUserImage,ivFvrt;
        TextView tvTitle,tvPrice,tvLocation;
        LinearLayout linearLayout;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        ProgressBar progressBar;
        public ViewHolder(View itemView) {
            super(itemView);

            ivPlaceImage=itemView.findViewById(R.id.ivBoatOnLocImage);
            ivUserImage = itemView.findViewById(R.id.ivUserImageBoatonLocation);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvLocation=itemView.findViewById(R.id.tvLoc);
            linearLayout = itemView.findViewById(R.id.llBookedBoats);
            progressBar = itemView.findViewById(R.id.innerProgressBookedBoats);
            ivFvrt=itemView.findViewById(R.id.ivBoatOnLocImage);
            sharedPreferences = activity.getSharedPreferences("abc", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
    }
}
