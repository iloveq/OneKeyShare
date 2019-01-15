package com.woaigmz.share.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.woaigmz.share.R;
import com.woaigmz.share.ShareChannel;
import com.woaigmz.share.ShareProxy;


/**
 * Created by haoran on 2018/8/6.
 */

public class ShareDialog extends DialogFragment implements IShareView, View.OnClickListener {

    private static final String KEY = "ShareDialog";
    private static final String CHANNEL = "channel";
    private static final String COUNT = "spanCount";

    private ShareProxy.OnShareClickListener listener;
    private Context context;

    @Override
    public void setOnShareClickListener(ShareProxy.OnShareClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_bottom_share);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        assert window != null;
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        RecyclerView recyclerView = dialog.findViewById(R.id.recycler_view_share);
        TextView tvShareCancel = dialog.findViewById(R.id.tv_share_cancel);
        assert getArguments() != null;
        int[] arr = getArguments().getIntArray(CHANNEL);
        int count = getArguments().getInt(COUNT);
        assert arr != null;
        recyclerView.setLayoutManager(new GridLayoutManager(context, count == 0 ? 4 : count));
        recyclerView.setAdapter(new ShareAdapter(arr));
        tvShareCancel.setOnClickListener(this);
        return dialog;
    }


    @Override
    public IShareView createShareDialog(Context context, int[] shareChannel, int spanCount) {
        this.context = context;
        Bundle bundle = new Bundle();
        bundle.putIntArray(CHANNEL, shareChannel);
        bundle.putInt(COUNT, spanCount);
        ShareDialog dialog = new ShareDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public int show(FragmentTransaction transaction) {
        return super.show(transaction, KEY);
    }

    @Override
    public void show(FragmentManager manager) {
        super.show(manager, KEY);
    }

    @Override
    public void onClick(View v) {
        dismissDialog();
    }

    @Override
    public void dismissDialog() {
        listener.onShareClick(ShareChannel.CHANNEL_CANNEL);
        dismissAllowingStateLoss();
    }

    public static IShareView get() {
        return new ShareDialog();
    }


    private class ShareAdapter extends RecyclerView.Adapter<MyShareHolder> {

        private int[] resId;
        private String[] name;

        ShareAdapter(int[] arr) {
            resId = new int[arr.length];
            name = new String[arr.length];
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == ShareChannel.CHANNEL_WECHAT) {
                    resId[i] = R.mipmap.share_wx;
                    name[i] = "微信";
                } else if (arr[i] == ShareChannel.CHANNEL_WECHAT_MOMENT) {
                    resId[i] = R.mipmap.share_wx_moment;
                    name[i] = "朋友圈";
                } else if (arr[i] == ShareChannel.CHANNEL_QQ) {
                    resId[i] = R.mipmap.share_qq;
                    name[i] = "QQ";
                } else if (arr[i] == ShareChannel.CHANNEL_QQ_ZONE) {
                    resId[i] = R.mipmap.share_qq_zone;
                    name[i] = "QQ空间";
                } else if (arr[i] == ShareChannel.CHANNEL_WEIBO) {
                    resId[i] = R.mipmap.share_sine;
                    name[i] = "微博";
                } else if (arr[i] == ShareChannel.CHANNEL_MORE) {
                    resId[i] = R.mipmap.share_more;
                    name[i] = "更多";
                } else {
                    throw new RuntimeException(" ShareChannel 数组初始化错误 ");
                }
            }
        }

        @NonNull
        @Override
        public MyShareHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyShareHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_share, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyShareHolder holder, int position) {
            final int pos = holder.getAdapterPosition();
            holder.tvName.setText(name[pos]);
            try{
                Drawable drawable = holder.tvName.getContext().getResources().getDrawable(resId[pos]);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                holder.tvName.setCompoundDrawables(null, drawable, null, null);
            }catch (Exception e){
                e.printStackTrace();
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (name[pos]) {
                        case "微信":
                            listener.onShareClick(ShareChannel.CHANNEL_WECHAT);
                            break;
                        case "朋友圈":
                            listener.onShareClick(ShareChannel.CHANNEL_WECHAT_MOMENT);
                            break;
                        case "QQ":
                            listener.onShareClick(ShareChannel.CHANNEL_QQ);
                            break;
                        case "QQ空间":
                            listener.onShareClick(ShareChannel.CHANNEL_QQ_ZONE);
                            break;
                        case "微博":
                            listener.onShareClick(ShareChannel.CHANNEL_WEIBO);
                            break;
                        case "更多":
                            listener.onShareClick(ShareChannel.CHANNEL_MORE);
                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return resId.length;
        }

    }

    private class MyShareHolder extends RecyclerView.ViewHolder {

        private TextView tvName;

        MyShareHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_share);
        }
    }
}
