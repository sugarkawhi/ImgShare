package com.icool.imgshare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button mButtonLong, mButtonShort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonLong = findViewById(R.id.btn_long_text);
        mButtonShort = findViewById(R.id.btn_short_text);
        final ShareDialog shareDialog = new ShareDialog(this);
        mButtonLong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.setContent(getString(R.string.long_text)).show();
            }
        });
        mButtonShort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.setContent(getString(R.string.short_text)).show();

            }
        });

        shareDialog.setOnShareClickListener(new ShareDialog.OnShareClickListener() {
            @Override
            public void onShare(ScrollView scrollView) {
                File file = createSharePic(scrollView);
                doShare(file);
            }
        });
    }

    //todo put it in thread
    public File createSharePic(ScrollView scrollView) {
        int width = scrollView.getWidth();
        int height = 0;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            View childView = scrollView.getChildAt(i);
            height += childView.getHeight();
            childView.setBackgroundColor(Color.WHITE);
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        scrollView.draw(canvas);

        String path = getApplication().getExternalCacheDir().getAbsolutePath() + File.separator + "share";
        File file = new File(path);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
        }
        File imgFile = new File(file.getAbsolutePath() + File.separator + "img.png");
        if (imgFile.exists()) imgFile.delete();
        try {
            boolean createNewFile = imgFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imgFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return imgFile;
    }


    public void doShare(File file) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        // 比如发送二进制文件数据流内容（比如图片、视频、音频文件等等）
        // 指定发送的内容 (EXTRA_STREAM 对于文件 Uri )
        Uri uri = Uri.fromFile(file);
        // 指定发送内容的类型 (MIME type)
        sendIntent.setType("image/*");
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(sendIntent, "分享到"));
    }

}
