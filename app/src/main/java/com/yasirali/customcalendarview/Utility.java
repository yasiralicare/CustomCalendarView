package com.yasirali.customcalendarview;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.alamkanak.weekview.WeekViewEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yasirali on 7/3/15.
 */
public class Utility {
    public static ArrayList<String> nameOfEvent = new ArrayList<String>();
    public static ArrayList<String> startDates = new ArrayList<String>();
    public static ArrayList<String> endDates = new ArrayList<String>();
    public static ArrayList<String> descriptions = new ArrayList<String>();

    public static ArrayList<String> readCalendarEvent(Context context) {
        Cursor cursor = context.getContentResolver()
                .query(Uri.parse("content://com.android.calendar/events"),
                        new String[] { "calendar_id", "title", "description",
                                "dtstart", "dtend", "eventLocation" }, null,
                        null, null);
        cursor.moveToFirst();
        // fetching calendars name
        String CNames[] = new String[cursor.getCount()];

        // fetching calendars id
        nameOfEvent.clear();
        startDates.clear();
        endDates.clear();
        descriptions.clear();
        for (int i = 0; i < CNames.length; i++) {

            nameOfEvent.add(cursor.getString(1));
            startDates.add(getDate(Long.parseLong(cursor.getString(3))));
            endDates.add(getDate(Long.parseLong(cursor.getString(4))));
            descriptions.add(cursor.getString(2));
            CNames[i] = cursor.getString(1);
            cursor.moveToNext();

        }
        return nameOfEvent;
    }

    public static List<WeekViewEvent> getCalendarEvents(Context context, int newYear, int newMonth) {
        Cursor cursor = context.getContentResolver()
                .query(Uri.parse("content://com.android.calendar/events"),
                        new String[] { "calendar_id", "title", "description",
                                "dtstart", "dtend", "eventLocation" }, null,
                        null, null);
        cursor.moveToFirst();

        // Populate the week view with some events.
        List<WeekViewEvent> events = new ArrayList<>();

        int eventCount = cursor.getCount();




        for (int i = 0; i < eventCount; i++) {

            String title = cursor.getString(1);

            Long startTime = Long.parseLong(cursor.getString(3));
            Long endTime = Long.parseLong(cursor.getString(4));

            Calendar calendarStart = Calendar.getInstance();
            Calendar calendarEnd = Calendar.getInstance();

            calendarStart.setTimeInMillis(startTime);
            calendarEnd.setTimeInMillis(endTime);

            Calendar cal1 = (Calendar) calendarStart.clone();
            cal1.set(Calendar.HOUR_OF_DAY, calendarStart.get(Calendar.HOUR_OF_DAY));
            cal1.set(Calendar.MINUTE, calendarStart.get(Calendar.MINUTE));
            cal1.set(Calendar.MONTH, calendarStart.get(Calendar.MONTH));
            cal1.set(Calendar.YEAR, calendarStart.get(Calendar.YEAR));

            Calendar cal2 = (Calendar) calendarEnd.clone();
            cal2.set(Calendar.HOUR_OF_DAY, calendarEnd.get(Calendar.HOUR_OF_DAY));
            cal2.set(Calendar.MINUTE, calendarEnd.get(Calendar.MINUTE));
            cal2.set(Calendar.MONTH, calendarEnd.get(Calendar.MONTH));
            cal2.set(Calendar.YEAR, calendarEnd.get(Calendar.YEAR));

            WeekViewEvent event = new WeekViewEvent(i, title, cal1, cal2);
            event.setColor(context.getResources().getColor(R.color.event_color_02));
            events.add(event);
            cursor.moveToNext();

        }
        return events;
    }

    public static String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}

