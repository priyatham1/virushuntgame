package com.ps.vh.constant;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.ps.vh.R;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class C {

    public static final String INTENT_FROM = "INTENT_FROM" ;
    public static final String ACTIVITY_ABOUT = "ACTIVITY_ABOUT";
    public static final String ACTIVITY_MAIN = "ACTIVITY_MAIN";

    public static final String MAIN_INTENT_KEY = "MAIN_INTENT_KEY" ;
    public static final String MAIN_INTENT_VAL_LOGIN = "MAIN_INTENT_VAL_LOGIN" ;
    public static final String MAIN_INTENT_VAL_SETTINGS = "MAIN_INTENT_VAL_SETTINGS" ;
    public static final String MAIN_INTENT_VAL_ABOUT = "MAIN_INTENT_VAL_ABOUT" ;

    public static String logtag = "CorH";
    public static Bitmap smallAliveBmp = null;
    public static Bitmap smallDeadBmp = null;

    public static Bitmap searchCountry = null;
    public static Bitmap searchState = null;
    public static Bitmap searchCity = null;

    public static Bitmap dying_1 = null;
    public static Bitmap dying_2 = null;
    public static Bitmap dying_3 = null;
    public static float battle_zoom = 13;
    public static float country_zoom_start = 2;
    public static float country_zoom_end = 3.4f;

    public static float state_zoom_start = 3.6f;
    public static float state_zoom_end = 6;

    public static float city_zoom_start = 6.1f;
    public static float city_zoom_end = 11;

    public static float place_selected_zoom = 13;

    public static Bitmap[] dyingArray = new Bitmap[11];
    public static int screenHeight;
    public static int screenWidth;
    public static int scaledHeight;
    public static int scaledWidth;
    public static int REQ_CODE_LOCATION = 1;

    public static int[] zoomarr = {R.drawable.custom_zoom_1,
            R.drawable.custom_zoom_2,
            R.drawable.custom_zoom_3,
            R.drawable.custom_zoom_4,
            R.drawable.custom_zoom_5,
            R.drawable.custom_zoom_6,
            R.drawable.custom_zoom_7,
            R.drawable.custom_zoom_8,
            R.drawable.custom_zoom_9,
            R.drawable.custom_zoom_10};

    public static Bitmap resizeMapIcons(Resources resources, int drawable, int width, int height){


        Bitmap imageBitmap = BitmapFactory.decodeResource(resources, drawable);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    public static void prepareBitmaps(Activity activity, Resources resources) {
        getScreenDimensions(activity);

        //smallAliveBmp = Bitmap.createScaledBitmap(resizeMapIcons(resources, R.drawable.alive_5, 250, 200 ), scaledWidth, scaledHeight, true);
        smallAliveBmp = BitmapFactory.decodeResource(resources, R.drawable.red_alive);
        smallDeadBmp = BitmapFactory.decodeResource(resources, R.drawable.trophy);
        searchCountry = BitmapFactory.decodeResource(resources, R.drawable.icon_search_country);
        searchState = BitmapFactory.decodeResource(resources, R.drawable.icon_search_state);
        searchCity = BitmapFactory.decodeResource(resources, R.drawable.icon_search_city);



        dyingArray[0] = BitmapFactory.decodeResource(resources, R.drawable.red_dying_1);
        dyingArray[1] = BitmapFactory.decodeResource(resources, R.drawable.red_dying_2);
        dyingArray[2] = BitmapFactory.decodeResource(resources, R.drawable.red_dying_3);
        dyingArray[3] = BitmapFactory.decodeResource(resources, R.drawable.red_dying_4);
        dyingArray[4] = BitmapFactory.decodeResource(resources, R.drawable.red_dying_5);
        dyingArray[5] = BitmapFactory.decodeResource(resources, R.drawable.red_dying_6);
        dyingArray[6] = BitmapFactory.decodeResource(resources, R.drawable.red_dying_7);
        dyingArray[7] = BitmapFactory.decodeResource(resources, R.drawable.red_dying_8);
        dyingArray[8] = BitmapFactory.decodeResource(resources, R.drawable.red_dying_9);
        dyingArray[9] = BitmapFactory.decodeResource(resources, R.drawable.red_dying_10);
        dyingArray[10] = smallDeadBmp;



    }

    private static void getScreenDimensions(Activity activity) {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;//screen height in pixels
        screenWidth = displaymetrics.widthPixels;//screen width in pixels
        Log.d(C.logtag, "screen height:"+screenWidth + " screenWidth:"+screenWidth);
        float h= (float) (screenHeight/(4.4));//scaling w.r.t screen dimensions
        float w= (float)(screenWidth/(3.5));
        scaledWidth = (int)w;
        scaledHeight = (int)h;

    }

    public static void pushRoundImage(ImageView imageView, String imagePath){

        if(imagePath != null){
           imagePath = imagePath + "?height=100&width=100&access_token=1029541770814685%7C66d2b387fa91b8257a5183730211630b";
        }
        // "https://graph.facebook.com/v2.2/" + user.getUserId() + "/picture?height=120&type=normal"
        Picasso.with(imageView.getContext())
                .load(imagePath)
                .error(R.drawable.no_pic)
                .placeholder(R.drawable.no_pic)
                .transform(new CropCircleTransformation())
                .resize(100, 100)
                .into(imageView);
    }
}
