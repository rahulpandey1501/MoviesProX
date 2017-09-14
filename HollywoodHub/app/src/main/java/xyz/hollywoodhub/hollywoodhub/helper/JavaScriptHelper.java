package xyz.hollywoodhub.hollywoodhub.helper;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.ValueCallback;
import android.webkit.WebView;

/**
 * Created by rpandey.ppe on 26/07/17.
 */

public class JavaScriptHelper {

    private Handler handler;
    private WebView webView;
    private static final int RECEIVED_CALLBACK = 100;
    private static final int EMPTY_MESSAGE = 0;
    private static final String JS_RECEIVED_VALUE = "js_return_value";

    public JavaScriptHelper(WebView webView, Handler handler) {
        this.handler = handler;
        this.webView = webView;
        setupHandler();
    }

    private void setupHandler() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case RECEIVED_CALLBACK:
                        break;
                }
                return true;
            }
        });
    }

    public void executeJavascript(String js, final int what) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript(js, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    if (what == EMPTY_MESSAGE) {
                        handler.sendEmptyMessage(what);
                    } else {
                        Message message = new Message();
                        message.what = what;
                        Bundle bundle = new Bundle();
                        bundle.putString(JS_RECEIVED_VALUE, value);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                }
            });
        } else {
            webView.loadUrl("javascript:" + js);
        }
    }

    public void executeJavascriptWithDelay(final String script, final int what, final int timeInMs) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(timeInMs);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                executeJavascript(script, what);
            }
        });
    }
}
