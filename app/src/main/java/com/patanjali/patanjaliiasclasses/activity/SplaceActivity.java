package com.patanjali.patanjaliiasclasses.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.patanjali.patanjaliiasclasses.GooglePlayStoreAppVersionNameLoader;
import com.patanjali.patanjaliiasclasses.R;
import com.patanjali.patanjaliiasclasses.VersionCheckListner;

import java.util.Timer;
import java.util.TimerTask;

public class SplaceActivity extends AppCompatActivity {

    private Handler mWaitHandler = new Handler();

    private static int currentPage = 0;
    private ViewPager viewPager;
    private ImageAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    Button nextbtton;
    private TextView[] dots;
    private int[] layouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splace);

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_splace);

        new GooglePlayStoreAppVersionNameLoader(this, new VersionCheckListner() {
            @Override
            public void onGetResponse(boolean isUpdateAvailable) {
                if (isUpdateAvailable){
                    openGoToPlayStoreAlert();
                }
            }
        }).execute();

        nextbtton = findViewById(R.id.nextbtton);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);

        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.splash_screen3};

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new ImageAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == layouts.length) {
                    currentPage = 0;
                }
                if (viewPager.getCurrentItem()<2){
                    viewPager.setCurrentItem(currentPage=viewPager.getCurrentItem()+1, true);
                }
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2000, 2000);

        nextbtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchnextScreen();
            }
        });

    }
  private void openGoToPlayStoreAlert(){
      final AlertDialog.Builder builder=new AlertDialog.Builder(SplaceActivity.this);
      builder.setCancelable(false);
      builder.setTitle("NEW UPDATES");
      builder.setMessage("A New Update is Available");

      builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                      try {
                          startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                      } catch (android.content.ActivityNotFoundException anfe) {
                          startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                      }
                  }
              }).setNegativeButton("No", new DialogInterface.OnClickListener() {

          @Override
          public void onClick(DialogInterface dialog, int which) {
              dialog.dismiss();
          }
      });
      AlertDialog alertDialog = builder.create();
      alertDialog.show();

    }
    private void launchnextScreen() {
        Intent intent=new Intent(SplaceActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position==2){
                nextbtton.setVisibility(View.VISIBLE);
            }else {
                nextbtton.setVisibility(View.GONE);
            }

          /*  // autometic send to next page
            if (position==2){
                Intent intent=new Intent(SplaceActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }*/
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public class ImageAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public ImageAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
