package com.example.lex.watchlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {

    // state the listview and the edittext
    ListView movie_layout;
    EditText search_bar;

    // state  the watchlist
    ArrayList<String> movies;

    // state this activity
    MainActivity main = this;

    // state the moviearray that comes back from the search
    ArrayList<String> movieArray = new ArrayList<String>(4);

    // state a boolean to notice if the movie is added or deleted
    Boolean add = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get intent and extras
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        // load the watchlist
        LoadSharedPreferencesMovies();

        // add or delete the movie from the watchlist
        try {
            String movietitle = extras.getString("movietitle");

            if (extras.getBoolean("add") || !extras.getBoolean("add")) {
                add = extras.getBoolean("add");
                if (add == true) {
                    // add the movie
                    movies.add(movietitle);
                } else {
                    // delete the movie
                    movies.remove(movietitle);
                }
            }
        } catch (RuntimeException e){
            e.printStackTrace();
        }

        // save the new movielist
        SaveSharedPreferencesMovies();

        // sate the searchbar
        search_bar = (EditText) findViewById(R.id.search_bar);
        assert search_bar != null;

        // check for a savedinstancestate
        if (savedInstanceState != null) {
            // set the text to the saved state
            String savedsearch = savedInstanceState.getString("search");
            search_bar.setText(savedsearch);
        }

        // make a listview of the watchlist
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, movies);
        movie_layout = (ListView) findViewById(R.id.movie_ListView);
        assert movie_layout != null;
        movie_layout.setAdapter(arrayAdapter);

        // set an onclick listener for every list item
        movie_layout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // state the movie and the asynctask
                String moviesearch = movies.get(position);
                MovieAsyncTask asyncTask = new MovieAsyncTask(main);

                // search the movie
                add = false;
                asyncTask.execute(moviesearch);
            }
        });
    }

    // search for a movie
    public void moviesearch(View view) {

        // send a toast
        Toast.makeText(main, "searching for movies...", Toast.LENGTH_SHORT).show();

        //
        String moviesearch = search_bar.getText().toString();
        MovieAsyncTask asyncTask = new MovieAsyncTask(this);
        asyncTask.execute(moviesearch);

        search_bar.getText().clear();
    }

    public void movieStartIntent(ArrayList<String> movieData) throws ExecutionException, InterruptedException, TimeoutException {

        movieArray = movieData;
        makeMovieAdapter();
    }

    public void makeMovieAdapter() {
        Intent intent = new Intent(main, MovieView.class);
        if (movieArray.size() != 0) {
            if (movies.contains(movieArray.get(0))){
                add = false;
            }
            else {
                add = true;
            }

            intent.putExtra("movietitle", movieArray.get(0));
            intent.putExtra("movieposter", movieArray.get(1));
            intent.putExtra("movieyear", movieArray.get(2));
            intent.putExtra("movieplot", movieArray.get(3));
            intent.putExtra("add", add);

            startActivity(intent);
        }
    }

    public void SaveSharedPreferencesMovies() {
        Set<String> set = new HashSet<String>();
        set.addAll(movies);

        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putStringSet("movies", set);
        editor.commit();
    }

    public void LoadSharedPreferencesMovies() {
        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);

        Set<String> moviegetter =  prefs.getStringSet("movies", null);

        if (moviegetter != null) {
            movies = new ArrayList<String>(moviegetter);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("search", search_bar.getText().toString());

        super.onSaveInstanceState(outState);
    }
}
