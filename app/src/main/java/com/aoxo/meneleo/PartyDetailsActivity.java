package com.aoxo.meneleo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.sql.Date;
import java.util.Calendar;
import java.util.Vector;

public class PartyDetailsActivity extends AppCompatActivity {
    private Vector<PartyPresentationElement> elements = new Vector<PartyPresentationElement>();
    private PartyData pd;
    private Button removeParty;
    private Button showOnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_details);

        pd = getIntent().getExtras().getParcelable("partyData");

        removeParty = (Button) findViewById(R.id.removePartyButton);
        showOnMap = (Button) findViewById(R.id.showOnMapButton);

        removeParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",22);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        showOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",33);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });


        Log.d("CDA", "wielkosc party data: "+pd.getMarkers().size());

        for (int i=0; i<pd.getMarkers().size(); i++)
        {
            Log.d("CDA", "wczytuje element: "+i);
            Log.d("CDA", "wczytuje element: "+pd.getMarkers().get(i).markerType.toString());
            addElement(pd.getMarkers().get(i));
        }

switch(pd.state)
{

    case 0:


            Calendar c = Calendar.getInstance();

            MarkerData tempMD = new MarkerData(MapPlaceType.NOW,pd.getLastLocation(),"", c.getTime());
            if(!pd.noLocation)
            {
                tempMD.setDistance(pd.getDistance());
            }
            addElement(tempMD);

        break;
    default: break;
}


       /* addElement(MapPlaceType.START);
        addElement(MapPlaceType.BEER);
        addElement(MapPlaceType.COOLPLACE);
        addElement(MapPlaceType.DRINK);
        addElement(MapPlaceType.PUB);

        addElement(MapPlaceType.VINE);
        addElement(MapPlaceType.BEER);
        addElement(MapPlaceType.COOLPLACE);
        addElement(MapPlaceType.DRINK);
        addElement(MapPlaceType.PUB);

        addElement(MapPlaceType.VINE);
        addElement(MapPlaceType.BEER);
        addElement(MapPlaceType.BEER);
        addElement(MapPlaceType.WHISKY);

        addElement(MapPlaceType.NOW);


        */



    }

    public void addElement(MarkerData md)
    {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        PartyPresentationElement ppe = new PartyPresentationElement(getBaseContext(), md);

        params.setMargins(0,0,0,0);



           // params.addRule(RelativeLayout.BELOW, elements.get(elements.size()-1).getId());


        elements.add(ppe);


        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.presentationContainer);
        mainLayout.addView(ppe);
       // mainLayout.addView(ppe,params);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED,returnIntent);
        super.onBackPressed();
    }
}
