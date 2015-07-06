package com.yasirali.customcalendarview.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.yasirali.customcalendarview.R;
import com.yasirali.customcalendarview.Utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by yasirali on 7/3/15.
 */
public class DayViewFragment extends Fragment {

    public ArrayList<String> items; // container to store calendar items which
    // needs showing the event marker
    ArrayList<String> events;

    public GregorianCalendar month, itemmonth;// calendar instances.

    private WeekView mWeekView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_day_view,container,false);

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) v.findViewById(R.id.weekView);

        // Set an action when any event is clicked.
        //mWeekView.setOnEventClickListener(mEventClickListener);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(mMonthChangeListener);

        // Set long press listener for events.
        //mWeekView.setEventLongPressListener(mEventLongPressListener);

        return v;
    }

    WeekView.MonthChangeListener mMonthChangeListener = new WeekView.MonthChangeListener() {
        @Override
        public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {


            // Populate the week view with some events.
            //List<WeekViewEvent> events = Utility.getCalendarEvents(getActivity(), newYear, newMonth);
            List<WeekViewEvent> events = new ArrayList<>();

            Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, 3);
            startTime.set(Calendar.MINUTE, 0);
            startTime.set(Calendar.MONTH, newMonth-1);
            startTime.set(Calendar.YEAR, newYear);
            Calendar endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR, 1);
            endTime.set(Calendar.MONTH, newMonth-1);
            WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime), startTime, endTime);
            event.setColor(getResources().getColor(R.color.event_color_01));
            events.add(event);



            startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, 3);
            startTime.set(Calendar.MINUTE, 30);
            startTime.set(Calendar.MONTH, newMonth-1);
            startTime.set(Calendar.YEAR, newYear);
            endTime = (Calendar) startTime.clone();
            endTime.set(Calendar.HOUR_OF_DAY, 4);
            endTime.set(Calendar.MINUTE, 30);
            endTime.set(Calendar.MONTH, newMonth-1);
            event = new WeekViewEvent(10, getEventTitle(startTime), startTime, endTime);
            event.setColor(getResources().getColor(R.color.event_color_02));
            events.add(event);

            startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, 4);
            startTime.set(Calendar.MINUTE, 20);
            startTime.set(Calendar.MONTH, newMonth-1);
            startTime.set(Calendar.YEAR, newYear);
            endTime = (Calendar) startTime.clone();
            endTime.set(Calendar.HOUR_OF_DAY, 5);
            endTime.set(Calendar.MINUTE, 0);
            event = new WeekViewEvent(10, getEventTitle(startTime), startTime, endTime);
            event.setColor(getResources().getColor(R.color.event_color_03));
            events.add(event);

            startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, 5);
            startTime.set(Calendar.MINUTE, 30);
            startTime.set(Calendar.MONTH, newMonth-1);
            startTime.set(Calendar.YEAR, newYear);
            endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR_OF_DAY, 2);
            endTime.set(Calendar.MONTH, newMonth-1);
            event = new WeekViewEvent(2, getEventTitle(startTime), startTime, endTime);
            event.setColor(getResources().getColor(R.color.event_color_02));
            events.add(event);

            startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, 5);
            startTime.set(Calendar.MINUTE, 0);
            startTime.set(Calendar.MONTH, newMonth-1);
            startTime.set(Calendar.YEAR, newYear);
            startTime.add(Calendar.DATE, 1);
            endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR_OF_DAY, 3);
            endTime.set(Calendar.MONTH, newMonth - 1);
            event = new WeekViewEvent(3, getEventTitle(startTime), startTime, endTime);
            event.setColor(getResources().getColor(R.color.event_color_03));
            events.add(event);

            startTime = Calendar.getInstance();
            startTime.set(Calendar.DAY_OF_MONTH, 15);
            startTime.set(Calendar.HOUR_OF_DAY, 3);
            startTime.set(Calendar.MINUTE, 0);
            startTime.set(Calendar.MONTH, newMonth-1);
            startTime.set(Calendar.YEAR, newYear);
            endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR_OF_DAY, 3);
            event = new WeekViewEvent(4, getEventTitle(startTime), startTime, endTime);
            event.setColor(getResources().getColor(R.color.event_color_04));
            events.add(event);

            startTime = Calendar.getInstance();
            startTime.set(Calendar.DAY_OF_MONTH, 1);
            startTime.set(Calendar.HOUR_OF_DAY, 3);
            startTime.set(Calendar.MINUTE, 0);
            startTime.set(Calendar.MONTH, newMonth-1);
            startTime.set(Calendar.YEAR, newYear);
            endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR_OF_DAY, 3);
            event = new WeekViewEvent(5, getEventTitle(startTime), startTime, endTime);
            event.setColor(getResources().getColor(R.color.event_color_01));
            events.add(event);

            startTime = Calendar.getInstance();
            startTime.set(Calendar.DAY_OF_MONTH, startTime.getActualMaximum(Calendar.DAY_OF_MONTH));
            startTime.set(Calendar.HOUR_OF_DAY, 15);
            startTime.set(Calendar.MINUTE, 0);
            startTime.set(Calendar.MONTH, newMonth-1);
            startTime.set(Calendar.YEAR, newYear);
            endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR_OF_DAY, 3);
            event = new WeekViewEvent(5, getEventTitle(startTime), startTime, endTime);
            event.setColor(getResources().getColor(R.color.event_color_02));
            events.add(event);

            return events;
        }
    };

    private String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }

    public Runnable calendarUpdater = new Runnable() {

        @Override
        public void run() {
            items.clear();

            // Print dates of the current week
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            String itemvalue;
            events = Utility.readCalendarEvent(getActivity());
            Log.d("=====Event====", events.toString());
            Log.d("=====Date ARRAY====", Utility.startDates.toString());

            for (int i = 0; i < Utility.startDates.size(); i++) {
                itemvalue = df.format(itemmonth.getTime());
                itemmonth.add(GregorianCalendar.DATE, 1);
                items.add(Utility.startDates.get(i).toString());
            }

        }
    };
}
