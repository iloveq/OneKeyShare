package com.woaigmz.share;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.woaigmz.share.ShareStatus.SHARE_STATUS_CANCEL;
import static com.woaigmz.share.ShareStatus.SHARE_STATUS_COMPLETE;
import static com.woaigmz.share.ShareStatus.SHARE_STATUS_ERROR;


/**
 * Created by haoran on 2018/8/8.
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({SHARE_STATUS_COMPLETE, SHARE_STATUS_CANCEL, SHARE_STATUS_ERROR})
public @interface ShareStatus {
    int SHARE_STATUS_COMPLETE = 1;
    int SHARE_STATUS_CANCEL = 2;
    int SHARE_STATUS_ERROR = 3;
}
