package com.brendanburns.colormatch;



/*
 * Created by Brendan on 7/22/2015.
 *
 */


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;


public class DrawView  extends View
{

    Paint paint;
    int top;
    int bot;

    int topV; //the V versions of the top and bot colors are in hue values instead of Color class form.
    int botV;

    public DrawView(Context context) {
        super(context);
        init();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    //The initialization that needs to be run on creation.
    private void init(){
        setBackgroundColor(Color.argb(0, 0, 0, 0));
        paint = new Paint();
        paint.setColor(top);
        setColor(0);
        randomizeTop();
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        int workingSpaceHeight = getMeasuredHeight() - 120;

        paint.setColor(top);
        canvas.drawRect(30, 30, getMeasuredWidth() - 30, workingSpaceHeight / 2 - 15, paint);

        paint.setColor(bot);
        canvas.drawRect(30, workingSpaceHeight / 2 + 15, getMeasuredWidth() - 30, workingSpaceHeight - 30, paint);
    }

    //sets the color for the player selected color box by taking in an int that represents a hue (between 0-1529)
    public void setColor(int x) {
        botV = x;
        bot = convertCol(x);
        this.invalidate();
    }

    //Chooses a new color for the player to match.
    public void randomizeTop()
    {
        Random rand = new Random();
        topV = rand.nextInt(1530);
        top =  convertCol(topV);
        this.invalidate();
    }

    //This function converts a hue value (between 0-1529) to the actual int that paint uses.
    private int convertCol(int x)
    {
        int r = 0;
        int g = 0;
        int b = 0;

        if(x < 255)
        {
            r = 255;
            g = x;
            b = 0;
        }
        else if(x < 510)
        {
            r = 254 - (x-255);//the 254 adresses the fact at 255 we want 254 becuse of x starting at zero.
            g = 255;
            b = 0;
        }
        else if(x < 765)
        {
            r = 0;
            g = 255;
            b = (x-509);
        }
        else if(x < 1020)
        {
            r = 0;
            g = 254 - (x-765);
            b = 255;
        }
        else if(x < 1275)
        {
            r = (x-1019);
            g = 0;
            b = 255;
        }
        else
        {
            r = 255;
            g = 0;
            b = 254 - (x-1275);
        }
        return Color.argb(255, r, g, b);
    }

    public double computerScore()
    {
        double diff = ((botV - topV) / 1530.0) * 100.0;
        if (diff < 0) diff = diff * -1.0;
        if (diff > 85) diff = 100.0 - diff;
        return diff;
    }
}