package com.patanjali.patanjaliiasclasses.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.patanjali.patanjaliiasclasses.Config;
import com.patanjali.patanjaliiasclasses.R;
import com.patanjali.patanjaliiasclasses.UserSession;

public class VideoPlayActivity extends AppCompatActivity {

    WebView webview;
    String url = "";
    private float m_downX;
    Context context;
    UserSession userSession;
    Config ut;
    ProgressBar progressBar;

    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

       // userSession = new UserSession(this);
        url = getIntent().getStringExtra("url");
       // Toast.makeText(getApplicationContext(), "url"+url, Toast.LENGTH_SHORT).show();
        //webview = findViewById(R.id.webview2);

        if (!isConnection(VideoPlayActivity.this)) {
           /* CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this)
                    .setDialogStyle(CFAlertDialog.CFAlertStyle.NOTIFICATION)
                    .setTitle("Internet")
                    .setIcon(R.drawable.ic_nowifi)
                    .setMessage("Internet not available, Cross check your internet connectivity and try again")
                    .addButton("OK", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.END, (dialog, which) -> {
                        //   Toast.makeText(VideoPlayActivity.this, "Upgrade tapped", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        onStop();
                    });

            builder.show();*/
        } else {
           // myouTubePlayerView=findViewById(R.id.video_youtube_player);
            webview =findViewById(R.id.videoView1);
            progressBar=(ProgressBar)findViewById(R.id.video_progressbar);
            Intent intent = getIntent();
            if (intent != null) {
                url = intent.getStringExtra("url");
                assert url != null;
                if (url.startsWith("https://youtu.be/") ){
                    String  videourl1=url.replaceAll("https://youtu.be/","");

                   /* YoutubeVideoPlay(videourl1);
                    myouTubePlayerView.setVisibility(View.VISIBLE);*/
                    webview.setVisibility(View.GONE);
                }else if (url.startsWith("https://www.youtube.com/watch?v=")){
                    String  videourl1=url.replaceAll("https://www.youtube.com/watch","");


                    String videolink2=videourl1.replaceAll("v=","");
                    String videolink3=videolink2.replace("?","");

                    //   Toast.makeText(this, ""+videolink3, Toast.LENGTH_SHORT).show();

                   /* YoutubeVideoPlay(videolink3);
                    myouTubePlayerView.setVisibility(View.VISIBLE);*/
                    webview.setVisibility(View.GONE);
                }

                else{
                    //myouTubePlayerView.setVisibility(View.GONE);
                    webview.setVisibility(View.VISIBLE);
                    VideoPlay(url);
                }
                //videourl=videourl.replaceAll("https://youtu.be/","");
            }

        }
    }

    private void VideoPlay(String videourls) {
        progressBar.setVisibility(View.VISIBLE);
        //Creating MediaController
        mediaController= new MediaController(this);
        mediaController.setAnchorView(webview);

        //specify the location of media file
        Uri uri= Uri.parse(videourls);

        webview.loadUrl(url);
        webview.setWebViewClient(new WebViewClient());
        // webview.setWebViewClient(new MyWebViewClient());
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
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        final Activity activity = this;
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                activity.setProgress(progress * 1000);
            }
        });

       /* //Setting MediaController and URI, then starting the videoView
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        mp.start();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });*/
    }

    //Internet Connection check
    public  boolean isConnection(Context context){
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo=connectivityManager.getActiveNetworkInfo();

        if (netinfo !=null&&netinfo.isConnectedOrConnecting()){
            NetworkInfo wifi=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile!=null && mobile.isConnectedOrConnecting()) || (wifi!=null && wifi.isConnectedOrConnecting() ))return true;
            else return false;

        }else
            return false;


    }
}