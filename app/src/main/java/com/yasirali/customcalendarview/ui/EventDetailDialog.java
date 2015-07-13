package com.yasirali.customcalendarview.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alamkanak.weekview.WeekViewEvent;
import com.yasirali.customcalendarview.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
        //LinearLayout eventDetailItem2 = (LinearLayout) findViewById(R.id.eventDetailItem2);
        LinearLayout eventDetailItem3 = (LinearLayout) findViewById(R.id.eventDetailItem3);

        ((ImageView)eventDetailItem1.findViewById(R.id.imageViewIcon)).setImageResource(android.R.drawable.ic_menu_recent_history);
        //((ImageView)eventDetailItem2.findViewById(R.id.imageViewIcon)).setImageResource(android.R.drawable.ic_menu_view);
        ((ImageView)eventDetailItem3.findViewById(R.id.imageViewIcon)).setImageResource(android.R.drawable.ic_menu_today);

        ((TextView)findViewById(R.id.textViewEventTitle)).setText(mWeekViewEvent.getName());

        String startTime = getFormattedTime(mWeekViewEvent.getStartTime());
        String endTime = getFormattedTime(mWeekViewEvent.getEndTime());

        String combinedTime;

        if(startTime.substring(startTime.length() - 2).equalsIgnoreCase(endTime.substring(startTime.length() - 2))){
            combinedTime = startTime.substring(0, startTime.length() - 3) + " - " + endTime;
        } else{
            combinedTime = startTime + " - " + endTime;
        }

        ((TextView)eventDetailItem1.findViewById(R.id.textView2)).setText(combinedTime);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd");
        String d = sdf.format(mWeekViewEvent.getStartTime().getTime());
        ((TextView)eventDetailItem1.findViewById(R.id.textView1)).setText(d);

        ((TextView)eventDetailItem3.findViewById(R.id.textView1)).setText("Events");
        ((TextView)eventDetailItem3.findViewById(R.id.textView2)).setVisibility(View.GONE);


    }

    private String getFormattedTime(Calendar startTime) {
        String startHour = String.valueOf(startTime.get(Calendar.HOUR));
        String startMinute = String.valueOf(startTime.get(Calendar.MINUTE));
        String amPm = startTime.get(Calendar.AM_PM) == 0 ? "AM" : "PM";
        startHour = startHour.length() <= 1 ? "0" + startHour : startHour;
        startMinute = startMinute.length() <= 1 ? "0" + startMinute : startMinute;
        return startHour + ":" + startMinute + " " + amPm;
    }
}
