package com.ps.vh.home;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import com.ps.vh.AboutActivity;
import com.ps.vh.HighScoresActivity;
import com.ps.vh.IntroVideoActivity;
import com.ps.vh.MapsActivity;
import com.ps.vh.R;
import com.ps.vh.constant.C;


public class LoginUIActivity extends AppCompatActivity {

    private static final String TAG = "CorH";
    private CallbackManager mCallbackManager;
    private FirebaseAuth mFirebaseAuth;
    AccessTokenTracker accessTokenTracker;

    /*
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Task t = GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this);
        //setContentView(R.layout.activity_firebase_ui);
        setContentView(R.layout.home);

        playIntroVideo();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mCallbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = findViewById(R.id.login_button);
        checkAndToggleGoButn();



        //Setting the permission that we need to read
        loginButton.setPermissions("public_profile");
        C.prepareBitmaps(this,getResources());
       //initializeAds1();

        //Registering callback!
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Sign in completed
                Log.i(TAG, "onSuccess: logged in successfully");

                //handling the token for Firebase Auth
                handleFacebookAccessToken(loginResult.getAccessToken());

                //Getting the user information
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        Log.i(TAG, "onCompleted: response: " + response.toString());
                        try {
                            String name = object.getString("name");
                            //String birthday = object.getString("birthday");

                            Log.i(TAG, "onCompleted: Name: " + name);
                           // Log.i(TAG, "onCompleted: Birthday: " + birthday);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i(TAG, "onCompleted: JSON exception");
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });


    }

    private void playIntroVideo() {

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        if (!sharedPreferences.getBoolean("CH_NEW_INTRO_VIDEO_SHOWN", false)) {
            Intent intent = new Intent(this, IntroVideoActivity.class);
            SharedPreferences.Editor sharedPreferencesEditor =
                    PreferenceManager.getDefaultSharedPreferences(this).edit();
            sharedPreferencesEditor.putBoolean("CH_NEW_INTRO_VIDEO_SHOWN", true);
            sharedPreferencesEditor.apply();
            startActivity(intent);
        } else {
            return;
        }
    }

    private void checkAndToggleGoButn() {
        View goButton = findViewById(R.id.go);
        View fbLoginButton = findViewById(R.id.login_button);
        View logoutButton = findViewById(R.id.logout_button);
        View logoutLayout = findViewById(R.id.logout_layout);
        if(mFirebaseAuth.getCurrentUser() != null) {
            Log.d(C.logtag, mFirebaseAuth.getCurrentUser().toString());
        }
        if(mFirebaseAuth.getCurrentUser() != null && !mFirebaseAuth.getCurrentUser().isAnonymous()){
            ((Button)goButton).setText("Let's Hunt");
            ((Button)fbLoginButton).setVisibility(View.GONE);
            ((Button)logoutButton).setVisibility(View.VISIBLE);
            logoutLayout.setVisibility(View.VISIBLE);

        } else {
            ((Button)goButton).setText("Skip Login");
            ((Button)logoutButton).setVisibility(View.GONE);
            logoutLayout.setVisibility(View.GONE);
            ((Button)fbLoginButton).setVisibility(View.VISIBLE);


        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        final View goButton = findViewById(R.id.go);
        final View fbButton = findViewById(R.id.login_button);
        final View logoutButton = findViewById(R.id.logout_button);


        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            Log.i(TAG, "onComplete: login completed with user: " + user.getDisplayName());
                            //Starting maps activity
                            //((Button)goButton).setText("Let's hunt");
                            //((com.facebook.login.widget.LoginButton)fbButton).setVisibility(View.GONE);
                            //((Button)logoutButton).setVisibility(View.VISIBLE);
                            checkAndToggleGoButn();

                            launchMapView();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginUIActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    signOut();
                }

            }
        };

        accessTokenTracker.startTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        //initializeAds();
        Intent intent = getIntent();
        String intentStr = null;
        if(intent != null) {
            intentStr = intent.getStringExtra(C.MAIN_INTENT_KEY);
        }

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if (currentUser != null && !currentUser.isAnonymous()) {
            Log.i(TAG, "onStart: Someone logged in <3");
            if( !  C.MAIN_INTENT_VAL_SETTINGS.equals(intentStr)) {
                launchMapView();
            }
        } else {
            Log.i(TAG, "onStart: No one logged in :/");
        }
    }


    public void goToMapNoSignin(View view) {
        if(mFirebaseAuth.getCurrentUser() == null){
            mFirebaseAuth.signInAnonymously();
    //        signInAnonymouslyAndStartActivity(this, MapsActivity.class, Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
           }
       launchMapView();
    }

    public void launchMapView(){

        Intent intent = new Intent(this, MapsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //
        // intent.putExtra(EXTRA_MESSAGE, message);
        startActivityIfNeeded(intent,0);


    }

    public void gotToScores(View view) {
        if(mFirebaseAuth.getCurrentUser() == null){
          signInAnonymouslyAndStartActivity(this, HighScoresActivity.class);
        } else {
          Intent intent = new Intent(this, HighScoresActivity.class);
          startActivity(intent);
        }

    }

    private void signInAnonymouslyAndStartActivity(final Activity activity, final Class actClass){

        Log.d(C.logtag, "Trying to signin anonymously"+mFirebaseAuth);
        mFirebaseAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(activity, actClass);
                    startActivity(intent);
                } else {
                    Toast.makeText(activity, "Error reaching network. Please try later !", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
       // loadAd();

    }

    public void signOut() {
        // [START auth_sign_out]
        Log.d(C.logtag, "Logout Called");
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        checkAndToggleGoButn();
        // [END auth_sign_out]
    }

    public void logoutUser(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginUIActivity.this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        builder.setTitle("Confirm Logout !");
        builder.setMessage(user.getDisplayName()+", Do you want to logout?");

        //Yes Button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               Log.i(C.logtag, "Yes button Clicked!");
                signOut();
            }
        });

        //No Button
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(C.logtag,"No button Clicked!");
                dialog.dismiss();

            }
        });

        AlertDialog alertDialog = builder.create();
        View decorView = alertDialog.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        alertDialog.show();

    }

    public void goToAbout(View view) {
        if(mFirebaseAuth.getCurrentUser() == null){
            signInAnonymouslyAndStartActivity(this, AboutActivity.class);
        } else {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }
    }
}