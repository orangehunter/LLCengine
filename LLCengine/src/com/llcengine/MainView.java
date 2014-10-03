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
	Bitmap how,r,rb,s,sb,t,tb,x,xb;
	int rot=0;
	int al=0;
	Bottom r_btn,s_btn,t_btn,x_btn;
	//========================================
	int pointx;//觸控到螢幕的x座標
	int pointy;//觸控到螢幕的y座標
	Paint paint;			//畫筆的參考
	MainActivity activity;
	boolean deTouchJump=true;
	int pointerCount=0;

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
		int bottomSize=180;
		int btm_first=130,btm_dis=270;
		r=Graphic.bitSize(LoadBitmap(R.drawable.btn_circle), bottomSize, bottomSize);
		s=Graphic.bitSize(LoadBitmap(R.drawable.btn_square), bottomSize, bottomSize);
		t=Graphic.bitSize(LoadBitmap(R.drawable.btn_triangle), bottomSize, bottomSize);
		x=Graphic.bitSize(LoadBitmap(R.drawable.btn_x), bottomSize, bottomSize);
		rb=Graphic.bitSize(LoadBitmap(R.drawable.grey_circle), bottomSize, bottomSize);
		sb=Graphic.bitSize(LoadBitmap(R.drawable.grey_square), bottomSize, bottomSize);
		tb=Graphic.bitSize(LoadBitmap(R.drawable.grey_tirangle), bottomSize, bottomSize);
		xb=Graphic.bitSize(LoadBitmap(R.drawable.grey_x), bottomSize, bottomSize);
		r_btn=new Bottom(activity,rb,r,btm_first,640);
		s_btn=new Bottom(activity,sb,s,btm_first+btm_dis,640);
		t_btn=new Bottom(activity,tb,t,btm_first+btm_dis+btm_dis,640);
		x_btn=new Bottom(activity,xb,x,btm_first+btm_dis+btm_dis+btm_dis,640);

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
			r_btn.drawBtm(canvas, paint);
			s_btn.drawBtm(canvas, paint);
			t_btn.drawBtm(canvas, paint);
			x_btn.drawBtm(canvas, paint);
			
			canvas.drawText(String.valueOf(pointerCount), 100, 100, paint);

			int btm_first=130,btm_dis=270;
			if(r_btn.getBottom()){
				Graphic.drawPic(canvas, r, btm_first, 400, 0, 255, paint);
			}
			if(s_btn.getBottom()){
				Graphic.drawPic(canvas, s, btm_first+btm_dis, 400, 0, 255, paint);
			}
			if(t_btn.getBottom()){
				Graphic.drawPic(canvas, t, btm_first+btm_dis*2, 400, 0, 255, paint);
			}
			if(x_btn.getBottom()){
				Graphic.drawPic(canvas, x, btm_first+btm_dis*3, 400, 0, 255, paint);
			}
			//===============================================================================
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event){//觸控事件
		pointerCount = event.getPointerCount();
		pointx=(int) event.getX(0);
		pointy=(int) event.getY(0);

		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN://按下
			if(deTouchJump==true){
				if(r_btn.isIn(pointx, pointy)){
					r_btn.setBottomTo(true);
				}
				if(s_btn.isIn(pointx, pointy)){
					s_btn.setBottomTo(true);
				}
				if(t_btn.isIn(pointx, pointy)){
					t_btn.setBottomTo(true);
				}
				if(x_btn.isIn(pointx, pointy)){
					x_btn.setBottomTo(true);
				}
				deTouchJump=false;
			}
			break;
		case MotionEvent.ACTION_UP://抬起
			if(deTouchJump==false){
				if(r_btn.getBottom()){
					r_btn.setBottomTo(false);
				}
				if(s_btn.getBottom()){
					s_btn.setBottomTo(false);
				}
				if(t_btn.getBottom()){
					t_btn.setBottomTo(false);
				}
				if(x_btn.getBottom()){
					x_btn.setBottomTo(false);
				}
				deTouchJump=true;
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