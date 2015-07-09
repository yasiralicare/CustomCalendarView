package com.yasirali.customcalendarview.ui;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yasirali.customcalendarview.R;
import com.yasirali.customcalendarview.model.Event;

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
import java.util.List;
import java.util.Locale;

public class SingleDayViewActivity extends ActionBarActivity {

    Toolbar toolbar;

    private WeekView mWeekView;

    List<Event> events = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_day_view);

        // Creating The Toolbar and setting it as the Toolbar for the activity

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();

        Calendar selectedDate = (Calendar) b.getSerializable("selectedDate");

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);

        AssetManager assetManager = getAssets();

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

        mWeekView.goToDate(selectedDate);

        // Set long press listener for events.
        //mWeekView.setEventLongPressListener(mEventLongPressListener);

    }

    WeekView.MonthChangeListener mMonthChangeListener = new WeekView.MonthChangeListener() {
        @Override
        public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {


            // Populate the week view with some events.
            //List<WeekViewEvent> events = Utility.getCalendarEvents(getActivity(), newYear, newMonth);

            List<WeekViewEvent> events = generateEventsFromJson(newYear, newMonth);

            //generateDummyEvents(newYear, newMonth, events);

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

                WeekViewEvent event = new WeekViewEvent(1, ev.getText().trim(), startTime, endTime);
                event.setColor(getResources().getColor(R.color.event_color_02));
                events.add(event);


            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return events;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dayview_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_goto_today) {
            mWeekView.goToToday();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
