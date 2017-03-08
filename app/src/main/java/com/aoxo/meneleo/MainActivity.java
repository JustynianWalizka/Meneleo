package com.aoxo.meneleo;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;

import java.util.List;
import java.util.Vector;

public class MainActivity extends FragmentActivity {

    private Button btnBack, btnNext;
    private PagerAdapter mPagerAdapter;
    private ViewPager pager;
    private List<Fragment> fragments = new Vector<Fragment>();
    private LocationListener locationListener;
    private LocationManager locationManager;

    private LinearLayout dotsLayout;
    private TextView[] dots;

    private ScreenProfile profile;
    private ScreenHome home;
    private ScreenMap map;

    private PartyData party;
    private int lCounter = 0;


    Handler h2 = new Handler();
    long starttime = 0;
    Runnable run = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - starttime;
            int seconds = (int) (millis / 1000);

            int minutes = seconds / 60;
            seconds     = seconds % 60;
            int hours = minutes / 60;

            Log.i("Zuzka", String.format("%d:%02d:%02d", hours,minutes, seconds));
            home.updateTimer(String.format("%d:%02d:%02d", hours,minutes, seconds));
            h2.postDelayed(this, 1000);
        }
    };

    public void startPartyPresentation(PartyData pdp)
    {
       // Log.i("Zuzka", "wielkosc listy punktow"+pdp.getMapPoints().size());
        map.startPartyPresentation(pdp);
        pager.setCurrentItem(2);
    }
    public void getData(ScreenMap instance) {
        Log.i("Zuzka", "data request");
     //   instance.setPosition(50.0357316, 19.9000488);
    }


    public void setMarker(MapPlaceType mp, String description) {
        party.setMarker(mp, description);
        map.redrawMapElements();
    }

    public void updateDistance() {
        profile.updateCurrentDistance();

    }

    public void startParty(boolean start) {
        if (start) {

            startGPS();
            starttime = System.currentTimeMillis();
            h2.postDelayed(run,0);
            party = new PartyData();
            map.startTracking(party);
            profile.addElement(party, true);
            setMarker(MapPlaceType.START, "Party start!");


            //  /  // Calendar c = Calendar.getInstance();
            //  /  //  System.out.println("Current time => " + c.getTime());//  SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            // String formattedDate = df.format(c.getTime());
            //    profile.addElement("PartyName","0m",formattedDate,true);
        } else {
            stopGPS();
            h2.removeCallbacks(run);
            map.stopTracking();
            setMarker(MapPlaceType.END, "End of party");
            party.state=1;

        }

    }

    public PartyData getPartyInstance() {
        return party;
    }

    public void profileReady(ScreenProfile fragment) {
        profile = fragment;
    }

    public void mapReady(ScreenMap fragment) {
        map = fragment;

    }

    public void startPageReady(ScreenHome fragment) {
        home = fragment;
    }

    public void startGPS() {

        Log.i("Zuzka", "start gps");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.i("Zuzka", "dupa a nie gps");
            return;
        }
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
    }

    public void stopGPS() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(locationListener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_layout);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        initializePaging();
        //checkPermissions();
        initializeLocationServices();

        // KURWA!!


        btnBack = (Button) findViewById(R.id.btn_back);
        btnNext = (Button) findViewById(R.id.btn_next);
        pager.setCurrentItem(1);
        addBottomDots(1);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int current = getItem(-1);

                if (current >= 0) {
                    // move to next screen
                    pager.setCurrentItem(current);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem(+1);
                if (current < fragments.size()) {
                    // move to next screen
                    pager.setCurrentItem(current);
                }
            }
        });



        Log.i("Zuzka", "inicjalizacja tego dziada: "+lCounter);
        lCounter++;

    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[fragments.size()];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }
    /*private void checkPermissions()
    {
        Log.i("Zuzka", "Check if we have permission for GPS");

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            Log.i("Zuzka", "nie ma wiec leci request");

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    99);


        }
        else
        {
            Log.i("Zuzka", "jest zgoda na gps");
        }
    }*/
    private void initializeLocationServices() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
               // location.
                map.setPosition(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }

    private void initializePaging() {

        fragments.add(Fragment.instantiate(this, ScreenProfile.class.getName()));
        fragments.add(Fragment.instantiate(this, ScreenHome.class.getName()));
        fragments.add(Fragment.instantiate(this, ScreenMap.class.getName()));

        mPagerAdapter = new PagerAdapter(this.getSupportFragmentManager(), fragments);

        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(mPagerAdapter);
        pager.setOffscreenPageLimit(2);
        pager.addOnPageChangeListener(viewPagerPageChangeListener);

    }

    private void initializeMapLocationSettings()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.googleMap.setMyLocationEnabled(true);
    }
    private int getItem(int i) {
        return pager.getCurrentItem() + i;
    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.i("Zuzka", "Przyszedl request code: " + requestCode);
        switch (requestCode) {
            case 99: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {



                    Log.i("Zuzka", "Permission granted for location");

                } else {

                    Log.i("Zuzka", "Cos nie tak z tym szitem");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
*/
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {


        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            if(position==0)
            {
                Log.i("Zuzka", "strona 1");
                btnBack.setVisibility(View.INVISIBLE);
                btnNext.setBackgroundColor(Color.alpha(0));
                btnNext.setTextColor(Color.DKGRAY);


            }
            if(position==1)
            {
                Log.i("Zuzka", "strona 2");
                btnNext.setTextColor(Color.WHITE);
                btnBack.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
                btnBack.setBackgroundColor(Color.alpha(0));
                btnNext.setBackgroundColor(Color.alpha(0));


            }

            if(position==2)
            {
                Log.i("Zuzka", " strona z mapa");
                initializeMapLocationSettings();
                btnBack.setBackgroundResource(R.drawable.rounded_button);

                btnNext.setVisibility(View.INVISIBLE);
                btnBack.setVisibility(View.VISIBLE);
                startGPS(); //samo wylaczy potem gpsa jak ustawi na pozycji

            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {


        }
    };

    @Override
    public void onStart() {
        super.onStart();


    }



    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

    @Override
    public void onStop() {
        super.onStop();


    }
}
