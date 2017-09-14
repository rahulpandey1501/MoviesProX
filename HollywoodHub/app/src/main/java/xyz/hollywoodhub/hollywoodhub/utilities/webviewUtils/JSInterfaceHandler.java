package xyz.hollywoodhub.hollywoodhub.utilities.webviewUtils;

import android.os.Handler;
import android.webkit.JavascriptInterface;

import org.jsoup.Jsoup;

/**
 * Created by rpandey.ppe on 26/07/17.
 */

public class JSInterfaceHandler {

    public interface JSInterfaceCallbacks {
        void onProcessHTML(String id, String html);
    }

    public static final String TAG = "JSHandler";

    private JSInterfaceCallbacks jsInterfaceCallbacks;

    public JSInterfaceHandler(JSInterfaceCallbacks jsInterfaceCallbacks) {
        this.jsInterfaceCallbacks = jsInterfaceCallbacks;
    }

    @JavascriptInterface
    public void processHTML(final String id, final String html) {
        jsInterfaceCallbacks.onProcessHTML(id, html);
    }

}
