package com.example.lex.watchlist;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;


/**
 * Created by lex on 2/27/2017.
 */

class PosterAsyncTask extends AsyncTask<String, Integer, Bitmap> {
    Context context;
    MovieView mainAct;

    public PosterAsyncTask(MovieView main) {
        this.mainAct = main;
        this.context = this.mainAct.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {}

    protected Bitmap doInBackground(String... params) {
        return HttpRequestHelper.getBitmapFromURL(params);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        this.mainAct.SetPoster(result);
    }
}
