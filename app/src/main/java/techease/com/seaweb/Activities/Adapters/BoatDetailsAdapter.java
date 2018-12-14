package techease.com.seaweb.Activities.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import techease.com.seaweb.Activities.Models.BoatDetail.BoatDetailDataModel;
import techease.com.seaweb.Activities.Models.BoatDetail.BoatFileModel;
import techease.com.seaweb.Activities.Models.ImageModelBoatDetails;
import techease.com.seaweb.R;

public class BoatDetailsAdapter extends RecyclerView.Adapter<BoatDetailsAdapter.ViewHolder> {

    Activity activity;
    Context context;
    List<ImageModelBoatDetails> boatDetailsDataModelList;


    public BoatDetailsAdapter(Activity activity, List<ImageModelBoatDetails> boatDetailsDataModelList) {
        this.activity=activity;
        this.context=activity;
        this.boatDetailsDataModelList=boatDetailsDataModelList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_image,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageModelBoatDetails model = boatDetailsDataModelList.get(position);

        Glide.with(context).load(model.getFile()).into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return boatDetailsDataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.iv);

        }
    }
}
