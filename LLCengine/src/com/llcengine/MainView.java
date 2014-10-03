package com.llcengine;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint({ "ViewConstructor", "WrongCall", "ClickableViewAccessibility" })
public class MainView extends SurfaceView
implements SurfaceHolder.Callback{
	//===============宣告======================
	Bitmap how,how2,how3,back;
	int rot=0;
	int al=0;
	Bottom test;
	//========================================
	int pointx;//觸控到螢幕的x座標
	int pointy;//觸控到螢幕的y座標
	Paint paint;			//畫筆的參考
	MainActivity activity;

	public MainView(MainActivity mainActivity) {
		super(mainActivity);
		this.activity = mainActivity;
		this.getHolder().addCallback(this);//設定生命周期回調接口的實現者


	}
	public Bitmap LoadBitmap(int r){
		return BitmapFactory.decodeResource(getResources(), r);
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		paint = new Paint();//建立畫筆
		paint.setAntiAlias(true);//開啟抗鋸齒
		//=============圖片載入==================
		how=Graphic.bitSize(LoadBitmap(R.drawable.ic_launcher),200, 200);
		how2=Graphic.bitSize(LoadBitmap(R.drawable.ic_launcher),195, 195);
		how3=Graphic.bitSize(LoadBitmap(R.drawable.ic_launcher),10, 10);
		//back=Graphic.bitSize(LoadBitmap(R.drawable.back), 1280, 720);
		test=new Bottom(activity, how, how2, 200, 400);
		//=====================================
		Constant.Flag=true;
		//=============螢幕刷新=================================================
		new Thread(){
			@SuppressLint("WrongCall")
			public void run()
			{
				while(Constant.Flag){
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					SurfaceHolder myholder=MainView.this.getHolder();
					Canvas canvas = myholder.lockCanvas();//取得畫布
					onDraw(canvas);
					if(canvas != null){
						myholder.unlockCanvasAndPost(canvas);
					}
				}

			}
		}.start();
		//===========================================================================
	}
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {//重新定義的繪制方法
		if(canvas!=null){
			super.onDraw(canvas);
			canvas.clipRect(new Rect(0,0,Constant.SCREEN_WIDTH,Constant.SCREEN_HIGHT));//只在螢幕範圍內繪制圖片
			canvas.drawColor(Color.WHITE);//界面設定為白色
			paint.setAntiAlias(true);	//開啟抗鋸齒
			//================================畫面繪製========================================
			//Graphic.drawPic(canvas, back, 1280/2, 720/2, 0, 255, paint);
			Graphic.drawPic(canvas, how, 200, 100, 0, 255, paint);//繪圖範例_無特效
			
			Graphic.drawPic(canvas, how, 200, 200, 0, al, paint);////繪圖範例_透明度
			al+=5;
			if(al>=255)
				al=0;
			
			Graphic.drawPic(canvas, how, 400, 300, rot, 255, paint);//繪圖範例_旋轉
			rot+=5;
			if(rot%360==0&&rot!=0)
				rot=0;
			test.drawBtm(canvas, paint);
			//===============================================================================
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event){//觸控事件
		pointx=(int) event.getX();
		pointy=(int) event.getY();
		
			switch(event.getAction())
			{
			case MotionEvent.ACTION_DOWN://按下
				if(test.isIn(pointx, pointy)){
					test.setBottomTo(false);
				}
				break;
			case MotionEvent.ACTION_UP://抬起
			//activity.changeView(1);
				if(test.isIn(pointx, pointy)){
					test.setBottomTo(true);
				}
				break;
			}
		
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {

	}

	public void surfaceDestroyed(SurfaceHolder arg0) {//銷毀時被呼叫
		Constant.Flag=false;
	}


}