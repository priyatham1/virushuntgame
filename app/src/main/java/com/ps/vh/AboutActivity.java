package com.ps.vh;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.Task;
import com.ps.vh.constant.C;
import com.ps.vh.home.LoginUIActivity;
import com.ps.vh.scores.CustomListAdapter;
import com.ps.vh.scores.Items;
import com.ps.vh.service.TargetService;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends AppCompatActivity  {

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
        setContentView(R.layout.activity_about);
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


    public void playVideo(View view) {
        Intent intent = new Intent(this, IntroVideoActivity.class);
        intent.putExtra(C.INTENT_FROM, C.ACTIVITY_ABOUT);
        startActivity(intent);
    }

    public void goToChFB(View view) {

        try {
            Uri uri = Uri.parse("fb://facewebmodal/f?href=" + "https://www.facebook.com/Virus-Hunt-102134611675018"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (Exception e){
            Uri uri = Uri.parse("https://www.facebook.com/Virus-Hunt-102134611675018"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }

    public void goToChInsta(View view) {
        Uri uri = Uri.parse("https://instagram.com/_u/virushunt_game"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void goToClaFB(View view) {
        try {
            Uri uri = Uri.parse("fb://facewebmodal/f?href=" + "https://www.facebook.com/coffeelab.apps"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (Exception e){
            Uri uri = Uri.parse("https://www.facebook.com/coffeelab.apps"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    public void goToClaInsta(View view) {
        Uri uri = Uri.parse("https://instagram.com/_u/coffeelabapps"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void goToClaLi(View view) {
        Uri uri = Uri.parse("https://www.linkedin.com/company/coffee-lab-apps"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void goToClaSpo(View view) {
        Uri uri = Uri.parse("https://open.spotify.com/playlist/3iTpdtjwmGaNQkCll4J4Ys?si=D8qd7t8eQVGHKpuPesy2yg"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void goToClaWeb(View view) {
        Uri uri = Uri.parse("http://www.coffeelabapps.com"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}