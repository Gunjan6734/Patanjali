package com.patanjali.patanjaliiasclasses.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.patanjali.patanjaliiasclasses.R;

public class ImageViewActivity extends AppCompatActivity {

    //ImageView imageView;
    WebView webview;
    String imgurl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_image_view);

       // imageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        if (intent != null) {
            imgurl = getIntent().getStringExtra("url");
           // Toast.makeText(getApplicationContext(), "url" + imgurl, Toast.LENGTH_SHORT).show();

           // Glide.with(ImageViewActivity.this).load(imgurl).placeholder(R.drawable.indicator_corner_bg).into(imageView);

            webview = findViewById(R.id.webview03);

            webview.loadUrl(imgurl);
            webview.setWebViewClient(new WebViewClient());
            webview.getSettings().setJavaScriptEnabled(true);
            webview.getSettings().setUseWideViewPort(true);
            webview.setWebChromeClient(new WebChromeClient());
            webview.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Win64; x64; rv:46.0) Gecko/20100101 Firefox/68.0");
            webview.getSettings().setGeolocationEnabled(true);
            webview.getSettings().setDomStorageEnabled(true);
            webview.getSettings().setDatabaseEnabled(true);
            webview.getSettings().setSupportMultipleWindows(true);
            webview.getSettings().setAppCacheEnabled(true);
            webview.getSettings().setNeedInitialFocus(true);
            webview.getSettings().setLoadWithOverviewMode(true);
            webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webview.getSettings().setBlockNetworkImage(true);
            final Activity activity = this;
            webview.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    activity.setProgress(progress * 1000);
                }
            });

        }
    }
}
