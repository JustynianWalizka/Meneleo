package com.aoxo.meneleo;

import android.app.Activity;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
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

public class PartyData implements Parcelable{

    private double distance = 0;
    private Date date;
    private List<MarkerData> markers;
    private List<Location> locations;
    private long uid;
    private String dateFormat = "dd-MMM-yyyy";
    private Calendar c;
    public boolean reparationNeeded = false; // some markers does not have location
    public boolean noLocation = true;
    public int state = 0; // status imprezy.


    public PartyData()
    {
        c = Calendar.getInstance();
        date = c.getTime();
        uid = date.getTime(); // uid is time in miliseconds

        markers = new ArrayList<MarkerData>();
        locations = new ArrayList<Location>();
        state = 0;
    }

    public PartyData(long uid, List<Location> locations, List<MarkerData> markers, int state)
    {
        this.uid = uid;
        this.locations = locations;
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

    public List<Location> getLocations()
    {
       return locations;
    }

    public List<LatLng> getLocationsAsLatLng()
    {
        List<LatLng> tmpList = new ArrayList<>();
        for(int i=0; i<locations.size();i++)
        {
            tmpList.add(getLocationAsLatLngAtIndex(i));
        }
      return tmpList;
    }

    public void setTrackPoint(Location location)
    {
        noLocation = false;
        locations.add(location);
        distance = getDistance();
    }

    public Location getLastLocation()
    {
        Log.d("CDA", "getLastLacation");
        if(locations.size()!=0) {


            Log.d("CDA", "jest git");
            return locations.get(locations.size() - 1);
        }
        else
        {
            Log.d("CDA", "brak lokacji. zwracam null: ");
            return null;
        }
    }


    public LatLng getLocationAsLatLngAtIndex(int index)
    {
        return new LatLng(locations.get(index).getLatitude(),
                          locations.get(index).getLongitude());
    }
    public void setMarker(MapPlaceType mp, String description)
    {
        if(getLastLocation()==null)
        {
            Log.d("CDA", "repair needed!!");
            reparationNeeded = true;
        }
        MarkerData markerData = new MarkerData(mp,getLastLocation(),description,c.getTime());
        markerData.setDistance(distance);

        markers.add(markerData);

    }

    public double getDistance()
    {
        double d=0;
        if(locations.size()>1)
        {
            for(int i=1; i<locations.size(); i++)
            {
                d+= distFrom(
                        (float)locations.get(i-1).getLatitude(),
                        (float)locations.get(i-1).getLongitude(),
                        (float)locations.get(i).getLatitude(),
                        (float)locations.get(i).getLongitude());
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

    public static String getDistanceAsString(double distance)
    {
        DecimalFormat df=new DecimalFormat("0.00");
        String dist;

        //double d = Double.MAX_EXPONENT;
        if(distance<1000)
        {
            dist = String.valueOf((int)distance)+"m";
        }
        else
        {
            distance=distance/1000;
            dist=String.valueOf(df.format(distance))+"km";
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


    //============== parcel shit
    private PartyData(Parcel in) {

        markers = new ArrayList<MarkerData>();
        locations = new ArrayList<Location>();
        date = new Date();

        uid = in.readLong();
        state = in.readInt();
        noLocation = in.readByte() != 0;

        in.readTypedList(markers, MarkerData.CREATOR);
        in.readTypedList(locations,Location.CREATOR);

        date.setTime(uid);
        c = Calendar.getInstance();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        /*
         this.uid = uid;
        this.locations = locations;
        this.markers = markers;
        this.state = state;
        this.date.setTime(uid);
         */
        dest.writeLong(uid);
        dest.writeInt(state);
        dest.writeByte((byte) (noLocation ? 1 : 0));

        dest.writeTypedList(markers);
        dest.writeTypedList(locations);


    }

    public static final Parcelable.Creator<PartyData> CREATOR
            = new Parcelable.Creator<PartyData>() {
        public PartyData createFromParcel(Parcel in) {
            return new PartyData(in);
        }

        public PartyData[] newArray(int size) {
            return new PartyData[size];
        }
    };
}
