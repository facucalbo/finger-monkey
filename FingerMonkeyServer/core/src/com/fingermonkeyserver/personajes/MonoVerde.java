package com.fingermonkeyserver.personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.fingermonkeyserver.utilidades.Recursos;

public class MonoVerde extends Personaje{
	protected Texture mono;
	protected TextureRegion monoRegion;
	protected String imgMono = Recursos.MONOVERDE;
	protected int ancho = 180, alto = 42, cantMonos = 5;
	protected TextureRegion[] frameMono;
	
	public MonoVerde() {
		super.personaje = mono;
		super.personajeRegion = monoRegion;
		super.imgPersonaje = imgMono;
		super.ancho = ancho;
		super.alto = alto;
		super.cantPersonajes = cantMonos;
		super.framePersonaje = frameMono;
	}
}
