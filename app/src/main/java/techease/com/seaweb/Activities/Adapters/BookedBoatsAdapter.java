package techease.com.seaweb.Activities.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import java.util.List;

import techease.com.seaweb.Activities.Activities.FullscreenActivity;
import techease.com.seaweb.Activities.Fragment.BoatDetailFragment;
import techease.com.seaweb.Activities.Models.BookedBoatsDataModel;
import techease.com.seaweb.R;

public class BookedBoatsAdapter extends RecyclerView.Adapter<BookedBoatsAdapter.ViewHolder> {

    String boatid;
    Context context;
    Activity activity;
    List<BookedBoatsDataModel> bookedBoatsDataModelList;
    public BookedBoatsAdapter(Activity activity, List<BookedBoatsDataModel> bookedBoatsDataModelList) {
        this.activity=activity;
        this.context=activity;
        this.bookedBoatsDataModelList=bookedBoatsDataModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_service, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final BookedBoatsDataModel model = bookedBoatsDataModelList.get(position);

        holder.tvLocation.setText(model.getLocation());
        holder.progressBar.setVisibility(View.VISIBLE);
        if (!model.getImage().equals(""))
        {
            Glide.with(context).load(model.getImage()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                    holder.progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    holder.progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(holder.ivPlaceImage);
        }



        if (!model.getUserPicture().equals(""))
        {
            Picasso.get().load(model.getUserPicture()).into(holder.ivUserImage);
        }

        holder.tvPrice.setText(model.getPrice()+"$");
        holder.tvTitle.setText(model.getTitle());


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boatid=model.getPid().toString();
                holder.editor.putString("boatid",boatid).commit();
                Fragment fragment = new BoatDetailFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).addToBackStack("abc").commit();


            }
        });

    }

    @Override
    public int getItemCount() {
        return bookedBoatsDataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPlaceImage,ivUserImage,ivFvrt;
        TextView tvTitle,tvPrice,tvLocation;
        LinearLayout linearLayout;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPlaceImage=itemView.findViewById(R.id.ivBoatOnLocImage);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            ivUserImage = itemView.findViewById(R.id.ivUserImageBoatonLocation);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvLocation=itemView.findViewById(R.id.tvLoc);
            progressBar = itemView.findViewById(R.id.innerProgressBookedBoats);
            linearLayout = itemView.findViewById(R.id.llBookedBoats);
            ivFvrt=itemView.findViewById(R.id.ivFvrt);
            sharedPreferences = activity.getSharedPreferences("abc", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            ivFvrt.setVisibility(View.GONE);

        }
    }
}
