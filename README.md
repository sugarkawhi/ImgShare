# ImgShare
模拟掌阅书籍选中段落分享

# 功能要点 
+ 1.获取View图像绘制到bitmap
+ 2.Bitmap 存储到本地文件

这里呢，主要还是用到的是ScrollView,因为可能一屏展示不完全。

```
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
```
        
这里有一个坑，就是，当ScrollView有设置padding的时候，只能绘制当前一屏的图像。

```
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
```

