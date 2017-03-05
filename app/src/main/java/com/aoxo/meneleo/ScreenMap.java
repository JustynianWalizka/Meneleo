package com.aoxo.meneleo;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.GoogleMap.OnCameraMoveCanceledListener;
import com.google.android.gms.maps.GoogleMap.OnCameraMoveListener;
import com.google.android.gms.maps.GoogleMap.OnCameraMoveStartedListener;


public class ScreenMap extends Fragment implements OnCameraMoveStartedListener,
        OnCameraMoveListener,
        OnCameraMoveCanceledListener,
        OnCameraIdleListener,
        OnMapReadyCallback {

    MapView mMapView;
    public GoogleMap googleMap;
    MarkerOptions marker;
    public Location currentPosition;
    Polyline polyline;
    Polyline tmpPolyline;
    private boolean tracking = false;
    boolean autoMoveToPoint = true;
    boolean partyPresentation = false;
    boolean showPlaces = false;

    Button btn_show_layers;
    Button btn_add_thing;

    Button btn_add_pub;
    Button btn_add_cp;
    Button btn_add_other;
    Button btn_add_beer;
    Button btn_add_drink;
    Button btn_add_vine;
    Button btn_add_cognac;
    Button btn_add_whiskey;
    Button btn_add_vodka;



    private PartyData party;
    private PartyData partyPresentationData;
    private Button btn_goToPosition;



    public ScreenMap() {
        // Required empty public constructor

    }

    public void set_visibility_of_place_buttons(boolean visibility)
    {
        btn_add_pub.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
        btn_add_cp.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
        btn_add_other.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
        btn_add_beer.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
        btn_add_drink.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
        btn_add_vine.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
        btn_add_cognac.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
        btn_add_whiskey.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
        btn_add_vodka.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }
    public void show_place_buttons()
    {
        btn_add_pub.setVisibility(View.VISIBLE);
        btn_add_cp.setVisibility(View.VISIBLE);
        btn_add_other.setVisibility(View.VISIBLE);
        btn_add_beer.setVisibility(View.VISIBLE);
        btn_add_drink.setVisibility(View.VISIBLE);
        btn_add_vine.setVisibility(View.VISIBLE);
        btn_add_cognac.setVisibility(View.VISIBLE);
        btn_add_whiskey.setVisibility(View.VISIBLE);
        btn_add_vodka.setVisibility(View.VISIBLE);
    }

    public void hide_place_buttons()
    {
        btn_add_pub.setVisibility(View.INVISIBLE);
        btn_add_cp.setVisibility(View.INVISIBLE);
        btn_add_other.setVisibility(View.INVISIBLE);
        btn_add_beer.setVisibility(View.INVISIBLE);
        btn_add_drink.setVisibility(View.INVISIBLE);
        btn_add_vine.setVisibility(View.INVISIBLE);
        btn_add_cognac.setVisibility(View.INVISIBLE);
        btn_add_whiskey.setVisibility(View.INVISIBLE);
        btn_add_vodka.setVisibility(View.INVISIBLE);
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



        btn_goToPosition = (Button) v.findViewById(R.id.button_gotoPosition);
        btn_add_thing = (Button) v.findViewById(R.id.btn_add_thing);
        btn_show_layers = (Button) v.findViewById(R.id.btn_show_layers);

        btn_add_pub = (Button) v.findViewById(R.id.btn_add_pub);
        btn_add_cp = (Button) v.findViewById(R.id.btn_add_cp);
        btn_add_other = (Button) v.findViewById(R.id.btn_add_other);
        btn_add_beer = (Button) v.findViewById(R.id.btn_add_beer);
        btn_add_drink = (Button) v.findViewById(R.id.btn_add_drink);
        btn_add_vine = (Button) v.findViewById(R.id.btn_add_vine);
        btn_add_cognac = (Button) v.findViewById(R.id.btn_add_coniac);
        btn_add_whiskey = (Button) v.findViewById(R.id.btn_add_whiskey);
        btn_add_vodka  = (Button) v.findViewById(R.id.btn_add_vodka);

        set_visibility_of_place_buttons(false);




        btn_goToPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoMoveToPoint = true;
                partyPresentation = false;
                redrawMapElements();

                if (currentPosition != null) {
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(getCurrentPositionAsLatLng()).zoom(17).build();
                    //marker.position(new LatLng(lat, lon));
                    googleMap.animateCamera(CameraUpdateFactory
                            .newCameraPosition(cameraPosition));
                }

            }
        });
        btn_add_thing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPlaces = !showPlaces;

               set_visibility_of_place_buttons(showPlaces);

            }
        });

        btn_add_pub.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Activity act = getActivity();
                if(act instanceof MainActivity) {

                    ((MainActivity) act).setMarker(MapPlaceType.PUB,"Pub");

                }
            }
        });

        btn_add_cp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Activity act = getActivity();
                if(act instanceof MainActivity) {

                    ((MainActivity) act).setMarker(MapPlaceType.COOLPLACE,"Cool place");

                }
            }
        });

        btn_add_other.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Activity act = getActivity();
                if(act instanceof MainActivity) {

                    ((MainActivity) act).setMarker(MapPlaceType.OTHER,"Other");

                }
            }
        });

        btn_add_beer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Activity act = getActivity();
                if(act instanceof MainActivity) {

                    ((MainActivity) act).setMarker(MapPlaceType.BEER,"Beer");

                }
            }
        });

        btn_add_drink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Activity act = getActivity();
                if(act instanceof MainActivity) {

                    ((MainActivity) act).setMarker(MapPlaceType.DRINK,"Drink");

                }
            }
        });

        btn_add_vine.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Activity act = getActivity();
                if(act instanceof MainActivity) {

                    ((MainActivity) act).setMarker(MapPlaceType.VINE,"Vine");

                }
            }
        });

        btn_add_cognac.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Activity act = getActivity();
                if(act instanceof MainActivity) {

                    ((MainActivity) act).setMarker(MapPlaceType.COGNAC,"Cognac");

                }
            }
        });

        btn_add_whiskey.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Activity act = getActivity();
                if(act instanceof MainActivity) {

                    ((MainActivity) act).setMarker(MapPlaceType.WHISKY,"Whisky");

                }
            }
        });

        btn_add_vodka.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Activity act = getActivity();
                if(act instanceof MainActivity) {

                    ((MainActivity) act).setMarker(MapPlaceType.VODKA,"Vodka");

                }
            }
        });

        return v;
    }



    public void setPosition(Location location) {

        if (tracking) {

            currentPosition = location;
            party.setTrackPoint(location);
          /*  if(party.getMapPoints().size()==1)
            {

                Activity act = getActivity();
                if(act instanceof MainActivity) {

                    ((MainActivity) act).setMarker(MapPlaceType.START,"Start");

                }

            }*/
            //mapPoints.add(currentPosition);
            redrawMapElements();
            Activity act = getActivity();
            if (act instanceof MainActivity) {
                ((MainActivity) act).updateDistance();
            }


        } else {
            currentPosition = location;
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(getCurrentPositionAsLatLng()).zoom(15).build();
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

    public LatLng getCurrentPositionAsLatLng()
    {
        return new LatLng(currentPosition.getLatitude(),currentPosition.getLongitude());
    }
    public void startPartyPresentation(PartyData pdp)
    {
        if(pdp.getLocations().size()>0) {
            partyPresentationData = pdp;
            partyPresentation = true;
            autoMoveToPoint = false;
            redrawMapElements();
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(pdp.getLocationAsLatLngAtIndex(0)).zoom(17).build();
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
            rectOptions.addAll(partyPresentationData.getLocationsAsLatLng());
            rectOptions.color(Color.GREEN);

        }
        else
        {
            rectOptions.addAll(party.getLocationsAsLatLng()).color(Color.BLUE);
        }





        polyline = googleMap.addPolyline(rectOptions);
        //tmpPolyline = googleMap.addPolyline(rectOptionsPresentation);

       // Log.i("Zuzka", "dlugosc map points: "+party.getMapPoints().size());
        for(int i=0; i<party.getMarkers().size(); i++)
        {
            googleMap.addMarker(party.getMarkers().get(i).getMarker());
        }
        if(autoMoveToPoint) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(getCurrentPositionAsLatLng()).zoom(17).build();
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
