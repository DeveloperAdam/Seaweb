package techease.com.seaweb.Activities.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import techease.com.seaweb.Activities.Fragment.BoatDetailFragment;
import techease.com.seaweb.Activities.Models.BoatDetailsDataModel;
import techease.com.seaweb.Activities.Models.BoatTypeDataModel;
import techease.com.seaweb.R;

public class BoatTypesAdapter extends RecyclerView.Adapter<BoatTypesAdapter.ViewHolder> {
    Activity activity;
    List<BoatTypeDataModel> boatTypeDataModelList;
   public static String boatType;

    private int lastCheckedPosition = -1;
    public BoatTypesAdapter(Activity activity, List<BoatTypeDataModel> boatTypeDataModelList) {
        this .activity = activity;
        this.boatTypeDataModelList = boatTypeDataModelList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_boat_type, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        BoatTypeDataModel model = boatTypeDataModelList.get(position);

        holder.radioButton.setText(model.getBoatType());
        holder.radioButton.setChecked(position == lastCheckedPosition);

        holder.textView.setText(model.getBoatType());

    }

    @Override
    public int getItemCount() {
        return boatTypeDataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RadioButton radioButton;
        TextView textView;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        public ViewHolder(View itemView) {
            super(itemView);
            sharedPreferences = activity.getSharedPreferences("abc", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            radioButton = itemView.findViewById(R.id.radio);
           // textView = itemView.findViewById(R.id.tvBoatname);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastCheckedPosition = getAdapterPosition();
                        boatType = textView.getText().toString();
                        editor.putString("boattype",boatType).commit();

                    notifyDataSetChanged();
                }
            });


        }
    }
}
