package xyz.hollywoodhub.hollywoodhub;

import android.app.Application;

import com.google.gson.Gson;

/**
 * Created by rpandey.ppe on 23/07/17.
 */

public class HollywoodHubApplication extends Application {

    private static HollywoodHubApplication mInstance;
    public static Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static Gson getGsonInstance() {
        if (gson != null) {
            return gson;
        } else {
            gson = new Gson();
            return gson;
        }
    }

    public static synchronized HollywoodHubApplication getInstance() {
        return mInstance;
    }
    public static synchronized HollywoodHubApplication getContext() {
        return mInstance;
    }
}
