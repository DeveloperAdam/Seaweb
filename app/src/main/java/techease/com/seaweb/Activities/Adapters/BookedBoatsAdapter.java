package techease.com.seaweb.Activities.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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
        Glide.with(context).load(model.getImage()).into(holder.ivPlaceImage);
        // Glide.with(context).load(model.getUserPicture()).into(holder.ivUserImage);
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

//
//                FullscreenActivity.flag = true;
//
//
//                Intent intent = new Intent(activity,FullscreenActivity.class);
//                intent.putExtra("boatid",boatid);
//                activity.startActivity(intent);

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPlaceImage=itemView.findViewById(R.id.ivBoatOnLocImage);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvLocation=itemView.findViewById(R.id.tvLoc);
            linearLayout = itemView.findViewById(R.id.llBookedBoats);
            ivFvrt=itemView.findViewById(R.id.ivBoatOnLocImage);
            sharedPreferences = activity.getSharedPreferences("abc", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
         //   ivFvrt.setVisibility(View.INVISIBLE);

        }
    }
}
