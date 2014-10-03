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
	//===============�ŧi======================
	Bitmap how,r,rb,s,sb,t,tb,x,xb;
	int rot=0;
	int al=0;
	Bottom r_btn,s_btn,t_btn,x_btn;
	//========================================
	int pointx;//Ĳ����ù���x�y��
	int pointy;//Ĳ����ù���y�y��
	Paint paint;			//�e�����Ѧ�
	MainActivity activity;
	boolean deTouchJump=true;
	int pointerCount=0;

	public MainView(MainActivity mainActivity) {
		super(mainActivity);
		this.activity = mainActivity;
		this.getHolder().addCallback(this);//�]�w�ͩR�P���^�ձ��f����{��


	}
	public Bitmap LoadBitmap(int r){
		return BitmapFactory.decodeResource(getResources(), r);
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		paint = new Paint();//�إߵe��
		paint.setAntiAlias(true);//�}�ҧܿ���
		//=============�Ϥ����J==================
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
		//=============�ù���s=================================================
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
					Canvas canvas = myholder.lockCanvas();//���o�e��
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
	protected void onDraw(Canvas canvas) {//���s�w�q��ø���k
		if(canvas!=null){
			super.onDraw(canvas);
			canvas.clipRect(new Rect(0,0,Constant.SCREEN_WIDTH,Constant.SCREEN_HIGHT));//�u�b�ù��d��ø��Ϥ�
			canvas.drawColor(Color.WHITE);//�ɭ��]�w���զ�
			paint.setAntiAlias(true);	//�}�ҧܿ���
			//================================�e��ø�s========================================
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
	public boolean onTouchEvent(MotionEvent event){//Ĳ���ƥ�
		pointerCount = event.getPointerCount();
		pointx=(int) event.getX(0);
		pointy=(int) event.getY(0);

		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN://���U
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
		case MotionEvent.ACTION_UP://��_
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

	public void surfaceDestroyed(SurfaceHolder arg0) {//�P���ɳQ�I�s
		Constant.Flag=false;
	}


}