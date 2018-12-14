package techease.com.seaweb.Activities.Fragment.Chatting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.comix.overwatch.HiveProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techease.com.seaweb.Activities.Adapters.Chat.MessageListAdapter;
import techease.com.seaweb.Activities.Adapters.SuggestedPlaceAdapter;
import techease.com.seaweb.Activities.Models.Chat.MessageDetailDataModel;
import techease.com.seaweb.Activities.Models.Chat.MessageDetailResponseModel;
import techease.com.seaweb.Activities.Models.Chat.PriceRequestResponseModel;
import techease.com.seaweb.Activities.Models.MessageDetailModel;
import techease.com.seaweb.Activities.Models.PriceListModel;
import techease.com.seaweb.Activities.Models.SuggestedPlaceModel;
import techease.com.seaweb.Activities.Utils.ApiClient;
import techease.com.seaweb.Activities.Utils.ApiService;
import techease.com.seaweb.R;


public class MessagesListFragment extends Fragment {

    ImageView ivBack;
    EditText etTextArea;
    Button btnSent;
   public static TextView tvUser;
    RecyclerView recyclerView;
    List<MessageDetailModel> messageListModels;
    MessageListAdapter adapter;
    HiveProgressView hiveProgressView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userId,ownerid,message,pro_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messages_list, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        btnSent = view.findViewById(R.id.button_chatbox_send);
        etTextArea = view.findViewById(R.id.edittext_chatbox);
        ivBack = view.findViewById(R.id.ivBackMesgDet);
        hiveProgressView = view.findViewById(R.id.progressMsgList);
        tvUser = view.findViewById(R.id.tvUser);
        pro_id = sharedPreferences.getString("proid","");
        ownerid = sharedPreferences.getString("ownerid","");
        userId = sharedPreferences.getString("userid","");
        recyclerView = view.findViewById(R.id.rvMessageList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        messageListModels = new ArrayList<>();

        btnSent.setEnabled(false);

        hiveProgressView.setVisibility(View.VISIBLE);
        adapter = new MessageListAdapter(getActivity(),messageListModels);
        recyclerView.setAdapter(adapter);
        apiCall();


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new InboxFragment();
                fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();

            }
        });

        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                message = etTextArea.getText().toString();

                if (message.equals(""))
                {
                    btnSent.setEnabled(false);
                }
                else
                {
                    hiveProgressView.setVisibility(View.VISIBLE);
                    apicall();
                    apiCall();
                }

            }
        });


       etTextArea.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               btnSent.setEnabled(false);
           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               btnSent.setEnabled(true);

           }

           @Override
           public void afterTextChanged(Editable editable) {

           }
       });
        return view;
    }

    private void apicall() {

        Log.d("zmaSent",userId+"  "+ownerid+"  "+message+ "  "+pro_id );
        ApiService services = ApiClient.getClient().create(ApiService.class);
        Call<PriceRequestResponseModel> call = services.sentRequest(userId,message,ownerid,pro_id);
        call.enqueue(new Callback<PriceRequestResponseModel>() {
            @Override
            public void onResponse(Call<PriceRequestResponseModel> call, Response<PriceRequestResponseModel> response) {


                if (response.isSuccessful())
                {
                    hiveProgressView.clearAnimation();
                    hiveProgressView.setVisibility(View.GONE);
                    Log.d("zmaPReq",response.toString());

                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(messageListModels.size()-1);
                    etTextArea.setText("");

                }
                else
                {
                    Log.d("zmaPReq",response.toString());
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    hiveProgressView.clearAnimation();
                    hiveProgressView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<PriceRequestResponseModel> call, Throwable t) {
                hiveProgressView.clearAnimation();
                hiveProgressView.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getCause().toString(), Toast.LENGTH_SHORT).show();
                Log.d("zmaPReq",t.getCause().toString());
            }
        });
    }

    private void apiCall() {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://divergense.com/boat/App/getMessages"
                , new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ZmaMsgDetails", response);

                messageListModels.clear();
               hiveProgressView.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int i=0; i<jsonArray.length(); i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        MessageDetailModel model = new MessageDetailModel();
                        model.setMessage(object.getString("message"));
                        model.setName(object.getString("name"));
                        model.setPrice(object.getString("price"));
                        model.setSender(object.getString("sender"));
                        model.setTime(object.getString("time"));
                        model.setImage(object.getString("image"));
                        messageListModels.add(model);
                    }

                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    hiveProgressView.setVisibility(View.GONE);
                    e.printStackTrace();
                    Log.d("error", String.valueOf(e.getCause()));
                }


            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hiveProgressView.setVisibility(View.GONE);
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
                params.put("userid",userId);
                params.put("clientid",ownerid);
                Log.d("zmaParm",params.toString());
                return params;
            }
        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);


//        Log.d("zmaMsgDet",userId+"  "+ownerid);
//        ApiService services = ApiClient.getClient().create(ApiService.class);
//        Call<MessageDetailResponseModel> call = services.getMessageDetails(userId,ownerid);
//        call.enqueue(new Callback<MessageDetailResponseModel>() {
//            @Override
//            public void onResponse(Call<MessageDetailResponseModel> call, Response<MessageDetailResponseModel> response) {
//
//                hiveProgressView.setVisibility(View.GONE);
//                if (response.isSuccessful())
//                {
//                    Log.d("zmaInboxDetail",response.toString());
//
//                    messageListModels.addAll(response.body().getData());
//
//                    adapter.notifyDataSetChanged();
//
//                }
//                else
//
//                {
//                    hiveProgressView.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MessageDetailResponseModel> call, Throwable t) {
//
//                hiveProgressView.setVisibility(View.GONE);
//            }
//        });
    }
}
