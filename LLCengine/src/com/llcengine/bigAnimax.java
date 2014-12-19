package com.llcengine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class bigAnimax {
	Boolean animax_flag=false;
	Boolean pause_flag=false;

	MainActivity activity;
	int animax_buffer=3;
	int r;

	int last_draw;
	double counter;
	double counter_ret;
	double speed;

	double count_unit;
	int start_position;
	int duration;

	int animaxLength;

	int pic_width;
	int pic_hight;

	int x;
	int y;

	Bitmap bit[];

	Thread line[];
	Runnable run;
	int count;

	public bigAnimax(MainActivity activity,int length,int width,int hight,int R){
		this.activity=activity;
		animaxLength=length;
		bit=new Bitmap [animaxLength];
		pic_width=width;
		pic_hight=hight;
		this.r=R;
		resetBit();
		setBuffer();
	}
	public void resetBit(){
		for(int i=0;i<animaxLength;i++){
			if(i>animax_buffer){
				if(bit[i]!=null){
					bit[i].recycle();
				}
			}else{
				if(bit[i]==null){
					bit[i]=Graphic.LoadBitmap(activity.getResources(), r+i, pic_width, pic_hight,2);
				}
			}
		}
	}

	public void setRunable(){
		run=new Runnable(){
			@Override
			public void run() {
			
				count++;
				int buffer=count+animax_buffer;
				if(buffer<animaxLength){
					try{
						if(bit[count-1]!=null){
						bit[count-1].recycle();
						}
						if(bit[buffer]==null){
						bit[buffer]=Graphic.LoadBitmap(activity.getResources(), r+buffer, pic_width, pic_hight,2);
						Log.v("LoadAnimax", ""+buffer);
						}
						
					}catch(OutOfMemoryError e){
						animax_flag=false;
						Log.e("bigAnimax","Not enough Memory");
						animax_buffer--;
						setBuffer();
					}
				}
			}
		};
	}

	public void setBuffer(){
		if(line!=null){
			line=null;
		}
		setRunable();
		line=new Thread[animax_buffer];
		for(int i=0;i<animax_buffer;i++){
			line[i]=new Thread(run);
		}
	}
	public void startBuffer(){
		for(int i=0;i<animax_buffer;i++){
			if(!line[i].isAlive()){
				line[i].run();
				break;
			}
		}
	}
	public void setPosition(int x,int y){//設定位置
		this.x=x;
		this.y=y;
	}
	public void startByTime(int CurrentPosition,int duration){//啟動(有設定長度)
		this.duration=duration;
		this.count_unit=(animaxLength*1.0)/(this.duration*1.0);
		this.speed=0;
		this.start_position=CurrentPosition;
		count=0;
		this.counter=0;
		counter_ret=0;
		this.last_draw=0;
		animax_flag=true;
	}
	public void startBySpeed(double speed){//啟動(無設定長度)
		this.speed=speed;
		this.duration=0;
		count=0;
		counter=0;
		counter_ret=0;
		this.last_draw=0;
		animax_flag=true;	
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
		return (int)counter;
	}
	public Boolean getFlag(){
		return animax_flag;
	}
	public void drawAnimax(int CurrentPosition,Canvas canvas,Paint paint){
		if(animax_flag){
			if(speed==0){
				counter=count_unit*(CurrentPosition-start_position)-counter_ret;

			}else if(duration==0){
				if(!pause_flag){
					counter+=speed;
				}
			}
			if(((int)counter)<animaxLength){
				int t=((int)counter)-last_draw;
				if(t==1){
					last_draw=((int)counter);
					startBuffer();
				}else if(t>1){
					counter_ret+=1;
					counter--;
				}
				try{
					Graphic.drawPic(canvas, bit[((int)counter)], x, y, 0, 255, paint);
					Log.v("drawAnimax",""+((int)counter));
				}catch(NullPointerException e){
					Graphic.drawPic(canvas, bit[((int)counter)-1], x, y, 0, 255, paint);
					/*animax_flag=false;
					Log.e("bigAnimax","Not enough buffer");
					animax_buffer++;
					activity.writeData();
					setBuffer();*/
				}
			}else{
				animax_flag=false;
				resetBit();
			}
		}
	}
	public void recycle(){
		for(int i=0;i<bit.length;i++){
			if(bit[i]!=null){
				bit[i].recycle();
			}
		}		
	}
}
