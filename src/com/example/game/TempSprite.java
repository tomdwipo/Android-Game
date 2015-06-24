package com.example.game;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class TempSprite {
	
	float x;
	float y;
	private Bitmap bit;
	private int life= 3;
	List<TempSprite> temp;
	
	public TempSprite(List<TempSprite> temp,GameView view,float x,float y,Bitmap bit) {
		this.x = Math.min(Math.max(x-bit.getWidth()/2,0), view.getWidth() - bit.getWidth());
		this.y = Math.min(Math.max(y-bit.getHeight()/2,0), view.getHeight() - bit.getHeight());
		this.bit = bit;
		this.temp =temp;
		
	}
	
	public void onDraw(Canvas canvas){
		update();
		canvas.drawBitmap(bit, x, y,null);
	}
	public void update(){
		if(--life < 1){
			temp.remove(life);
		}
	}

}
