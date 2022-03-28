package com.fingermonkey.inouts;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class Entradas extends InputAdapter{

	private boolean abajo = false, arriba = false, left = false, right = false,enter = false;
	private int keycode;
//			q = false, w = false,e = false, r = false,t = false,
//			y = false, u = false,i = false, o = false,p = false,
//			a = false, s = false,d = false, f = false,g = false,
//			h = false, j = false,k = false, l = false,z = false,
//			x = false, c = false,v = false, b = false,n = false,
//			m = false;
	
	public int getKeycode() {
		return keycode;		
	}
	
	public boolean isAbajo() {
		return abajo;
	}

	public boolean isArriba() {
		return arriba;
	}
	
	public boolean isEnter() {
		return enter;
	}
	
	public boolean isRight() {
		return right;
	}
	
	public boolean isLeft() {
		return left;
	}
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.DOWN) {
			abajo = true;
		}else if(keycode == Keys.UP) {
			arriba = true;
		}else if(keycode == Keys.ENTER) {
			enter = true;
		}else if(keycode == Keys.RIGHT) {
			right = true;
		}else if(keycode == Keys.LEFT) {
			left = true;
		}
		this.keycode = keycode;
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		this.keycode = 0;
		
		if(keycode == Keys.DOWN) {
			abajo = false;
		}
		if(keycode == Keys.UP) {
			arriba = false;
		}else if(keycode == Keys.ENTER) {
			enter = false;
		}else if(keycode == Keys.RIGHT) {
			right = false;
		}else if(keycode == Keys.LEFT) {
			left = false;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}



}
