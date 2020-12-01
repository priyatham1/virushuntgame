package com.ps.vh.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.ps.vh.R;
import com.ps.vh.constant.C;

public class SoundUtil  {
    MediaPlayer mediaPlayer;

    public static void createInstance(Context context){
        if(SoundUtilFactory.singleInstance == null){
            SoundUtilFactory.singleInstance = new SoundUtil(context);
        }
    }
    public static SoundUtil getInstance() {
        if(SoundUtilFactory.singleInstance == null){
            Log.e(C.logtag, "SoundUtil instance not created yet");
        }
        return SoundUtilFactory.singleInstance;
    }
    private SoundUtil(Context context){
        mediaPlayer = MediaPlayer.create(context, R.raw.sound_kill);
        mediaPlayer.setVolume(100, 100);
    }

    private SoundUtil(Context context, int resourceId ){
        mediaPlayer = MediaPlayer.create(context, resourceId);
    }

    public void playKillSound(){
        mediaPlayer.start(); // no need to call prepare(); create() does that for you

    }



}
