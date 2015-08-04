package com.brendanburns.colormatch;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

@SuppressWarnings("serial") //with this annotation we are going to hide compiler warning

/**
 * Created by Brendan on 7/27/2015.
 */

public class ProfileList
{
    private static final String TAG = ProfileList.class.getName();
    private static final String FILENAME = "myFile.txt";

    Profile[] myList;
    int numberOfProfiles;
    Context myCon;
    int currentPro;

    public ProfileList(Context c)
    {
        myList = new Profile[10];
        myCon = c;
    }

    public void fileCheck()
    {
        File myFile = myCon.getFileStreamPath(FILENAME);
        if (!myFile.exists())
        {
            writeStartingFile();
        }
    }

    private String readFromFile() throws IOException
    {
        String ret = "";

        try {
            InputStream inputStream = myCon.openFileInput(FILENAME);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e)
        {
            writeStartingFile();
            Log.e(TAG, "File not found: " + e.toString());
        }
        catch (IOException e)
        {
            Log.e(TAG, "Can not read file: " + e.toString());
        }

        return ret;
    }

    //This fuction reads the information from the saves file and puts them into a object which the game can use.
    public void updateProfiles() throws IOException
    {
        String info = readFromFile();

        String[] split = info.split(";");

        numberOfProfiles = 0;
        int x = 0;

        while (x < (split.length - 2))
        {
            String name = split[x];
            x++;
            int matchesDone = Integer.parseInt(split[x]);
            x++;
            double[] d = new double[10];
            for(int y = 0; y < 10; y++)
            {
                d[y] = Double.parseDouble(split[x]);
                x++;
            }
            myList[numberOfProfiles] = new Profile(name, matchesDone, d);
            numberOfProfiles++;
        }
        //Log.e(TAG,"I am GETTING!!!!!! ->" +split[split.length - 1]);
        currentPro = Integer.parseInt(split[split.length - 1]);
    }

    public String printProfiles()
    {
        String out = "";
        for (int x = 0; x < numberOfProfiles; x++)
        {
            out = out + myList[x].name + "";
            out = out + myList[x].matchesDone+ "";
            for (int y = 0; y < 10; y++)
            {
                out = out + myList[x].rollingScore[y]+ "";
            }
        }
        return out;
    }


    public void writeStartingFile()
    {
        Log.e(TAG, "Initialization started.");
        numberOfProfiles = 1;
        double[] d = {0,0,0,0,0,0,0,0,0,0};
        myList[0] = new Profile("Defualt", 0, d );
        writeToFile(profilesToString());
    }

    public void save()
    {
        writeToFile(profilesToString());
    }

    public String profilesToString()
    {
        String out = "";
        for(int x = 0; x < numberOfProfiles; x++)
        {
            out = out + myList[x].name + ";";
            out = out + myList[x].matchesDone + ";";
            for (int y = 0; y < 10; y++)
            {
                out = out + myList[x].rollingScore[y] + ";";
            }
        }
        out = out + currentPro + ";";
        Log.i(TAG, out);
        return out;
    }

    private void writeToFile(String data)
    {
        try
        {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(myCon.openFileOutput(FILENAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e)
        {
            Log.e(TAG, "File write failed: " + e.toString());
        }
    }


    public void addScore(double score)
    {
        myList[currentPro].addNextScore(score);
        save();
    }

    public void addProfile(String name)
    {
        double[] d = {0,0,0,0,0,0,0,0,0,0};
        myList[numberOfProfiles] = new Profile(name, 0, d);
        numberOfProfiles++;
        save();
    }

    public void removeProfile(int number)
    {
        numberOfProfiles--;
        for (int x = number; x < numberOfProfiles; x++)
        {
            myList[x] = myList[x+1];
        }
        myList[9] = null;
        writeToFile(profilesToString());
    }

    public String getScore()
    {
        if (myList[currentPro].matchesDone == 0)
        {
            return "N/A";
        }
        double sum = 0;
        for (int x = 0; (x < 10) && (x <  myList[currentPro].matchesDone); x++)
        {
            sum += myList[currentPro].rollingScore[x];
        }
        if (myList[currentPro].matchesDone < 10)
        {
            return  "" + (sum / myList[currentPro].matchesDone);
        }
        return "" + (sum / 10.0);
    }
}
