package xyz.hollywoodhub.hollywoodhub.rest;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import xyz.hollywoodhub.hollywoodhub.BuildConfig;
import xyz.hollywoodhub.hollywoodhub.HollywoodHubApplication;
import xyz.hollywoodhub.hollywoodhub.rest.cmovieshd.CMoviesHdService;
import xyz.hollywoodhub.hollywoodhub.rest.gomovies.GoMoviesService;
import xyz.hollywoodhub.hollywoodhub.utilities.Utils;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BASIC;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

/**
 * Created by rpandey.ppe on 23/07/17.
 */

public class RestClient {
    private static Retrofit mRetrofit = null;

    private static synchronized Retrofit getInstance(Class<?> className) {
        if (mRetrofit == null) {
            mRetrofit = buildRetrofit(className);
        }
        return mRetrofit;
    }

    public static <T> T create(Class<T> className) {
        return getInstance(className).create(className);
    }

    public static void reset() {
        mRetrofit = null;
    }

    private static Retrofit buildRetrofit(Class<?> className) {
        OkHttpClient.Builder httpClient;
        httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(provideHeaderInterceptor())
                .addInterceptor(provideHttpLoggingInterceptor())
                .addInterceptor(provideOfflineCacheInterceptor())
                .addNetworkInterceptor(provideCacheInterceptor())
                .cache(provideCache());

        String BASE_URL = "";
        if (className == CMoviesHdService.class) {
            BASE_URL = CMoviesHdService.BASE_URL;
        } else if (className == GoMoviesService.class) {
            BASE_URL = GoMoviesService.BASE_URL;
        }

        OkHttpClient client = httpClient.build();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();
    }

    private static Interceptor provideHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                String string = original.url().toString();
                string = string.replace("%26", "&");
                string = string.replace("%3D", "=");
                Request request = original.newBuilder().url(string)
                        .method(original.method(), original.body()).build();

                return chain.proceed(request);
            }
        };
    }

    private static Cache provideCache() {
        Cache cache = null;
        try {
            cache = new Cache(new File(HollywoodHubApplication.getInstance().getCacheDir(), "http-response"), 10 * 1024 * 1024); // 10 MB
        } catch (Exception e) {
            Log.e(e.getMessage(), "Could not create Cache!");
        }
        return cache;
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.v("http", message);
                    }
                });
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? BASIC : NONE);
        return httpLoggingInterceptor;
    }

    private static Interceptor provideCacheInterceptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                okhttp3.Response originalResponse = chain.proceed(chain.request());
                String cacheControl = originalResponse.header("Cache-Control");
                if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                        cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, max-age=" + 5000)
                            .build();
                } else {
                    return originalResponse;
                }
            }
        };
    }

    private static Interceptor provideOfflineCacheInterceptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request();
                if (!Utils.isNetworkAvailable()) {
                    request = request.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached")
                            .build();
                }
                return chain.proceed(request);
            }
        };
    }
}
