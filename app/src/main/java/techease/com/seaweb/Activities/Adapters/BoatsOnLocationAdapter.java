package techease.com.seaweb.Activities.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import techease.com.seaweb.Activities.Fragment.BoatDetailFragment;
import techease.com.seaweb.Activities.Fragment.BoatsOnLocationFragment;
import techease.com.seaweb.Activities.Models.BoatOnLocationModel;
import techease.com.seaweb.R;

public class BoatsOnLocationAdapter extends RecyclerView.Adapter<BoatsOnLocationAdapter.ViewHolder> {

    android.support.v7.app.AlertDialog alertDialog;
    String boatid,isFvrt;
    String message;

    String userid;
    Activity activity;
    String type;
    Context context;
    List<BoatOnLocationModel> boatsOnLocationDataModels;

    public BoatsOnLocationAdapter(Activity activity, List<BoatOnLocationModel> boatOnLocationModel) {
        this.activity = activity;
        context = activity;
        this.boatsOnLocationDataModels = boatOnLocationModel;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_list_service, viewGroup, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final BoatOnLocationModel model=boatsOnLocationDataModels.get(i);
        viewHolder.tvLocation.setText(model.getLocation());

        viewHolder.ownername.setText(model.getOwnerName());
        Picasso.get().load(model.getFile()).into(viewHolder.ivPlaceImage);
        if (!model.getUserImg().equals(""))
        {
            Picasso.get().load(model.getUserImg()).into(viewHolder.ivUserImage);
        }


        viewHolder.tvPrice.setText(model.getFullPrice()+"$");
        viewHolder.tvTitle.setText(model.getTitle());
        isFvrt=model.getIs_fvrt();
        if (isFvrt.equals("true"))
        {
            viewHolder.ivFavrt.setBackgroundResource(R.drawable.fillheart);
        }
        else
        {
            viewHolder.ivFavrt.setBackgroundResource(R.drawable.white_heart);
        }
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boatid=model.getPid();
                viewHolder.editor.putString("boatid",boatid).commit();
                Fragment fragment = new BoatDetailFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack("abc").commit();


            }
        });

        
        viewHolder.ivFavrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userid=viewHolder.sharedPreferences.getString("userid","");
                type = "Boat";
                boatid=model.getPid();
                isFvrt=model.getIs_fvrt();

                apicall();

//                if (isFvrt.equals("true"))
//                {
//                    Toast.makeText(activity, "if", Toast.LENGTH_SHORT).show();
//                    viewHolder.ivFavrt.setBackgroundResource(R.drawable.white_heart);
//                    apicall();
//
//                    isFvrt = "false";
//                }
//                else
//                    if (isFvrt.equals("false"))
//                {
//                    Toast.makeText(activity, "else", Toast.LENGTH_SHORT).show();
//                    viewHolder.ivFavrt.setBackgroundResource(R.drawable.fillheart);
//                    apicall();
//                    isFvrt = "true";
//                }



            }
        });

    }

    private void apicall() {
        Log.d("zma",boatid+type+userid);
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://divergense.com/boat/App/favorite_boat"
                , new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("zmaAddFvrt", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    message = jsonObject.getString("message");

                    Fragment fragment = new BoatsOnLocationFragment();
                    ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("error", String.valueOf(error.getCause()));
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid",userid);
                params.put("pro_id",boatid);
                params.put("type",type);
                Log.d("zmaParm",params.toString());
                return params;
            }
        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(activity);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);


    }

    @Override
    public int getItemCount() {
        return boatsOnLocationDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        LinearLayout linearLayout;
        ImageView ivPlaceImage,ivUserImage;
        ImageView ivFavrt;
        TextView tvTitle,tvPrice,tvLocation,ownername;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPlaceImage=itemView.findViewById(R.id.ivBoatOnLocImage);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvLocation=itemView.findViewById(R.id.tvLoc);
            linearLayout = itemView.findViewById(R.id.llBookedBoats);
            ivFavrt= itemView.findViewById(R.id.ivFvrt);
            ownername = itemView.findViewById(R.id.tvOwnerNameBoatsonLo);
            ivUserImage= itemView.findViewById(R.id.ivUserImageBoatonLocation);
            sharedPreferences = activity.getSharedPreferences("abc", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
    }
}
