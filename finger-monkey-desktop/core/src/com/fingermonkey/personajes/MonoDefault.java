package com.fingermonkey.personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.fingermonkey.utilidades.Recursos;

public class MonoDefault extends Personaje{

	protected Texture mono;
	protected TextureRegion monoRegion;
	protected String imgMono = Recursos.FOTOMONO;
	protected int ancho = 180, alto = 42, cantMonos = 5;
	protected TextureRegion[] frameMono;
	
	public MonoDefault() {
		super.personaje = mono;
		super.personajeRegion = monoRegion;
		super.imgPersonaje = imgMono;
		super.ancho = ancho;
		super.alto = alto;
		super.cantPersonajes = cantMonos;
		super.framePersonaje = frameMono;
	}
}
