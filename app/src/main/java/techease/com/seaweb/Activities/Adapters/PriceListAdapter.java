package techease.com.seaweb.Activities.Adapters;

import android.app.Activity;
import android.content.Context;
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
import android.widget.TextView;

import java.util.List;

import techease.com.seaweb.Activities.Activities.FullscreenActivity;
import techease.com.seaweb.Activities.Fragment.DatePickerBookingFragment;
import techease.com.seaweb.Activities.Models.PriceListModel;
import techease.com.seaweb.R;

public class PriceListAdapter extends RecyclerView.Adapter<PriceListAdapter.ViewHolder> {
    Activity activity;
    List<PriceListModel> priceListModelList;
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public PriceListAdapter(Activity activity, List<PriceListModel> priceListModelList, Context context) {
        this.activity = activity;
        this.priceListModelList = priceListModelList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_pricelist,parent,false);
        sharedPreferences = context.getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final PriceListModel model = priceListModelList.get(position);

        holder.tvPrice.setText(model.getPrice()+"$");
        holder.tvPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new DatePickerBookingFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().
                        replace(R.id.container, fragment).commit();
                editor.putString("total",model.getPrice() ).commit();
            }
        });
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
