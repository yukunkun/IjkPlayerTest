package com.matrix.yukun.playerapplication;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.matrix.yukun.playerapplication.media.IRenderView;
import com.matrix.yukun.playerapplication.media.IjkVideoView;

public class VideoPlayActivity extends AppCompatActivity {
    private IjkVideoView videoView;
    String URL = "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4";
    String URL1="http://baobab.wdjcdn.com/14564977406580.mp4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        videoView = (IjkVideoView) findViewById(R.id.video_view);
//        videoView.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);
        videoView.setVideoURI(Uri.parse(URL1));
        videoView.start();
    }

    public void pause(View view) {
//        videoView.pause();
        //分辨率切换
//        videoView.toggleAspectRatio();
        //更换url
        videoView.setVideoURI(Uri.parse(URL));
    }

}
