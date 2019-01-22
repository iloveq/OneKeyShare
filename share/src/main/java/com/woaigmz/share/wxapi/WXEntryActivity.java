package com.woaigmz.share.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.woaigmz.share.api.WXShare;
import com.woaigmz.share.model.AppId;
import com.woaigmz.share.utils.SerializeUtils;
import static com.woaigmz.share.utils.Utils.getSerializePath;


/**
 * 微信客户端回调activity示例
 *
 * 修改微信登陆过程中获取code, 之后发送消息給, Activity直接进行页面跳转
 */
public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI iwxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppId appId = (AppId) SerializeUtils.deserialization(getSerializePath(this.getApplication()));
        iwxapi = WXAPIFactory.createWXAPI(this, appId.getWECHAT());
        iwxapi.handleIntent(getIntent(), this);
    }


    @Override
    public void onReq(BaseReq req) {
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        WXShare.sendBroadcast(this, resp.errCode);
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        iwxapi.handleIntent(intent, this);
        finish();
    }

}
