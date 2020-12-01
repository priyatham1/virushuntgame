package com.ps.vh;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.ps.vh.constant.C;
import com.ps.vh.home.LoginUIActivity;
import com.ps.vh.listeners.MapClickListener;
import com.ps.vh.listeners.MapIdleListener;
import com.ps.vh.listeners.MapMovedListener;
import com.ps.vh.service.BackgroundMusicService;
import com.ps.vh.util.SoundUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Log log;
    private AdView mAdView;
    private boolean startWithCurrentLocation = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_maps);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            log.d(C.logtag, "OnCreate called");
            initSearchFragment();
           playBackgroundSound();
          // initializeAds();
            //Ads

            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                    Log.d(C.logtag, "AdMob initialized" );
                }
            });
            mAdView = findViewById(R.id.adView2);
            Log.d(C.logtag, "mAdView="+mAdView );
           // List<String> testDeviceIds = Arrays.asList("06396D53F92BC7C0FEA5997C89289159");
            AdRequest adRequest = new AdRequest.Builder().build();
           //  boolean isTestDevice = adRequest.isTestDevice(this);
            //TextView admobBannerText = findViewById(R.id.admobBannerId);
           // admobBannerText.setText("Ad Here. Is Test Device:"+isTestDevice);
            mAdView.loadAd(adRequest);
            //mAdView.setVisibility(View.);



        } catch (Exception e) {
            log.e(C.logtag, e.getMessage());
        }
    }







    private void initSearchFragment() {
        // Initialize the AutocompleteSupportFragment.

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "KEY_HERE", Locale.US);
        }
        PlacesClient placesClient = Places.createClient(this);


        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.VIEWPORT));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(C.logtag, "Place: " + place.getName() + ", " + place.getId());
                moveCameraToPlace(place);
            }


            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(C.logtag, "An error occurred: " + status);
            }
        });
    }

    public void showSearchSuggestions(){
        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        startActivityForResult(intent, 1);
    }

    private void moveCameraToPlace(Place place) {
        Log.i(C.logtag, place.getLatLng().toString());
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(place.getViewport(),0));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(C.place_selected_zoom));
        //mMap.setLatLngBoundsForCameraTarget(place.getViewport());

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setRotateGesturesEnabled(false);


        MapMovedListener mapMovedListener = new MapMovedListener();
        MapIdleListener mapIdleListener = new MapIdleListener(MapsActivity.this, this, mMap);

        MapClickListener mapClickListener = new MapClickListener();
        mMap.setOnMapClickListener(mapClickListener);

       // mMap.setOnCameraMoveListener(mapMovedListener);
        mMap.setOnCameraIdleListener(mapIdleListener);

        mMap.setOnMarkerClickListener(mapIdleListener);

        mapIdleListener.setKillMode(getKillModeMap());
        mapIdleListener.setNormalMode(getNormalModeMap());

        SoundUtil.createInstance(this);

      //  C.prepareBitmaps(getResources());

    }

    public MapStyleOptions getKillModeMap(){
        return MapStyleOptions.loadRawResourceStyle(
                this, R.raw.map_kill_mode_style);
    }

    public MapStyleOptions getNormalModeMap(){
        return MapStyleOptions.loadRawResourceStyle(
                this, R.raw.map_normal_mode_style);
    }

    private void stopBackgroundMusic(){
        getApplicationContext().stopService(new Intent(this,
                BackgroundMusicService.class));
    }
    @Override
    protected void onDestroy() {

        super.onDestroy();
        Log.i(C.logtag, "MAP OnDestroy ***");

        stopBackgroundMusic();
    }

    @Override
    public void onLowMemory() {

        super.onLowMemory();
        Log.i(C.logtag, "MAP OnLowMemory ***");

        stopBackgroundMusic();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(C.logtag, "MAP OnPause ***");

       // stopBackgroundMusic();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(C.logtag, "MAP OnResume ***");

        playBackgroundSound();
      //  loadAd();
    }

    @Override
    protected void onStart() {
        super.onStart();
       // initializeAds();
        Log.i(C.logtag, "MAP OnStart ***");

       // playBackgroundSound();

    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(C.logtag, "MAP OnStop ***");

        stopBackgroundMusic();

    }

    public void goToHome(View view) {
        Intent intent = new Intent(this, LoginUIActivity.class);
        intent.putExtra(C.MAIN_INTENT_KEY, C.MAIN_INTENT_VAL_SETTINGS);
        startActivity(intent);

    }


    private void initializeAds(){
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.d(C.logtag, "AdMob initialized with status"+initializationStatus.getAdapterStatusMap().values());
            }
        });
        loadAd();
        //MobileAds.initialize(getApplicationContext(), "ca-app-pub-~9726042167");
    }

    private void loadAd(){
        mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void toggleMusic(View view) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        View v = findViewById(R.id.btn_togglemusic);
        boolean musicPlaying = sharedPreferences.getBoolean("CH_MUSIC_PLAYING", true );
        if (musicPlaying) {
            //v.setBackground();
            stopBackgroundSound();
            setMusicPlaying(false);
            v.setBackgroundResource(R.drawable.custom_music_off);

        } else  {
            //v.setBackground();
            startBackgroundSound();
            setMusicPlaying(true);
            v.setBackgroundResource(R.drawable.custom_music_on);

        }
    }

    private void setMusicPlaying(boolean musicPlaying) {
        SharedPreferences.Editor sharedPreferencesEditor =
                PreferenceManager.getDefaultSharedPreferences(this).edit();
        sharedPreferencesEditor.putBoolean("CH_MUSIC_PLAYING", musicPlaying);
        sharedPreferencesEditor.apply();
    }


    public void playBackgroundSound() {
            SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(this);
            View v = findViewById(R.id.btn_togglemusic);
           boolean musicPlaying = sharedPreferences.getBoolean("CH_MUSIC_PLAYING", true );
            if (musicPlaying) {
                startBackgroundSound();
                setMusicPlaying(true);
                v.setBackgroundResource(R.drawable.custom_music_on);
            } else {
                stopBackgroundSound();
                setMusicPlaying(false);
                v.setBackgroundResource(R.drawable.custom_music_off);
            }
        }



      public void startBackgroundSound() {
         log.d(C.logtag, "In PlayBackgroundSound ");
        Intent intent = new Intent(this, BackgroundMusicService.class);
         startService(intent);
    }

    public void stopBackgroundSound() {
        log.d(C.logtag, "In StopBackgroundMuic ");
        Intent intent = new Intent(this, BackgroundMusicService.class);
        stopService(intent);
    }
}
