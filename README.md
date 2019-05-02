# 一键分享, 支持 QQ QQ空间 微信好友 微信朋友圈 新浪微博。

[注意：关于新版微信分享结果总是成功](https://mp.weixin.qq.com/cgi-bin/announce?action=getannouncement&announce_id=11526372695t90Dn&version=&lang=zh_CN&token=)

### 使用 ：
一：OneKeyShare引入
project 下
```
allprojects {

    repositories {

        google()
        jcenter()
        maven { url 'https://jitpack.io' }
        // 微博 sdk
        maven { url "https://dl.bintray.com/thelasterstar/maven/" }

    }

}

```
module 下 qq 的 id 和 OneKeyShare 依赖

```
defaultConfig {
        // ...
        manifestPlaceholders=[
                "tencentAuthId":"tencent1104675129"
        ]

    }
    
dependencies {
    // ...
    implementation 'com.github.woaigmz:OneKeyShare:1.0.0'
}
```

二：初始化

```
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 需按顺序填写 qqId , wxId , weiboId 根据任务紧急情况,可延迟加载
        ShareProxy.getInstance().init(this,new String[]{"1104675129","wxde66fe31ef4425d0","1550938859"});
    }
}
```

三：调用
```
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
```
![使用.gif](https://github.com/woaigmz/OneKeyShare/blob/master/img/use.png)

### 介绍 ：

（一）注意事项： https://www.jianshu.com/p/f70545bd86a1

（二）设计思想： https://www.jianshu.com/p/92da26647aa9

### demo 图 ：

![未安装时.gif](https://github.com/woaigmz/OneKeyShare/blob/master/img/uninstall.gif)

![QQ.gif](https://github.com/woaigmz/OneKeyShare/blob/master/img/qq.gif)

![微信.gif](https://github.com/woaigmz/OneKeyShare/blob/master/img/wxs.gif)






      
