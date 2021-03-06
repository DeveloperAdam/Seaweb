package techease.com.seaweb.Activities.Adapters;

import android.app.Activity;
import android.content.Context;
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

import com.squareup.picasso.Picasso;

import java.util.List;

import techease.com.seaweb.Activities.Fragment.BoatDetailFragment;
import techease.com.seaweb.Activities.Models.SearchBoatsFileModel;
import techease.com.seaweb.Activities.Models.SearchedBoatsDataModel;
import techease.com.seaweb.R;

public class SearchedBoatsAdapter extends RecyclerView.Adapter<SearchedBoatsAdapter.Holder> {

    Context context;
    Activity activity;
    String boatid,isFvrt;
    List<SearchedBoatsDataModel> searchedBoatsDataModelList;

    public SearchedBoatsAdapter(Activity activity, List<SearchedBoatsDataModel> searchedBoatsDataModelList) {
        this.activity =activity;
        this.context = activity;
        this.searchedBoatsDataModelList = searchedBoatsDataModelList;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_list_service, viewGroup, false);

        return new Holder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final  Holder viewHolder, int position) {

     final    SearchedBoatsDataModel model = searchedBoatsDataModelList.get(position);

        viewHolder.tvLocation.setText(model.getLocation());
        Picasso.get().load(model.getFiles().get(0).getFile()).into(viewHolder.ivPlaceImage);
        viewHolder.tvPrice.setText(model.getPriceDay());
        viewHolder.tvTitle.setText(model.getTitle());


        if (!model.getUserPicture().equals(""))
        {
            Picasso.get().load(model.getUserPicture()).into(viewHolder.ivUserImage);
        }
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boatid=model.getPid().toString();
                viewHolder.editor.putString("boatid",boatid).commit();
                Fragment fragment = new BoatDetailFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack("abc").commit();


            }
        });


    }

    @Override
    public int getItemCount() {
        return searchedBoatsDataModelList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        LinearLayout linearLayout;
        ImageView ivPlaceImage,ivUserImage,ivFavrt;
        TextView tvTitle,tvPrice,tvLocation;

        public Holder(View itemView) {
            super(itemView);


            ivPlaceImage=itemView.findViewById(R.id.ivBoatOnLocImage);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            ivUserImage = itemView.findViewById(R.id.ivUserImageBoatonLocation);
            tvLocation=itemView.findViewById(R.id.tvLoc);
            linearLayout = itemView.findViewById(R.id.llBookedBoats);
            ivFavrt= itemView.findViewById(R.id.ivFvrt);
            sharedPreferences = activity.getSharedPreferences("abc", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();

            ivFavrt.setBackgroundResource(0);

        }
    }
}
