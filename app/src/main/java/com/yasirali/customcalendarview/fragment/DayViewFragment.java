package com.yasirali.customcalendarview.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yasirali.customcalendarview.R;

/**
 * Created by yasirali on 7/3/15.
 */
public class DayViewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_day_view,container,false);
        return v;
    }
}
