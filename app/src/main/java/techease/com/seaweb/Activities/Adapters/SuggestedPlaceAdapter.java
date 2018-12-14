package techease.com.seaweb.Activities.Adapters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import techease.com.seaweb.Activities.Activities.FullscreenActivity;
import techease.com.seaweb.Activities.Fragment.BoatTypeFragment;
import techease.com.seaweb.Activities.Fragment.BoatsOnLocationFragment;
import techease.com.seaweb.Activities.Models.SuggestedPlaceModel;
import techease.com.seaweb.R;

public class SuggestedPlaceAdapter extends RecyclerView.Adapter<SuggestedPlaceAdapter.ViewHolder> {
    Activity activity;
    List<SuggestedPlaceModel> suggestedPlaceModels;
    String id;
    public SuggestedPlaceAdapter(Activity activity, List<SuggestedPlaceModel> suggestedPlaceModels) {

        this.activity = activity;
        this.suggestedPlaceModels = suggestedPlaceModels;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_suggestedloc, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

      final   SuggestedPlaceModel model = suggestedPlaceModels.get(position);

        holder.tvPlace.setText(model.getName());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id=model.getId();
                holder.editor.putString("placeid",id).commit();

                Intent intent=new Intent(activity, FullscreenActivity.class);
                holder.editor.putString("placeid",id).commit();
//                activity.overridePendingTransition(R.animator.fade_in,R.animator.fade_out);
                activity.startActivity(intent);
                activity.finish();

            }
        });
    }

    @Override
    public int getItemCount() {
        return suggestedPlaceModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvPlace;
        RelativeLayout relativeLayout;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        public ViewHolder(View itemView) {
            super(itemView);

            tvPlace = itemView.findViewById(R.id.tvSuggestedPlace);
            relativeLayout = itemView.findViewById(R.id.rlSearched);
            sharedPreferences = activity.getSharedPreferences("abc", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();

        }
    }
}
