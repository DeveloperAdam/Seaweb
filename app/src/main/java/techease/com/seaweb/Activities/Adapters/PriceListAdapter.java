package techease.com.seaweb.Activities.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import techease.com.seaweb.Activities.Models.PriceListModel;
import techease.com.seaweb.R;

public class PriceListAdapter extends RecyclerView.Adapter<PriceListAdapter.ViewHolder> {
    Activity activity;
    List<PriceListModel> priceListModelList;

    public PriceListAdapter(Activity activity, List<PriceListModel> priceListModelList) {
        this.activity = activity;
        this.priceListModelList = priceListModelList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_pricelist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PriceListModel model = priceListModelList.get(position);

        holder.tvPrice.setText(model.getPrice()+"$");
        holder.tvFromTo.setText(model.getFrom()+" - "+model.getTo());
    }

    @Override
    public int getItemCount() {
        return priceListModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFromTo,tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            tvFromTo = itemView.findViewById(R.id.tvFromTo);
            tvPrice = itemView.findViewById(R.id.tvPricePriceList);

        }
    }
}
