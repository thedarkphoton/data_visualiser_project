package com.darkphoton.data_visualiser_project;

import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.darkphoton.data_visualiser_project.data.JSONDownloader;
import com.darkphoton.data_visualiser_project.data.processed.PIndicator;

import java.util.ArrayList;
import java.util.List;

public class SideBarDrawer implements DrawerLayout.DrawerListener {
    private MainActivity _context;
    private DrawerLayout _drawer;
    private ListView _listView;

    public SideBarDrawer(MainActivity context){
        _context = context;

        List<Class> set = new ArrayList<>(PIndicator.indicatorClasses.values());
        SideBarAdapter indicatorAdapter = new SideBarAdapter(context, android.R.layout.simple_list_item_1, set);

        _listView = (ListView) context.findViewById(R.id.side_list);
        _listView.setAdapter(indicatorAdapter);

        _drawer = (DrawerLayout) context.findViewById(R.id.drawer_layout);
        _drawer.setDrawerListener(this);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {
        final int firstItemPosition = _listView.getFirstVisiblePosition();
        final int lastItemPosition = firstItemPosition + _listView.getChildCount() - 1;

        ArrayList<String> urls = new ArrayList<>();

        for (int i = 0; i < _listView.getCount(); i++) {
            RelativeLayout item;

            if (i < firstItemPosition || i > lastItemPosition ) {
                item = (RelativeLayout) _listView.getAdapter().getView(i, null, _listView);
            } else {
                final int childIndex = i - firstItemPosition;
                item = (RelativeLayout) _listView.getChildAt(childIndex);
            }

            CheckBox checkbox = (CheckBox) item.getChildAt(1);
            if (checkbox.isChecked()){
                urls.add("http://api.worldbank.org/countries/indicators/" + ((TextView) item.getChildAt(0)).getText().toString() + "?date=2010:2015&format=json&per_page=10000");
            }
        }

        if (urls.size() > 0) {
            JSONDownloader d = new JSONDownloader(_context, _context.jsonJob);
            d.execute(urls);
        }
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
