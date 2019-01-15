package com.woaigmz.share.model;

import java.io.Serializable;

/**
 * Created by haoran on 2018/8/3.
 */

public class AppId implements Serializable {

    private static final long serialVersionUID = -7293755653343866205L;

    private String QQ;

    public AppId(String QQ, String WECHAT, String WEIBO) {
        this.QQ = QQ;
        this.WECHAT = WECHAT;
        this.WEIBO = WEIBO;
    }

    private String WECHAT;
    private String WEIBO;

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getWECHAT() {
        return WECHAT;
    }

    public void setWECHAT(String WECHAT) {
        this.WECHAT = WECHAT;
    }

    public String getWEIBO() {
        return WEIBO;
    }

    public void setWEIBO(String WEIBO) {
        this.WEIBO = WEIBO;
    }
}
