package com.darkphoton.data_visualiser_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.darkphoton.data_visualiser_project.data.JSONDownloader;
import com.darkphoton.data_visualiser_project.data.DataJob;
import com.darkphoton.data_visualiser_project.data.raw.DataCache;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private JSONDownloader d;
    private TextView txtData;
    private DataCache dataCache;
    private DataJob jsonJob = new DataJob() {
        @Override
        public void run(ArrayList<JSONArray> jsons) throws JSONException {
            dataCache = new DataCache();
            for (JSONArray json : jsons) {
                dataCache.updateDataCache(new DataCache(json.getJSONArray(1)).getCountries());
            }

            txtData.setText(dataCache.getCountries().toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtData = (TextView)findViewById(R.id.txtData);
        d = new JSONDownloader(this, jsonJob);
        d.execute("http://api.worldbank.org/countries/indicators/5.04.01.02.impexp?per_page=1000&date=2010:2015&format=json");
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
}
