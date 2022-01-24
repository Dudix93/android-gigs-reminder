package com.mdodot.gigsreminder;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BandsAdapter extends ArrayAdapter<BandModel>  {
    private ArrayList<BandModel> dataSet;
    Activity mContext;

    private static class ViewHolder {
        TextView band;
    }

    public BandsAdapter(ArrayList<BandModel> data, Activity context){
        super(context, R.layout.array_list_item_w_deletion, data);
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
            convertView = inflater.inflate(R.layout.array_list_item_w_deletion, parent, false);
            viewHolder.band = convertView.findViewById(R.id.array_list_simple_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.band.setText(bandModel.getBandName());

        if (getContext().getClass() == AddEvent.class) {
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
        }
        else {
            convertView.findViewById(R.id.deleteListItemIcon).setVisibility(View.INVISIBLE);
            viewHolder.band.setGravity(Gravity.CENTER_HORIZONTAL);
        }

        return convertView;
    }
}
