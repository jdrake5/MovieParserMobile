package company.com.movieparsermobile;

/**
 * Created by John Drake on 3/26/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String poster_path;
    private boolean adult;
    private String overview;
    private String release_date;
    private int[] genre_ids;
    private int id;
    private String original_title;
    private String original_language;
    private String title;
    private String backdrop_path;
    private double popularity;
    private long vote_count;
    private boolean video;
    private double vote_average;

    /**
     * The url returned from the json is incompete so it is prefeaced with the necesary string
     * @return - the proper url string to access the image
     */
    public String getPoster_path() {
        return "https://image.tmdb.org/t/p/w1280/" + poster_path;
    }

    /**
     * Getters for all fields
     * @return - the indicated field
     */
    public int[] getGenre_ids() {
        return genre_ids;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getId() {
        return id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public long getVote_count() {
        return vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVote_average() {
        return vote_average;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.release_date);
        dest.writeString(this.overview);
        dest.writeString(this.poster_path);
    }

    public Movie(){
    }

    protected Movie(Parcel in) {
        this.title = in.readString();
        this.release_date = in.readString();
        this.overview = in.readString();
        this.poster_path = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
