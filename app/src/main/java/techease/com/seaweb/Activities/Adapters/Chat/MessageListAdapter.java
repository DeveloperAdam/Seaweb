package techease.com.seaweb.Activities.Adapters.Chat;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import techease.com.seaweb.Activities.Fragment.Chatting.MessagesListFragment;
import techease.com.seaweb.Activities.Models.Chat.MessageDetailDataModel;
import techease.com.seaweb.Activities.Models.MessageDetailModel;
import techease.com.seaweb.R;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {
    Activity activity;
    List<MessageDetailModel> messageListModels;
    String who;
    public MessageListAdapter(Activity activity, List<MessageDetailModel> messageListModels) {

        this.activity = activity;
        this.messageListModels = messageListModels;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_chatlist, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MessageDetailModel model = messageListModels.get(position);
        MessagesListFragment.tvUser.setText(model.getName());


        holder.tvTime.setText(model.getTime());
        holder.tvName.setText(model.getName());

//        Picasso.get().load(model.getImage()).into(holder.imageView);

        who =  model.getSender();
        String msg;
        if (who.equals("from"))
        {

            holder.tvMessage.setBackgroundResource(R.drawable.client_side_bubble);
            holder.tvMessage.setText(model.getMessage().trim());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 1.0f;
            params.gravity = Gravity.LEFT;

            holder.tvName.setLayoutParams(params);
            holder.tvMessage.setLayoutParams(params);
            holder.tvTime.setLayoutParams(params);

        }
        else
        {
            holder.tvName.setText("");
            holder.tvMessage.setText(model.getMessage().trim());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 1.0f;
            params.gravity = Gravity.RIGHT;

            holder.tvName.setLayoutParams(params);
            holder.tvMessage.setLayoutParams(params);
            holder.tvTime.setLayoutParams(params);
        }

    }

    @Override
    public int getItemCount() {
        return messageListModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvMessage,tvName,tvTime;
        ImageView imageView;
        LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);

            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvName = itemView.findViewById(R.id.text_message_name);
            tvTime = itemView.findViewById(R.id.text_message_time);
          //  imageView = itemView.findViewById(R.id.ivChatUser);

            //linearLayout = itemView.findViewById(R.id.llmsg);
        }
    }
}
