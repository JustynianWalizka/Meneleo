package com.aoxo.meneleo;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MarkerData implements Parcelable{


    public Location location;
    public String description;
    public MarkerOptions marker;
    public Date date;
    private String dateFormat = "h:mm a";
    public MapPlaceType markerType;
    private int indexOnMapPoints;


    public MarkerData(MapPlaceType mp, Location location, String description, Date date)
    {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        markerType = mp;
        BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker();
        this.date = date;

        this.location = location;
        this.description = description;
        marker = new MarkerOptions();
        marker.position(new LatLng(location.getLatitude(), location.getLongitude()));
        marker.title(description);
        marker.snippet(df.format(date));


        // Changing marker icon
        switch(mp)
        {
            case PUB:       icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_pub);
                break;
            case COOLPLACE: icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_coolplace);
                break;
            case OTHER:     icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_other);
                break;
            case BEER:      icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_beer);
                break;
            case VODKA:     icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_vodka);
                break;
            case WHISKY:    icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_whiskey);
                break;
            case VINE:      icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_vine);
                break;
            case COGNAC:    icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_cognac);
                break;
            case DRINK:     icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_drink);
                break;
            case START:     icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_start);
                break;
            case END:       icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_finish);
                break;
            default:        icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_default);
                break;


        }
        marker.icon(icon);

    }

    public MarkerOptions getMarker()
    {
        return marker;
    }

    public MarkerData(Parcel in)
    {

    }
    public static final Parcelable.Creator<MarkerData> CREATOR
            = new Parcelable.Creator<MarkerData>() {
        public MarkerData createFromParcel(Parcel in) {
            return new MarkerData(in);
        }

        public MarkerData[] newArray(int size) {
            return new MarkerData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeParcelable(location,flags);
        dest.writeString(description);
        dest.writeLong(date.getTime());
        dest.writeString(markerType.toString());



    }
}
