package com.example.game;

import java.util.List;

import android.graphics.Bitmap;

public class TempSprite {
	
	float x;
	float y;
	private Bitmap bit;
	private int life= 3;
	List<TempSprite> temp;
	
	public TempSprite(List<TempSprite> temp,GameView view,float x,float y,Bitmap bit) {
		this.x = Math.min(Math.max(x-bit.getWidth()/2,0), view.getWidth() - bit.getWidth());
		
	}

}
