package xyz.hollywoodhub.hollywoodhub.rest.cmovieshd;

import android.util.SparseArray;

import java.util.HashMap;

import retrofit2.Call;
import xyz.hollywoodhub.hollywoodhub.model.FilterModel;
import xyz.hollywoodhub.hollywoodhub.rest.BaseApi;
import xyz.hollywoodhub.hollywoodhub.rest.IRestCallBacks;
import xyz.hollywoodhub.hollywoodhub.rest.RestClient;
import xyz.hollywoodhub.hollywoodhub.rest.RestConstants;

/**
 * Created by rpandey.ppe on 24/07/17.
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
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put(RestConstants.SORT_KEY, sortBy);
        queryMap.put(RestConstants.PAGE_KEY, pageNo);
        for (int i = 0; i < filters.size(); ++i) {
            FilterModel filterModel = filters.valueAt(i);
            String query = filterModel.getValue();
            if (queryMap.containsKey(filterModel.getKey())) {
                query = queryMap.get(filterModel.getKey());
                query += "&" + filterModel.getKey() + "=" + filterModel.getValue();
            }
            queryMap.put(filterModel.getKey(), query);
        }

        Call<String> call = RestClient.create(CMoviesHdService.class).getFilter(queryMap);
        triggerCall(call);
    }
}
