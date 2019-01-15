package com.woaigmz.sharedemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.woaigmz.share.ShareChannel;
import com.woaigmz.share.ShareProxy;
import com.woaigmz.share.ShareStatus;
import com.woaigmz.share.model.ShareBean;
import com.woaigmz.share.view.IShareView;
import com.woaigmz.share.view.ShareActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.toShare).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        IShareView dialog = ShareProxy.getInstance().createShareDialog(this,new int[]{ShareChannel.CHANNEL_QQ,ShareChannel.CHANNEL_WECHAT,ShareChannel.CHANNEL_WEIBO},3);
        ShareProxy.getInstance().setOnShareClickListener(dialog,this,new ShareBean("title","content","http://wx3.sinaimg.cn/large/006nLajtly1fkegnmnwuxj30dw0dw408.jpg",0,"https://www.baidu.com"));
        dialog.show(getSupportFragmentManager());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            int status = data.getIntExtra(ShareActivity.RESULT_STATUS, -1);
            switch (status) {
                case ShareStatus.SHARE_STATUS_COMPLETE:
                    Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
                    break;
                case ShareStatus.SHARE_STATUS_ERROR:
                    Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
                    break;
                case ShareStatus.SHARE_STATUS_CANCEL:
                    Toast.makeText(this, "分享取消", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }



}
