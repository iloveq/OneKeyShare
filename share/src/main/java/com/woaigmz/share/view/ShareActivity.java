package com.woaigmz.share.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.sina.weibo.sdk.share.WbShareCallback;
import com.woaigmz.share.R;
import com.woaigmz.share.ShareChannel;
import com.woaigmz.share.ShareListener;
import com.woaigmz.share.ShareStatus;
import com.woaigmz.share.api.QShare;
import com.woaigmz.share.api.SineShare;
import com.woaigmz.share.api.WXShare;
import com.woaigmz.share.model.AppId;
import com.woaigmz.share.model.ShareBean;
import com.woaigmz.share.utils.SerializeUtils;


import static com.woaigmz.share.utils.Utils.getSerializePath;



public class ShareActivity extends Activity implements WbShareCallback, ShareListener {

    private static final String SHARE_ENTRY = "ShareBean";
    private static final String SHARE_CHANNEL = "ShareChannel";


    public static final String RESULT_CHANNEL = "result_channel";
    public static final String RESULT_STATUS = "result_msg";
    private static final long TIMEOUT = 15 * 1000;

    public static Intent getIntent(Context context, ShareBean share, int type) {
        Intent intent = new Intent(context, ShareActivity.class);
        intent.putExtra(SHARE_ENTRY, share);
        intent.putExtra(SHARE_CHANNEL, type);
        return intent;
    }


    private int shareType;
    private WXShare wxShare;
    private SineShare sineShare;
    private QShare qShare;
    private AppId appId;
    private ShareBean shareBean;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        progress = findViewById(R.id.progress);
        progress.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress.setVisibility(View.GONE);
            }
        },TIMEOUT);
        Intent intent = getIntent();
        shareBean = intent.getParcelableExtra(SHARE_ENTRY);
        shareType = intent.getIntExtra(SHARE_CHANNEL, -1);
        appId = (AppId) SerializeUtils.deserialization(getSerializePath(this.getApplication()));
        switch (shareType) {
            case ShareChannel.CHANNEL_WECHAT:
                weiXinFriend();
                break;
            case ShareChannel.CHANNEL_WECHAT_MOMENT:
                weiXinCircle();
                break;
            case ShareChannel.CHANNEL_QQ:
                qqShare();
                break;
            case ShareChannel.CHANNEL_QQ_ZONE:
                qZoneShare();
                break;
            case ShareChannel.CHANNEL_WEIBO:
                weiBoShare();
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (sineShare != null) {
            sineShare.doResultIntent(intent, this);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (qShare != null) {
            qShare.onActivityResultData(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progress.setVisibility(View.GONE);
        if (wxShare != null) {
            wxShare.unregisterWXReceiver();
        }
    }

    private void weiXinFriend() {
        wxShare = new WXShare(this, appId.getWECHAT(), false,this);
        wxShare.registerWXReceiver();
        wxShare.sendWebShareMessage(shareBean);
    }

    private void weiXinCircle() {
        wxShare = new WXShare(this, appId.getWECHAT(), true,this);
        wxShare.registerWXReceiver();
        wxShare.sendWebShareMessage(shareBean);
    }

    private void weiBoShare() {
        sineShare = new SineShare(this, appId.getWEIBO());
        sineShare.sendWebShareMessage(shareBean);
    }

    private void qqShare() {
        qShare = new QShare(this, appId.getQQ(), true,this);
        qShare.sendWebShareMessage(shareBean);
    }

    private void qZoneShare() {
        qShare = new QShare(this, appId.getQQ(), false,this);
        qShare.sendWebShareMessage(shareBean);
    }


    @Override
    public void onWbShareSuccess() {
        onShareSuccess();
    }

    @Override
    public void onWbShareCancel() {
        onShareCancel();
    }

    @Override
    public void onWbShareFail() {
        onShareFail();
    }

    @Override
    public void onShareSuccess() {
        finishWithResult(ShareStatus.SHARE_STATUS_COMPLETE);
    }


    @Override
    public void onShareCancel() {
        finishWithResult(ShareStatus.SHARE_STATUS_CANCEL);
    }

    @Override
    public void onShareFail() {
        finishWithResult(ShareStatus.SHARE_STATUS_ERROR);
    }

    @Override
    public void onSdkSetupError(String msg) {
        progress.setVisibility(View.GONE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, R.string.toast_cancel_share, Toast.LENGTH_SHORT).show();
        finishWithResult(ShareStatus.SHARE_STATUS_CANCEL);

    }

    public void finishWithResult(@ShareStatus final int status) {
        progress.setVisibility(View.GONE);
        Intent intent = new Intent();
        intent.putExtra(RESULT_CHANNEL, shareType);
        intent.putExtra(RESULT_STATUS, status);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
