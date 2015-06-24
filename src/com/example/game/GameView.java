package com.example.game;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
@SuppressLint("WrongCall")
public class GameView extends SurfaceView {

	private SurfaceHolder holder;
	private GameThread gameLoop;
	private Sprite sp;
	private Bitmap tempBit;
	long lastClick;
	ArrayList<Sprite> sprite = new ArrayList<Sprite>();
	ArrayList<TempSprite> temp = new ArrayList<TempSprite>();
	
	public GameView(Context context) {
		super(context);
		holder = getHolder();
		holder.addCallback(new SurfaceHolder.Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
			
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				create();
				new Down().execute();
				
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				
			}
		});
		tempBit = BitmapFactory.decodeResource(getResources(),R.drawable.blood);
		
	}
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		for(int i =temp.size()-1 ; i >=0 ;i--){
			temp.get(i).onDraw(canvas);
		}
		for(Sprite sprites : sprite){
			sprites.onDraw(canvas);
			
		}
		
		
	}
	
	public void create(){
		sprite.add(createSp(R.drawable.image1));
		sprite.add(createSp(R.drawable.image2));
		sprite.add(createSp(R.drawable.image3));
		sprite.add(createSp(R.drawable.image4));
	}
	
	public Sprite createSp(int resource){
		Bitmap bit = BitmapFactory.decodeResource(getResources(),resource);
		sp = new Sprite(this, bit);
		
		return sp;
	}
	
	public class Down extends AsyncTask<Void, Void, Void>{
		
		@Override
		protected Void doInBackground(Void... params) {
			gameLoop = new GameThread(GameView.this);

				gameLoop.start();
		
			return null;
		}
		
	}
	
	public class GameThread extends Thread{

		private GameView view;
		static final long FPS = 10;
		Canvas c;
		
		public GameThread(GameView view) {
			this.view = view;
		}
		
		@SuppressLint("WrongCall")
		@Override
		public void run() {
			long tickps = 100 /FPS;
			long startTime;
			long sleepTime;
			while(true){
				
				startTime = System.currentTimeMillis();
				try {
					c = view.getHolder().lockCanvas();
					synchronized (view.getHolder()) {
						view.onDraw(c);
					}
				} finally{
					if(c!=null){
						view.getHolder().unlockCanvasAndPost(c);
					}	
				}
			/*	sleepTime = tickps - (System.currentTimeMillis()-startTime);
				try {
					if(sleepTime>0){
						sleep(sleepTime);
					}else{
						sleep(10);
					}
				} catch (Exception e) {
				}*/
				
				try {
					sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
	
		if(System.currentTimeMillis() - lastClick > 500){
			lastClick = System.currentTimeMillis();
			float x = event.getX();
			float y = event.getY();
			synchronized (getHolder()) {
				for(int i =sprite.size()-1 ;i >=0;i--){
					Sprite sprites = sprite.get(i);
					if(sprites.isCollection(x,y)){
						sprite.remove(sprites);
						temp.add(new TempSprite(temp, this, x, y, tempBit));
						break;
					}
					
					
				}
			}
		}
		
		return true;
	}
}
