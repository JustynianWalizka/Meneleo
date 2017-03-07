package com.aoxo.meneleo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.DecimalFormat;

/**
 * Created by tomek on 23.09.2016.
 */

public class PartyButtonItem extends View implements View.OnClickListener {


    private final static int WIDTH_PADDING = 30;
    private final static int HEIGHT_PADDING = 10;
    private final static int buttonHeight = 150;
    private Paint textPaint = new Paint();
    private Paint linePaint = new Paint();
    private Paint datePaint = new Paint();
    private Paint distanceTextPaint = new Paint();
    private String partyDate;
    private String partyDistance;
    private boolean active = false;
    private PartyData party;


    private String label;


    private final Bitmap globe_icon;
   // private final InternalListener listenerAdapter = new InternalListener();

    public void setActive()
    {
        active = true;
    }
    public void setNotActive()
    {
        active = false;
    }
    public PartyButtonItem(Context context, PartyData party) {
        super(context);

        this.party = party;
        this.label = "dupatest";
        this.globe_icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_globe_24);

        this.setOnClickListener(this);

        setFocusable(true);
        setBackgroundColor(Color.WHITE);
        //setOnClickListener(listenerAdapter);

        setClickable(true);

    }

    public PartyData getPartyData()
    {
        return party;
    }
    @Override
    protected void onDraw(Canvas canvas)
    {

        Log.d("CDA", "draw called on party button");
        if(active)
        {
           // this.setBackgroundColor(Color.GREEN);
           // this.setBackgroundResource(R.drawable.bg_1);
            this.setBackgroundColor(Color.WHITE);
        }
        else
        {
            this.setBackgroundColor(Color.WHITE);
        }
        //this.setBackgroundColor(Color.GREEN);
        linePaint.setColor(Color.BLUE);

        linePaint.setStrokeWidth(2);
        textPaint.setTextSize(50);
        textPaint.setColor(Color.BLACK);
        datePaint.setColor(Color.GRAY);
        distanceTextPaint.setColor(Color.LTGRAY);
       // distanceTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        distanceTextPaint.setTextSize(70);
        datePaint.setTextSize(40);
        canvas.drawLine(0, buttonHeight-1, this.getWidth(), buttonHeight-1, linePaint);



        canvas.drawBitmap(globe_icon, this.getWidth() - globe_icon.getWidth()-((this.getHeight()-globe_icon.getWidth())/2),
                                    ((this.getHeight()-globe_icon.getWidth())/2), null);
        canvas.drawText(label, WIDTH_PADDING, textPaint.getTextSize()+10, textPaint);

        canvas.drawText(party.getStartDateString(), WIDTH_PADDING, textPaint.getTextSize()+10 + datePaint.getTextSize()+15, datePaint);
        canvas.drawText(party.getDistanceAsString(), 730, this.getHeight()- ((this.getHeight()-70)/2) - 10, distanceTextPaint);
    }
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        setMeasuredDimension(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec)
         {
                 int preferred = 50;//image.getWidth()+20;
                 return getMeasurement(measureSpec, preferred);
         }

    private int measureHeight(int measureSpec)
         {
                 int preferred = buttonHeight;
                 return getMeasurement(measureSpec, preferred);
         }

    public void updateDistance()
    {

        this.invalidate();
    }
    private int getMeasurement(int measureSpec, int preferred)
         {
                int specSize = MeasureSpec.getSize(measureSpec);
                 int measurement = 0;

                 switch(MeasureSpec.getMode(measureSpec))
                 {
                        case MeasureSpec.EXACTLY:
                                 // This means the width of this view has been given.
                                 measurement = specSize;
                                 break;
                         case MeasureSpec.AT_MOST:
                                // Take the minimum of the preferred size and what
                                // we were told to be.
                                 measurement = Math.min(preferred, specSize);
                                 break;
                        default:
                                 measurement = preferred;
                                 break;
                     }

                 return measurement;
            }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), PartyDetailsActivity.class);
        intent.putExtra("partyData", this.party);
        getContext().startActivity(intent);
    }


}
