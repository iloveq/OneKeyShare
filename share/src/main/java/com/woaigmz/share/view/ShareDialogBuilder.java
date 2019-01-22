package com.woaigmz.share.view;

import android.app.Activity;

import com.woaigmz.share.ShareProxy;
import com.woaigmz.share.model.ShareBean;

public class ShareDialogBuilder {

    private Activity activity;
    private IShareView dialog;

    private int[] shareChannels;
    private int[] resIcon;
    private int column;
    private ShareBean model;


    public ShareDialogBuilder setContext(Activity activity) {
        this.activity = activity;
        return this;
    }

    public ShareDialogBuilder setShareIcon(int[] res) {
        this.resIcon = res;
        return this;
    }

    public ShareDialogBuilder setShareChannels(int[] shareChannels) {
        this.shareChannels = shareChannels;
        return this;
    }

    public ShareDialogBuilder setColumn(int column) {
        this.column = column;
        return this;
    }

    public ShareDialogBuilder setModel(ShareBean model) {
        this.model = model;
        return this;
    }

    public IShareView build(){
        if (resIcon==null){
            dialog = ShareProxy.getInstance().createShareDialog(activity,shareChannels,column);
        }else {
            dialog = ShareProxy.getInstance().createShareDialog(activity,shareChannels,resIcon,column);
        }
        ShareProxy.getInstance().setOnShareClickListener(dialog,activity,model);
        return dialog;
    }


}
