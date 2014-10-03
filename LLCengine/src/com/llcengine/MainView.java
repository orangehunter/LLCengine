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
	Bitmap how,how2,how3,back;
	int rot=0;
	int al=0;
	Bottom test;
	//========================================
	int pointx;//Ĳ����ù���x�y��
	int pointy;//Ĳ����ù���y�y��
	Paint paint;			//�e�����Ѧ�
	MainActivity activity;

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
		how2=Graphic.bitSize(LoadBitmap(R.drawable.ic_launcher),195, 195);
		how3=Graphic.bitSize(LoadBitmap(R.drawable.ic_launcher),10, 10);
		//back=Graphic.bitSize(LoadBitmap(R.drawable.back), 1280, 720);
		test=new Bottom(activity, how, how2, 200, 400);
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
			//Graphic.drawPic(canvas, back, 1280/2, 720/2, 0, 255, paint);
			Graphic.drawPic(canvas, how, 200, 100, 0, 255, paint);//ø�Ͻd��_�L�S��
			
			Graphic.drawPic(canvas, how, 200, 200, 0, al, paint);////ø�Ͻd��_�z����
			al+=5;
			if(al>=255)
				al=0;
			
			Graphic.drawPic(canvas, how, 400, 300, rot, 255, paint);//ø�Ͻd��_����
			rot+=5;
			if(rot%360==0&&rot!=0)
				rot=0;
			test.drawBtm(canvas, paint);
			//===============================================================================
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event){//Ĳ���ƥ�
		pointx=(int) event.getX();
		pointy=(int) event.getY();
		
			switch(event.getAction())
			{
			case MotionEvent.ACTION_DOWN://���U
				if(test.isIn(pointx, pointy)){
					test.setBottomTo(false);
				}
				break;
			case MotionEvent.ACTION_UP://��_
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

	public void surfaceDestroyed(SurfaceHolder arg0) {//�P���ɳQ�I�s
		Constant.Flag=false;
	}


}