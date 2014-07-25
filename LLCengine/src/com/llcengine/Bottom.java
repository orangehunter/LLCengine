package com.llcengine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bottom {
	MainActivity activity;
	float x;//圖片的中央x座標
	float y;//圖片的中央y座標
	float width;//虛擬按鈕的寬
	float height;//虛擬按鈕的高
	Bitmap onBitmap;//按下狀態的圖片
	Bitmap offBitmap;//未按下狀態的圖片
	Bitmap stringBitmap;
	boolean isOn=true;//按下狀態為false
	public Bottom(MainActivity activity,Bitmap onBitmap,Bitmap offBitmap,Bitmap stringBitmap,int x,int y){
		this.activity=activity;
		//this.isOn=activity.backgroundsoundFlag;
		this.onBitmap=onBitmap;
		this.offBitmap=offBitmap;
		this.stringBitmap=stringBitmap;
		this.width=offBitmap.getWidth();
		this.height=offBitmap.getHeight();
		this.x=Coordinate.CoordinateX(x)-(this.width/2);
		this.y=Coordinate.CoordinateY(y)-(this.height/2);
	}
	
	
	public void drawBtm(Canvas canvas,Paint paint){//繪製按鈕
		if(isOn)
			canvas.drawBitmap(onBitmap, x, y, paint);
		else
			canvas.drawBitmap(offBitmap, x, y,paint);
		canvas.drawBitmap(stringBitmap, x, y, paint);
	}
	
	
	public void setBottom(){//切換按鈕狀態
		this.isOn=!this.isOn;
	}
	
	public void setBottomTo(Boolean i){
		this.isOn=i;
	}
	public boolean getBottom(){
		return this.isOn;
	}
	
	public boolean isIn(float pointx,float pointy){//判斷觸控位置
		if(pointx>=x&&pointx<=x+width&&      	pointy>=y&&pointy<=y+height)
			return true;
		return false;
	}
}//