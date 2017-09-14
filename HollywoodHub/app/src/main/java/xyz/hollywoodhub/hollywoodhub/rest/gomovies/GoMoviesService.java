package xyz.hollywoodhub.hollywoodhub.rest.gomovies;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import xyz.hollywoodhub.hollywoodhub.model.EpisodeIdsModel;

/**
 * Created by rpandey.ppe on 28/07/17.
 */

public interface GoMoviesService {

    String BASE_URL = "https://gostream.is/";

    @GET("movie/filter/{contentType}/{sortBy}/{genre}/{country}/{release}/{defaultAll}/{quality}/{pageNo}")
    Call<String> getFilteredResponse(
            @Path("contentType") String contentType,
            @Path("sortBy") String sortBy,
            @Path("genre") String genre,
            @Path("country") String country,
            @Path("release") String release,
            @Path("defaultAll") String defaultAll,
            @Path("quality") String quality,
            @Path("pageNo") String pageNo
    );

    @GET("movie/search/{query}")
    Call<String> searchContent(
            @Path("query") String query
    );

    @GET("movie/filter/movie/{sortBy}/all/all/all/all/all/{pageNo}")
    Call<String> getMovies(
            @Path("sortBy") String sortBy,
            @Path("pageNo") String pageNo
    );

    @GET("movie/filter/series/{sortBy}/all/all/all/all/all/{pageNo}")
    Call<String> getTVSeries(
            @Path("sortBy") String sortBy,
            @Path("pageNo") String pageNo
    );

    @GET()
    Call<String> getResponse(
            @Url String episodeUrl
    );
}
