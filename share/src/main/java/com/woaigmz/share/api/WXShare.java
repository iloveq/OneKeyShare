package com.woaigmz.share.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.woaigmz.share.R;
import com.woaigmz.share.ShareListener;
import com.woaigmz.share.model.IShareModel;
import com.woaigmz.share.utils.BitmapAsyncTask;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.woaigmz.share.utils.Utils.bmpToByteArray;
import static com.woaigmz.share.utils.Utils.getWxShareBitmap;


/**
 * Created by haoran on 2018/8/7.
 */

public class WXShare {

    private static final String ACTION_WX_CALLBACK = "com.woaigmz.share.action.WX_CALLBACK";
    private static final String EXTRA_WX_RESULT = "wx_result";

    private final IWXAPI iwxapi;
    private WXShareReceiver receiver;
    private Context context;
    private ShareListener shareListener;
    private boolean isTimeLine;
    private AtomicBoolean init = new AtomicBoolean(false);

    /**
     * constructor for WXShare
     *
     * @param context context
     * @param appId   微信 id
     * @param flag    true：好友 false：朋友圈
     */
    public WXShare(Context context, String appId, boolean flag,ShareListener listener) {
        this.context = context;
        this.shareListener = listener;
        iwxapi = WXAPIFactory.createWXAPI(context, appId);
        this.isTimeLine = flag;
        if (!iwxapi.isWXAppInstalled()) {
            listener.onSdkSetupError(context.getResources().getString(R.string.wx_uninstall));
            init.set(false);
        }else {
            init.set(true);
        }

    }

    /**
     * 注册微信回调广播
     */
    public void registerWXReceiver() {
        receiver = new WXShareReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_WX_CALLBACK);
        context.registerReceiver(receiver, intentFilter);
    }

    /**
     * unregister
     */
    public void unregisterWXReceiver() {
        if (null != context && null != receiver) {
            context.unregisterReceiver(receiver);
        }
    }


    public void sendTextMessage(IShareModel shareEntry) {

        if (!init.get()) return;
        String title = shareEntry.getTitle();
        String content = shareEntry.getContent();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        WXTextObject textObj = new WXTextObject();
        textObj.text = content;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.title = title;
        msg.description = content;
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = isTimeLine ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        iwxapi.sendReq(req);

    }

    public void sendImageMessage(IShareModel shareEntry) {

        if (!init.get()) return;
        String imgUrl = shareEntry.getImgUrl();
        final int drawableId = shareEntry.getDrawableId();
        final WXMediaMessage msg = new WXMediaMessage();
        new BitmapAsyncTask(context, imgUrl, new BitmapAsyncTask.OnBitmapListener() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                msg.mediaObject = new WXImageObject(bitmap);
                msg.setThumbImage(getWxShareBitmap(bitmap));
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("img");
                req.message = msg;
                req.scene = isTimeLine ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
                iwxapi.sendReq(req);
            }

            @Override
            public void onException(Exception exception) {
                Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), drawableId);
                WXImageObject imgObj = new WXImageObject(bmp);
                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
                bmp.recycle();
                msg.thumbData = bmpToByteArray(thumbBmp, true);
                msg.mediaObject = imgObj;
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("img");
                req.message = msg;
                req.scene = isTimeLine ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
                iwxapi.sendReq(req);
            }
        }).execute();

    }


    public void sendWebShareMessage(IShareModel share) {

        if (!init.get()) return;
        final String title = share.getTitle();
        final String content = share.getContent();
        final String imgUrl = share.getImgUrl();
        final String actionUrl = share.getActionUrl();
        final int drawableId = share.getDrawableId();
        final SendMessageToWX.Req req = new SendMessageToWX.Req();

        if (TextUtils.isEmpty(imgUrl)) {
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), drawableId);
            WXMediaMessage wxMediaMessage = new WXMediaMessage();
            wxMediaMessage.title = title;
            wxMediaMessage.description = content;
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
            bmp.recycle();
            wxMediaMessage.thumbData = bmpToByteArray(thumbBmp, true);
            wxMediaMessage.mediaObject = new WXWebpageObject(actionUrl);
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = wxMediaMessage;
            req.scene = isTimeLine ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
            iwxapi.sendReq(req);

        } else {

            new BitmapAsyncTask(context, imgUrl, new BitmapAsyncTask.OnBitmapListener() {
                @Override
                public void onSuccess(Bitmap bitmap) {

                    WXMediaMessage wxMediaMessage = new WXMediaMessage();
                    wxMediaMessage.title = title;
                    wxMediaMessage.description = content;
                    wxMediaMessage.setThumbImage(getWxShareBitmap(bitmap));

                    wxMediaMessage.mediaObject = new WXWebpageObject(actionUrl);

                    req.transaction = String.valueOf(System.currentTimeMillis());
                    req.message = wxMediaMessage;
                    req.scene = isTimeLine ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
                    iwxapi.sendReq(req);
                }

                @Override
                public void onException(Exception exception) {

                    WXMediaMessage wxMediaMessage = new WXMediaMessage();
                    wxMediaMessage.title = title;
                    wxMediaMessage.description = content;
                    Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), drawableId);
                    Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
                    bmp.recycle();
                    wxMediaMessage.thumbData = bmpToByteArray(thumbBmp, true);
                    wxMediaMessage.mediaObject = new WXWebpageObject(actionUrl);
                    req.transaction = String.valueOf(System.currentTimeMillis());
                    req.message = wxMediaMessage;
                    req.scene = isTimeLine ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
                    iwxapi.sendReq(req);

                }
            }).execute();
        }


    }


    public static void sendBroadcast(Context context, int errCode) {
        Intent intent = new Intent();
        intent.setAction(ACTION_WX_CALLBACK);
        intent.putExtra(EXTRA_WX_RESULT, errCode);
        context.sendBroadcast(intent);
    }

    /**
     * 微信分享回调广播
     */
    private class WXShareReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra(EXTRA_WX_RESULT)) {
                int errCode = intent.getIntExtra(EXTRA_WX_RESULT, BaseResp.ErrCode.ERR_USER_CANCEL);
                Log.w("WXShareReceiver", "errCode:" + errCode);
                switch (errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        if (shareListener != null)
                            shareListener.onShareSuccess();
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        if (shareListener != null)
                            shareListener.onShareCancel();
                        break;
                    default:
                        if (shareListener != null)
                            shareListener.onShareFail();
                        break;
                }
            }
        }

    }


    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


}
