package com.brendanburns.colormatch;



import android.content.Context;
import android.content.res.AssetManager;

import java.io.*;


/**
 * Created by Brendan on 7/27/2015.
 */
public class Profile
{

    public String name;
    public int matchesDone;
    public double rollingScore[];


    public Profile(String n, int md, double[] rs)
    {
        name = n;
        matchesDone = md;
        rollingScore = rs;//there will be 10 scores tracked
    }


    public void addNextScore(double score)
    {
        rollingScore[matchesDone%10] = score;//a Global vereable would be a good idea here for the 10, but I will never change it, so its ok!
        matchesDone++;
    }


}
