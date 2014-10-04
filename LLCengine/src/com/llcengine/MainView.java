package com.llcengine;




import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.SparseArray;
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
	SparseArray<PointF> mActivePointers=new SparseArray<PointF>();
	SparseArray<Integer> btn_pointer=new SparseArray<Integer>();
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
			r_btn.setBottomTo(false);
			s_btn.setBottomTo(false);
			t_btn.setBottomTo(false);
			x_btn.setBottomTo(false);
			for(int i=0;i<btn_pointer.size();i++){
				int st=btn_pointer.valueAt(i);
				switch(st){
				case 0:
					r_btn.setBottomTo(true);
					break;
				case 1:
					s_btn.setBottomTo(true);
					break;
				case 2:
					t_btn.setBottomTo(true);
					break;
				case 3:
					x_btn.setBottomTo(true);
					break;
				}
			}
			String tx="",ty="";
			for(int i=0;i<mActivePointers.size();i++){
				PointF point = mActivePointers.valueAt(i);
				tx+=String.valueOf(point.x)+",";
				ty+=String.valueOf(point.y)+",";
			}
			canvas.drawText(tx, 100, 200, paint);
			canvas.drawText(ty, 100, 300, paint);
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event){//觸控事件
		pointerCount = event.getPointerCount();

		// get pointer index from the event object
		int pointerIndex = event.getActionIndex();

		// get pointer ID
		int pointerId = event.getPointerId(pointerIndex);

		switch(event.getActionMasked())
		{
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN://按下
			PointF f = new PointF();
			f.x = event.getX(pointerIndex);
			f.y = event.getY(pointerIndex);
			mActivePointers.put(pointerId, f);
			if(r_btn.isIn(f.x, f.y)){
				btn_pointer.put(pointerId, 0);
			}
			if(s_btn.isIn(f.x, f.y)){
				btn_pointer.put(pointerId,1);
			}
			if(t_btn.isIn(f.x, f.y)){
				btn_pointer.put(pointerId, 2);
			}
			if(x_btn.isIn(f.x, f.y)){
				btn_pointer.put(pointerId, 3);
			}
			break;
		case MotionEvent.ACTION_MOVE:  // a pointer was moved
			for (int size = event.getPointerCount(), i = 0; i < size; i++) {
				PointF point = mActivePointers.get(event.getPointerId(i));
				if (point != null) {
					point.x = event.getX(i);
					point.y = event.getY(i);
				}
			}
			break;

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
		case MotionEvent.ACTION_CANCEL: 
			mActivePointers.remove(pointerId);
			btn_pointer.remove(pointerId);
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