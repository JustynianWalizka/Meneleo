package com.aoxo.meneleo;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.FloatMath;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.common.SignInButton;
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
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.GoogleMap.OnCameraMoveCanceledListener;
import com.google.android.gms.maps.GoogleMap.OnCameraMoveListener;
import com.google.android.gms.maps.GoogleMap.OnCameraMoveStartedListener;

import java.util.ArrayList;
import java.util.List;


public class Page3Fragment extends Fragment implements OnCameraMoveStartedListener,
        OnCameraMoveListener,
        OnCameraMoveCanceledListener,
        OnCameraIdleListener,
        OnMapReadyCallback {

    MapView mMapView;
    public GoogleMap googleMap;
    MarkerOptions marker;
    public LatLng currentPosition;
    Polyline polyline;
    Polyline tmpPolyline;
    private boolean tracking = false;
    boolean autoMoveToPoint = true;
    boolean partyPresentation = false;

    Button startParty;
    Button pubButton;
    Button atmButton;
    Button otherButton;

    private PartyData party;
    private PartyData partyPresentationData;
    private Button gotoPositionButton;



    public Page3Fragment() {
        // Required empty public constructor

    }

    public void startTracking(PartyData party) {
        this.party = party;
        tracking = true;
    }

    public void stopTracking() {
        tracking = false;
    }

    @Override
    public void onCameraMoveStarted(int reason) {

        if (reason == OnCameraMoveStartedListener.REASON_GESTURE) {
            /*(Toast.makeText(this, "The user gestured on the map.",
                    Toast.LENGTH_SHORT).show();*/
            autoMoveToPoint = false;
            Log.i("dddddddddd", "Reason of move: user gestures" + reason);
        } else if (reason == OnCameraMoveStartedListener
                .REASON_API_ANIMATION) {
           /* Toast.makeText(this, "The user tapped something on the map.",
                    Toast.LENGTH_SHORT).show();*/
            autoMoveToPoint = false;
            Log.i("dddddddddd", "Reason of move: The user tapped something on the map " + reason);
        } else if (reason == OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
            /*Toast.makeText(this, "The app moved the camera.",
                    Toast.LENGTH_SHORT).show();*/
            Log.i("dddddddddd", "Reason of move: The app moved the camera. " + reason);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       /* android.app.Fragment fragment = android.app.Fragment.instantiate(this, MapViewFragment.class.getName());
        MapViewFragment mapFragment = (MapViewFragment) fragment;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();*/

        View v = inflater.inflate(R.layout.fragment_page3, container,
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

        //googleMap = mMapView.getMap();
        mMapView.getMapAsync(this);

        pubButton = (Button) v.findViewById(R.id.button_map_pub);
        atmButton = (Button) v.findViewById(R.id.button_map_atm);
        otherButton = (Button) v.findViewById(R.id.button_map_other);
        gotoPositionButton = (Button) v.findViewById(R.id.button_gotoPosition);


        pubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity act = getActivity();
                if (act instanceof MainActivity) {

                    ((MainActivity) act).setMarker(1, "Pub");

                }
            }
        });

        atmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity act = getActivity();
                if (act instanceof MainActivity) {

                    ((MainActivity) act).setMarker(1, "Bankomat");

                }
            }
        });

        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity act = getActivity();
                if (act instanceof MainActivity) {

                    ((MainActivity) act).setMarker(1, "Inne");

                }
            }
        });
        gotoPositionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoMoveToPoint = true;
                partyPresentation = false;
                redrawMapElements();

                if (currentPosition != null) {
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(currentPosition).zoom(17).build();
                    //marker.position(new LatLng(lat, lon));
                    googleMap.animateCamera(CameraUpdateFactory
                            .newCameraPosition(cameraPosition));
                }

            }
        });


        return v;
    }


    public void setPosition(double lat, double lon) {
        if (tracking) {

            currentPosition = new LatLng(lat, lon);
            party.setTrackPoint(currentPosition);
            //mapPoints.add(currentPosition);
            redrawMapElements();
            Activity act = getActivity();
            if (act instanceof MainActivity) {
                ((MainActivity) act).updateDistance();
            }


        } else {
            currentPosition = new LatLng(lat, lon);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(lat, lon)).zoom(15).build();
            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
            Activity act = getActivity();
            if (act instanceof MainActivity) {
                ((MainActivity) act).stopGPS();
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap callbackGoogleMap) {
        googleMap = callbackGoogleMap;

        // create marker


        // adding marker
        // setPosition(latitude,longitude);
        googleMap.setOnCameraMoveListener(this);

        googleMap.setOnCameraIdleListener(this);
        googleMap.setOnCameraMoveStartedListener(this);
        googleMap.setOnCameraMoveCanceledListener(this);


        Activity act = getActivity();
        if(act instanceof MainActivity)
        {
            ((MainActivity) act).mapReady(this);
        }

    }

    public void startPartyPresentation(PartyData pdp)
    {
        if(pdp.getMapPoints().size()>0) {
            partyPresentationData = pdp;
            partyPresentation = true;
            autoMoveToPoint = false;
            redrawMapElements();
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(pdp.getMapPoints().get(0)).zoom(17).build();
            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        }
    }
    public void redrawMapElements() {
        googleMap.clear();
        PolylineOptions rectOptions = new PolylineOptions();
        PolylineOptions rectOptionsPresentation = new PolylineOptions();
        if(partyPresentation)
        {

            //rectOptions.addAll(party.getMapPoints()).color(Color.parseColor("#402278d4"));
            rectOptions.addAll(partyPresentationData.getMapPoints()).color(Color.GREEN);

        }
        else
        {
            rectOptions.addAll(party.getMapPoints()).color(Color.BLUE);
        }





        polyline = googleMap.addPolyline(rectOptions);
        //tmpPolyline = googleMap.addPolyline(rectOptionsPresentation);

        for(int i=0; i<party.markersCount; i++)
        {
            googleMap.addMarker(party.getMarkers().get(i).getMarker());
        }
        if(autoMoveToPoint) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(currentPosition).zoom(17).build();
            //marker.position(new LatLng(lat, lon));
            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        }


    }

    @Override
    public void onCameraIdle() {

    }

    @Override
    public void onCameraMoveCanceled() {

    }

    @Override
    public void onCameraMove() {

    }
}
