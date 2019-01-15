package com.woaigmz.share.presenter;

import android.app.Activity;


import com.woaigmz.share.ShareChannel;
import com.woaigmz.share.model.ShareBean;
import com.woaigmz.share.view.ShareActivity;

import java.lang.ref.WeakReference;

/**
 * Created by haoran on 2018/8/7.
 */

public class SharePresenter implements ISharePresenter {


    private static volatile SharePresenter presenter = null;

    public static ISharePresenter start(Activity activity) {
        if (presenter == null) {
            synchronized (SharePresenter.class) {
                if (presenter == null)
                    presenter = new SharePresenter(activity);
            }
        }
        return presenter;
    }

    private SharePresenter(Activity activity) {
        this.context = new WeakReference<>(activity);
    }

    private void startActivityForResult(int type, ShareBean entry) {
        Activity activity = context.get();
        assert activity != null : " activity weak reference maybe a null object ";
        activity.startActivityForResult(ShareActivity.getIntent(activity, entry, type), type);
    }

    private WeakReference<Activity> context;

    @Override
    public void onShareWeiBo(ShareBean entry) {
        startActivityForResult(ShareChannel.CHANNEL_WEIBO, entry);
    }

    @Override
    public void onShareWxCircle(ShareBean entry) {
        startActivityForResult(ShareChannel.CHANNEL_WECHAT_MOMENT, entry);
    }

    @Override
    public void onShareWx(ShareBean entry) {
        startActivityForResult(ShareChannel.CHANNEL_WECHAT, entry);
    }

    @Override
    public void onShareQQ(ShareBean entry) {
        startActivityForResult(ShareChannel.CHANNEL_QQ, entry);
    }

    @Override
    public void onShareQZone(ShareBean entry) {
        startActivityForResult(ShareChannel.CHANNEL_QQ_ZONE, entry);
    }


}
