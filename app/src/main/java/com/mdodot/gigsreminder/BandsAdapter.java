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

public class BandsAdapter extends ArrayAdapter<BandModel>  {
    private ArrayList<BandModel> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView band;
    }

    public BandsAdapter(ArrayList<BandModel> data, Context context){
        super(context, R.layout.array_list_item_simple, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final BandModel bandModel = getItem(position);
        final ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.array_list_item_simple, parent, false);
            viewHolder.band = convertView.findViewById(R.id.array_list_simple_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.band.setText(bandModel.getBandName());

        convertView.findViewById(R.id.deleteListItemIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dataSet.isEmpty()) {
                    AddEvent.supportsList.remove(bandModel);
                    AddEvent.supportsAdapter.notifyDataSetChanged();
                    AddEvent.supportsForDeletion.add(bandModel);
                }
            }
        });

        return convertView;
    }
}
