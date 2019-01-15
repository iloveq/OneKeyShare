-keepattributes InnerClasses
-dontoptimize
#================ QQ分享 start================
-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}

#=============== QQ分享 end================


#================微信分享 start================

-keep class com.tencent.mm.opensdk.** {
    *;
}

-keep class com.tencent.wxop.** {
    *;
}

-keep class com.tencent.mm.sdk.** {
    *;
}


#================ 微博 ================
-keep class com.sina.weibo.sdk.** { *; }

