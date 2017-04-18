package com.aoxo.meneleo;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MarkerData implements Parcelable, Externalizable {

    // values that are stored in database
    private int version = 0;        // all this values will be stored as text
    public Date date;               //
    public MapPlaceType markerType; //
    public String description;      //
    private double distance = 0;    //
    public boolean noLocation = false;
    public Location location;       //

    // -------------------------------

    public MarkerOptions marker;
    private String dateFormat = "H:mm";
    SimpleDateFormat df = new SimpleDateFormat(dateFormat);

    public MarkerData()
    {
        date = new Date();
        marker = new MarkerOptions();
    }
    public MarkerData(MapPlaceType mp, Location location, String description, Date date)
    {
        initializeMarker(mp,location,description,date);

    }

    private void initializeMarker(MapPlaceType mp, Location location, String description, Date date)
    {
        markerType = mp;
        BitmapDescriptor icon;
        this.date = date;

        this.location = location;
        this.description = description;
        marker = new MarkerOptions();
        if(location != null)
        {

            marker.position(new LatLng(location.getLatitude(), location.getLongitude()));
            marker.title(description);
            marker.snippet(df.format(date));
            noLocation=false;
            Log.d("CDA", "ustawiam noLocation na false");
        }
        else
        {
            Log.d("CDA", "ustawiam noLocation na true");
            noLocation=true;
        }



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
    public String getMarkerDataAsString()
    {
        String outputString = "{";
        String outputVersion = Integer.toString(version);
        String outputDate = Long.toString(date.getTime());
        String outputMarkerType = markerType.toString();
        String outputDescription = description.replaceAll("[\\{\\}\\|]",""); // need to remove special characters from description
        String outputDistance = Double.toString(distance);
        String outputNoLocation = Integer.toString(noLocation ? 1 : 0);
        String outputLocation = "";
        if(!noLocation)
        {
            outputLocation = location.getLatitude()+";"+location.getLongitude();
        }
        else
        {
            outputLocation = "null;null";
        }


        outputString += outputVersion         + "|" +
                        outputDate            + "|" +
                        outputMarkerType      + "|" +
                        outputDescription     + "|" +
                        outputDistance        + "|" +
                        outputNoLocation      + "|" +
                        outputLocation;

        outputString += "}";
        Log.d("CDA", "zapisane dane markera: "+outputString);
        return outputString;
    }

    public MarkerData(String markerData)  // create marker from string
    {
        markerData = markerData.replaceAll("[\\{\\}]","");  // remove {} if any
        String[] inputString = markerData.split("\\|");
        if(inputString.length>0) {

            version = Integer.parseInt(inputString[0]);
            Date tmpDate = new Date();
            tmpDate.setTime(Long.parseLong(inputString[1]));
            MapPlaceType tmpMPT = readMapPlaceType(inputString[2]);
            String tmpDesctiption = inputString[3];
            distance = Double.parseDouble(inputString[4]);
            noLocation =  Integer.parseInt(inputString[5])!= 0;

            Location tmpLocation = null;
            if(!noLocation)
            {
                tmpLocation = new Location("Mello Location Provider");
                String [] tmpStringLocation = inputString[6].split(";");
                tmpLocation.setLatitude(Double.parseDouble(tmpStringLocation[0]));
                tmpLocation.setLongitude(Double.parseDouble(tmpStringLocation[1]));
            }
            initializeMarker(tmpMPT, tmpLocation, tmpDesctiption,tmpDate);

        }
        else
        {
            // cos poszlo nie tak
            Log.d("Error: ", "empty data");
        }

    }

    public String getDateAsString()
    {
       return df.format(date);
    }
    public double getDistance()
    {
        return distance;
    }

    public void setDistance(double distance)
    {
        this.distance = distance;
    }

    public MarkerOptions getMarker()
    {
        return marker;
    }

    public void setLocation(Location location)
    {
        if(location != null) {
            this.location = location;
            marker.position(new LatLng(location.getLatitude(), location.getLongitude()));
            marker.title(description);
            marker.snippet(df.format(date));
            noLocation=false;
        }
        else
        {
            noLocation = true;
        }

    }
    private MapPlaceType readMapPlaceType(String name)
    {
        MapPlaceType mpt = MapPlaceType.valueOf(name);



        Log.d("CDA", "wczytany map place type to: "+mpt.toString());
        return mpt;

    }
    public MarkerData(Parcel in)
    {
        date = new Date();

        location = (Location) in.readParcelable(Location.class.getClassLoader());
        description = in.readString();
        date.setTime(in.readLong());


        markerType = readMapPlaceType(in.readString());
        noLocation = in.readByte() != 0;
        distance = in.readDouble();


        marker = new MarkerOptions();
        if(!noLocation)
        {
            marker.position(new LatLng(location.getLatitude(), location.getLongitude()));
            marker.title(description);
            marker.snippet(df.format(date));
        }



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
        dest.writeByte((byte) (noLocation ? 1 : 0));
        dest.writeDouble(distance);




    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {


        out.writeDouble(location.getLatitude());
        out.writeDouble(location.getLongitude());
        out.writeObject(description);
        out.writeLong(date.getTime());
        out.writeObject(markerType.toString());
        out.writeByte((byte) (noLocation ? 1 : 0));
        out.writeDouble(distance);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {



        location = new Location("Location Provider");
        location.setLatitude(in.readDouble());
        location.setLongitude(in.readDouble());

        description =(String) in.readObject();
        date.setTime(in.readLong());


        markerType = readMapPlaceType((String) in.readObject());
        noLocation = in.readByte() != 0;
        distance = in.readDouble();

        if(!noLocation)
        {
            marker.position(new LatLng(location.getLatitude(), location.getLongitude()));
            marker.title(description);
            marker.snippet(df.format(date));
        }
    }
}
