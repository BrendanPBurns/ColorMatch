package com.brendanburns.colormatch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;


public class MainMenu extends ActionBarActivity {
    ProfileList playersInfo;
    Button startGame;
    Button profilesB;
    TextView mainT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        playersInfo = new ProfileList(this);
        playersInfo.fileCheck();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);



        startGame = (Button) findViewById(R.id.StartGame);
        //Setting the listener for the match button
        startGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), ColorMatch.class);
                startActivity(myIntent);

            }
        });

        profilesB = (Button) findViewById(R.id.startProf);
        profilesB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), ProfileScreen.class);
                startActivity(myIntent);

            }
        });


        mainT = (TextView) findViewById(R.id.MainText);




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
}
