package xyz.hollywoodhub.hollywoodhub.rest.cmovieshd;

import java.util.HashMap;

import retrofit2.Call;
import xyz.hollywoodhub.hollywoodhub.rest.BaseApi;
import xyz.hollywoodhub.hollywoodhub.rest.IRestCallBacks;
import xyz.hollywoodhub.hollywoodhub.rest.RestClient;
import xyz.hollywoodhub.hollywoodhub.rest.RestConstants;

/**
 * Created by rpandey.ppe on 24/07/17.
 */

public class GetMoviesApi extends BaseApi<String> {
    private String pageNo;
    private String sortBy;

    public GetMoviesApi(String sortBy, String pageNo, IRestCallBacks<String> callBacks) {
        super(callBacks);
        this.pageNo = pageNo;
        this.sortBy = sortBy;
        this.callBacks = callBacks;
    }

    public void fetch() {
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put(RestConstants.SORT_KEY, sortBy);
        queryMap.put(RestConstants.PAGE_KEY, pageNo);
        queryMap.put(RestConstants.QUALITY_KEY, "all");
        queryMap.put(RestConstants.YEAR_KEY, "all");
        queryMap.put(RestConstants.TYPE_KEY, RestConstants.TYPE_MOVIE);
        Call<String> call = RestClient.create(CMoviesHdService.class).getFilter(queryMap);
        triggerCall(call);
    }
}
