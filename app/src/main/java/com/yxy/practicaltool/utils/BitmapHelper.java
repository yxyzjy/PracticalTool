package com.yxy.practicaltool.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Base64;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class BitmapHelper {

	public static Bitmap getBitmap(String url) {
		Bitmap bm = null;
		try {
			URL iconUrl = new URL(url);
			URLConnection conn = iconUrl.openConnection();
			HttpURLConnection http = (HttpURLConnection) conn;

			int length = http.getContentLength();

			conn.connect();
			// 获得图像的字符流
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is, length);
			bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();// 关闭流
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return bm;
	}


	public static String getRealFilePath(final Context context, final Uri uri) {
		if ( null == uri ) return null;
		final String scheme = uri.getScheme();
		String data = null;
		if ( scheme == null )
			data = uri.getPath();
		else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
			data = uri.getPath();
		} else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
			Cursor cursor = context.getContentResolver().query( uri, new String[] { ImageColumns.DATA }, null, null, null );
			if ( null != cursor ) {
				if ( cursor.moveToFirst() ) {
					int index = cursor.getColumnIndex( ImageColumns.DATA );
					if ( index > -1 ) {
						data = cursor.getString( index );
					}
				}
				cursor.close();
			}
		}
		return data;
	}


	/**
	 * @Description: 根据图片路径转字符串
	 * @param @param path
	 * @return String 返回类型
	 */
	public static String bitmapToBase64(String path) {
		String result = null;
		ByteArrayOutputStream baos = null;
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeFile(path);
			if (bitmap != null) {
				baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
				baos.flush();
				baos.close();
				byte[] bitmapBytes = baos.toByteArray();
				result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (baos != null) try {baos.close();} catch (Exception e) {}
			if (bitmap != null) bitmap.recycle();
		}
		return result;
	}
	/**
	 * @Description: 根据bitmap转字符串
	 * @param @param bitmap
	 * @return String 返回类型
	 */
	public static String bitmap2Base64(Bitmap bitmap) {
		String result = null;
		ByteArrayOutputStream baos = null;
		try {
			if (bitmap != null) {
				baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
				baos.flush();
				baos.close();
				byte[] bitmapBytes = baos.toByteArray();
				result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (baos != null) try {baos.close();} catch (Exception e) {}
			if (bitmap != null) bitmap.recycle();
		}
		return result;
	}


	//dip转像素值
	public static int dip2px(Context context, double d) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (d * scale + 0.5f);
	}

	//像素值转dip
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/***
	 * 图片的缩放方法
	 *
	 * @param bgimage
	 *            ：源图片资源
	 * @param newWidth
	 *            ：缩放后宽度
	 * @param newHeight
	 *            ：缩放后高度
	 * @return
	 */
	public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);
		return bitmap;
	}

	public static void setImgFromSD(ImageView iv, int width) {
		File file = new File(Environment.getExternalStorageDirectory()+"/photo_jq.jpg");
		if(file.exists()) {
			Options opts = new Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
			opts.inSampleSize = opts.outWidth/width;
			opts.inJustDecodeBounds = false;
			Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
			iv.setImageBitmap(bm);
		}
	}

	/**
	 *
	 * 根据图片大小压缩图片
	 * @param pathString
	 *                  图片绝对路径
	 * @return Bitmap压缩后图片
	 */
	public static Bitmap getDiskBitmap(String pathString) {
		Bitmap bitmap = null;
		Bitmap bMapRotate = null;
		try {
			File file = new File(pathString);
			if (file.exists()) {
				BitmapFactory.Options opt = new BitmapFactory.Options();
				opt.inPreferredConfig = Bitmap.Config.RGB_565;
				opt.inPurgeable = true;
				opt.inInputShareable = true;
				opt.inTempStorage = new byte[1024 * 1024 * 10];
				long length = file.length();
				if (length / (1024 * 1024) > 4) {
					opt.inSampleSize = 16;
				} else if (length / (1024 * 1024) >= 1) {
					opt.inSampleSize = 8;
				} else if (length / (1024 * 512) >= 1) {
					opt.inSampleSize = 4;
				} else if (length / (1024 * 256) >= 1) {
					opt.inSampleSize = 2;
				} else {
					opt.inSampleSize = 1;
				}
				bitmap = BitmapFactory.decodeFile(pathString, opt);
				int orientation = getDegress(pathString);
					/*
					 * if(bitmap.getHeight() < bitmap.getWidth()){ orientation = 90;
					 * } else { orientation = 0; }
					 */
				if (orientation != 0) {
					Matrix matrix = new Matrix();
					matrix.postRotate(orientation);
					bMapRotate = Bitmap
							.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
									bitmap.getHeight(), matrix, true);
				} else {
					bMapRotate = Bitmap.createScaledBitmap(bitmap,
							bitmap.getWidth(), bitmap.getHeight(), true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (bMapRotate != null) {
			return bMapRotate;
		}
		return bitmap;
	}


	/**
	 * 读取图片属性：旋转的角度
	 *
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int getDegress(String path) {
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

	/**
	 * 旋转图片
	 * @param bitmap
	 * @param degress
	 * @return
	 */
	public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
		if (bitmap != null) {
			Matrix m = new Matrix();
			m.postRotate(degress);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
			return bitmap;
		}
		return bitmap;
	}

	/**
	 * 质量压缩方法(如果图片大于指定的大小，循环压缩)
	 * @param image
	 * @param maxkb
	 * @return    参数
	 * @return Bitmap    返回类型
	 */
	public static Bitmap compressImage(Bitmap image, int maxkb) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		//循环判断如果压缩后图片是否大于100kb,大于继续压缩
		while ( baos.toByteArray().length / 1024 > maxkb) {
			//重置baos即清空baos
			baos.reset();
			//每次都减少10
			options -= 10;
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);
		}
		if(image != null) image.recycle();
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
		return bitmap;
	}

	/**
	 * 图片按比例大小压缩方法（根据路径获取图片并压缩）
	 * @param @param srcPath
	 * @param @return    参数
	 * @return Bitmap    返回类型
	 */
	public static Bitmap getImage(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		//现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;//这里设置高度为800f
		float ww = 480f;//这里设置宽度为480f
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
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return compressImage(bitmap, 100);//压缩好比例大小后再进行质量压缩
	}

	/**
	 * 图片按比例大小压缩方法（根据路径获取图片并压缩）
	 * @param @param srcPath
	 * @param @param maxkb
	 * @param @return    参数
	 * @return Bitmap    返回类型
	 */
	public static Bitmap getImage(String srcPath, int maxkb) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		//现在主流手机比较多是1280*720分辨率，所以高和宽我们设置为
		float hh = 1280f;//这里设置高度为1280f
		float ww = 720f;//这里设置宽度为720f
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
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return compressImage(bitmap, maxkb); //压缩好比例大小后再进行质量压缩
	}

	/**
	 * 图片按比例大小压缩方法（根据Bitmap图片压缩）
	 * @param @param image
	 * @param @return    参数
	 * @return Bitmap    返回类型
	 */
	public static Bitmap compressImage(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
		if( baos.toByteArray().length / 1024 > 1024) {
			// 重置baos即清空baos
			baos.reset();
			// 这里压缩50%，把压缩后的数据存放到baos中
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
		}
		if(image != null) image.recycle();
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		//现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;//这里设置高度为800f
		float ww = 480f;//这里设置宽度为480f
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
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap, 100);//压缩好比例大小后再进行质量压缩
	}





}
