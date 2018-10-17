package com.pro.wardrobe.Application;

import android.app.Application;

import com.pro.wardrobe.Extra.TypefaceUtil;

public class Wardrobe extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "Roboto_Regular.ttf");

    }
}
