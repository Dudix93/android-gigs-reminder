package com.mdodot.gigsreminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class VenuesAdapter extends ArrayAdapter<VenueModel>  {
    private ArrayList<VenueModel> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView venue;
        TextView town;
    }

    public VenuesAdapter(ArrayList<VenueModel> data, Context context){
        super(context, R.layout.array_list_item_venues, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VenueModel venueModel = getItem(position);
        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.array_list_item_venues, parent, false);
            viewHolder.venue = (TextView) convertView.findViewById(R.id.venue_name);
            viewHolder.town = (TextView) convertView.findViewById(R.id.town);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.venue.setText(venueModel.getName());
        viewHolder.town.setText(venueModel.getTown());

        return convertView;
    }
}