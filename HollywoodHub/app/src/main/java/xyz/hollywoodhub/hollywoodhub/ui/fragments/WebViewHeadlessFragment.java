package xyz.hollywoodhub.hollywoodhub.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jsoup.Jsoup;

import xyz.hollywoodhub.hollywoodhub.constants.Constants;
import xyz.hollywoodhub.hollywoodhub.helper.JsoupHelper;
import xyz.hollywoodhub.hollywoodhub.model.WebViewSourceModel;
import xyz.hollywoodhub.hollywoodhub.utilities.webviewUtils.JSInterfaceHandler;

/**
 * Created by rpandey.ppe on 10/09/17.
 */

public class WebViewHeadlessFragment extends Fragment implements JSInterfaceHandler.JSInterfaceCallbacks {

    private static final String KEY_CONTEXT_ID = "context_id";
    private static final String KEY_URL = "url";

    private WebView webView;
    private boolean isCloudflareBypassed;
    private String givenURL;
    private String contextId;
    private EventBus eventBus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventBus = EventBus.getDefault();
        initialize();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        eventBus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        eventBus.unregister(this);
    }

    private void initialize() {
        webView = new WebView(getContext());
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(false);
        webView.addJavascriptInterface(new JSInterfaceHandler(this), JSInterfaceHandler.TAG);

        enableWebViewCache();

        setWebViewClient();
    }

    private void enableWebViewCache() {
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setAppCachePath(webView.getContext().getCacheDir().getPath());
    }

    private void setWebViewClient() {

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                webView.loadUrl(String.format(Constants.PROCESS_HTML_JS, contextId));
                if (isCloudflareBypassed) {
                    webView.stopLoading();
                }
            }
        });

    }

    public void loadURL(String contextId, String url) {
        this.contextId = contextId;
        webView.loadUrl(url);
    }

    @Subscribe
    public void onWebViewSourceRequest(WebViewSourceModel.Request request) {
        loadURL(request.getContextId(), request.getURL());
    }

    @Override
    public void onProcessHTML(String id, String html) {
        isCloudflareBypassed = JsoupHelper.hasClass(Jsoup.parse(html), "main-content");
        if (isCloudflareBypassed) {
            WebViewSourceModel.Response response = new WebViewSourceModel.Response(id, html);
            EventBus.getDefault().post(response);
            Log.d("source", html);
        }
    }
}
