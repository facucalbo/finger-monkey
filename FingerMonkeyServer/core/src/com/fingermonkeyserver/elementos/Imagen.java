package com.fingermonkeyserver.elementos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.fingermonkeyserver.utilidades.Render;

public class Imagen {

	public Texture t;
	public Sprite s; 
	
	public Imagen(String ruta) {
		t = new Texture(ruta);
		s = new Sprite(t);
	}
	
	public Imagen(String ruta, int ancho, int alto) {
		t = new Texture(ruta);
		s = new Sprite(t);
	}
	
	public void dibujar() {
		s.draw(Render.b);
	}

}
