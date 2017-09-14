package xyz.hollywoodhub.hollywoodhub.rest.cmovieshd;

import java.util.HashMap;

import retrofit2.Call;
import xyz.hollywoodhub.hollywoodhub.rest.BaseApi;
import xyz.hollywoodhub.hollywoodhub.rest.IRestCallBacks;
import xyz.hollywoodhub.hollywoodhub.rest.RestClient;
import xyz.hollywoodhub.hollywoodhub.rest.RestConstants;
import xyz.hollywoodhub.hollywoodhub.rest.gomovies.GoMoviesService;

/**
 * Created by rpandey.ppe on 24/07/17.
 */

public class GetSearchApi extends BaseApi<String> {
    private String pageNo;
    private String query;

    public GetSearchApi(String query, String pageNo, IRestCallBacks<String> callBacks) {
        super(callBacks);
        this.pageNo = pageNo;
        this.query = query;
        this.callBacks = callBacks;
    }

    public void fetch() {
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put(RestConstants.SEARCH_KEY, query);
        queryMap.put(RestConstants.PAGE_KEY, pageNo);
        Call<String> call = RestClient.create(CMoviesHdService.class).searchContent(queryMap);
        triggerCall(call);
    }
}
