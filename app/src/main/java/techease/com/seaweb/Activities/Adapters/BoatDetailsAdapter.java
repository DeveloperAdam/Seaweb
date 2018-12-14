package techease.com.seaweb.Activities.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ImageModelBoatDetails model = boatDetailsDataModelList.get(position);

        holder.progressBar.setVisibility(View.VISIBLE);
        Glide.with(context).load(model.getFile()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;

            }
        }).into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return boatDetailsDataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        ProgressBar progressBar;
        public ViewHolder(View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.iv);
            progressBar = itemView.findViewById(R.id.innerProgressBoatDetail);

        }
    }
}
