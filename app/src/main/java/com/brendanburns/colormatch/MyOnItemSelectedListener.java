package com.brendanburns.colormatch;

/**
 * Created by Brendan on 7/28/2015.
 */
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class MyOnItemSelectedListener implements OnItemSelectedListener {


    ProfileScreen myScreen;
    ProfileList myList;
    public void giveList(ProfileList ml)
    {
        myList = ml;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        myList.currentPro = pos;
        //myScreen.over = 0;
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}
