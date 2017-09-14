package xyz.hollywoodhub.hollywoodhub.rest;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rpandey.ppe on 24/07/17.
 */

public abstract class BaseApi<T> {

    protected IRestCallBacks<T> callBacks;

    protected BaseApi(IRestCallBacks<T> callBacks) {
        this.callBacks = callBacks;
    }

    protected void triggerCall(Call<T> call) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                callBacks.onResponse(call, response);
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                callBacks.onFailure(call, t);
            }



        });
    }
}
