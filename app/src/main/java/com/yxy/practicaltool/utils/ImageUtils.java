package com.yxy.practicaltool.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Log;


import com.yxy.practicaltool.common.Constants;
import com.yxy.practicaltool.common.L;
import com.yxy.practicaltool.common.ToastUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片处理工具类
 *
 * @author vendor
 */
public class ImageUtils {

    private static final String TAG = "ImageUtil";

    Context context;

    /**
     * 文件名
     */
    String fileName;

    /**
     * 图片的存储路径
     */
    String picPath;

    public ImageUtils(Context context) {
        this.context = context;
    }

    /**
     * 对图片进行压缩处理
     *
     * @param f 图片文件
     * @return 压缩处理过的图片
     */
    public Bitmap compress(File f) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;

            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            System.gc();
        }
        return null;
    }

    //获取无旋转的path
    public String getPathByPathNoRotate(String srcPath) {

        String bm = getBitmapPathByPath(srcPath);

        return bm;
    }
    //获取无旋转的bitmap
    public Bitmap getBitmapByPathNoRotate(String srcPath) {

        Bitmap bm = getBitmapByPath(srcPath);

        return bm;
    }


    //根据路径获取图片并按比例大小压缩
    public String getBitmapPathByPath(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 1080f;//这里设置高度为800f
        float ww = 608f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inPreferredConfig = Config.RGB_565;//降低图片从ARGB888到RGB565
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        //调整图片角度
        int degree = readPictureDegree(srcPath);
        if (degree != 0) {//旋转照片角度
            bitmap = rotateBitmap(bitmap, degree);
        }
        L.e("根据路径获取图片并按比例大小压缩=" + bitmap.getByteCount());
        return compressImageToPath(bitmap);//压缩好比例大小后再进行质量压缩
    }
    //根据路径获取图片并按比例大小压缩
    public Bitmap getBitmapByPath(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 1080f;//这里设置高度为800f
        float ww = 608f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inPreferredConfig = Config.RGB_565;//降低图片从ARGB888到RGB565
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        //调整图片角度
        int degree = readPictureDegree(srcPath);
        if (degree != 0) {//旋转照片角度
            bitmap = rotateBitmap(bitmap, degree);
        }
        L.e("根据路径获取图片并按比例大小压缩=" + bitmap.getByteCount());
        return compressImageToBitmap(bitmap);//压缩好比例大小后再进行质量压缩
    }
    //根据Bitmap按比例大小图片压缩
    public String getBitmapByBitmap(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 1080f;//这里设置高度为800f
        float ww = 608f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inPreferredConfig = Config.RGB_565;//降低图片从ARGB888到RGB565
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImageToPath(bitmap);//压缩好比例大小后再进行质量压缩
    }

    //质量压缩
    public String compressImageToPath(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        String filePath = Environment.getExternalStorageDirectory() + Constants.DATA_DIR + Utils.getImageFileName();
        FileOutputStream fos = null;
        if (image != null) {
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把图像数据存放到baos中
            int options = 90;
            if (baos.toByteArray().length / 1024 <= 200) {
                //写入本地后保存
                try {
                    fos = new FileOutputStream(filePath);
                    fos.write(baos.toByteArray());
                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return filePath;
            }
            while (baos.toByteArray().length / 1024 > 300) {    //循环判断如果压缩后图片是否大于300kb,大于继续压缩
                baos.reset();//重置baos即清空baos

                options -= 10;//每次都减少10
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把图像数据存放到baos中
//                image.compress(Bitmap.CompressFormat.JPEG, options, stream);//将图像读取到Stream中
            }
            //此处转有问题 压缩的baos 转bitmap 变大（190K变大1000K左右）。 直接转file没有问题
//            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
//            bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
//            L.e("======================压缩 后bitmap.getByteCount()=" + bitmap.getByteCount());
            try {
                fos = new FileOutputStream(filePath);
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

//            Bitmap bitmap=BitmapFactory.decodeFile(filePath);
//            int quality = 100;
//            bitmap.compress(Bitmap.CompressFormat.PNG, quality, stream);
//            L.e("bitmap decodeFile 质量压缩====================="+bitmap.getByteCount());
            return filePath;
        } else {
            return null;
        }

    }
    //质量压缩
    public Bitmap compressImageToBitmap(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        String filePath = Environment.getExternalStorageDirectory() + Constants.DATA_DIR + Utils.getImageFileName();
        FileOutputStream fos = null;
        if (image != null) {
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把图像数据存放到baos中
            int options = 90;
            if (baos.toByteArray().length / 1024 <= 300) {
                return image;
            }
            while (baos.toByteArray().length / 1024 > 300) {    //循环判断如果压缩后图片是否大于300kb,大于继续压缩
                baos.reset();//重置baos即清空baos

                options -= 10;//每次都减少10
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把图像数据存放到baos中
//                image.compress(Bitmap.CompressFormat.JPEG, options, stream);//将图像读取到Stream中
            }
            //此处转有问题 压缩的baos 转bitmap 变大（190K变大1000K左右）。 直接转file没有问题
//            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
//            bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
//            L.e("======================压缩 后bitmap.getByteCount()=" + bitmap.getByteCount());

            try {
                fos = new FileOutputStream(filePath);
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bitmap bitmap= BitmapFactory.decodeFile(filePath);
//            int quality = 100;
//            bitmap.compress(Bitmap.CompressFormat.PNG, quality, stream);
//            L.e("bitmap decodeFile 质量压缩====================="+bitmap.getByteCount());
            return bitmap;
        } else {
            return null;
        }

    }
    /**
     * 函数名称 : toRoundCorner 功能描述 : 图片圆角处理 参数及返回值说明：
     *
     * @param bitmap
     * @param pixels
     * @return 修改记录： 日期：2012-12-11 下午02:19:34 修改人：vendor 描述 ：
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) throws OutOfMemoryError {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }

        return output;
    }

    /**
     * 加载hashName加载本地图片
     *
     * @param filePahtAndName
     * @return
     */
   /* public Bitmap loadHashNameImage(String filePahtAndName) {
        try {
            String hashName = Utils.getHashName(filePahtAndName);
            Log.d(TAG, "loadHashNameImage hashName = " + hashName);

            String filePath = FileTools.getParentPath(filePahtAndName);
            return BitmapFactory.decodeStream(new FileInputStream(new File(filePath + "/" + hashName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }*/


    public Bitmap getImageFromAssetFile(String fileName) {
        Bitmap image = null;
        try {
            AssetManager assetManager = context.getAssets();
            java.io.InputStream is = assetManager.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            System.err.print(e.toString());
            ToastUtils.showToast(context, e.toString());
        }
        return image;
    }

    /**
     * 转换 drawable to bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888 : Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 转换 Bitmap to drawable
     *
     * @param bitmap
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    /**
     * 函数名称 : saveBitmap 功能描述 : 图片保存 参数及返回值说明：
     *
     * @param bmp
     * @param fileName
     */
    public File saveBitmap(Bitmap bmp, String fileName) throws IOException {
        if (bmp == null)
            return null;

        this.fileName = fileName;

        if (fileName == null) {
            this.fileName = "aaa.png";
        }

        File f = Environment.getExternalStorageDirectory();
        String picDirectory = f.getAbsolutePath() + Constants.DATA_DIR;
        this.picPath = picDirectory + "/" + this.fileName;

        // 判断文件夹是否存在
        File picFile = new File(picDirectory);
        if (!picFile.exists()) {
            picFile.mkdir();
        }

        File file = new File(this.picPath);
        if (picFile.exists()) {
            picFile.deleteOnExit();
        }
        file.createNewFile();


        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        return this.picPath;
        return file;
    }

    /**
     * Save image to the SD card
     *
     * @param photoBitmap
     * @param path
     * @param photoName
     */
    public static boolean savePhotoToSDCard(Bitmap photoBitmap, String path, String photoName) {
        boolean flag = false;
        if (Utils.checkSDCardAvailable()) {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File photoFile = new File(path, photoName);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
                        fileOutputStream.flush();
                    }
                }
                flag = true;
            } catch (FileNotFoundException e) {
                photoFile.delete();
                e.printStackTrace();
                flag = false;
            } catch (IOException e) {
                photoFile.delete();
                e.printStackTrace();
                flag = false;
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    flag = false;
                }
            }
        }
        return flag;
    }

    public File saveHashPhotoToSDCard(String path, String fileName) {
        L.e("");
        File fos = null;
        if (Utils.checkSDCardAvailable()) {
            //必须先创建文件夹
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            try {
                fos = new File(path + fileName);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fos;
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    //旋转照片
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

}
