package com.aoxo.meneleo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tomek on 26.02.2017.
 */

public class PartyPresentationElement extends View implements View.OnClickListener {
    private final static int WIDTH_PADDING = 0;
    private final static int HEIGHT_PADDING = 0;
    private final static int ICON_MARGIN_LEFT = 200;
    private final static int elementHeight = 230;

    private String elementTime = "error";
    private String elementDistance = "error";
    private String elementTitle = "error";
    private String elementDescription = "error";
    private String dateFormat = "h:mm k";



    private Bitmap pp_icon;
    private Bitmap pp_route;
    // private final InternalListener listenerAdapter = new InternalListener();



    public PartyPresentationElement(Context context, MarkerData md) {
        super(context);


        loadImages(context, md.markerType);
        elementTime = md.getDateAsString();
        elementDistance = PartyData.getDistanceAsString(md.getDistance());
        elementDescription = md.description;

        setFocusable(true);
        setBackgroundColor(Color.WHITE);
        //setOnClickListener(listenerAdapter);

        setClickable(true);

    }

    private void loadImages(Context context, MapPlaceType mpt)
    {
        pp_route = BitmapFactory.decodeResource(context.getResources(),R.drawable.pp_route_both);
        switch(mpt)
        {
            case PUB:       pp_icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.pp_pub);
                elementTitle = "Pub";
                break;
            case COOLPLACE: pp_icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.pp_coolplace);
                elementTitle = "Cool place";
                break;
            case OTHER:     pp_icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.pp_other);
                elementTitle = "Other";
                break;
            case BEER:      pp_icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.pp_beer);
                elementTitle = "Beer";
                break;
            case VODKA:     pp_icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.pp_vodka);
                elementTitle = "Vodka";
                break;
            case WHISKY:    pp_icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.pp_vodka);
                elementTitle = "Whisky";
                break;
            case VINE:      pp_icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.pp_vine);
                elementTitle = "Vine";
                break;
            case COGNAC:    pp_icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.pp_cognac);
                elementTitle = "Cognac";
                break;
            case DRINK:     pp_icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.pp_drink);
                elementTitle = "Drink";
                break;
            case START:     pp_icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.pp_start);
                pp_route = BitmapFactory.decodeResource(context.getResources(),R.drawable.pp_route_down);
                elementTitle = "Start";
                break;
            case END:       pp_icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.pp_end);
                pp_route = BitmapFactory.decodeResource(context.getResources(),R.drawable.pp_route_up);
                elementTitle = "End";
                break;
            case NOW:        pp_icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.pp_now);
                pp_route = BitmapFactory.decodeResource(context.getResources(),R.drawable.pp_route_up);
                elementTitle = "NOW";
                break;


        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {



        Paint datePaint = new Paint() ;
        Paint distancePaint = new Paint();
        Paint titlePaint = new Paint();
        Paint descriptionPaint = new Paint();

        distancePaint.setColor(Color.GRAY);
        distancePaint.setTextSize(35);

        titlePaint.setColor(Color.DKGRAY);
        titlePaint.setTextSize(55);

        descriptionPaint.setColor(Color.GRAY);
        descriptionPaint.setTextSize(45);

        datePaint.setColor(Color.DKGRAY);
        datePaint.setTextSize(45);



       // canvas.drawLine(0, elementHeight-1, this.getWidth(), elementHeight-1, linePaint);


        canvas.drawBitmap(pp_icon, ICON_MARGIN_LEFT,0, null);
        canvas.drawBitmap(pp_route, ICON_MARGIN_LEFT,0, null);
        canvas.drawText(elementTime,50,datePaint.getTextSize()+60,datePaint);
        canvas.drawText(elementDistance,50,datePaint.getTextSize()+65 + distancePaint.getTextSize(),distancePaint );
        canvas.drawText(elementTitle,375,titlePaint.getTextSize()+60,titlePaint);
        canvas.drawText(elementDescription,375,titlePaint.getTextSize()+70+ descriptionPaint.getTextSize(),descriptionPaint);
        /*canvas.drawText(label, WIDTH_PADDING, textPaint.getTextSize()+10, textPaint);

        canvas.drawText(party.getStartDateString(), WIDTH_PADDING, textPaint.getTextSize()+10 + datePaint.getTextSize()+15, datePaint);
        canvas.drawText(party.getDistanceAsString(), 730, this.getHeight()- ((this.getHeight()-70)/2) - 10, distanceTextPaint);*/
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(widthMeasureSpec, elementHeight);

    }

    @Override
    public void onClick(View v) {

    }
}
