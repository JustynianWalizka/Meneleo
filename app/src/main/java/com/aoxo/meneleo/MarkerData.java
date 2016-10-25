package com.aoxo.meneleo;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tomek on 09.10.2016.
 */

public class MarkerData {

    int type;
    LatLng location;
    String description;
    MarkerOptions marker;
    Date date;
    private String dateFormat = "dd-MMM-yyyy";


    public MarkerData(int type, LatLng location, String description, Date date)
    {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);

        this.date = date;
        this.type = type;
        this.location = location;
        this.description = description+ "\n" + df.format(date);;
        marker = new MarkerOptions().position(
                location).title(description);

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

    }

    public MarkerOptions getMarker()
    {
        return marker;
    }
}
