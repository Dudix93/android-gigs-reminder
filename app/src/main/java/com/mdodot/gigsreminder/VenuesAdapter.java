package com.mdodot.gigsreminder;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class VenuesAdapter extends ArrayAdapter<VenueModel>  {
    private ArrayList<VenueModel> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView venue;
        TextView town;
        ImageView edit;
        ImageView delete;
    }

    public VenuesAdapter(ArrayList<VenueModel> data, Context context){
        super(context, R.layout.array_list_item_venues, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final VenueModel venueModel = getItem(position);
        final ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.array_list_item_venues, parent, false);
            viewHolder.venue = convertView.findViewById(R.id.venue_name);
            viewHolder.town = convertView.findViewById(R.id.town);
            viewHolder.edit = convertView.findViewById(R.id.editVenueIcon);
            viewHolder.delete = convertView.findViewById(R.id.deleteVenueIcon);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.venue.setText(venueModel.getName());
        viewHolder.town.setText(venueModel.getTown());

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            private final int venueId = venueModel.getId();
            @Override
            public void onClick(View view) {
                if (mContext instanceof VenuesActivity) {
                    DialogFragment deleteVenueFragment = new DeleteDialogFragment();
                    Bundle args = new Bundle();
                    args.putInt("id", venueId);
                    args.putInt("msg", R.string.venue_delete);
                    deleteVenueFragment.setArguments(args);
                    deleteVenueFragment.show(((VenuesActivity) mContext).getSupportFragmentManager(), "deleteVenue");
                }
            }
        });

        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            private final VenueModel venue = venueModel;
            @Override
            public void onClick(View view) {
                if (mContext instanceof VenuesActivity) {
                    ((VenuesActivity) mContext).editVenue(venue);
                }
            }
        });

        return convertView;
    }
}