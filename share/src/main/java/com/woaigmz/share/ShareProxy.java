package com.woaigmz.share;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.woaigmz.share.model.AppId;
import com.woaigmz.share.model.ShareBean;
import com.woaigmz.share.presenter.SharePresenter;
import com.woaigmz.share.utils.SerializeUtils;
import com.woaigmz.share.view.IShareView;
import com.woaigmz.share.view.ShareDialog;

import static com.woaigmz.share.utils.Utils.getSerializePath;

public class ShareProxy {

    private static final String TAG = "ShareProxy";

    public interface OnShareClickListener {
        void onShareClick(@ShareChannel int channel);
    }

    private ShareProxy() {
    }

    private static class Holder {
        private static final ShareProxy IN = new ShareProxy();
    }

    public static ShareProxy getInstance(){
        return Holder.IN;
    }

    public void init(Context context, String[] appIds) {

        if (context != null && appIds != null && appIds.length == 3) {
            try {
                SerializeUtils.serialization(getSerializePath(context), new AppId(appIds[0], appIds[1], appIds[2]));
            } catch (Exception e) {
                Log.d(TAG,e.getMessage());
            }
        } else {
            throw new RuntimeException(" ShareSdk init error ");
        }

    }

    public IShareView createShareDialog(Context context,int[] shareChannels,int column){
        return ShareDialog.get().createShareDialog(context,shareChannels,column);
    }

    public IShareView createShareDialog(Context context,int[] shareChannels,int[] resIcons,int column){
        return ShareDialog.get().createShareDialog(context,shareChannels,resIcons,column);
    }

    public void setOnShareClickListener(IShareView view, final Activity activity, final ShareBean shareBean) {
        view.setOnShareClickListener(new OnShareClickListener() {
            @Override
            public void onShareClick(int channel) {
                switch (channel) {
                    case ShareChannel.CHANNEL_WECHAT:
                        SharePresenter.start(activity).onShareWx(shareBean);
                        break;
                    case ShareChannel.CHANNEL_WECHAT_MOMENT:
                        SharePresenter.start(activity).onShareWxCircle(shareBean);
                        break;
                    case ShareChannel.CHANNEL_QQ:
                        SharePresenter.start(activity).onShareQQ(shareBean);
                        break;
                    case ShareChannel.CHANNEL_QQ_ZONE:
                        SharePresenter.start(activity).onShareQZone(shareBean);
                        break;
                    case ShareChannel.CHANNEL_WEIBO:
                        SharePresenter.start(activity).onShareWeiBo(shareBean);
                        break;
                    case ShareChannel.CHANNEL_MORE:
                        break;
                    case ShareChannel.CHANNEL_CANNEL:
                        break;

                }
            }
        });

    }

}
