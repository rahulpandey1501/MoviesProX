package xyz.hollywoodhub.hollywoodhub.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.hollywoodhub.hollywoodhub.R;
import xyz.hollywoodhub.hollywoodhub.model.DownloadModel;
import xyz.hollywoodhub.hollywoodhub.model.EpisodesModel;
import xyz.hollywoodhub.hollywoodhub.ui.enums.ContentType;
import xyz.hollywoodhub.hollywoodhub.utilities.webviewUtils.DataProvider;

public class ContentDetailsActivity extends BaseActivity implements DataProvider.Callbacks {

    @BindView(R.id.web_view) WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_details_actvity);
        initialize();
    }

    @Override
    protected void registerEvent() {
        eventBus = EventBus.getDefault();
    }

    private void initialize() {
        unbinder = ButterKnife.bind(this);
        String contentUrl = EventBus.getDefault().removeStickyEvent(String.class);
        ContentType contentType = EventBus.getDefault().removeStickyEvent(ContentType.class);
        DataProvider.newInstance(this, webView, contentUrl, contentType).initialize();
        loadUrl(contentUrl);
    }

    private void loadUrl(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void onMovieEpisodesLoad(List<EpisodesModel> movieEpisode) {
        if (movieEpisode != null) {
            Log.d("MEpisodes", movieEpisode.size() + "");
        }
    }

    @Override
    public void onTVSeriesEpisodesLoad(List<List<EpisodesModel>> tvSeriesEpisode) {
        if (tvSeriesEpisode != null) {
            Log.d("TEpisodes", tvSeriesEpisode.size() + "");
        }
    }

    @Override
    public void onDownloadSourceLoad(DownloadModel downloadModel) {
        if (downloadModel != null) {
            Log.d("Download", downloadModel.getPlaylist().size() + "");
        }
    }

    @Override
    public void onTrailerLinkLoad(String url) {
        if (url != null) {
            Log.d("Trailer", url);
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.stopLoading();
            webView.destroy();
        }
        super.onDestroy();
    }
}
