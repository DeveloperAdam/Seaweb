package techease.com.seaweb.Activities.Adapters.TripsAdapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import techease.com.seaweb.Activities.Adapters.BoatDetailsAdapter;
import techease.com.seaweb.Activities.Models.FavrtDataModel;
import techease.com.seaweb.Activities.Models.Trip.FavrtTripsDataModel;
import techease.com.seaweb.R;

public class FavrtTripsAdapter extends RecyclerView.Adapter<FavrtTripsAdapter.ViewHolder> {
    Activity activity;
    List<FavrtTripsDataModel> favrtTripsDataModels;


    public FavrtTripsAdapter(Activity activity, List<FavrtTripsDataModel> favrtTripsDataModels) {

        this.activity = activity;
        this.favrtTripsDataModels = favrtTripsDataModels;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_fvrttrips,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FavrtTripsDataModel model = favrtTripsDataModels.get(position);


        Picasso.get().load(model.getBoatImage()).into(holder.ivFvrt);
        Picasso.get().load(model.getUserPicture()).into(holder.ivUser);

        String fvrt = model.getIsFavorite();
        if (fvrt.equals("true"))
        {
            holder.ivHeart.setBackgroundResource(R.drawable.fillheart);
        }

        holder.tvPrice.setText(model.getPrice()+"$");
        holder.tvTitle.setText(model.getTitle());
        holder.tvUsername.setText(model.getUsername());

    }

    @Override
    public int getItemCount() {
        return favrtTripsDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivFvrt,ivUser,ivHeart;
        TextView tvUsername,tvTitle,tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);

            ivFvrt = itemView.findViewById(R.id.ivFvrttrip);
            ivUser = itemView.findViewById(R.id.ivUserFvrtTrips);
            ivHeart = itemView.findViewById(R.id.ivFvrtTripsHeart);
            tvUsername = itemView.findViewById(R.id.tvUsernameFvrtTrips);
            tvTitle = itemView.findViewById(R.id.tvFvrtTripTitle);
            tvPrice = itemView.findViewById(R.id.tvFvrttripPrice);

        }
    }
}
