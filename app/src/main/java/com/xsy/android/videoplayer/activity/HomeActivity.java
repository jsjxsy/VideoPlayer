package com.xsy.android.videoplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.filemanager.lib.activity.MainFragmentActivity;
import com.xsy.android.videoplayer.R;
import com.xsy.android.videoplayer.fragment.CartoonFragment;
import com.xsy.android.videoplayer.fragment.MovieFragment;
import com.xsy.android.videoplayer.fragment.TeleplayFragment;
import com.xsy.android.videoplayer.fragment.VarietyFragment;

import utils.ApkUpdateControl;

//import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
//import com.mabeijianxi.smallvideorecord2.model.MediaRecorderConfig;

public class HomeActivity extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public static final int MOVIE_TYPE = 0x01;
    public static final int TELEPLAY_TYPE = 0x02;
    public static final int CARTOON_TYPE = 0x03;
    public static final int VARIETY_TYPE = 0x04;

    HandlerThread thread = new HandlerThread(HomeActivity.class.getSimpleName());
    Handler uiHander = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        thread.start();
        Log.e("xsy", " UI thread name : " + Thread.currentThread().getId());
        Handler handler = new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //sub thread

                Log.e("xsy", "thread name : " + Thread.currentThread().getName());
            }
        };
        handler.post(new Runnable() {
            @Override
            public void run() {
                //sub thread
                Log.e("xsy", " post thread name : " + Thread.currentThread().getId());
            }
        });


        class Task implements Runnable {
            Handler a;

            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Looper.prepare();
                Process.setThreadPriority(Thread.currentThread().getPriority());
                Looper myLooper = Looper.myLooper();
                a = new Handler(myLooper) {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Log.e("xsy", "handle Message thread name : " + Thread.currentThread().getId());
                    }
                };

                synchronized (HomeActivity.class) {
                    Log.e("xsy", "notifyAll before");
                    HomeActivity.class.notifyAll();
                }

                Looper.loop();
            }

        }
        ;
        final Task task = new Task();
        final Thread thread1 = new Thread(task);
        thread1.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (HomeActivity.class) {
                    if (task.a == null) {
                        try {
                            Log.e("xsy", "wait before");
                            HomeActivity.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                task.a.sendEmptyMessage(1);
            }
        }).start();


        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        TextView tip = (TextView) findViewById(R.id.id_text_view_tip);
        tip.setSelected(true);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return MovieFragment.newInstance();
                    case 1:
                        return TeleplayFragment.newInstance();
                    case 2:
                        return CartoonFragment.newInstance();
                    case 3:
                        return VarietyFragment.newInstance();
                    default:
                        return MovieFragment.newInstance();
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getString(R.string.movie_title);
                    case 1:
                        return getString(R.string.teleplay_title);
                    case 2:
                        return getString(R.string.comic_title);
                    case 3:
                        return getString(R.string.variety_title);
                    default:
                        return getString(R.string.movie_title);
                }
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        updateApp();
    }


    public void updateApp() {
        ApkUpdateControl.getInstance().checkVersion(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.navigation_home:
                                // Do nothing, we're already on that screen
                                break;
                            case R.id.navigation_recommend:
                                Intent intent = new Intent(HomeActivity.this, RecommendActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.navigation_record_video:
//                                recordVideo();
                                break;
                            case R.id.navigation_local_video_audio:
                                Intent intent2 = new Intent(HomeActivity.this, MainFragmentActivity.class);
                                startActivity(intent2);
                                break;
                            case R.id.navigation_local_image:
                                Intent intent3 = new Intent(HomeActivity.this, MainFragmentActivity.class);
                                startActivity(intent3);
                                break;
                            case R.id.navigation_network_image:
                                Intent intent4 = new Intent(HomeActivity.this, NetworkImageActivity.class);
                                startActivity(intent4);
                                break;
                            case R.id.navigation_settings:
                                break;
                            case R.id.navigation_reply:
                                Intent intent5 = new Intent(HomeActivity.this, SettingsActivity.class);
                                intent5.putExtra("url", "http://www.toutiao.com/i6464067912297611789/");
                                startActivity(intent5);
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }


//    public void recordVideo() {
//
//        MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
//                .fullScreen(true)
//                .smallVideoWidth(480)
//                .smallVideoHeight(8000)
//                .recordTimeMax(1000 * 6)
//                .recordTimeMin(1500)
//                .maxFrameRate(20)
//                .videoBitrate(580000)
//                .captureThumbnailsTime(1)
//                .build();
//        Intent intent = new Intent(this, MediaRecorderActivity.class);
//        intent.putExtra(MediaRecorderActivity.MEDIA_RECORDER_CONFIG_KEY, config);
//        startActivity(intent);
//
//    }


}
