package com.example.lex.watchlist;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by lex on 2/27/2017.
 */

class MovieAsyncTask extends AsyncTask<String, Integer, String> {
    Context context;
    MainActivity mainAct;

    public MovieAsyncTask(MainActivity main) {
        this.mainAct = main;
        this.context = this.mainAct.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected String doInBackground(String... params) {
        try {
            return HttpRequestHelper.downloadFromServer(params);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        ArrayList movies = new ArrayList<String>(4);

        try {
            JSONObject movieStreamObject = new JSONObject(result);
            String resulttitle = movieStreamObject.getString("Title");
            String resultposter = movieStreamObject.getString("Poster");
            String resultyear = movieStreamObject.getString("Year");
            String resultplot = movieStreamObject.getString("Plot");

            for (int i = 0; i < 4; i++) {
                movies.add("");
            }
            movies.set(0, resulttitle);
            movies.set(1, resultposter);
            movies.set(2, resultyear);
            movies.set(3, resultplot);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "No such movie found...", Toast.LENGTH_SHORT).show();
        }
        try {
            this.mainAct.movieStartIntent(movies);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
