package company.com.movieparsermobile;

import java.util.List;

/**
 * Class with fields the same name as json equivalents for parsing
 * Created by John Drake on 3/26/2017.
 */

public class MovieList {
    private long page;
    private Movie[] results;
    private long total_results;
    private long total_pages;

    public long getTotal_results() {
        return total_results;
    }

    public long getPage() {
        return page;
    }

    public Movie[] getResults() {
        return results;
    }

    public long getTotal_pages() {
        return total_pages;
    }

}
