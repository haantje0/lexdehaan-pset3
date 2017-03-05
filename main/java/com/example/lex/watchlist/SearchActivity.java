package com.example.lex.watchlist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    EditText search_bar;
    ListView lvItems;
    ArrayList<String> movieArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_bar = (EditText) findViewById(R.id.search_bar);
        assert search_bar != null;
    }

    public void moviesearch(View view) {
        String moviesearch = search_bar.getText().toString();
        //MovieAsyncTask asyncTask = new MovieAsyncTask(this);
        //asyncTask.execute(moviesearch);

        search_bar.getText().clear();
    }

    public void movieStartIntent(ArrayList<String> movieData) {

        movieArray = movieData;
        makeMovieAdapter();
    }

    public void makeMovieAdapter() {
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, movieArray);
        lvItems = (ListView) findViewById(R.id.movie_ListView);
        assert lvItems != null;
        lvItems.setAdapter(arrayAdapter);
    }
}
