package xyz.hollywoodhub.hollywoodhub.rest.cmovieshd;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by rpandey.ppe on 23/07/17.
 */

public interface CMoviesHdService {

    String BASE_URL = "http://pomovies.com";

    @GET("/filter/")
    Call<String> getFilter(
            @QueryMap Map<String, String> options
    );

    @GET("/search/")
    Call<String> searchContent(
            @QueryMap Map<String, String> options
    );
}
