package com.darkphoton.data_visualiser_project.data;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;

public class JSONDownloader extends AsyncTask<String, String, Void> {
    private ProgressDialog _progressDialog;
    private ArrayList<JSONArray> _results;
    private DataJob _job;

    public JSONDownloader(AppCompatActivity context, DataJob job){
        _progressDialog = new ProgressDialog(context);
        _results = new ArrayList<>();
        _job = job;
    }

    @Override
    protected void onPreExecute() {
        _progressDialog.setMessage("Downloading your data...");
        _progressDialog.show();
        _progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface arg0) {
                JSONDownloader.this.cancel(true);
            }
        });
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            JSONArray json = null;
            do {
                String path;
                if (json == null)
                    path = params[0] + "&page=1";
                else
                    path = params[0] + "&page=" + (json.getJSONObject(0).getInt("page") + 1);

                StringBuilder stringBuilder = new StringBuilder();
                URL data = new URL(path);
                BufferedInputStream bis = new BufferedInputStream(data.openStream());

                byte[] buffer = new byte[1024];
                int bytesRead;
                while((bytesRead = bis.read(buffer)) > 0) {
                    String text = new String(buffer, 0, bytesRead);
                    stringBuilder.append(text);
                }
                bis.close();

                json = new JSONArray(stringBuilder.toString());
                _results.add(json);
            } while (json.getJSONObject(0).getInt("page") < json.getJSONObject(0).getInt("pages"));

        } catch (java.io.IOException e) {
            Log.e("URLException", "Error: " + e.toString());
            _progressDialog.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        try {
            _job.run(_results);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        _progressDialog.dismiss();
    }
}