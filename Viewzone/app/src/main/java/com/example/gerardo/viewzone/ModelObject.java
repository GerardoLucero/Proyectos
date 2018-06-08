package com.example.gerardo.viewzone;

/**
 * Created by Gerardo on 15/08/2017.
 */

public enum ModelObject {

    RED(10, R.layout.activity_main2),
    MAP(11, R.layout.activity_maps);

    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}