package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class ListNeighbourPagerAdapter extends FragmentPagerAdapter {

    // >>> Initialement dans le code & opt2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public ListNeighbourPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    // //fin

    /* >>> Opt1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private Context myContext;
    int totalTabs;

    public ListNeighbourPagerAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }
    */ //fin

    /**
     * getItem is called to instantiate the fragment for the given page.
     * @param position
     * @return
     */

    /* >>> Initialement dans le code >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public Fragment getItem(int position) {
        return NeighbourFragment.newInstance();
    }
    */ //fin

    /* >>> Opt1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return NeighbourFragment.newInstance();
            case 1:
                return FavoriteFragment.newInstance();
            default:
                return NeighbourFragment.newInstance();
        }
    }
    */ //fin

    // >>> Opt2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public Fragment getItem(int position) {
        return NeighbourFragment.newInstance(position);
    }
    // //fin

    /**
     * get the number of pages
     * @return
     */

    /* >>> Initialement dans le code >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public int getCount() {
        return 1;
    }
    */ //fin

    /* >>> Opt1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public int getCount() {
        return totalTabs;
    }
    */ //fin

    // >>> Opt2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Override
    public int getCount() {
        return 2;
    }
    // //fin
}