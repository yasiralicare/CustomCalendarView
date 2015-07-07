package com.yasirali.customcalendarview.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by yasirali on 7/7/15.
 */
public class EventTitleTextView extends TextView{

    private float density;

    public EventTitleTextView(Context context) {
        super(context);
        density = context.getResources().getDisplayMetrics().density;
    }

    public EventTitleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        float density = context.getResources().getDisplayMetrics().density;
    }

    public EventTitleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        float density = context.getResources().getDisplayMetrics().density;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        decorateText();
    }

    private void decorateText(){
        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
        this.setBackgroundColor(Color.CYAN);
        this.setTextColor(Color.BLACK);
    }
}
