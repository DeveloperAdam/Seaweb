package techease.com.seaweb.Activities.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import techease.com.seaweb.Activities.Models.GetAllPlacesDataModel;
import techease.com.seaweb.R;

public class ListOfPlacesNamesAdapter extends RecyclerView.Adapter<ListOfPlacesNamesAdapter.ViewHolder> {

    Activity activity;
    List<GetAllPlacesDataModel> dataModelList;


    public ListOfPlacesNamesAdapter(Activity activity, List<GetAllPlacesDataModel> dataModelList) {
        this.activity = activity;
        this.dataModelList = dataModelList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_listofplaces_names, viewGroup, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final GetAllPlacesDataModel model=dataModelList.get(position);

        holder.tvName.setText(model.getName());


    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        public ViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvPlacesNames);
        }
    }
}
