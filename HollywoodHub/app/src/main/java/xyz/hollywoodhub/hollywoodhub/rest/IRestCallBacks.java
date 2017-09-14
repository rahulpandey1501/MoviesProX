package xyz.hollywoodhub.hollywoodhub.rest;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by rpandey.ppe on 24/07/17.
 */

public interface IRestCallBacks<T> {
    void onResponse(Call<T> call, Response<T> response);
    void onFailure(Call<T> call, Throwable t);
}
