package com.brendanburns.colormatch;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;


public class ColorMatch extends ActionBarActivity {


    TextView scorePrint;
    SeekBar selectBar;
    DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_match);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_color_match, menu);

        //Set up the two color boxes.
        drawView = (DrawView) findViewById(R.id.view);

        //Sets up for changing the text for the score menu.
        scorePrint = (TextView) findViewById(R.id.score);

        //Sets up the seek bar for the player to select a color.
        selectBar = (SeekBar) findViewById(R.id.seekBar);
        selectBar.setMax(255 * 6 - 1);//sets the hue bar to the max number of hues this system supports.
        selectBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                drawView.setColor(selectBar.getProgress());
            }
        });

        //readies the button for checking the players answers, and the new color to match button
        Button accept = (Button) findViewById(R.id.match);
        Button newButton = (Button) findViewById(R.id.newB);


        //Setting the listener for the match button
        accept.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                scorePrint.setText("Score: " + drawView.computerScore() + "% difference!");
            }
        });

        newButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                drawView.randomizeTop();
            }
        });

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
