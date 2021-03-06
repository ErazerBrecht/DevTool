package com.example.brecht.sensortest;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import java.lang.reflect.Field;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener{
    ActionBar actionbar;
    ViewPager viewPager;
    SwipePageAdapter swipe;
    ActionBar.Tab StopwatchTab;
    ActionBar.Tab RotationTab;
    ActionBar.Tab LoginTab;
    ActionBar.Tab AccelerometerTab;
    ActionBar.Tab AcceleroOrientTab;
    ActionBar.Tab GraphTab;

    public static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.pager);
        swipe = new SwipePageAdapter(getSupportFragmentManager());
        actionbar = getActionBar();
        viewPager.setAdapter(swipe);

        StopwatchTab = actionbar.newTab();
        StopwatchTab.setIcon(R.drawable.stopwatch);
        StopwatchTab.setTabListener(this);

        LoginTab = actionbar.newTab();
        LoginTab.setIcon(R.drawable.login_in);
        LoginTab.setTabListener(this);

        RotationTab = actionbar.newTab();
        RotationTab.setIcon(R.drawable.rotation);
        RotationTab.setTabListener(this);

        AccelerometerTab=actionbar.newTab();
        AccelerometerTab.setIcon(R.drawable.accelerometer);
        AccelerometerTab.setTabListener(this);

        AcceleroOrientTab=actionbar.newTab();
        AcceleroOrientTab.setIcon(R.drawable.accelerometer);
        AcceleroOrientTab.setTabListener(this);

        GraphTab = actionbar.newTab();
        GraphTab.setText("GRAPH");
        GraphTab.setTabListener(this);

        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionbar.addTab(StopwatchTab);
        actionbar.addTab(LoginTab);
        actionbar.addTab(RotationTab);
        actionbar.addTab(AccelerometerTab);
        actionbar.addTab(AcceleroOrientTab);
        actionbar.addTab(GraphTab);

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionbar.setSelectedNavigationItem(position);
            }
        });

        makeActionOverflowMenuShown();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void makeActionOverflowMenuShown() {
        //Devices with hardware menu button (e.g. Samsung Note) don't show action overflow menu
        //This code adds the overflow menu manually
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
    }

    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            AcceleroFragment.OnKeyDown(keyCode);

            //and so on...
        }
        return super.onKeyDown(keyCode, event);
    }
*/

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        //Method needs to be implemented (interface)
        //But we don't need it! So it's empty
            }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        //Method needs to be implemented (interface)
        //But we don't need it! So it's empty
    }

}
