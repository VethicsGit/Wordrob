package com.pro.wardrobe.Extra;

import android.graphics.drawable.Drawable;

public class Dashboard_list_model {

    Drawable drawable;
    String name;

    public Dashboard_list_model(Drawable drawable, String name) {
        this.drawable = drawable;
        this.name = name;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
