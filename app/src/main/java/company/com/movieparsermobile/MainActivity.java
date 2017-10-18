package company.com.movieparsermobile;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String urlString = "https://api.themoviedb.org/3/movie/popular?api_key=";
    private RecyclerView mRecyclerView;
    private ArrayList<Movie> movies = new ArrayList<>();

    // movieAdapter connects mArticles to mRecyclerView
    private MovieAdapter movieAdapter;

    /**
     * When the app is activated it wll connect views in the xml activity to the java
     * It will also create and array or URL's each one representing a page of movies
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Initially, movies is an empty ArrayList.  We populate it with MovieAsyncTask.
        movieAdapter = new MovieAdapter(movies);
        mRecyclerView.setAdapter(movieAdapter);

        // Construct URL and request data...
        try {
            int numOfPages = 5;
            URL[] urls = new URL[numOfPages];
            for (int j = 0; j< urls.length; j++) {

                // all page url's are the same but with different page numbers
                urls[j] = new URL(urlString + ApiKey.apiKey + "&page=" + (j+1));
            }
            new MovieAsyncTask(this).execute(urls);

            // Fetch the movies on a background thread; it will populate movies the array.
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * This class (1) takes a URL, (2) makes an HTTP request, (3) parses the resulting JSON
     * into a MovieList, and (4) returns the array of Movie.
     *
     * This class requires a Context in its constructor so that we can make Toasts.
     */
    public class MovieAsyncTask extends AsyncTask<URL, Void, Movie[]> {

        Context context;

        public MovieAsyncTask(Context context) {
            this.context = context;
        }

        // This function is done on the background thread.
        @Override
        protected Movie[] doInBackground(URL... params) {
            MovieList movieCollection = null;
            Movie[] allMovies = new Movie[0];
            try{

                // for every url in the array we will connect and retrieve movies
                //taking json from url, parsing with gson
                for (int i = 0; i < params.length; i++) {
                    URL url = params[i];
                    URLConnection connection = url.openConnection();
                    connection.connect();
                    InputStream inStream = connection.getInputStream();
                    InputStreamReader inStreamReader = new InputStreamReader(inStream, Charset.forName("UTF-8"));
                    Gson gson = new Gson();
                    movieCollection = gson.fromJson(inStreamReader, MovieList.class);
                    inStream.close();
                    inStreamReader.close();
                    Movie[] copyArray = allMovies;
                    allMovies = new Movie[movieCollection.getResults().length + copyArray.length];
                    CopyArray(movieCollection, allMovies, copyArray);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // returns an array with all the movies from the first 10 pages
            return allMovies;
        }

        /**
         * to expand the size of the movie array we create a new array
         * this new array has all the movies we previously found plus the ones retrieved from the last url connection
         * @param movieCollection - instance of the class holding the latest movies from last url call
         * @param allMovies - array of all the movies collected thus far
         * @param copyArray - copy of allMovies so that more can be added
         */
        private void CopyArray(MovieList movieCollection, Movie[] allMovies, Movie[] copyArray) {
            for (int l = 0; l < allMovies.length; l++) {
                if (l < copyArray.length) {
                    allMovies[l] = copyArray[l];
                }
                else {
                    allMovies[l] = movieCollection.getResults()[l-copyArray.length];
                }
            }
        }

        // This code is run on the UI thread
        @Override
        protected void onPostExecute(Movie[] movieArray) {

            // Pop up a Toast if we failed to get data.
            if (movieArray == null) {
                Toast.makeText(context, "Failed to get network data", Toast.LENGTH_LONG).show();
                return;
            }

            // Empty the ArrayList of movies (movies) and fill it with the ones we loaded.
            movies.clear();
            for (Movie movie : movieArray) {
                Log.d("Movie", movie.getTitle());
                Log.d("LINK", movie.getPoster_path());
                movies.add(movie);
            }

            // Poke movieAdapter to let it know that its data changed.
            movieAdapter.notifyDataSetChanged();
        }
    }
}