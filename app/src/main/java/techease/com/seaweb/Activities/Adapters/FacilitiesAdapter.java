package techease.com.seaweb.Activities.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import techease.com.seaweb.Activities.Models.FacilitiesGroupModel;
import techease.com.seaweb.Activities.Models.FacilitiesModel;
import techease.com.seaweb.Activities.Models.FacilitiesSubItemModel;
import techease.com.seaweb.R;

public class FacilitiesAdapter extends BaseExpandableListAdapter {
    Activity activity;
    List<FacilitiesGroupModel> listDataHeadings;
    HashMap<FacilitiesGroupModel, List<FacilitiesSubItemModel>> listDataChild;
    private Context _context;

    public FacilitiesAdapter(Activity activity, List<FacilitiesGroupModel> listDataHeadings, HashMap<FacilitiesGroupModel, List<FacilitiesSubItemModel>> listDataChild) {

        this._context = activity;
        this.activity = activity;
        this.listDataHeadings = listDataHeadings;
        this.listDataChild = listDataChild;

        }


    @Override
    public int getGroupCount() {
        return this.listDataHeadings.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeadings.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeadings.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeadings.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        FacilitiesGroupModel headerTitle = (FacilitiesGroupModel) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.custom_facilities, null);
        }

        TextView lblListHeader = convertView
                .findViewById(R.id.tvFacilityHeading);
        lblListHeader.setText(headerTitle.getHeading());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final FacilitiesSubItemModel childText = (FacilitiesSubItemModel) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.custom_facilities_subitem, null);

        }

        TextView tvName = convertView.findViewById(R.id.tvFacilitiesSubItem);
        tvName.setText(childText.getName());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
