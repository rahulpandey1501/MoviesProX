package xyz.hollywoodhub.hollywoodhub.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;

import xyz.hollywoodhub.hollywoodhub.constants.Constants;
import xyz.hollywoodhub.hollywoodhub.utilities.webviewUtils.JSInterfaceHandler;

/**
 * Created by rpandey.ppe on 11/09/17.
 */

public class CloudflareWebViewPageSourceHelper implements JSInterfaceHandler.JSInterfaceCallbacks {

    private WebView webView;
    private boolean isCloudflareBypassed;
    private String contextId;
    private Context context;
    private Callbacks callbacks;

    public interface Callbacks {

        void onPageSourceLoad(String contextId, String html);
    }

    private CloudflareWebViewPageSourceHelper(Context context, Callbacks callbacks) {
        this.context = context;
        this.callbacks = callbacks;
    }

    public static CloudflareWebViewPageSourceHelper newInstance(Context context, Callbacks callbacks) {
        return new CloudflareWebViewPageSourceHelper(context, callbacks);
    }

    public CloudflareWebViewPageSourceHelper initialize() {
        webView = new WebView(context);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(false);
        webView.addJavascriptInterface(new JSInterfaceHandler(this), JSInterfaceHandler.TAG);

        enableWebViewCache();

        setWebViewClient();

        return this;
    }

    private void enableWebViewCache() {
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setAppCachePath(webView.getContext().getCacheDir().getPath());
    }

    private void setWebViewClient() {

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("started", url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("finished", url);

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

    @Override
    public void onProcessHTML(String id, String html) {
        isCloudflareBypassed = JsoupHelper.hasClass(Jsoup.parse(html), "main-content");
        if (isCloudflareBypassed) {
            callbacks.onPageSourceLoad(id, html);
            Log.d("source", html);
        }
    }
}
