package com.icool.imgshare;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * 分享
 * Created by ZhaoZongyao on 2018/4/17.
 */

public class ShareDialog extends BottomPopDialog {

    ScrollView mScrollView;
    TextView mTextViewContent;
    TextView mBtnShare;
    TextView mBtnCancel;

    OnShareClickListener mOnShareClickListener;

    public ShareDialog(Context context) {
        super(context);
        mScrollView = findViewById(R.id.scrollView);
        mTextViewContent = findViewById(R.id.tv_content);
        mBtnCancel = findViewById(R.id.btn_cancel);
        mBtnShare = findViewById(R.id.btn_share);
        setDimAmount(0.5F);
        setGravity(Gravity.CENTER);
        setContentHeight(getWindow().getWindowManager().getDefaultDisplay().getHeight());
        mBtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnShareClickListener.onShare(mScrollView);
            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_share;
    }

    public ShareDialog setContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            mTextViewContent.setText(content);
        }
        return this;
    }

    public void setOnShareClickListener(OnShareClickListener onShareClickListener) {
        mOnShareClickListener = onShareClickListener;
    }

    public interface OnShareClickListener {
        void onShare(ScrollView scrollView);
    }


}
