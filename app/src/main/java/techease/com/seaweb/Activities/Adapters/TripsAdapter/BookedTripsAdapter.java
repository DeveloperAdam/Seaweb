package techease.com.seaweb.Activities.Adapters.TripsAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        BookedTripsDataModel model = tripsDataModels.get(position);

        holder.tvLocation.setText(model.getLocation());
        Glide.with(activity).load(model.getImage()).into(holder.ivPlaceImage);
         Glide.with(activity).load(model.getUserPicture()).into(holder.ivUserImage);
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

        public ViewHolder(View itemView) {
            super(itemView);

            ivPlaceImage=itemView.findViewById(R.id.ivBoatOnLocImage);
            ivUserImage = itemView.findViewById(R.id.ivUserImageBoatonLocation);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvLocation=itemView.findViewById(R.id.tvLoc);
            linearLayout = itemView.findViewById(R.id.llBookedBoats);
            ivFvrt=itemView.findViewById(R.id.ivBoatOnLocImage);
            sharedPreferences = activity.getSharedPreferences("abc", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
    }
}
