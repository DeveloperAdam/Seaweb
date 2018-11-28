package techease.com.seaweb.Activities.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import techease.com.seaweb.Activities.Models.TripListModel;
import techease.com.seaweb.R;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.ViewHolder> {
    Activity activity;
    List<TripListModel> tripListModels;

    public TripListAdapter(Activity activity, List<TripListModel> tripListModels) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return tripListModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,ivUser;
        TextView tvTitle,tvLocation,tvDuration;
        public ViewHolder(View itemView) {
            super(itemView);

        }
    }
}
