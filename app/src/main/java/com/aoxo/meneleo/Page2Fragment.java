package com.aoxo.meneleo;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    ImageButton showButton;
    TextView counterText;
    TextView tv_partyTimeElapsed;
    private boolean isPartyStarted = false;
    private boolean isAddButtonsVisible = false;
    private LinearLayout LL2;

    public Page2Fragment() {
        // Required empty public constructor
    }

    public void updateTimer(String text)
    {
        tv_partyTimeElapsed.setText(text); // update text of timer on home screen
    }


    private void showButtonClicked()
    {

        isAddButtonsVisible = !isAddButtonsVisible;

        showButton.setBackgroundResource(isAddButtonsVisible ? R.drawable.ic_hide_normal : R.drawable.ic_show_normal);
        startParty.setEnabled(!isAddButtonsVisible);
        LL.setEnabled(isAddButtonsVisible);
        LL2.setEnabled(isAddButtonsVisible);



        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator valueAnimatorX = ValueAnimator.ofFloat(1.0f,0.0f);
        valueAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if(isAddButtonsVisible) {
                    startParty.setAlpha((float) animation.getAnimatedValue());
                    LL.setAlpha(1-((float) animation.getAnimatedValue()));
                    LL2.setAlpha(1-((float) animation.getAnimatedValue()));


                }
                else
                {
                    startParty.setAlpha(1-(float) animation.getAnimatedValue());
                    LL.setAlpha(((float) animation.getAnimatedValue()));
                    LL2.setAlpha(((float) animation.getAnimatedValue()));
                }

                if(LL.getAlpha()== 0)
                {
                    LL.setVisibility(View.INVISIBLE);
                    LL2.setVisibility(View.INVISIBLE);
                }
                else
                {
                    LL.setVisibility(View.VISIBLE);
                    LL2.setVisibility(View.VISIBLE);
                }
            }
        });
        animatorSet.playTogether(valueAnimatorX);
        animatorSet.setDuration(500);
        animatorSet.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_page2, container, false);

        startParty = (ImageButton) v.findViewById(R.id.btnStartParty);
        LL = (LinearLayout) v.findViewById(R.id.pubButtons);


        LL2 = (LinearLayout) v.findViewById(R.id.pubButtons2);

        counterText = (TextView) v.findViewById(R.id.counter_text);
        tv_partyTimeElapsed = (TextView) v.findViewById(R.id.tv_partyTimeElapsed) ;
        tv_partyTimeElapsed.setVisibility(View.INVISIBLE);
        counterText.setVisibility(View.INVISIBLE);
        LL.setAlpha(0);
        LL2.setAlpha(0);
        LL.setEnabled(false);
        LL2.setEnabled(false);


        pubButton = (ImageButton) v.findViewById(R.id.btnPub);
        atmButton = (ImageButton) v.findViewById(R.id.btnAtm);
        otherButton = (ImageButton) v.findViewById(R.id.btnOther);
        showButton = (ImageButton) v.findViewById(R.id.btn_main_show);
        showButton.setVisibility(View.INVISIBLE);

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
                //LL.setVisibility(isPartyStarted ? View.INVISIBLE : View.VISIBLE);
                //LL2.setVisibility(isPartyStarted ? View.INVISIBLE : View.VISIBLE);

                showButton.setVisibility(isPartyStarted ? View.INVISIBLE : View.VISIBLE);
                counterText.setVisibility(isPartyStarted ? View.INVISIBLE : View.VISIBLE);
                tv_partyTimeElapsed.setVisibility(isPartyStarted ? View.INVISIBLE : View.VISIBLE);
                isPartyStarted = !isPartyStarted;
                startParty.setBackgroundResource(isPartyStarted ? R.drawable.ic_stop_btn_normal : R.drawable.start_btn_selector);

            }
        });

     /*   private void ease() {
            AnimatorSet animatorSet = new AnimatorSet();
            ValueAnimator valueAnimatorX = ValueAnimator.ofFloat(fromX,toX, fromX);
            valueAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    view.setTranslationX((float) animation.getAnimatedValue());
                }
            });
            animatorSet.playTogether(valueAnimatorX);
            animatorSet.setDuration(1500);
            animatorSet.start();
        }*/

        showButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showButtonClicked();
            }
        });

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
