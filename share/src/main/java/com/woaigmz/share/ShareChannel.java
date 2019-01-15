package com.woaigmz.share;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.woaigmz.share.ShareChannel.CHANNEL_CANNEL;
import static com.woaigmz.share.ShareChannel.CHANNEL_MORE;
import static com.woaigmz.share.ShareChannel.CHANNEL_QQ;
import static com.woaigmz.share.ShareChannel.CHANNEL_QQ_ZONE;
import static com.woaigmz.share.ShareChannel.CHANNEL_WECHAT;
import static com.woaigmz.share.ShareChannel.CHANNEL_WECHAT_MOMENT;
import static com.woaigmz.share.ShareChannel.CHANNEL_WEIBO;


/**
 * Created by haoran on 2018/8/3.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({CHANNEL_WECHAT, CHANNEL_WECHAT_MOMENT, CHANNEL_QQ, CHANNEL_QQ_ZONE, CHANNEL_WEIBO, CHANNEL_MORE, CHANNEL_CANNEL})
public @interface ShareChannel {

    int CHANNEL_WECHAT = 0;
    int CHANNEL_WECHAT_MOMENT = 1;
    int CHANNEL_QQ = 2;
    int CHANNEL_QQ_ZONE = 3;
    int CHANNEL_WEIBO = 4;
    int CHANNEL_MORE = 5;
    int CHANNEL_CANNEL = -1;
}
