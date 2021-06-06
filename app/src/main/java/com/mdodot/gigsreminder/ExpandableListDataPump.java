package com.mdodot.gigsreminder;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData(Context context, String placeId, Cursor cursor) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        if (((cursor != null) && (cursor.getCount() > 0))) {

            List<String> events = new ArrayList<String>();
            int eventBandPos = cursor.getColumnIndex(GigEntry.COL_EVENT_BAND);
            int eventDatePos = cursor.getColumnIndex(GigEntry.COL_EVENT_DATE);

            while (cursor.moveToNext()) {
                String band = cursor.getString(eventBandPos);
                String date = cursor.getString(eventDatePos);
                events.add(band);
            }
            cursor.close();

            expandableListDetail.put(context.getResources().getString(R.string.upcoming_events), events);

        } else {
            expandableListDetail.put(context.getResources().getString(R.string.no_upcoming_events), null);
        }
        return expandableListDetail;
    }
}
