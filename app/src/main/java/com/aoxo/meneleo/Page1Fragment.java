package com.aoxo.meneleo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;


public class Page1Fragment extends Fragment {

    private View v;
    private Vector<PartyButtonItem> elements = new Vector<PartyButtonItem>();
    private PartyButtonItem pbi;
    public Page1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_page1, container,
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
    public void addElement(PartyData party, boolean active)
    {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        pbi = new PartyButtonItem(getContext(), party);
        pbi.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Activity act = getActivity();
            if(act instanceof MainActivity)
            {
                ((MainActivity) act).startPartyPresentation(((PartyButtonItem) v).getPartyData());

            }

        }
    });
        if(active) pbi.setActive();
        pbi.setId(pbi.generateViewId());

        params.setMargins(5,10,5,0);

        if(elements.size()==0)
        {
            params.addRule(RelativeLayout.BELOW, R.id.textView4);
        }
        else
        {
            params.addRule(RelativeLayout.BELOW, elements.get(elements.size()-1).getId());
        }

        elements.add(pbi);

        RelativeLayout mainLayout = (RelativeLayout) v.findViewById(R.id.profileContainer);
        mainLayout.addView(pbi,params);


    }


}
