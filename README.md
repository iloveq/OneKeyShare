# 一键分享, 支持 QQ QQ空间 微信好友 微信朋友圈 新浪微博。

[注意：关于新版微信分享结果总是成功](https://mp.weixin.qq.com/cgi-bin/announce?action=getannouncement&announce_id=11526372695t90Dn&version=&lang=zh_CN&token=)

简书地址：

（一） https://www.jianshu.com/p/f70545bd86a1

（二） https://www.jianshu.com/p/92da26647aa9

![未安装时.gif](https://github.com/woaigmz/OneKeyShare/blob/master/img/uninstall.gif)

![QQ.gif](https://github.com/woaigmz/OneKeyShare/blob/master/img/qq.gif)

![微信.gif](https://github.com/woaigmz/OneKeyShare/blob/master/img/wxs.gif)

![使用.gif](https://github.com/woaigmz/OneKeyShare/blob/master/img/use.png)

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
      
