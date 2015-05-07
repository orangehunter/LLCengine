package com.musicsalvation;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

public class Graphic {

    public static Bitmap LoadBitmap(Resources rs,int r,boolean withAlpha){
        InputStream inputStream = rs.openRawResource(r);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        if(withAlpha) {
            options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        }else {
            options.inPreferredConfig=Bitmap.Config.RGB_565;
        }
        inputStream = rs.openRawResource(r);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null, options);
        try {
            inputStream.reset();
        } catch (IOException e) {
            Log.e("graphic", ""+e);
        }
        if(bitmap==null){
            Log.e("graphic", "bitmail null");
        }
        return bitmap;
    }

    public static Bitmap LoadBitmap(Resources rs,int r,int x,int y,int scale,boolean withAlpha){
		try{
		 InputStream inputStream = rs.openRawResource(r);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds=false;
            if(withAlpha) {
                options.inPreferredConfig = Bitmap.Config.ARGB_4444;
            }else {
                options.inPreferredConfig=Bitmap.Config.RGB_565;
            }
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inSampleSize = scale;
		 Bitmap s=BitmapFactory.decodeStream(inputStream, null, options);
		    return Bitmap.createScaledBitmap(s, (int)Coordinate.CoordinateX(x), (int)Coordinate.CoordinateY(y), true);
		}catch(OutOfMemoryError e){
			return null;
		}
		//return BitmapFactory.decodeResource(getResources(), r);
	}
    public static Bitmap LoadBitmap(Resources rs,int r,int x,int y,boolean withAlpha){
		InputStream inputStream = rs.openRawResource(r);
		BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        if(withAlpha) {
            options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        }else {
            options.inPreferredConfig=Bitmap.Config.RGB_565;
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null,options);
        
        int heightRatio = (int)Math.ceil(options.outHeight/y);//(float)Coordinate.CoordinateY(y));
        int widthRatio = (int)Math.ceil(options.outWidth/x);//(float)Coordinate.CoordinateX(x));
        
        if (heightRatio > 1 || widthRatio > 1)
        {
         if (heightRatio > widthRatio)
         {
          options.inSampleSize = heightRatio;
         } else {
          options.inSampleSize = widthRatio;
         }
        }
        
        options.inJustDecodeBounds = false;
        inputStream = rs.openRawResource(r);
        bitmap = BitmapFactory.decodeStream(inputStream,null, options);
        bitmap=bitSize(bitmap,x,y);
        try {
			inputStream.reset();
		} catch (IOException e) {
			Log.e("graphic", ""+e);
		}
        if(bitmap==null){
        	Log.e("graphic", "bitmail null");
        }
     return bitmap;
	}

    public static Bitmap bitSize(Bitmap bf,int f,int g){//大小修改
		int bw=0;
		int bh=0;
		float scaleWidth=0;
		float scaleHeight=0;
		Matrix matrix = new Matrix();
		while(scaleWidth<=0&&scaleHeight<=0){
			bw=bf.getWidth();
			bh=bf.getHeight();
			scaleWidth = Coordinate.CoordinateX(f) / bw;
			scaleHeight = Coordinate.CoordinateY(g)/ bh;
		}
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bit=Bitmap.createBitmap(bf, 0,0,bw,bh, matrix, true);
		matrix.reset();

		return bit;
	}
    public static Bitmap MirrorFlipHorizontal(Bitmap bf){//鏡像水平翻轉
        Matrix matrix = new Matrix();
        matrix.postScale(-1,1);
        Bitmap bit=Bitmap.createBitmap(bf, 0,0,bf.getWidth(),bf.getHeight(), matrix, true);
        matrix.reset();

        return bit;
    }
    public static Bitmap cutArea(Bitmap bt, int start_x, int start_y, int width, int height){//區域切割
		Bitmap temp=Bitmap.createBitmap(bt, start_x,start_y, width, height);
		return temp;
	}

    public static void drawPic(Canvas canvas,Bitmap bit,int mid_x,int mid_y,float rot,int alpha,Paint paint){
		paint.setAntiAlias(true);
		paint.setAlpha(alpha);
		float x=Coordinate.CoordinateX(mid_x),y=Coordinate.CoordinateY(mid_y);
		if(rot%360==0)
			canvas.drawBitmap(bit, x-(bit.getWidth()/2), y-(bit.getHeight()/2), paint);
		else{
			Matrix matrix = new Matrix();
			matrix.preRotate(rot, (bit.getWidth()/2), (bit.getHeight()/2));
			matrix.postTranslate( x-(bit.getWidth()/2), y-(bit.getHeight()/2));
			canvas.drawBitmap(bit, matrix, paint);
			matrix.reset();
		}
		paint.reset();
	}

	public static void drawLine(Canvas canvas, int color, int start_x, int start_y, int end_x, int end_y, int with, Paint paint){
		paint.setColor(color);
		paint.setStrokeWidth(with);
		canvas.drawLine(Coordinate.CoordinateX(start_x), Coordinate.CoordinateY(start_y), Coordinate.CoordinateX(end_x),Coordinate.CoordinateY( end_y), paint);
		paint.reset();
	}
    public static void drawRect(Canvas canvas,int color,int start_x,int start_y,int end_x,int end_y,Paint paint){
        paint.setColor(color);
        canvas.drawRect(Coordinate.CoordinateX(start_x), Coordinate.CoordinateY(start_y), Coordinate.CoordinateX(end_x),Coordinate.CoordinateY( end_y), paint);
        paint.reset();
    }
    public static void drawText(Canvas canvas,String st,int start_x,int start_y,int color,int size,Paint paint){
        paint.setColor(color);
        paint.setTextSize(Coordinate.CoordinateX(size));
        try {
            canvas.drawText(st, Coordinate.CoordinateX(start_x), Coordinate.CoordinateY(start_y), paint);
        }catch (Exception e){};
        paint.reset();
    }
}
