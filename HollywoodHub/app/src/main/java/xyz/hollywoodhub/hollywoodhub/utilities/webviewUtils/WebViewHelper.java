package xyz.hollywoodhub.hollywoodhub.utilities.webviewUtils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import xyz.hollywoodhub.hollywoodhub.constants.Constants;
import xyz.hollywoodhub.hollywoodhub.constants.InterceptorConstants;
import xyz.hollywoodhub.hollywoodhub.ui.ProcessHTML;

/**
 * Created by rpandey.ppe on 04/09/17.
 */

public class WebViewHelper {

    private WebView webView;
    private Handler handler;
    private String contentURL;
    private ProcessHTML processHTML;
    private WebView xhrResponseWebView;
    private boolean isEpisodesLoaded = false;
    private WebViewCallbacks webViewCallbacks;

    interface WebViewCallbacks {
        void onLoadTrailerLink(String url);
    }

    private WebViewHelper(ProcessHTML processHTML, WebView webView, WebViewCallbacks webViewCallbacks, String contentURL) {
        this.webView = webView;
        this.processHTML = processHTML;
        this.xhrResponseWebView = new WebView(webView.getContext());
        this.contentURL = contentURL;
        this.webViewCallbacks = webViewCallbacks;
        this.handler = new Handler(Looper.getMainLooper());
        initialize();
    }

    static WebViewHelper initialize(DataProvider context, ProcessHTML processHTML, WebView webView, String contentURL) {
        return new WebViewHelper(processHTML, webView, context, contentURL);
    }

    @SuppressLint("AddJavascriptInterface")
    private void initialize() {

        addWebViewSettings();
        enableWebViewCache();

        webView.addJavascriptInterface(new JSInterfaceHandler(processHTML), JSInterfaceHandler.TAG);
        xhrResponseWebView.addJavascriptInterface(new JSInterfaceHandler(processHTML), JSInterfaceHandler.TAG);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        setWebViewCallbacks();
    }

    private void setWebViewCallbacks() {
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("webview_page_started", url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("webview_page_finish", url);

                if (!isEpisodesLoaded && url.contains(contentURL)) {
                    fetchEpisodes();
                    fetchYoutubeTrailer();
                    isEpisodesLoaded = true;
                }
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, final String url) {
                if (url.contains(InterceptorConstants.LOAD_MOVIE_DOWNLOAD_SOURCES)) {
                    loadInterceptedURL(url);
                }
                else if (url.contains(InterceptorConstants.TRAILER_LINK)) {
                    webViewCallbacks.onLoadTrailerLink(url);
                }

                return super.shouldInterceptRequest(view, url);
            }
        });

        xhrResponseWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("xhr_page_started", url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("xhr_page_finish", url);
                if (url.contains(InterceptorConstants.LOAD_MOVIE_DOWNLOAD_SOURCES)) {
                    loadPageSource(xhrResponseWebView, InterceptorConstants.LOAD_MOVIE_DOWNLOAD_SOURCES);
                }
            }
        });
    }

    private void fetchYoutubeTrailer() {
        triggerJSFunction(Constants.CLICK_TRAILER_LINK, webView);
    }

    private void fetchEpisodes() {
        loadPageSource(webView, InterceptorConstants.LOAD_MOVIE_EPISODE);
    }

    private void loadPageSource(WebView webView, String id) {
        triggerJSFunction(String.format(Constants.PROCESS_HTML_JS, id), webView);
    }

    private void triggerJSFunction(String jsFunction, WebView webView) {
        webView.loadUrl(jsFunction);
    }

    private void loadInterceptedURL(final String url) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                webView.stopLoading(); // stop the current loading and try to get the response from xhrRequest
                xhrResponseWebView.loadUrl(url);
            }
        });
    }

    private void addWebViewSettings() {
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webView.getSettings().setMediaPlaybackRequiresUserGesture(true);
        }

        xhrResponseWebView.getSettings().setDomStorageEnabled(true);
        xhrResponseWebView.getSettings().setJavaScriptEnabled(true);
    }

    private void enableWebViewCache() {
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setAppCachePath(webView.getContext().getCacheDir().getPath());
    }
}
