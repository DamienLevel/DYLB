package com.dylb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dylb.webSocket.WebSocketFactory;

public class MainActivity extends ActionBarActivity {

    PlaceholderFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            myFragment = new PlaceholderFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, myFragment)
                    .commit();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return myFragment.onKeyDown(keyCode) || super.onKeyDown(keyCode, event);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        WebView webView;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onStart() {
            super.onStart();

            webView = (WebView) this.getActivity().findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                }
            });
            webView.setWebViewClient(
                    new WebViewClient() {
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            view.loadUrl(url);
                            return false;
                        }
                    }
            );
            // TODO Change the URL
            webView.loadUrl("http://google.fr/");
            webView.addJavascriptInterface(new WebSocketFactory(webView), "WebSocketFactory");
        }

        public boolean onKeyDown(int keyCode) {
            // Check if the key event was the Back button and if there's history
            if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                webView.goBack();
                return true;
            }

            return false;
        }
    }

}