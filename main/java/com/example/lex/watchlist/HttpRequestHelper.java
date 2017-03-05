package com.example.lex.watchlist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lex on 2/27/2017.
 */
public class HttpRequestHelper {

    protected static synchronized String downloadFromServer(String... params) throws MalformedURLException {
        String result = "";
        String chosenTag = params[0];

        URL url = new URL("http://www.omdbapi.com/?t=" + chosenTag);

        HttpURLConnection connect;

        if (url != null) {
            try {
                connect = (HttpURLConnection) url.openConnection();
                connect.setRequestMethod("GET");

                Integer responceCode = connect.getResponseCode();
                if (responceCode >= 200 && responceCode <= 300) {
                    BufferedReader bReader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                    String line;
                    while ((line = bReader.readLine()) != null) {
                        result += line;
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return result;
    }

    public static Bitmap getBitmapFromURL(String... src) {
        try {
            URL url = new URL(src[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
