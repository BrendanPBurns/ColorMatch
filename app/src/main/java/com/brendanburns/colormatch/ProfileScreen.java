package com.brendanburns.colormatch;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ProfileScreen extends ActionBarActivity {
    public int over = 0;
    ProfileList playersInfo;
    TextView Stats;
    EditText input;
    Spinner players;
    Button addP, removeP, select;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);


        playersInfo = new ProfileList(this);
        try
        {
            playersInfo.updateProfiles();
        }
        catch (IOException e)
        {

        }
        Stats = (TextView) findViewById(R.id.ProfileInfo);
        players = (Spinner) findViewById(R.id.playerList);
        list = new ArrayList<String>();
        for(int x = 0; x < playersInfo.numberOfProfiles; x++)
        {
            list.add(playersInfo.myList[x].name);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        players.setAdapter(dataAdapter);

        // Spinner item selection Listener
        addListenerOnSpinnerItemSelection();
        updateScreen();
    }


    public void addListenerOnSpinnerItemSelection()
    {
        MyOnItemSelectedListener MOISL = new MyOnItemSelectedListener();
        MOISL.giveList(playersInfo);
        players.setOnItemSelectedListener(MOISL);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_screen, menu);



        input = (EditText) findViewById(R.id.inName);

        addP = (Button) findViewById(R.id.addPro);
        //Setting the listener for the match button
        addP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String name = input.getText().toString();
                if(playersInfo.numberOfProfiles == 8)
                {
                    Stats.setText("There can be no more then 8 profiles! \nPlease Remove one first.");
                }
                else
                {
                    if (name.contains(";"))
                    {
                        Stats.setText("No \";\" charactors please.");
                    }
                    else if (name.length() < 2)
                    {
                        Stats.setText("Please make the name Longer!");
                    }
                    else
                    {
                        list.add(name);
                        playersInfo.addProfile(name);
                        playersInfo.currentPro = (playersInfo.numberOfProfiles - 1);
                        players.setSelection(playersInfo.currentPro);
                        playersInfo.save();
                        updateScreen();
                    }
                }

            }
        });

        removeP = (Button) findViewById(R.id.removePro);
        //Setting the listener for the match button
        removeP.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if (playersInfo.numberOfProfiles > 1)
                {
                    list.remove(playersInfo.currentPro);
                    playersInfo.removeProfile(playersInfo.currentPro);
                    playersInfo.currentPro = 0;
                    players.setSelection(0);
                    updateScreen();
                } else {
                    Stats.setText("there must be at least one profile!");
                }
            }
        });

        select = (Button) findViewById(R.id.Select);
        //Setting the listener for the match button
        select.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                playersInfo.save();
                updateScreen();
            }
        });

        players.setSelection(playersInfo.currentPro);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    //Rewrites the new information to the textView!
    public void updateScreen()
    {
        String screenText = "name: " + playersInfo.myList[playersInfo.currentPro].name + "\n";
        screenText += "Matches done: " + playersInfo.myList[playersInfo.currentPro].matchesDone + "\n";
        screenText += "Score over the last 10 games: " + playersInfo.getScore() + "%diff \n";
        screenText += "Profile ID: " + playersInfo.currentPro;
        Stats.setText(screenText);
    }
}
