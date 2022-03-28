package com.fingermonkey.elementos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.fingermonkey.utilidades.Recursos;
import com.fingermonkey.utilidades.Render;

public class Indicador {

	private Texture indicador;
	private TextureRegion indicadorRegion;
	private TextureRegion[] indicadorFrame;
	
	public Indicador() {
		indicador = new Texture(Recursos.INDICADOR);
		indicadorRegion = new TextureRegion(indicador);
		TextureRegion[][] temp = indicadorRegion.split(indicador.getWidth()/2, indicador.getHeight()/2);
		indicadorFrame = new TextureRegion[(temp.length * temp[0].length)];
		int indice = 0;
		
		for (int i = 0; i < temp[0].length; i++) {
			for (int j = 0; j < temp.length; j++) {
				indicadorFrame[indice++] = temp[i][j];
			}
		}
	}
	
	public void dibujar(int indice, int posX,int posY) {
		Render.b.draw(indicadorFrame[indice], posX, posY, 
				indicadorFrame[indice].getRegionWidth()/1.7f, indicadorFrame[indice].getRegionHeight()/1.7f);
	}
	
	public float getAlto() {
		return (indicadorFrame[0].getRegionWidth()/1.7f);
	}
}
