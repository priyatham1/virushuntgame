package com.ps.vh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.VideoView;

import com.ps.vh.constant.C;
import com.ps.vh.home.LoginUIActivity;

public class IntroVideoActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {
    private String intentFrom = null;
    private MediaPlayer mplayer = null;
    private VideoView videoView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_video);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        startVideo();
    }

    private void startVideo(){

        Intent intent = getIntent();
        if(intent != null) {
            intentFrom = intent.getStringExtra(C.INTENT_FROM);
            if(! C.ACTIVITY_ABOUT.equals(intentFrom)){
                intentFrom = C.ACTIVITY_MAIN;
            }
        }
        VideoView videoView = (VideoView)findViewById(R.id.VideoView);
        this.videoView = videoView;
        //MediaController mediaController = new MediaController(this);
        // mediaController.setAnchorView(videoView);
        //videoView.setMediaController(mediaController);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.intro_video);
        videoView.setVideoURI(uri);
        videoView.setOnCompletionListener(this);
        videoView.setOnPreparedListener(this);
        videoView.setOnErrorListener(this);
        ///  videoView.setVideoPath(R.raw.intro_video);


        videoView.start();


    }


   /** private void hideButtons(){
        View btns = findViewById(R.id.intro_video_btn_layout);
        btns.setVisibility(View.GONE);
    }**/

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
       // mediaPlayer.release();
        Intent intent = null;
        if(C.ACTIVITY_ABOUT.equals(intentFrom)){
             intent = new Intent(this, AboutActivity.class);
        } else {
             intent = new Intent(this, LoginUIActivity.class);
        }

        startActivity(intent);

       // View v = findViewById(R.id.VideoView);
       // View btns = findViewById(R.id.intro_video_btn_layout);
       // v.setVisibility(View.GONE);
       // btns.setVisibility(View.VISIBLE);
    }

    public void goToHome(View view) {
        Intent intent = new Intent(this, LoginUIActivity.class);
        intent.putExtra(C.MAIN_INTENT_KEY, C.MAIN_INTENT_VAL_ABOUT);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(this.mplayer != null) {
         //   this.mplayer.release();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(this.mplayer != null) {
         //   this.mplayer.release();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.videoView.start();


    }

    @Override
    protected void onPause() {
        super.onPause();
        if(this.mplayer != null) {
           // this.mplayer.pause();
        }
      //  this.videoView.pause();
    }

    public void replayVideo(View view) {
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        this.mplayer = mediaPlayer;

    }
}