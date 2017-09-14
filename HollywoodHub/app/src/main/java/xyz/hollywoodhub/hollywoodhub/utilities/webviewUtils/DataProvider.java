package xyz.hollywoodhub.hollywoodhub.utilities.webviewUtils;

import android.content.Context;
import android.webkit.WebView;

import java.util.List;

import xyz.hollywoodhub.hollywoodhub.model.DownloadModel;
import xyz.hollywoodhub.hollywoodhub.model.EpisodesModel;
import xyz.hollywoodhub.hollywoodhub.ui.ProcessHTML;
import xyz.hollywoodhub.hollywoodhub.ui.enums.ContentType;

/**
 * Created by rpandey.ppe on 09/09/17.
 */

public class DataProvider implements WebViewHelper.WebViewCallbacks, ProcessHTML.Callback {

    private WebView webView;
    private String contentUrl;
    private Callbacks callbacks;
    private ContentType contentType;

    public interface Callbacks {
        void onMovieEpisodesLoad(List<EpisodesModel> movieEpisode);
        void onTVSeriesEpisodesLoad(List<List<EpisodesModel>> tvSeriesEpisode);
        void onDownloadSourceLoad(DownloadModel downloadModel);
        void onTrailerLinkLoad(String url);
    }

    private DataProvider(Context context, WebView webView, String contentUrl, ContentType contentType) {
        this.webView = webView;
        this.contentUrl = contentUrl;
        this.contentType = contentType;
        this.callbacks = (Callbacks) context;
    }

    public static DataProvider newInstance(Context context, WebView webView, String contentUrl, ContentType contentType) {
        return new DataProvider(context, webView, contentUrl, contentType);
    }

    public void initialize() {
        ProcessHTML processHTML = ProcessHTML.initialize(this, contentType);
        WebViewHelper.initialize(this, processHTML, webView, contentUrl);
    }

    @Override
    public void onMovieEpisodesLoad(List<EpisodesModel> movieEpisode) {
        callbacks.onMovieEpisodesLoad(movieEpisode);
    }

    @Override
    public void onTVSeriesEpisodesLoad(List<List<EpisodesModel>> tvSeriesEpisode) {
        callbacks.onTVSeriesEpisodesLoad(tvSeriesEpisode);
    }

    @Override
    public void onDownloadSourceLoad(DownloadModel downloadModel) {
        callbacks.onDownloadSourceLoad(downloadModel);
    }

    @Override
    public void onLoadTrailerLink(String url) {
        callbacks.onTrailerLinkLoad(url);
    }
}
