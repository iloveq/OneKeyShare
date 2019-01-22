# 一键分享, 支持 QQ QQ空间 微信好友 微信朋友圈 新浪微博。

[注意：关于新版微信分享结果总是成功](https://mp.weixin.qq.com/cgi-bin/announce?action=getannouncement&announce_id=11526372695t90Dn&version=&lang=zh_CN&token=)

简书地址：

（一） https://www.jianshu.com/p/f70545bd86a1

（二） https://www.jianshu.com/p/92da26647aa9

![未安装时.gif](https://upload-images.jianshu.io/upload_images/8886407-d46914f1227ed2f8.gif?imageMogr2/auto-orient/strip)

![QQ.gif](https://upload-images.jianshu.io/upload_images/8886407-3f880a7b16753c35.gif?imageMogr2/auto-orient/strip)

![微信.gif](https://upload-images.jianshu.io/upload_images/8886407-cdc8e3aa15580665.gif?imageMogr2/auto-orient/strip)

```
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ShareProxy.getInstance().init(this,new String[]{"1104675129","wxde66fe31ef4425d0","1550938859"});
    }
}
```

```
public class ShareInitAction implements ScAction {

    private static final String TAG = "ShareInitAction";

    @Override
    public void invoke(Context context, Bundle bundle, String s, ScCallback scCallback) {
        Log.e(TAG,"start");
        // QQ 微信 微博
        ShareSdkProxy.getInstance().init(context, new String[]{"214506", "wxa552e31d6839de85", "1550938859"});
        Log.e(TAG,"end");
    }

}
```

```
public class ShareShowAction implements ScAction {

    @Override
    public void invoke(Context context, Bundle bundle, String s, ScCallback scCallback) {

        Activity activity = (Activity) context;

          new ShareDialogBuilder()
                        .setContext(activity)
                        .setShareChannels(new int[]{ShareChannel.CHANNEL_QQ,ShareChannel.CHANNEL_WECHAT,ShareChannel.CHANNEL_WEIBO})
        //                .setShareIcon(new int[]{R.drawable.icon,R.drawable.icon,R.drawable.icon})
                        .setColumn(3)
                        .setModel(new ShareBean("title",
                                "content",
                                "http://wx3.sinaimg.cn/large/006nLajtly1fkegnmnwuxj30dw0dw408.jpg",
                                0,
                                "https://www.baidu.com"))
                        .build()
                        .show(getSupportFragmentManager());

    }

}
```
