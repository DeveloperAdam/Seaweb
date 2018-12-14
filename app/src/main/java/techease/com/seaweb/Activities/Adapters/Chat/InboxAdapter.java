package techease.com.seaweb.Activities.Adapters.Chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import techease.com.seaweb.Activities.Activities.BottomActivity;
import techease.com.seaweb.Activities.Activities.FullscreenActivity;
import techease.com.seaweb.Activities.Adapters.GetAllPlacesAdapter;
import techease.com.seaweb.Activities.Adapters.SearchedBoatsAdapter;
import techease.com.seaweb.Activities.Fragment.BoatDetailFragment;
import techease.com.seaweb.Activities.Fragment.Chatting.MessagesListFragment;
import techease.com.seaweb.Activities.Models.Chat.InboxDataModel;
import techease.com.seaweb.R;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder> {

    Activity activity;
    List<InboxDataModel> inboxDataModels;
    String id,proid;
    public static boolean inbox;

    public InboxAdapter(Activity activity, List<InboxDataModel> inboxDataModels) {

        this.activity = activity;
        this.inboxDataModels = inboxDataModels;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_inbox, viewGroup, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final InboxDataModel model = inboxDataModels.get(position);

        Picasso.get().load(model.getImage()).into(holder.imageView);
        holder.tvTime.setText(model.getLastTime());
        holder.tvName.setText(model.getName());

        inbox = false;

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetAllPlacesAdapter.boatOnLoc = false;
                id = model.getId();
                proid = model.getProId();
                holder.editor.putString("proid",proid).commit();
                holder.editor.putString("ownerid",id).commit();
                inbox = true;
                Intent intent=new Intent(activity, FullscreenActivity.class);
                activity.overridePendingTransition(R.animator.slide_out_up,R.animator.slide_in_up);
                activity.startActivity(intent);
                activity.finish();

//                Fragment fragment = new MessagesListFragment();
//                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
//                fragment.setExitTransition(new Slide(Gravity.LEFT));
//                ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction().replace(R.id.container_trip,fragment).addToBackStack("abc").commit();


            }
        });
    }

    @Override
    public int getItemCount() {
        return inboxDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvName,tvTime;
        LinearLayout linearLayout;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        public ViewHolder(View itemView) {
            super(itemView);

            sharedPreferences = activity.getSharedPreferences("abc", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            imageView = itemView.findViewById(R.id.ivUserPicInbox);
            tvName = itemView.findViewById(R.id.tvNameInbox);
            tvTime = itemView.findViewById(R.id.tvTimeInobx);
            linearLayout = itemView.findViewById(R.id.llInbox);
        }
    }
}
