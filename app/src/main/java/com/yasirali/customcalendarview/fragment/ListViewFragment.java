package com.yasirali.customcalendarview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yasirali.customcalendarview.R;

/**
 * Created by yasirali on 7/3/15.
 */
public class ListViewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_list_view,container,false);
        return v;
    }
}
