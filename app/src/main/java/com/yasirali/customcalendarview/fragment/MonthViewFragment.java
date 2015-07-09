package com.yasirali.customcalendarview.fragment;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yasirali.customcalendarview.R;
import com.yasirali.customcalendarview.Utility;
import com.yasirali.customcalendarview.adapter.MonthViewAdapter;
import com.yasirali.customcalendarview.model.Event;
import com.yasirali.customcalendarview.ui.SingleDayViewActivity;

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
public class MonthViewFragment extends Fragment{

    public GregorianCalendar month, itemmonth;// calendar instances.

    public MonthViewAdapter adapter;// adapter instance
    public Handler handler;// for grabbing some event values for showing the dot
    // marker.
    public ArrayList<String> items; // container to store calendar items which
    // needs showing the event marker
    ArrayList<String> event;
    LinearLayout rLayout;
    ArrayList<String> date;
    ArrayList<String> desc;
    private List<Event> mEvents;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.tab_month_view,container,false);


        Locale.setDefault(Locale.US);

        rLayout = (LinearLayout) view.findViewById(R.id.text);
        month = (GregorianCalendar) GregorianCalendar.getInstance();
        itemmonth = (GregorianCalendar) month.clone();

        items = new ArrayList<String>();

        adapter = new MonthViewAdapter(getActivity(), month);

        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        gridview.setAdapter(adapter);

        handler = new Handler();
        handler.post(calendarUpdater);

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

        RelativeLayout previous = (RelativeLayout) view.findViewById(R.id.previous);

        previous.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar(view);
            }
        });

        RelativeLayout next = (RelativeLayout) view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar(view);

            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // removing the previous view if added
                if (((LinearLayout) rLayout).getChildCount() > 0) {
                    ((LinearLayout) rLayout).removeAllViews();
                }
                desc = new ArrayList<String>();
                date = new ArrayList<String>();
                ((MonthViewAdapter) parent.getAdapter()).setSelected(v);
                String selectedGridDate = MonthViewAdapter.dayString
                        .get(position);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                try {
                    Date startDate = dateFormat.parse(selectedGridDate);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startDate);
                    Intent dayViewIntent = new Intent(getActivity(), SingleDayViewActivity.class);
                    dayViewIntent.putExtra("selectedDate", cal);
                    startActivity(dayViewIntent);
                } catch (ParseException e) {
                    e.printStackTrace();
                }




                /*String[] separatedTime = selectedGridDate.split("-");
                String gridvalueString = separatedTime[2].replaceFirst("^0*",
                        "");// taking last part of date. ie; 2 from 2012-12-02.
                int gridvalue = Integer.parseInt(gridvalueString);
                // navigate to next or previous month on clicking offdays.
                if ((gridvalue > 10) && (position < 8)) {
                    setPreviousMonth();
                    refreshCalendar(view);
                } else if ((gridvalue < 7) && (position > 28)) {
                    setNextMonth();
                    refreshCalendar(view);
                }
                ((MonthViewAdapter) parent.getAdapter()).setSelected(v);

                List<Event> events = getEvents();

                for(Event ev : events){

                    if(ev.getStartDate().split(" ")[0].equals(selectedGridDate)){
                        desc.add(ev.getText());
                    }
                }

                *//*for (int i = 0; i < Utility.startDates.size(); i++) {
                    if (Utility.startDates.get(i).equals(selectedGridDate)) {
                        desc.add(Utility.nameOfEvent.get(i));
                    }
                }*//*

                if (desc.size() > 0) {
                    for (int i = 0; i < desc.size(); i++) {
                        TextView rowTextView = new TextView(getActivity());

                        // set some properties of rowTextView or something
                        rowTextView.setText("Event:" + desc.get(i));
                        rowTextView.setTextColor(Color.BLACK);

                        // add the textview to the linearlayout
                        rLayout.addView(rowTextView);

                    }

                }*/

                desc = null;

            }

        });

        return view;
    }

    protected void setNextMonth() {
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMaximum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) + 1),
                    month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            month.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) + 1);
        }

    }

    protected void setPreviousMonth() {
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            month.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            month.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }

    }

    protected void showToast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();

    }

    public void refreshCalendar(View parentView) {
        TextView title = (TextView) parentView.findViewById(R.id.title);

        adapter.refreshDays();
        adapter.notifyDataSetChanged();
        handler.post(calendarUpdater); // generate some calendar items

        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
    }


    private List<Event> getEvents(){
        AssetManager assetManager = getActivity().getAssets();

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




    public Runnable calendarUpdater = new Runnable() {

        @Override
        public void run() {
            items.clear();


            mEvents = getEvents();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);



            for(Event ev : mEvents){
                try {

                    itemmonth.add(GregorianCalendar.DATE, 1);

                    Date startDate = dateFormat.parse(ev.getStartDate());

                    items.add(Utility.getDate(startDate.getTime()));


                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


            // Print dates of the current week
            /*DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            String itemvalue;
            event = Utility.readCalendarEvent(getActivity());
            Log.d("=====Event====", event.toString());
            Log.d("=====Date ARRAY====", Utility.startDates.toString());*/

            /*for (int i = 0; i < Utility.startDates.size(); i++) {
                itemvalue = df.format(itemmonth.getTime());
                itemmonth.add(GregorianCalendar.DATE, 1);
                items.add(Utility.startDates.get(i).toString());
            }*/
            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        }
    };
}
