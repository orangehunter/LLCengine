package com.llcengine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class smallAnimax {
	
	Boolean animax_flag=false;
	Boolean pause_flag=false;
	
	double animax_count_flag=0;	
	double count_unit;
	int pic_number=0;
	int duration;
	int start_position;
	int x;
	int y;
	
	Bitmap []pic;
	
	public smallAnimax(Bitmap pic[]){
		this.pic=new Bitmap [pic.length];
		this.pic=pic;
		this.pic_number=this.pic.length;
	}
	public void setDuration(int duration){//設定長度
		this.duration=duration;
		this.count_unit=(pic_number*1.0)/(this.duration*1.0);
	}
	public void setPosition(int x,int y){//設定位置
		this.x=x;
		this.y=y;
	}
	public void start(int CurrentPosition){//啟動(有設定長度)
		this.start_position=CurrentPosition;
		animax_flag=true;
	}
	public void start(){//啟動(無設定長度)
		animax_flag=true;
		 animax_count_flag=0;	
	}
	public void pause(){//暫停
		pause_flag=true;
	}
	public void resume(){//取消暫停
	pause_flag=false;		
	}
	public boolean getPause(){//取得暫停狀態
		return pause_flag;
	}
	public int getCount(){
		return (int)animax_count_flag;
	}
	public void drawEffect_time(int CurrentPosition,Canvas canvas,Paint paint){//繪圖(有設定長度){
		if(animax_flag){
			animax_count_flag=count_unit*(CurrentPosition-start_position);
			if(((int)animax_count_flag)<pic_number){
				Graphic.drawPic(canvas, pic[((int)animax_count_flag)], x, y, 0, 255, paint);
			}else{
				animax_flag=false;
			}
		}
	}
	public void drawEffect(double speed,Canvas canvas,Paint paint){//繪圖(無設定長度)
		if(animax_flag){
			if(!pause_flag){
			animax_count_flag+=speed;
			}
			if(((int)animax_count_flag)<pic_number){
				Graphic.drawPic(canvas, pic[((int)animax_count_flag)], x, y, 0, 255, paint);
			}else{
				animax_flag=false;
				animax_count_flag=0;
			}
		}
	}
	
	public Boolean getFlag(){
		return animax_flag;
	}
	
	//public int getCountFlag(){
		//return ((int)animax_count_flag);
	//}
	public void recycle(){
		for(int i=0;i<pic.length;i++){
			pic[i].recycle();
		}		
	}
}
