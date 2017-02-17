package com.matrix.yukun.playerapplication;

import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;


import java.util.ArrayList;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.MediaPlayerProxy;


public class MainActivity extends AppCompatActivity {
    String URL1="http://baobab.wdjcdn.com/14564977406580.mp4";
    String URL = "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4";
    private IjkMediaPlayer mIjkMediaPlayer;
    private MediaPlayerProxy mPlayerProxy;
    private SurfaceView mSurfaceView;
    boolean isFirstPlay=false;
    private ArrayList<String> urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化IJKPlayer
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        init();
        play(URL);
        setListener();
    }

    private void setListener() {
        mPlayerProxy.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                Log.i("-----onInfo",what+"+extra:"+extra);
                switch (what) {
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        break;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        break;
                }
                return false;
            }
        });
        mPlayerProxy.setOnBufferingUpdateListener(new IMediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(IMediaPlayer mp, int percent) {

//                Log.i("-----onBufferingUpdate",percent+"");

            }
        });
        mPlayerProxy.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer mp) {

                Log.i("-----onCompletion","onCompletion");
            }
        });
        mPlayerProxy.setOnVideoSizeChangedListener(new IMediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {

            }
        });
    }

    private void init() {
        mIjkMediaPlayer = new IjkMediaPlayer();
        mPlayerProxy = new MediaPlayerProxy(mIjkMediaPlayer);
        mPlayerProxy.setLooping(true);
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        urls = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            urls.add(URL);
            urls.add(URL1);
        }
    }

    private void play(String uri) {

        try {
            mPlayerProxy.setDataSource(uri);
            mPlayerProxy.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayerProxy.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(IMediaPlayer mp) {
                    mp.setDisplay(mSurfaceView.getHolder());
                    mp.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        //直接播放
        startPlay();
    }

    /**
     *
     * @param view play的 Btn
     */
    public void Play(View view) {
        if (mIjkMediaPlayer.isPlayable()) {
            if (!isFirstPlay) {
                mPlayerProxy.prepareAsync();
                isFirstPlay = true;
            } else {
                startVideo();
            }
        }
    }
    /**
     *
     * @param view pause的btn
     */
    public void pause(View view) {
        if (mPlayerProxy.isPlaying()) {
            pauseVideo();
        }
    }

    /**
     *  ChangeURL
     * @param view
     */
    int i=1;
    public void Change(View view) {
        if(i>=urls.size()){
            Toast.makeText(MainActivity.this, "没有更多了...", Toast.LENGTH_SHORT).show();
            return;
        }
        isFirstPlay=false;
        mPlayerProxy.stop();
        mPlayerProxy.reset();
        mIjkMediaPlayer.reset();
        if(i<urls.size()){
            play(urls.get(i++));
        }
    }

    //  一个按钮控制播放
    public void startPlay(){
        if (mPlayerProxy.isPlaying()) {
            pauseVideo();
        }else if (mIjkMediaPlayer.isPlayable()) {
            if (!isFirstPlay) {
                mPlayerProxy.prepareAsync();
                isFirstPlay = true;
            }else {
                startVideo();
            }
        }
    }
    /**
     * 开始
     */
    private void startVideo() {
        mPlayerProxy.start();
    }

    /**
     * 暂停
     */
    private void pauseVideo() {
        mPlayerProxy.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayerProxy.stop();
        mIjkMediaPlayer.release();
        mPlayerProxy.release();
    }

}
