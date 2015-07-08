package com.yasirali.customcalendarview.ui;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by yasirali on 7/7/15.
 */
public class EventTitleTextView extends TextView{


    public EventTitleTextView(Context context) {
        super(context);
    }

    public EventTitleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventTitleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        decorateText();
    }

    private void decorateText(){
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
        setBackgroundColor(Color.CYAN);
        setTextColor(Color.BLACK);
        setGravity(Gravity.CENTER | Gravity.LEFT);
        setLines(1);
        setEms(3);
        setEllipsize(TextUtils.TruncateAt.END);
    }
}
