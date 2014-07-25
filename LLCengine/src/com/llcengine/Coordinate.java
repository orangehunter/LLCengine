package com.llcengine;

public class Coordinate {
	
	public static float CoordinateX(int x){
		return  (x*Constant.GAME_WIDTH_UNIT);
	}

	public static float CoordinateY(int y){
		return  (y*Constant.SCREEN_HEIGHT_UNIT);
	}
	public static int AnalogSpeedMove(int now,int tomove){
		if(now>tomove){
			if(now-tomove>20)
				now-=(now-tomove)/10;
			if(now-tomove<=20)
				now-=1;
		}
		if(tomove>now){
			if(tomove-now>20)
				now+=(tomove-now)/10;
			if(tomove-now<=20)
				now+=1;
		}
		return now;
	}
}
