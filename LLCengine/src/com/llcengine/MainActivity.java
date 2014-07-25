package com.llcengine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
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
	int nowActivity=0;
	MainView mainview;
	Aview aview;

	Intent intent;
	Intent deintent;

	public void changeView(int what)//����Serface�T���o�e
	{
		Message msg = myHandler.obtainMessage(what); 
		myHandler.sendMessage(msg);
		nowActivity=what;
	} 
	
	Handler myHandler = new Handler(){//�����U��SurfaceView�ǰe���T��
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
		mainview.requestFocus();//���o�J�I
		mainview.setFocusableInTouchMode(true);//�]���iĲ��
	}
	private void goToAview() {
		if(aview==null)
		{
			aview=new Aview(this);
		}
		setContentView(aview);
		aview.requestFocus();//���o�J�I
		aview.setFocusableInTouchMode(true);//�]���iĲ��
	}

	
	public void callToast(int what)//Toast�T���ǰe
	{
		Message msg = toastHandler.obtainMessage(what); 
		toastHandler.sendMessage(msg);
	} 
	Handler toastHandler = new Handler(){//�B�z�U��SurfaceView�ǰe��Toast�T��
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
	public void createToast(String msg){//���Toast
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//�����L�{���u�e�\�վ�h�C�魵�q�A�Ӥ��e�\�վ�q�ܭ��q
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//�h�����D
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);//�h�����Y
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//�j����
		//this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//�j���

		//���o�ѪR��
		DisplayMetrics dm=new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		//���`�����O�����ù����M�e������
		//�ū�
		if(dm.widthPixels>dm.heightPixels)
		{
			Constant.SCREEN_WIDTH=dm.widthPixels;
			Constant.SCREEN_HIGHT=dm.heightPixels;
		}else
		{
			Constant.SCREEN_HIGHT=dm.widthPixels;
			Constant.SCREEN_WIDTH=dm.heightPixels;
		}
		if(Constant.SCREEN_HIGHT>Constant.SCREEN_WIDTH/16*9)//�N�ù��T�w��16:9
			Constant.SCREEN_HIGHT=Constant.SCREEN_WIDTH/16*9;
		else
			Constant.SCREEN_WIDTH=Constant.SCREEN_HIGHT/9*16;
		
		//����
		/*
		 if(dm.widthPixels<dm.heightPixels)
		{
			Constant.SCREEN_WIDTH=dm.widthPixels;
			Constant.SCREEN_HIGHT=dm.heightPixels;
		}else
		{
			Constant.SCREEN_HIGHT=dm.widthPixels;
			Constant.SCREEN_WIDTH=dm.heightPixels;
		}
		if(Constant.SCREEN_WIDTH>Constant.SCREEN_HIGHT/16*9)//�N�ù��T�w��16:9
			Constant.SCREEN_WIDTH=Constant.SCREEN_HIGHT/16*9;
		else
			Constant.SCREEN_HIGHT=Constant.SCREEN_WIDTH/9*16;
		*/
		

		Constant.GAME_WIDTH_UNIT= ((float)Constant.SCREEN_WIDTH/Constant.DEFULT_WITH);
		Constant.SCREEN_HEIGHT_UNIT= ((float)Constant.SCREEN_HIGHT/Constant.DEFULT_HIGHT);
		
		changeView(0);//�i�J"0�ɭ�"
	}
	
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent e)//���䰻��
	{
		if(keyCode==4)//��^��
		{
			switch(nowActivity)//�����ثe����
			{
			case 1:
				Constant.Flag=false;
				this.changeView(0);//�^��0�ɭ�
				break;
			case 0:
				System.exit(0);//���}����
				break;

			}
			return true;
		}
		/*if(keyCode==e.KEYCODE_HOME){//HOME��
			 System.exit(0);
			return true;
		}*/
		return false;

	}
	

	@Override 
	public void onResume(){
		Constant.setFlag(true);
		super.onResume();
	}
	@Override 
	public void onPause(){
		Constant.setFlag(false);
		super.onPause();		
	}
}
