package com.aoxo.meneleo;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Page2Fragment extends Fragment {

    View v;
    LinearLayout LL;
    ImageButton startParty;
    ImageButton pubButton;
    ImageButton atmButton;
    ImageButton otherButton;
    TextView start_button_msg;
    TextView tv_partyTimeElapsed;
    private boolean isPartyStarted = false;

    public Page2Fragment() {
        // Required empty public constructor
    }

    public void updateTimer(String text)
    {
        tv_partyTimeElapsed.setText(text); // update text of timer on home screen
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_page2, container, false);

        startParty = (ImageButton) v.findViewById(R.id.btnStartParty);
        LL = (LinearLayout) v.findViewById(R.id.pubButtons);
        LL.setVisibility(View.INVISIBLE);
        start_button_msg = (TextView) v.findViewById(R.id.l_start_btn_msg);
        tv_partyTimeElapsed = (TextView) v.findViewById(R.id.tv_partyTimeElapsed) ;
        tv_partyTimeElapsed.setVisibility(View.INVISIBLE);


        startParty.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.i("dddddddddd", "Start PARTY!!!!!");
                Activity act = getActivity();
                if(act instanceof MainActivity) {

                        ((MainActivity) act).startParty(!isPartyStarted);

                }
                else
                {
                    Log.i("dddddddddd", "cos nei tak z activity");
                }
                LL.setVisibility(isPartyStarted ? View.INVISIBLE : View.VISIBLE);
                start_button_msg.setVisibility(isPartyStarted ? View.VISIBLE : View.INVISIBLE);
                tv_partyTimeElapsed.setVisibility(isPartyStarted ? View.INVISIBLE : View.VISIBLE);
                isPartyStarted = !isPartyStarted;
                startParty.setBackgroundResource(isPartyStarted ? R.drawable.ic_stop_button : R.drawable.start_btn_selector);

             }
        });

        pubButton = (ImageButton) v.findViewById(R.id.btnPub);
        atmButton = (ImageButton) v.findViewById(R.id.btnAtm);
        otherButton = (ImageButton) v.findViewById(R.id.btnOther);





        pubButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Activity act = getActivity();
                if(act instanceof MainActivity) {

                    ((MainActivity) act).setMarker(1,"Pub");

                }
            }
        });

        atmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Activity act = getActivity();
                if(act instanceof MainActivity) {

                    ((MainActivity) act).setMarker(1,"Bankomat");

                }
            }
        });

        otherButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Activity act = getActivity();
                if(act instanceof MainActivity) {

                    ((MainActivity) act).setMarker(1,"Inne");

                }
            }
        });

        Activity act = getActivity();
        if(act instanceof MainActivity) {

            ((MainActivity) act).startPageReady(this);

        }

        return v;
    }




}
