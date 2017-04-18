package com.aoxo.meneleo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;


public class ScreenProfile extends Fragment implements View.OnClickListener {

    private View v;
    private Vector<PartyButtonItem> elements = new Vector<PartyButtonItem>();

    private PartyButtonItem pbi;
    public ScreenProfile() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_page1_new, container,
                false);




        Activity act = getActivity();
        if(act instanceof MainActivity)
        {
            ((MainActivity) act).profileReady(this);
        }
        else
        {
            Log.i("dddddddddd", "cos nei tak z activity");
        }

        return v;

    }

    public void updateCurrentDistance()
    {
        pbi.updateDistance();


        Log.i("Zuzka", "Page1: updte distance");
    }
    public void addElement(PartyData party)
    {

        pbi = new PartyButtonItem(getContext(), party);
        pbi.setOnClickListener(this);
        //pbi.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));



       /* pbi.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {


                Intent intent = new Intent(getContext(), PartyDetailsActivity.class);
                intent.putExtra("partyData", );
                startActivity(intent);

        }
    });*/
       /* if(party.state == 0) pbi.setActive();
        pbi.setId(pbi.generateViewId());

        params.setMargins(5,10,5,0);

        if(elements.size()==0)
        {
            params.addRule(RelativeLayout.BELOW, R.id.textView4);
        }
        else
        {
            params.addRule(RelativeLayout.BELOW, elements.get(elements.size()-1).getId());
        }*/

        elements.add(pbi);


        LinearLayout mainLayout = (LinearLayout) v.findViewById(R.id.profileContainer);
        mainLayout.addView(pbi);


    }


    @Override
    public void onClick(View v) {
        Log.i("OnClick: ", "id kliknietego obiektu "+v.getId());
        for(int i=0; i<elements.size(); i++)
        {
            if(v.getId() == elements.get(i).getId())
            {
                Intent intent = new Intent(getContext(), PartyDetailsActivity.class);
                intent.putExtra("partyData", (Parcelable) elements.get(i).getPartyData());
               // getContext().startActivityForResult(intent);
                startActivityForResult(intent,0);
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // Log.i("Party Details", "Party details closed, request code: "+requestCode+ " result code: "+resultCode);
        switch(resultCode)
        {
            case Activity.RESULT_OK: Log.i("Result", " Result OK, some data in data: "+ data.getIntExtra("result",999));
                break;
            case Activity.RESULT_CANCELED:
                Log.i("Result", " Result CANCELLED, NO DATA");
                break;
        }
    }
}
