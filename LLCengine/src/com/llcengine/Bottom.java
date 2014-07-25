package com.llcengine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bottom {
	MainActivity activity;
	float x;//�Ϥ�������x�y��
	float y;//�Ϥ�������y�y��
	float width;//�������s���e
	float height;//�������s����
	Bitmap onBitmap;//���U���A���Ϥ�
	Bitmap offBitmap;//�����U���A���Ϥ�
	Bitmap stringBitmap;
	boolean isOn=true;//���U���A��false
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
	
	
	public void drawBtm(Canvas canvas,Paint paint){//ø�s���s
		if(isOn)
			canvas.drawBitmap(onBitmap, x, y, paint);
		else
			canvas.drawBitmap(offBitmap, x, y,paint);
		canvas.drawBitmap(stringBitmap, x, y, paint);
	}
	
	
	public void setBottom(){//�������s���A
		this.isOn=!this.isOn;
	}
	
	public void setBottomTo(Boolean i){
		this.isOn=i;
	}
	public boolean getBottom(){
		return this.isOn;
	}
	
	public boolean isIn(float pointx,float pointy){//�P�_Ĳ����m
		if(pointx>=x&&pointx<=x+width&&      	pointy>=y&&pointy<=y+height)
			return true;
		return false;
	}
}//