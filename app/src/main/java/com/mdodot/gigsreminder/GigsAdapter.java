package com.mdodot.gigsreminder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class GigsAdapter extends ArrayAdapter<GigModel> {
    private ArrayList<GigModel> dataSet;
    private GigModel gigModel;
    private Context mContext;

    private static class ViewHolder {
        TextView band;
        TextView town;
        TextView date;
        TextView time;
        ImageView edit;
        ImageView delete;
    }

    public GigsAdapter(ArrayList<GigModel> data, Context context){
        super(context, R.layout.array_list_item_gigs, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        gigModel = getItem(position);
        final ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.array_list_item_gigs, parent, false);
            viewHolder.band = (TextView) convertView.findViewById(R.id.band_name);
            viewHolder.town = (TextView) convertView.findViewById(R.id.town);
            viewHolder.date = (TextView) convertView.findViewById(R.id.eventDate);
            viewHolder.time = (TextView) convertView.findViewById(R.id.eventTime);
            viewHolder.edit = convertView.findViewById(R.id.editIcon);
            viewHolder.delete = convertView.findViewById(R.id.deleteIcon);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.band.setText(gigModel.getBand());
        viewHolder.town.setText(gigModel.getTown());
        viewHolder.date.setText(gigModel.getDate());
        viewHolder.time.setText(gigModel.getTime());

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            private final int gigId = gigModel.getId();
            @Override
            public void onClick(View view) {
                if (mContext instanceof GigsActivity) {
                    DialogFragment deleteGigFragment = new DeleteGigDialogFragment();
                    Bundle args = new Bundle();
                    args.putInt("gigId", gigId);
                    deleteGigFragment.setArguments(args);
                    deleteGigFragment.show(((GigsActivity) mContext).getSupportFragmentManager(), "deleteGig");
                }
            }
        });

        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            private final GigModel event = gigModel;
            @Override
            public void onClick(View view) {
                if (mContext instanceof GigsActivity) {
                    ((GigsActivity) mContext).editGig(event);
                }
            }
        });

        convertView.findViewById(R.id.gig_entry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(view.getContext(), GigActivity.class));
            }
        });

        return convertView;
    }

    public static class DeleteGigDialogFragment extends DialogFragment {
        private int gigId;

        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            gigId = getArguments().getInt("gigId");
            builder.setMessage(R.string.event_delete)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ((GigsActivity)getActivity()).deleteGig(gigId);
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