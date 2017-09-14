package xyz.hollywoodhub.hollywoodhub.filter.filters;

import java.util.List;

import xyz.hollywoodhub.hollywoodhub.model.FilterModel;

/**
 * Created by rpandey.ppe on 23/07/17.
 */

public class CountryFilter {

    public static final String TAG = "Country";
    private List<FilterModel> filters;

    public List<FilterModel> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterModel> filters) {
        this.filters = filters;
    }

}
