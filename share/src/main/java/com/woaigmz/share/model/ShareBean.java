package com.woaigmz.share.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.woaigmz.share.R;


/**
 * Created by haoran on 2018/8/7.
 */

public class ShareBean implements IShareModel, Parcelable {

    private String title;
    private String content;
    private String imgUrl;
    private int drawable;
    private String actionUrl;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getImgUrl() {
        return imgUrl;
    }

    @Override
    public int getDrawableId() {
        return drawable == 0 ? R.drawable.icon : drawable;
    }

    @Override
    public String getActionUrl() {
        return actionUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.imgUrl);
        dest.writeInt(this.drawable);
        dest.writeString(this.actionUrl);
    }

    public ShareBean(String title, String content, String imgUrl, int drawable, String actionUrl) {
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.drawable = drawable;
        this.actionUrl = actionUrl;
    }


    protected ShareBean(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
        this.imgUrl = in.readString();
        this.drawable = in.readInt();
        this.actionUrl = in.readString();
    }

    public static final Creator<ShareBean> CREATOR = new Creator<ShareBean>() {
        @Override
        public ShareBean createFromParcel(Parcel source) {
            return new ShareBean(source);
        }

        @Override
        public ShareBean[] newArray(int size) {
            return new ShareBean[size];
        }
    };
}
