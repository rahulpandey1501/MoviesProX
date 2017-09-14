package xyz.hollywoodhub.hollywoodhub.rest.gomovies;

import retrofit2.Call;
import xyz.hollywoodhub.hollywoodhub.rest.BaseApi;
import xyz.hollywoodhub.hollywoodhub.rest.IRestCallBacks;
import xyz.hollywoodhub.hollywoodhub.rest.RestClient;

/**
 * Created by rpandey.ppe on 24/07/17.
 */

public class GetSearchApi extends BaseApi<String> {
    private String query;

    public GetSearchApi(String query, IRestCallBacks<String> callBacks) {
        super(callBacks);
        this.query = query;
        this.callBacks = callBacks;
    }

    public void fetch() {
        Call<String> call = RestClient.create(GoMoviesService.class).searchContent(query);
        triggerCall(call);
    }
}
