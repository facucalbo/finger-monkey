package com.fingermonkeyserver.elementos;

import com.badlogic.gdx.graphics.Color;
import com.fingermonkeyserver.utilidades.Utiles;

public class Letra {

	private char letra;
	private Texto letraTxt;
	
	public Letra(int size) {
		letraTxt =  new Texto("fuentes/Jungle Roar Bold.ttf", size, Color.WHITE);
		asignarLetra();
		letraTxt.setTxt(String.valueOf(letra));
	}
	
	public void dibujar(float x, float y) {
		letraTxt.setPosicion(x, y);
		letraTxt.dibujar();
	}
	
	public void setY(int y) {
		letraTxt.setY(y);
	}
	
	public void setX(int x) {
		letraTxt.setX(x);
	}
	
	public void setPosicion(int x, int y) {
		letraTxt.setX(x);
		letraTxt.setY(y);
	}
	
	public void asignarLetra() {
		letra = (char) (Utiles.r.nextInt(26)+65);
		letraTxt.setTxt(String.valueOf(letra));
	}
	
	public Texto getLetraTxt() {
		return letraTxt;
	}
	
	public char getLetra() {
		return letra;
	}
}
