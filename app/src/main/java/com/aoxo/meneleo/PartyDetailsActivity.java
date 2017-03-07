package com.aoxo.meneleo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Vector;

public class PartyDetailsActivity extends AppCompatActivity {
    private Vector<PartyPresentationElement> elements = new Vector<PartyPresentationElement>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PartyData pd = getIntent().getExtras().getParcelable("partyData");

        for (int i=0; i<pd.getMarkers().size(); i++)
        {
            addElement(pd.getMarkers().get(i).markerType);
        }


        setContentView(R.layout.activity_party_details);
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

    public void addElement(MapPlaceType mpt)
    {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        PartyPresentationElement ppe = new PartyPresentationElement(getBaseContext(), mpt);

        params.setMargins(0,0,0,0);



           // params.addRule(RelativeLayout.BELOW, elements.get(elements.size()-1).getId());


        elements.add(ppe);

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.presentationContainer);
        mainLayout.addView(ppe);
       // mainLayout.addView(ppe,params);
    }
}
