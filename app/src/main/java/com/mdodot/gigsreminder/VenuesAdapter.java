package com.mdodot.gigsreminder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
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
            viewHolder.edit = convertView.findViewById(R.id.editIcon);
            viewHolder.delete = convertView.findViewById(R.id.deleteIcon);
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
                    DialogFragment deleteVenueFragment = new DeleteVenueDialogFragment();
                    Bundle args = new Bundle();
                    args.putInt("venueId", venueId);
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
    
    public static class DeleteVenueDialogFragment extends DialogFragment {
        private int venueId;

        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            venueId = getArguments().getInt("venueId");
            builder.setMessage(R.string.event_delete)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ((VenuesActivity)getActivity()).deleteVenue(venueId);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //User cancelled the dialog
                        }
                    });
            return builder.create();
        }
    }
}