package com.llcengine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class MainActivity extends Activity{
	int nowView =0;
	MainView mainview;
	Aview aview;

	Intent intent;
	Intent deintent;

	public void changeView(int what)//切換Serface訊息發送
	{
		Message msg = myHandler.obtainMessage(what); 
		myHandler.sendMessage(msg);
		nowView =what;
	} 
	
	Handler myHandler = new Handler(){//接收各個SurfaceView傳送的訊息
		public void handleMessage(Message msg) {
			switch(msg.what)
			{
			case 0:
				goToMainView();
				break;
			case 1:
				goToAview();
				break;
		
			}
		}
	};
	
	private void goToMainView() {
		if(mainview==null)
		{
			mainview=new MainView(this);
		}
		setContentView(mainview);
		mainview.requestFocus();//取得焦點
		mainview.setFocusableInTouchMode(true);//設為可觸控
	}
	private void goToAview() {
		if(aview==null)
		{
			aview=new Aview(this);
		}
		setContentView(aview);
		aview.requestFocus();//取得焦點
		aview.setFocusableInTouchMode(true);//設為可觸控
	}

	
	public void callToast(int what)//Toast訊息傳送
	{
		Message msg = toastHandler.obtainMessage(what); 
		toastHandler.sendMessage(msg);
	} 
	Handler toastHandler = new Handler(){//處理各個SurfaceView傳送的Toast訊息
		public void handleMessage(Message msg) {
			switch(msg.what)//
			{
			case 0:
				createToast("");
				break;
			case 1:
				createToast("");
				break;
		
			}
		}
	};
	public void createToast(String msg){//顯示Toast
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//游戲過程中只容許調整多媒體音量，而不容許調整通話音量
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉標題
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉標頭
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//強制橫屏
		//this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//強制直屏
		changeView(0);//進入"0界面"
	}
	
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent e)//按鍵偵測
	{
		if(keyCode==4)//返回建
		{
			switch(nowView)//偵測目前介面
			{
			case 1:
				Constant.Flag=false;
				this.changeView(0);//回到0界面
				break;
			case 0:
				System.exit(0);//離開游戲
				break;

			}
			return true;
		}
		/*if(keyCode==e.KEYCODE_HOME){//HOME鍵
			 System.exit(0);
			return true;
		}*/
		return false;

	}



	@Override
	public void onResume(){
		Constant.setFlag(true);
		changeView(nowView);
		DisplayMetrics dm=new DisplayMetrics();
		if(Build.VERSION.SDK_INT>=19){
			getWindow().getDecorView().setSystemUiVisibility(
					this.getWindow().getDecorView().SYSTEM_UI_FLAG_LAYOUT_STABLE
							| this.getWindow().getDecorView().SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| this.getWindow().getDecorView().SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| this.getWindow().getDecorView().SYSTEM_UI_FLAG_HIDE_NAVIGATION
							| this.getWindow().getDecorView().SYSTEM_UI_FLAG_FULLSCREEN
							| this.getWindow().getDecorView().SYSTEM_UI_FLAG_IMMERSIVE_STICKY
							| this.getWindow().getDecorView().INVISIBLE);
			getWindowManager().getDefaultDisplay().getRealMetrics(dm);
		}else{
			//取得解析度
			getWindowManager().getDefaultDisplay().getMetrics(dm);
		}
		//給常數類別中的螢幕高和寬給予值
		if(dm.widthPixels>dm.heightPixels)
		{
			Constant.SYSTEM_WIDTH=dm.widthPixels;
			Constant.SYSTEM_HIGHT=dm.heightPixels;
		}else
		{
			Constant.SYSTEM_HIGHT=dm.widthPixels;
			Constant.SYSTEM_WIDTH=dm.heightPixels;
		}
		if(Constant.SYSTEM_HIGHT>Constant.SYSTEM_WIDTH/16*9) {//將螢幕固定為16:9
			Constant.SCREEN_HIGHT = Constant.SYSTEM_WIDTH / 16 * 9;//Y座標校正
			Constant.SCREEN_WIDTH=Constant.SYSTEM_WIDTH;
		}
		else{
			Constant.SCREEN_WIDTH = Constant.SYSTEM_HIGHT / 9 * 16;//X座標校正
			Constant.SCREEN_HIGHT=Constant.SYSTEM_HIGHT;
		}

		Constant.SCREEN_WIDTH_UNIT = ((float)Constant.SCREEN_WIDTH/Constant.DEFULT_WIDTH);
		Constant.SCREEN_HEIGHT_UNIT= ((float)Constant.SCREEN_HIGHT/Constant.DEFULT_HIGHT);
		super.onResume();
	}
	@Override
	public void onPause(){
		Constant.setFlag(false);
		super.onPause();
	}
}
