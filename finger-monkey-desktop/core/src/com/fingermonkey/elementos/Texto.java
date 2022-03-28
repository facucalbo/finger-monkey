package com.fingermonkey.elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.fingermonkey.utilidades.Render;

public class Texto {

	private BitmapFont fuente;
	private float x, y;
	private String txt;
	private GlyphLayout layout;
	
	public Texto(String rutaFuente, int dimension, Color color) {
		FreeTypeFontGenerator generador = new FreeTypeFontGenerator(Gdx.files.internal(rutaFuente));
		FreeTypeFontParameter parametros = new FreeTypeFontParameter();
		parametros.size = dimension;
		parametros.color = color;
		
			parametros.shadowColor = Color.BLACK;
			parametros.shadowOffsetX = 1;
			parametros.shadowOffsetY = 1;
		
		fuente = generador.generateFont(parametros);
		layout = new GlyphLayout();
		fuente.setColor(color);
	}
	
	public Texto(String rutaFuente, int dimension, Color color, Color sombra, float sombraX, float sombraY) {
		FreeTypeFontGenerator generador = new FreeTypeFontGenerator(Gdx.files.internal(rutaFuente));
		FreeTypeFontParameter parametros = new FreeTypeFontParameter();
		parametros.size = dimension;
		parametros.color = color;

			parametros.shadowColor = Color.BLACK;
			parametros.shadowOffsetX = 1;
			parametros.shadowOffsetY = 1;

		fuente = generador.generateFont(parametros);
	}
	
	public void dibujar() {
		fuente.draw(Render.b, txt, x, y);
	}
	
	public void setColor(Color color) {
		fuente.setColor(color);
	}
	
	public void setColor(float r, float g, float b, float a) {
		fuente.setColor(r, g, b, a);
	}
	
	public void setPosicion(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
		layout.setText(fuente, txt);
	}
	
	public float getAncho() {
		return layout.width;
	}
	
	public float getAlto() {
		return layout.height;
	}
	
	public Vector2 getDimension() {
		return new Vector2(layout.width,layout.height);
	}
	
	public Vector2 getPosicion() {
		return new Vector2(x,y);
	}
	
}
