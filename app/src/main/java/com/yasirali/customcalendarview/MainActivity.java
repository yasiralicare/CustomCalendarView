package com.yasirali.customcalendarview;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yasirali.customcalendarview.adapter.ViewPagerAdapter;
import com.yasirali.customcalendarview.slidingtab.SlidingTabLayout;
import com.yasirali.customcalendarview.ui.CustomViewPager;


public class MainActivity extends ActionBarActivity {


    Toolbar toolbar;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView leftDrawerList;
    private ArrayAdapter<String> navigationDrawerAdapter;
    private String[] leftSliderData = {"Home", "Android", "Sitemap", "About", "Contact Me"};


    CustomViewPager pager;
    ViewPagerAdapter viewPagerAdapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"List","Day", "Month"};
    int Numboftabs =3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creating The Toolbar and setting it as the Toolbar for the activity
        nitView();

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        initDrawer();

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        viewPagerAdapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (CustomViewPager) findViewById(R.id.pager);
        pager.setAdapter(viewPagerAdapter);
        pager.setSwipeable(false);

        // Assigning the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
    }

    private void nitView() {
        leftDrawerList = (ListView) findViewById(R.id.left_drawer);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationDrawerAdapter = new ArrayAdapter<>( MainActivity.this, android.R.layout.simple_list_item_1, leftSliderData);
        leftDrawerList.setAdapter(navigationDrawerAdapter);
    }

    private void initDrawer() {

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
