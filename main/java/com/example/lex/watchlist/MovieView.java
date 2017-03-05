package com.example.lex.watchlist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



public class MovieView extends AppCompatActivity {

    // set some variables
    ImageView poster;
    TextView title;
    TextView year;
    TextView plot;
    Button addbutton;
    Bundle movie;
    Boolean add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);

        // get intent
        Intent intent = getIntent();
        movie = intent.getExtras();

        // change the add/delete button
        add = movie.getBoolean("add");
        addbutton = (Button) findViewById(R.id.addButton);
        if (add == true){
            addbutton.setText("add");
        }
        else {
            addbutton.setText("delete");
        }

        // get the movie poster
        GetMoviePoster();

        // set the data
        title = (TextView) findViewById(R.id.movie_title);
        title.setText(movie.getString("movietitle"));

        year = (TextView) findViewById(R.id.movie_year);
        year.setText(movie.getString("movieyear"));

        plot = (TextView) findViewById(R.id.movie_plot);
        plot.setText(movie.getString("movieplot"));
    }

    // get the poster in an asynctask
    private void GetMoviePoster() {
        PosterAsyncTask posterAsyncTask = new PosterAsyncTask(this);
        posterAsyncTask.execute(movie.getString("movieposter"));
    }

    // get your posterdata back
    public void SetPoster(Bitmap result) {
        poster = (ImageView) findViewById(R.id.movie_poster);
        poster.setImageBitmap(result);
    }

    // add or delete the movie from the list
    public void add_delete_button(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("add", add);
        intent.putExtra("movietitle", movie.getString("movietitle"));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
