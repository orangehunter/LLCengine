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

	public void changeView(int what)//����Serface�T���o�e
	{
		Message msg = myHandler.obtainMessage(what); 
		myHandler.sendMessage(msg);
		nowView =what;
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
		changeView(0);//�i�J"0�ɭ�"
	}
	
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent e)//���䰻��
	{
		if(keyCode==4)//��^��
		{
			switch(nowView)//�����ثe����
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
			//���o�ѪR��
			getWindowManager().getDefaultDisplay().getMetrics(dm);
		}
		//���`�����O�����ù����M�e������
		if(dm.widthPixels>dm.heightPixels)
		{
			Constant.SYSTEM_WIDTH=dm.widthPixels;
			Constant.SYSTEM_HIGHT=dm.heightPixels;
		}else
		{
			Constant.SYSTEM_HIGHT=dm.widthPixels;
			Constant.SYSTEM_WIDTH=dm.heightPixels;
		}
		if(Constant.SYSTEM_HIGHT>Constant.SYSTEM_WIDTH/16*9) {//�N�ù��T�w��16:9
			Constant.SCREEN_HIGHT = Constant.SYSTEM_WIDTH / 16 * 9;//Y�y�Юե�
			Constant.SCREEN_WIDTH=Constant.SYSTEM_WIDTH;
		}
		else{
			Constant.SCREEN_WIDTH = Constant.SYSTEM_HIGHT / 9 * 16;//X�y�Юե�
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
