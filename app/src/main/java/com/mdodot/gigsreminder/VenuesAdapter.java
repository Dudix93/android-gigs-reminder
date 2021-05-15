package com.mdodot.gigsreminder;

import android.content.Context;
import android.content.Intent;
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
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.venue.setText(venueModel.getName());
        viewHolder.town.setText(venueModel.getTown());

        convertView.findViewById(R.id.venue_entry).setOnLongClickListener(new View.OnLongClickListener() {

            private final int venueId = venueModel.getId();

            @Override
            public boolean onLongClick(View view) {
                if (mContext instanceof VenuesActivity) {
                    DialogFragment optionsDialogFragment = new EntryOptionsDialogFragment();
                    Bundle args = new Bundle();
                    args.putInt("id", venueId);
                    args.putSerializable("venue", venueModel);
                    optionsDialogFragment.setArguments(args);
                    optionsDialogFragment.show(((VenuesActivity) mContext).getSupportFragmentManager(), "optionsVenue");
                }
                return true;
            }
        });

        convertView.findViewById(R.id.venue_entry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent venueActivityIntent = new Intent(view.getContext(), VenueActivity.class);
                venueActivityIntent.putExtra("venueName", venueModel.getName());
                venueActivityIntent.putExtra("venueTown", venueModel.getTown());
                venueActivityIntent.putExtra("placeId", venueModel.getPlaceId());
                mContext.startActivity(venueActivityIntent);
            }
        });

        return convertView;
    }
}