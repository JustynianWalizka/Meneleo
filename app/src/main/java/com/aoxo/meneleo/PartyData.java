package com.aoxo.meneleo;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by tomek on 09.10.2016.
 */

public class PartyData {

    private double distance = 0;
    private Date date;
    private Calendar c;
    private List<LatLng> mapPoints;
    private List<MarkerData> markers;
    private long uid;
    private String dateFormat = "dd-MMM-yyyy";

    public LatLng location;
    public int state; // status imprezy.


    public PartyData()
    {
        c = Calendar.getInstance();
        date = c.getTime();
        uid = date.getTime(); // uid is time in miliseconds

        markers = new ArrayList<MarkerData>();
        mapPoints = new ArrayList<>();
        state = 0;
    }

    public PartyData(long uid, List<LatLng> mapPoints, List<MarkerData> markers, int state)
    {
        this.uid = uid;
        this.mapPoints = mapPoints;
        this.markers = markers;
        this.state = state;
        this.date.setTime(uid);
    }

    public String getStartDateString()
    {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        return df.format(date);

    }

    public long getUid()
    {
        return uid;
    }

    public List<MarkerData> getMarkers()
    {
        return markers;
    }

    public List<LatLng> getMapPoints()
    {
       return mapPoints;
    }

    public void setTrackPoint(LatLng point)
    {
        mapPoints.add(point);
        location=point;
    }

    public void setMarker(MapPlaceType mp, String description)
    {

        markers.add(new MarkerData(mp,location,description,c.getTime()));

    }

    public double getDistance()
    {
        double d=0;
        if(mapPoints.size()>1)
        {
            for(int i=1; i<mapPoints.size(); i++)
            {
                d+= distFrom(
                        (float)mapPoints.get(i-1).latitude,
                        (float)mapPoints.get(i-1).longitude,
                        (float)mapPoints.get(i).latitude,
                        (float)mapPoints.get(i).longitude);
            }
        }


        return d;
    }



    public String getDistanceAsString()
    {
        DecimalFormat df=new DecimalFormat("0.00");
        String dist;
        double d = getDistance();
        //double d = Double.MAX_EXPONENT;
        if(d<1000)
        {
           dist = String.valueOf((int)d)+"m";
        }
        else
        {
            d=d/1000;
            dist=String.valueOf(df.format(d))+"km";
        }
        return dist;
    }

    public static float distFrom(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return (float) (earthRadius * c);

    }

    private double meterDistanceBetweenPoints(float lat_a, float lng_a, float lat_b, float lng_b) {
        float pk = (float) (180.0f/Math.PI);

        float a1 = lat_a / pk;
        float a2 = lng_a / pk;
        float b1 = lat_b / pk;
        float b2 = lng_b / pk;

        double t1 = Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2);
        double t2 = Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2);
        double t3 = Math.sin(a1)*Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return 6366000*tt;
    }




}
