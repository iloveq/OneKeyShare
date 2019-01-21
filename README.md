# 一键分享, 支持 QQ QQ空间 微信好友 微信朋友圈 新浪微博

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

        IShareView shareDialog = ShareSdkProxy.getInstance().createShareDialog(new int[]{ShareChannel.CHANNEL_QQ, ShareChannel.CHANNEL_WEIBO, ShareChannel.CHANNEL_WECHAT_MOMENT, ShareChannel.CHANNEL_QQ_ZONE}, 4);
        // 设置点击回调和数据
        ShareSdkProxy.getInstance().setOnShareClickListener(shareDialog, activity, new ShareBean("分享了", "今天天气不错", "http://118.89.233.211:3000/images/1530106897838_.jpg", R.drawable.ic_launcher, "http://www.baidu.com"));
        // 展示 dialog
        shareDialog.show(activity.getFragmentManager());

    }

}
```
