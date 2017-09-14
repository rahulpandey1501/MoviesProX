package xyz.hollywoodhub.hollywoodhub.filter.filters;

import java.util.List;

import xyz.hollywoodhub.hollywoodhub.model.FilterModel;

/**
 * Created by rpandey.ppe on 23/07/17.
 */

public class FilmTypeFilter extends FilterModel<FilmTypeFilter> {
    public final static String TAG = "Content Type";
    private List<FilterModel> filters;

    public List<FilterModel> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterModel> filters) {
        this.filters = filters;
    }
}
