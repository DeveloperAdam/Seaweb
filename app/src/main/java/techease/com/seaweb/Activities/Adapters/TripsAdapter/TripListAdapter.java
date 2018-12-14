package techease.com.seaweb.Activities.Adapters.TripsAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import techease.com.seaweb.Activities.Activities.FullscreenActivityTrip;
import techease.com.seaweb.Activities.Fragment.Trips.TripDetailsFragment;
import techease.com.seaweb.Activities.Models.Trip.AllTripsDataModel;
import techease.com.seaweb.R;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.ViewHolder> {
    Activity activity;
    List<AllTripsDataModel> tripListModels;
    public  static boolean tripDetail;
    public TripListAdapter(Activity activity, List<AllTripsDataModel> tripListModels) {
        this.activity = activity;
        this.tripListModels = tripListModels;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_trips_list, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final AllTripsDataModel model = tripListModels.get(position);

        holder.tvTitle.setText(model.getTitle());
       // holder.tvDuration.setText(model.getDuration());
      //  holder.tvLocation.setText(model.getLocation());
        Picasso.get().load(model.getImage()).into(holder.imageView);
      //  Picasso.get().load(model.getUserPicture()).into(holder.ivUser);


        holder.llTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tripDetail = true;
                holder.editor.putString("tripid",model.getPid().toString()).commit();

                Intent intent=new Intent(activity, FullscreenActivityTrip.class);
                activity.overridePendingTransition(R.animator.fade_out,R.animator.fade_in);
                activity.startActivity(intent);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tripListModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,ivUser;
        TextView tvTitle,tvLocation,tvDuration;
        LinearLayout llTrip;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        public ViewHolder(View itemView) {
            super(itemView);

            sharedPreferences = activity.getSharedPreferences("abc", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            imageView = itemView.findViewById(R.id.ivTrip);
           // ivUser = itemView.findViewById(R.id.ivTripUser);
            tvTitle = itemView.findViewById(R.id.tvTripTitle);
           // tvLocation = itemView.findViewById(R.id.tvTripLocation);
           // tvDuration = itemView.findViewById(R.id.tvTripDuration);
            llTrip = itemView.findViewById(R.id.llTrip);

        }
    }
}
