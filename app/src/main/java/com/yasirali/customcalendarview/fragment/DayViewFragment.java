package com.yasirali.customcalendarview.fragment;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.RectF;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yasirali.customcalendarview.R;
import com.yasirali.customcalendarview.model.Event;
import com.yasirali.customcalendarview.SingleDayViewActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by yasirali on 7/3/15.
 */
public class DayViewFragment extends Fragment {

    public ArrayList<String> items; // container to store calendar items which
    // needs showing the event marker

    public GregorianCalendar month, itemmonth;// calendar instances.

    private WeekView mWeekView;

    List<Event> events = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_day_view,container,false);

        setHasOptionsMenu(true);

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) v.findViewById(R.id.weekView);

        AssetManager assetManager = getActivity().getAssets();

        InputStream ims = null;
        try {
            ims = assetManager.open("cal.json");
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(ims);

            Type listType = new TypeToken<List<Event>>(){}.getType();

            events = gson.fromJson(reader, listType);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set an action when any event is clicked.
        //mWeekView.setOnEventClickListener(mEventClickListener);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(mMonthChangeListener);

        mWeekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent weekViewEvent, RectF rectF) {
                Calendar selectedDate = weekViewEvent.getStartTime();
                Intent dayViewIntent = new Intent(getActivity(), SingleDayViewActivity.class);
                dayViewIntent.putExtra("selectedDate", selectedDate);
                startActivity(dayViewIntent);
            }
        });





        // Set long press listener for events.
        //mWeekView.setEventLongPressListener(mEventLongPressListener);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dayview_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_goto_today:
                mWeekView.goToToday();
                break;
        }
        return true;
    }

    WeekView.MonthChangeListener mMonthChangeListener = new WeekView.MonthChangeListener() {
        @Override
        public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {


            // Populate the week view with some events.
            //List<WeekViewEvent> events = Utility.getCalendarEvents(getActivity(), newYear, newMonth);

            List<WeekViewEvent> events = generateEventsFromJson(newYear, newMonth);

            //List<WeekViewEvent> events = generateDummyEvents(newYear, newMonth);

            return events;
        }
    };

    private List<WeekViewEvent> generateEventsFromJson(int newYear, int newMonth) {

        List<WeekViewEvent> events = new ArrayList<>();

        List<Event> tempEvents = getEvents(newYear, newMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);


        for(Event ev : tempEvents){
            try {
                Date startDate = dateFormat.parse(ev.getStartDate());
                Date endDate = dateFormat.parse(ev.getEndDate());


                Calendar startTime = Calendar.getInstance();
                startTime.setTime(startDate);

                Calendar endTime = Calendar.getInstance();
                endTime.setTime(endDate);

                WeekViewEvent event = new WeekViewEvent(Integer.parseInt(ev.getId()), ev.getText().trim(), startTime, endTime);
                event.setColor(getResources().getColor(R.color.event_color_02));
                events.add(event);


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return events;
    }

    private List<WeekViewEvent> generateDummyEvents(int newYear, int newMonth) {

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

        return  events;
    }


    private List<Event> getEvents(int year, int month){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);

        List<Event> tempEvents = new ArrayList<>();

            Log.d("---", "-------------------***---------------------");

        final Calendar c = Calendar.getInstance();

            for(Event e : events){

                try {

                    Date startDate = dateFormat.parse(e.getStartDate());
                    c.setTime(startDate);

                    int tmpYear = c.get(Calendar.YEAR);
                    int tmpMonth = c.get(Calendar.MONTH);
                    if(tmpYear == year &&  tmpMonth == month){
                        tempEvents.add(e);
                        Log.d("Event", e.getText() + " Start:" + e.getStartDate() + " End:" + e.getEndDate());
                    }
                    /*Calendar cal = Calendar.getInstance(Locale.US);
                    cal.setTime(startDate);*/

                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }

            Log.d("---", "--------------------***--------------------");


        return tempEvents;
    }

    private String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }

    public Runnable calendarUpdater = new Runnable() {

        @Override
        public void run() {
            /*items.clear();

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
            }*/

        }
    };
}
