package xyz.hollywoodhub.hollywoodhub.rest.gomovies;

import android.util.SparseArray;

import retrofit2.Call;
import xyz.hollywoodhub.hollywoodhub.filter.filters.CountryFilter;
import xyz.hollywoodhub.hollywoodhub.filter.filters.FilmTypeFilter;
import xyz.hollywoodhub.hollywoodhub.filter.filters.GenreFilter;
import xyz.hollywoodhub.hollywoodhub.filter.filters.QualityFilter;
import xyz.hollywoodhub.hollywoodhub.filter.filters.ReleaseFilter;
import xyz.hollywoodhub.hollywoodhub.model.FilterModel;
import xyz.hollywoodhub.hollywoodhub.rest.BaseApi;
import xyz.hollywoodhub.hollywoodhub.rest.IRestCallBacks;
import xyz.hollywoodhub.hollywoodhub.rest.RestClient;

/**
 * Created by rpandey.ppe on 30/07/17.
 */


public class GetFiltersApi extends BaseApi<String> {
    private String pageNo;
    private String sortBy;
    private SparseArray<FilterModel> filters;

    public GetFiltersApi(String sortBy, String pageNo, SparseArray<FilterModel> filters, IRestCallBacks<String> callBacks) {
        super(callBacks);
        this.pageNo = pageNo;
        this.sortBy = sortBy;
        this.filters = filters;
    }

    public void fetch() {
        String contentType = "all";
        String genre = "all";
        String country = "all";
        String release = "all";
        String defaultAll = "all";
        String quality = "all";

        for (int i=0; i<filters.size(); ++i) {
            FilterModel model = filters.valueAt(i);

            switch (model.getTag()) {

                case FilmTypeFilter.TAG:
                    if (contentType.equals("all")) {
                        contentType = model.getValue();
                    } else {
                        contentType += "-" + model.getValue();
                    }
                    break;

                case GenreFilter.TAG:
                    if (genre.equals("all")) {
                        genre = model.getValue();
                    } else {
                        genre += "-" + model.getValue();
                    }
                    break;

                case CountryFilter.TAG:
                    if (country.equals("all")) {
                        country = model.getValue();
                    } else {
                        country += "-" + model.getValue();
                    }
                    break;

                case ReleaseFilter.TAG:
                    if (release.equals("all")) {
                        release = model.getValue();
                    } else {
                        release += "-" + model.getValue();
                    }
                    break;

                case QualityFilter.TAG:
                    if (quality.equals("all")) {
                        quality = model.getValue();
                    } else {
                        quality += "-" + model.getValue();
                    }
                    break;
            }
        }

        Call<String> call = RestClient.create(GoMoviesService.class).getFilteredResponse(
                contentType, sortBy, genre, country, release, defaultAll, quality, pageNo);
        triggerCall(call);
    }
}