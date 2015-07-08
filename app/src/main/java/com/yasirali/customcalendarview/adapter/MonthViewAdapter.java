package com.yasirali.customcalendarview.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yasirali.customcalendarview.R;
import com.yasirali.customcalendarview.model.Event;
import com.yasirali.customcalendarview.ui.EventTitleTextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by yasirali on 7/3/15.
 */
public class MonthViewAdapter extends BaseAdapter {

    private Context mContext;

    private java.util.Calendar month;
    public GregorianCalendar pmonth; // calendar instance for previous month
    /**
     * calendar instance for previous month for getting complete view
     */
    public GregorianCalendar pmonthmaxset;
    private GregorianCalendar selectedDate;
    int firstDay;
    int maxWeeknumber;
    int maxP;
    int calMaxP;
    int lastWeekDay;
    int leftDays;
    int mnthlength;
    String itemvalue, curentDateString;
    DateFormat df;

    private ArrayList<String> items;
    public static List<String> dayString;
    private View previousView;
    private List<Event> mEvents;


    public MonthViewAdapter(Context c, GregorianCalendar monthCalendar) {
        MonthViewAdapter.dayString = new ArrayList<String>();
        Locale.setDefault(Locale.US);
        month = monthCalendar;
        selectedDate = (GregorianCalendar) monthCalendar.clone();
        mContext = c;
        month.set(GregorianCalendar.DAY_OF_MONTH, 1);
        this.items = new ArrayList<String>();
        df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        curentDateString = df.format(selectedDate.getTime());
        mEvents = getEvents();
        refreshDays();
    }

    public void setItems(ArrayList<String> items) {
        for (int i = 0; i != items.size(); i++) {
            if (items.get(i).length() == 1) {
                items.set(i, "0" + items.get(i));
            }
        }
        this.items = items;
    }

    public int getCount() {
        return dayString.size();
    }

    public Object getItem(int position) {
        return dayString.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new view for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView dayView;
        if (convertView == null) { // if it's not recycled, initialize some
            // attributes
            LayoutInflater vi = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.calendar_day_item, null);

        }
        dayView = (TextView) v.findViewById(R.id.date);
        // separates daystring into parts.
        String[] separatedTime = dayString.get(position).split("-");
        // taking last part of date. ie; 2 from 2012-12-02
        String gridvalue = separatedTime[2].replaceFirst("^0*", "");
        // checking whether the day is in current month or not.
        if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
            // setting offdays to white color.
            dayView.setTextColor(Color.WHITE);
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
            dayView.setTextColor(Color.WHITE);
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else {
            // setting curent month's days in blue color.
            dayView.setTextColor(Color.BLUE);
        }

        if (dayString.get(position).equals(curentDateString)) {
            setSelected(v);
            previousView = v;
        } else {
            v.setBackgroundResource(R.drawable.list_item_day_view_background);
        }
        dayView.setText(gridvalue);

        // create date string for comparison
        String date = dayString.get(position);

        if (date.length() == 1) {
            date = "0" + date;
        }
        String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }

        LinearLayout eventTextContainer = (LinearLayout) v.findViewById(R.id.eventTextContainer);

        eventTextContainer.findViewById(R.id.eventDots).setVisibility(View.GONE);
        TextView tv1 = (TextView) eventTextContainer.findViewById(R.id.eventTitle1);
        TextView tv2= (TextView) eventTextContainer.findViewById(R.id.eventTitle2);
        TextView tv3 = (TextView) eventTextContainer.findViewById(R.id.eventTitle3);
        tv1.setText("");
        tv2.setText("");
        tv3.setText("");

        tv1.setVisibility(View.GONE);
        tv2.setVisibility(View.GONE);
        tv3.setVisibility(View.GONE);

        /*EventTitleTextView tv = new EventTitleTextView(mContext);
        tv.setText("123123123");
        tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        eventTextContainer.addView(tv);*/

        // show event text views if there is any event
        if (date.length() > 0 && items != null && items.contains(date)) {
            List<Event> events = getEventsByDate(date);
            if(events.size() <= 0){
                eventTextContainer.setVisibility(View.GONE);
            } else{
                eventTextContainer.setVisibility(View.VISIBLE);

                int size = events.size() >= 4 ? 3 : events.size();

                switch (size){
                    case 1:
                        tv1.setText(events.get(0).getText());
                        tv1.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        tv1.setText(events.get(0).getText());
                        tv2.setText(events.get(1).getText());
                        tv1.setVisibility(View.VISIBLE);
                        tv2.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        tv1.setText(events.get(0).getText());
                        tv2.setText(events.get(1).getText());
                        tv3.setText(events.get(2).getText());
                        tv1.setVisibility(View.VISIBLE);
                        tv2.setVisibility(View.VISIBLE);
                        tv3.setVisibility(View.VISIBLE);
                        break;
                }

                if(events.size() >= 4){
                    eventTextContainer.findViewById(R.id.eventDots).setVisibility(View.VISIBLE);
                }
            }
            /*for(Event ev : events){
                EventTitleTextView tv = new EventTitleTextView(mContext);
                tv.setText(ev.getText());
                tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                eventTextContainer.addView(tv);
            }*/
        }
        return v;
    }

    private List<Event> getEvents(){
        AssetManager assetManager = mContext.getAssets();

        List<Event> events = new ArrayList<>();

        try {
            InputStream ims = assetManager.open("cal.json");

            Gson gson = new Gson();
            Reader reader = new InputStreamReader(ims);

            Type listType = new TypeToken<List<Event>>(){}.getType();

            events = gson.fromJson(reader, listType);

            /*for(Event e : events){
                Log.d("Event Title:", e.getText());
            }*/


        } catch (IOException e) {
            e.printStackTrace();
        }

        return events;
    }

    private List<Event> getEventsByDate(String date){

        List<Event> events = new ArrayList<>();

            for(Event e : mEvents){
                if(e.getStartDate().split(" ")[0].equals(date)){
                    events.add(e);
                }
            }

        return events;
    }

    public View setSelected(View view) {
        if (previousView != null) {
            previousView.setBackgroundResource(R.drawable.list_item_day_view_background);
        }
        previousView = view;
        view.setBackgroundResource(R.drawable.calendar_cel_selectl);
        return view;
    }

    public void refreshDays() {
        // clear items
        items.clear();
        dayString.clear();
        Locale.setDefault(Locale.US);
        pmonth = (GregorianCalendar) month.clone();
        // month start day. ie; sun, mon, etc
        firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
        // finding number of weeks in current month.
        maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
        // allocating maximum row number for the gridview.
        mnthlength = maxWeeknumber * 7;
        maxP = getMaxP(); // previous month maximum day 31,30....
        calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
        /**
         * Calendar instance for getting a complete gridview including the three
         * month's (previous,current,next) dates.
         */
        pmonthmaxset = (GregorianCalendar) pmonth.clone();
        /**
         * setting the start date as previous month's required date.
         */
        pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

        /**
         * filling calendar gridview.
         */
        for (int n = 0; n < mnthlength; n++) {

            itemvalue = df.format(pmonthmaxset.getTime());
            pmonthmaxset.add(GregorianCalendar.DATE, 1);
            dayString.add(itemvalue);

        }
    }

    private int getMaxP() {
        int maxP;
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            pmonth.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }
        maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        return maxP;
    }

}
