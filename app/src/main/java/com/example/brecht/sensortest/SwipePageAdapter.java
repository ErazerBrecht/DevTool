package com.example.brecht.sensortest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Brecht on 3/03/2015.
 */
public class SwipePageAdapter extends FragmentPagerAdapter {
    public SwipePageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new StopwatchFragment();
            case 1:
                return new RootLoginFragment();
            case 2:
                return new RotationFragment();
            case 3:
                return new GyroscopeFragment();
            case 4:
                return new GravityFragment();
            case 5:
                return new MagnetometerFragment();
            case 6:
                return new AcceleroFragment();
            default:
                break;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 7;
    }
}