package com.ps.vh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ps.vh.constant.C;
import com.ps.vh.home.LoginUIActivity;
import com.ps.vh.model.Score;
import com.ps.vh.scores.CustomListAdapter;
import com.ps.vh.scores.Items;
import com.ps.vh.service.TargetService;

import java.util.ArrayList;
import java.util.List;

public class HighScoresActivity extends AppCompatActivity implements OnCompleteListener<QuerySnapshot>, OnFailureListener {

    private List<Items> itemsList = new ArrayList<Items>();
    private ListView listView;
    private CustomListAdapter adapter;
    private TargetService ts;
    private Task highScoresTask;
    private Task selfScoreTask;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_high_scores);
        ts = TargetService.getInstance();
        ts.getScores(this);


            //Initialize and create a new adapter with layout named list found in activity_main layout

            listView = (ListView) findViewById(R.id.scores_list);
            adapter = new CustomListAdapter(this, itemsList);
            listView.setAdapter(adapter);
             //read all rows from the database and add to the Items array

                for(int i=0; i<5; i++) {
                    Items items = new Items();
                    items.setName("Name" + i);
                    items.setScore(""+i);

                //    itemsList.add(items);
                }

        //All done, so notify the adapter to populate the list using the Items Array

      //  adapter.notifyDataSetChanged();
//initializeAds();
            }

/**
    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        itemsList.clear();
        Score s = null;
        if (task.isSuccessful()) {
            for (QueryDocumentSnapshot document : (QuerySnapshot)task.getResult()) {
                Log.d(C.logtag, document.getId() + " => " + document.getData());
                s = document.toObject(Score.class);
                Items items = new Items();
                items.setPicUrl(s.getPic());
                items.setName(s.getName());
                items.setScore(""+s.getCount());
                itemsList.add(items);
            }
        } else {
            Log.d(C.logtag, "Error getting scores: ", task.getException());
        }
        adapter.notifyDataSetChanged();
    } **/


    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        itemsList.clear();
        Score s = null;
        if (highScoresTask != null && highScoresTask.isSuccessful()) {
            for (QueryDocumentSnapshot document : (QuerySnapshot)highScoresTask.getResult()) {
               // Log.d(C.logtag, document.getId() + " => " + document.getData());
                s = document.toObject(Score.class);
                Items items = new Items();
                items.setPicUrl(s.getPic());
                items.setName(s.getName());
                items.setScore(""+s.getCount());
                itemsList.add(items);
            }
        } else {
            Log.d(C.logtag, "Error getting High scores: "+highScoresTask.getException());
        }



        if(selfScoreTask != null && selfScoreTask.isSuccessful()){
            TextView selfScoreNameView = (TextView) findViewById(R.id.self_score_name);
            TextView selfScoreCountView = (TextView) findViewById(R.id.self_score_count);
            ImageView selfScorePicView = (ImageView) findViewById(R.id.self_score_image);

           // Log.d(C.logtag, "self scores: "+selfScoreTask.getResult().toString());

            QuerySnapshot querySnapshot = (QuerySnapshot)selfScoreTask.getResult();
            if(querySnapshot.isEmpty()){
               // Log.d(C.logtag, "self score tak is empty");
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String name = user.isAnonymous()? "Anonymous":user.getDisplayName();
                selfScoreNameView.setText(name);
                selfScoreCountView.setText("0");
                C.pushRoundImage(selfScorePicView, String.valueOf(user.getPhotoUrl()));
            } else {
              //  Log.d(C.logtag, "self score tak is not empty");

                for (QueryDocumentSnapshot document : (QuerySnapshot) selfScoreTask.getResult()) {
                  //  Log.d(C.logtag, document.getId() + " => " + document.getData());
                  //  Log.d(C.logtag, s.getName() + s.getCount());
                    s = document.toObject(Score.class);
                    selfScoreNameView.setText(s.getName());
                    selfScoreCountView.setText(String.valueOf(s.getCount()));
                    C.pushRoundImage(selfScorePicView, s.getPic());
                }
            }
        } else {
            Log.d(C.logtag, "Error getting self scores: "+selfScoreTask.getException());
        }
        adapter.notifyDataSetChanged();

    }

    public Task getHighScoresTask() {
        return highScoresTask;
    }

    public void setHighScoresTask(Task highScoresTask) {
        this.highScoresTask = highScoresTask;
    }

    public Task getSelfScoreTask() {
        return selfScoreTask;
    }

    public void setSelfScoreTask(Task selfScoreTask) {
        this.selfScoreTask = selfScoreTask;
    }

    @Override
    public void onFailure(@NonNull Exception e) {

    }

    public void goToMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        // intent.putExtra(EXTRA_MESSAGE, message);
        startActivityIfNeeded(intent,0);
    }

    public void goToHome(View view) {
        Intent intent = new Intent(this, LoginUIActivity.class);
        intent.putExtra(C.MAIN_INTENT_KEY, C.MAIN_INTENT_VAL_SETTINGS);
        startActivity(intent);

    }


}