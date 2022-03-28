package com.fingermonkey.personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.fingermonkey.utilidades.Render;

public abstract class Personaje {

	protected Texture personaje;
	protected TextureRegion personajeRegion;
	protected String imgPersonaje;
	protected int ancho, alto, cantPersonajes;
	protected TextureRegion[] framePersonaje;
	private int posX, posY, anchoPj,altoPj, indice;
	protected int mano = 0;
	
	public void crearPersonaje() {
		personaje = new Texture(imgPersonaje);
		personajeRegion = new TextureRegion(personaje,ancho,alto);
		TextureRegion[][] pjIndividual = personajeRegion.split(ancho/cantPersonajes, alto);//la imagen de los personajes dividida ordenada en un array
		framePersonaje = new TextureRegion[pjIndividual.length * pjIndividual[0].length];
		int indice = 0;
		for (int i = 0; i < pjIndividual.length; i++) {
			for (int j = 0; j < pjIndividual[0].length; j++) {
				framePersonaje[indice++] = pjIndividual[i][j];
			}
		}
	}
	
	public void dibujarPersonaje() {
		Render.b.draw(framePersonaje[indice], posX, posY, anchoPj, altoPj);
	}
	
	public TextureRegion[] getFramePersonaje() {
		return framePersonaje;
	}
	
	public TextureRegion getPersonaje(int indice) {
		return framePersonaje[indice];
	}
	
	public int getMano() {
		return mano;
	}
	
	public void setMano(int mano) {
		this.mano = mano;
	}
	
	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public int getAnchoPj() {
		return framePersonaje[indice].getRegionWidth();
	}
	
	public int getAltoPj() {
		return framePersonaje[indice].getRegionHeight();
	}	
	
	public void setIndice(int indice) {
		this.indice = indice;
	}
	
	public void setPosX(int posX) {
		this.posX = posX;
	}
	
	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	public void setPosition(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	
	public void setAltoPj(int altoPj) {
		this.altoPj = altoPj;
	}
	
	public void setAnchoPj(int anchoPj) {
		this.anchoPj = anchoPj;
	}
}
