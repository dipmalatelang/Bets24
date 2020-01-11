package com.app.bet.HomeScreen.More;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.app.bet.R;

public class PrivacyTermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_terms);

        TextView TvTitile = findViewById(R.id.TvTitile);

        WebView WvContent = findViewById(R.id.WvContent);
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        TvTitile.setText(title);
        WvContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WvContent.loadUrl(url);
    }
}
