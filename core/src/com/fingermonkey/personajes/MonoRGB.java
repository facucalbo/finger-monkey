package com.fingermonkey.personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.fingermonkey.utilidades.Recursos;

public class MonoRGB extends Personaje{
	
	protected Texture monoRGB;
	protected TextureRegion monoRGBRegion;
	protected String imgMonoRGB = Recursos.MONOSRGB;
	protected int ancho = 144, alto = 42, cantMonosRGB = 4;
	protected TextureRegion[] frameMonoRGB;
	
	public MonoRGB() {
		super.personaje = monoRGB;
		super.personajeRegion = monoRGBRegion;
		super.imgPersonaje = imgMonoRGB;
		super.ancho = ancho;
		super.alto = alto;
		super.cantPersonajes = cantMonosRGB;
		super.framePersonaje = frameMonoRGB;
	}
}
