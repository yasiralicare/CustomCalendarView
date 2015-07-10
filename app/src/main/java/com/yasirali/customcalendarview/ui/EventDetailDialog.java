package com.yasirali.customcalendarview.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alamkanak.weekview.WeekViewEvent;
import com.yasirali.customcalendarview.R;

/**
 * Created by yasirali on 7/9/15.
 */
public class EventDetailDialog extends Dialog {

    Context context;
    WeekViewEvent mWeekViewEvent;

    public EventDetailDialog(Context context, WeekViewEvent weekViewEvent) {
        super(context);
        this.context = context;
        this.mWeekViewEvent = weekViewEvent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.event_detail);
        setCancelable(true);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.container);
        linearLayout.setBackgroundColor(mWeekViewEvent.getColor());

        LinearLayout eventDetailItem1 = (LinearLayout) findViewById(R.id.eventDetailItem1);
        LinearLayout eventDetailItem2 = (LinearLayout) findViewById(R.id.eventDetailItem2);
        LinearLayout eventDetailItem3 = (LinearLayout) findViewById(R.id.eventDetailItem3);

        ((ImageView)eventDetailItem1.findViewById(R.id.imageViewIcon)).setImageResource(android.R.drawable.ic_menu_recent_history);
        ((ImageView)eventDetailItem2.findViewById(R.id.imageViewIcon)).setImageResource(android.R.drawable.ic_menu_recent_history);
        ((ImageView)eventDetailItem3.findViewById(R.id.imageViewIcon)).setImageResource(android.R.drawable.ic_menu_today);

        ((TextView)findViewById(R.id.textViewEventTitle)).setText(mWeekViewEvent.getName());

    }
}
