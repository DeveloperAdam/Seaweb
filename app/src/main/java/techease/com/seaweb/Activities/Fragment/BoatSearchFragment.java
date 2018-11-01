package techease.com.seaweb.Activities.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import techease.com.seaweb.Activities.Models.SearchedBoatsDataModel;
import techease.com.seaweb.R;

public class BoatSearchFragment extends Fragment {

    ImageView ivBack;
    RecyclerView recyclerView;
    List<SearchedBoatsDataModel> searchedBoatsDataModelList;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_boat_search, container, false);


        return view;
    }

}
