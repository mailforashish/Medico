package com.medico.app.utils;

import android.graphics.Color;

public class CirclesConstant {

    private static final int COLOR_BLUE;
    private static final int COLOR_RED;
    private static final int COLOR_YELLOW;
    private static final int COLOR_GREEN;
    public static final int CIRCLE_COUNT = 4;
    public static final float DEFAULT_CIRCLE_RADIUS = 20.0F;
    public static final float DEFAULT_CIRCLE_MARGIN = 20.0F;
    public static final float DEFAULT_ANIM_DISTANCE = 50.0F;
    public static final long DEFAULT_ANIM_DURATION = 500L;
    public static final long DEFAULT_ANIM_DELAY = 150L;
    public static final int DEFAULT_ANIM_INTERPOLATOR = 0;

    public static final CirclesConstant INSTANCE;

    public final int getCOLOR_BLUE() {
        return COLOR_BLUE;
    }

    public final int getCOLOR_RED() {
        return COLOR_RED;
    }

    public final int getCOLOR_YELLOW() {
        return COLOR_YELLOW;
    }

    public final int getCOLOR_GREEN() {
        return COLOR_GREEN;
    }

    private CirclesConstant() {
    }

    static {
        CirclesConstant var0 = new CirclesConstant();
        INSTANCE = var0;
        COLOR_BLUE = Color.parseColor("#4285F4");
        COLOR_RED = Color.parseColor("#DB4437");
        COLOR_YELLOW = Color.parseColor("#F4B400");
        COLOR_GREEN = Color.parseColor("#0F9D58");
    }
}
