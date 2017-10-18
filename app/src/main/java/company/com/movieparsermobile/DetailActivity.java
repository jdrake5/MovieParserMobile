package company.com.movieparsermobile;

/**
 * Created by John Drake on 4/1/2017.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView releaseDateView;
    private TextView descriptionView;
    private ImageView posterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // get references to the views.
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        releaseDateView = (TextView) findViewById(R.id.releaseDateView);
        descriptionView = (TextView) findViewById(R.id.overviewView);
        posterView = (ImageView) findViewById(R.id.imageView);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(MovieAdapter.MOVIE);

        titleTextView.setText(movie.getTitle());
        releaseDateView.setText("Release Date: " + movie.getRelease_date());
        descriptionView.setText(movie.getOverview());
        Picasso.with(posterView.getContext()).load(movie.getPoster_path()).into(posterView);
    }
}