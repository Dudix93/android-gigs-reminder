package com.mdodot.gigsreminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<GigModel>  {
    private ArrayList<GigModel> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView band;
        TextView town;
    }

    public CustomAdapter(ArrayList<GigModel> data, Context context){
        super(context, R.layout.array_list_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

//    @Override
//    public void onClick(View v) {
//
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GigModel gigModel = getItem(position);
        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.array_list_item, parent, false);
            viewHolder.band = (TextView) convertView.findViewById(R.id.band_name);
            viewHolder.town = (TextView) convertView.findViewById(R.id.town);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.band.setText(gigModel.getBand());
        viewHolder.town.setText(gigModel.getTown());

        return convertView;
    }
}