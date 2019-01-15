package com.woaigmz.share.view;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.woaigmz.share.ShareProxy;


public interface IShareView {

    IShareView createShareDialog(Context context,int[] shareChannel, int spanCount);

    int show(FragmentTransaction transaction);

    void show(FragmentManager manager);

    void dismissDialog();

    void setOnShareClickListener(ShareProxy.OnShareClickListener listener);
}
