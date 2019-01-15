package com.woaigmz.sharedemo;

import android.app.Application;

import com.woaigmz.share.ShareProxy;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ShareProxy.getInstance().init(this,new String[]{"1104675129","wx0c47eeb22d93e905","1550938859"});
    }
}
