package com.aoxo.meneleo;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


import java.util.ArrayList;
import java.util.List;

public class MapViewFragment extends Fragment implements OnMapReadyCallback {
    MapView mMapView;
    private GoogleMap googleMap;
    MarkerOptions marker;
    public LatLng currentPosition;


    Polyline polyline;
    public List<LatLng> mapPoints = new ArrayList<LatLng>();

    public void setPosition(double lat, double lon)
    {
        PolylineOptions rectOptions = new PolylineOptions();
        currentPosition = new LatLng(lat,lon);
        mapPoints.add(currentPosition);

        rectOptions.addAll(mapPoints).color(Color.BLUE);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(lat, lon)).zoom(14).build();
        //marker.position(new LatLng(lat, lon));
        if (polyline!=null) {
            polyline.remove();
        }
        polyline =  googleMap.addPolyline(rectOptions);
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
        //googleMap.addMarker(marker);



    }
    public void setMarker( String txt)
    {
        marker = new MarkerOptions().position(
                currentPosition).title(txt);
        googleMap.addMarker(marker);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflat and return the layout
        View v = inflater.inflate(R.layout.fragment_map_view, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        double latitude = 50.091945;
        double longitude = 19.996079;
        marker = new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("Hello Maps");

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        //googleMap = mMapView.getMap();
        mMapView.getMapAsync(this);



        // Perform any camera updates here
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    public void onMapReady(GoogleMap callbackGoogleMap) {
        googleMap = callbackGoogleMap;
        double latitude = 50.091945;
        double longitude = 19.996079;

        // create marker


        // adding marker
        setPosition(latitude,longitude);
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}