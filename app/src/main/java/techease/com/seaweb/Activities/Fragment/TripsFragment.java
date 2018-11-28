package techease.com.seaweb.Activities.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.comix.overwatch.HiveProgressView;

import java.util.ArrayList;
import java.util.List;

import techease.com.seaweb.Activities.Adapters.TripListAdapter;
import techease.com.seaweb.Activities.Models.TripListModel;
import techease.com.seaweb.R;


public class TripsFragment extends Fragment {

    RecyclerView recyclerView;
    List<TripListModel> tripListModels;
    TripListAdapter tripListAdapter;
    HiveProgressView hiveProgressView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trips, container, false);

        sharedPreferences = getActivity().getSharedPreferences("abc", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        hiveProgressView = view.findViewById(R.id.progressTrip);
        recyclerView = view.findViewById(R.id.rvTrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tripListModels = new ArrayList<>();
        tripListAdapter = new TripListAdapter(getActivity(),tripListModels);
        recyclerView.setAdapter(tripListAdapter);
        hiveProgressView.showContextMenu();

        return view;
    }

}
