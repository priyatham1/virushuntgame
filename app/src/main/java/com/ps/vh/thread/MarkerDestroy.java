package com.ps.vh.thread;

import android.os.Handler;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.ps.vh.constant.C;

public class MarkerDestroy implements Runnable {

    Handler uiHandler = null;
    private Marker marker = null;
    int index = 0;

    public MarkerDestroy(Marker marker){
        this.marker = marker;
        this.index=0;

    }
    @Override
    public void run() {

        try {
            //Log.d(C.logtag, "staring marker animation with index:"+index);

            marker.setIcon(BitmapDescriptorFactory.fromBitmap(C.dyingArray[index]));
            index++;
            if(index < C.dyingArray.length) {
                uiHandler.postDelayed(this,200);
            }
        } catch(Exception e){

        }

    }

    public void execute() {
        uiHandler = new Handler();
        uiHandler.postDelayed(this,500);

    }
}
