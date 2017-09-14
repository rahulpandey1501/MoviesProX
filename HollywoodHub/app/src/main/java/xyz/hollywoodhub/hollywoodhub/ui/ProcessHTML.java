package xyz.hollywoodhub.hollywoodhub.ui;

import android.os.Handler;
import android.os.Looper;

import org.jsoup.Jsoup;

import java.util.List;

import xyz.hollywoodhub.hollywoodhub.HollywoodHubApplication;
import xyz.hollywoodhub.hollywoodhub.constants.InterceptorConstants;
import xyz.hollywoodhub.hollywoodhub.helper.ParserFactory;
import xyz.hollywoodhub.hollywoodhub.model.DownloadModel;
import xyz.hollywoodhub.hollywoodhub.model.EpisodesModel;
import xyz.hollywoodhub.hollywoodhub.ui.enums.ContentType;
import xyz.hollywoodhub.hollywoodhub.utilities.webviewUtils.DataProvider;
import xyz.hollywoodhub.hollywoodhub.utilities.webviewUtils.JSInterfaceHandler;

/**
 * Created by rpandey.ppe on 09/09/17.
 */

public class ProcessHTML implements JSInterfaceHandler.JSInterfaceCallbacks {

    private Handler handler;
    private Callback callback;
    private ContentType contentType;

    public interface Callback {
        void onMovieEpisodesLoad(List<EpisodesModel> movieEpisode);
        void onTVSeriesEpisodesLoad(List<List<EpisodesModel>> tvSeriesEpisode);
        void onDownloadSourceLoad(DownloadModel downloadModel);
    }

    public static ProcessHTML initialize(DataProvider context, ContentType contentType) {
        return new ProcessHTML(contentType, context);
    }

    private ProcessHTML(ContentType contentType, Callback callback) {
        this.callback = callback;
        this.contentType = contentType;
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onProcessHTML(String id, String htmlBody) {
        switch (id) {

            case InterceptorConstants.LOAD_MOVIE_EPISODE:
                if (contentType == ContentType.MOVIE) {
                    final List<EpisodesModel> movieEpisode = ParserFactory.getInstance().getMovieEpisode(Jsoup.parse(htmlBody));
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onMovieEpisodesLoad(movieEpisode);
                        }
                    });

                }
                else if (contentType == ContentType.TV_SERIES) {
                    final List<List<EpisodesModel>> tvSeriesEpisode = ParserFactory.getInstance().getTVSeriesEpisode(Jsoup.parse(htmlBody));
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onTVSeriesEpisodesLoad(tvSeriesEpisode);
                        }
                    });
                }

                break;

            case InterceptorConstants.LOAD_MOVIE_DOWNLOAD_SOURCES:
                final DownloadModel model = HollywoodHubApplication.getGsonInstance().fromJson(htmlBody, DownloadModel.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onDownloadSourceLoad(model);
                    }
                });
        }
    }
}
