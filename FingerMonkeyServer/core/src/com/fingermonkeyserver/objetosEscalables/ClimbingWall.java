package com.fingermonkeyserver.objetosEscalables;

import com.fingermonkeyserver.elementos.Imagen;
import com.fingermonkeyserver.utilidades.Config;
import com.fingermonkeyserver.utilidades.Render;

public abstract class ClimbingWall {

	protected int ancho, alto, indiceMovimiento;
	protected Imagen pared,pared2;
	
	public ClimbingWall(String rutaPared, int ancho, int alto, float indiceMovimiento) {//indiceMovimiento = distancia entre letras
		this.indiceMovimiento = (int) indiceMovimiento;
		this.ancho = ancho;
		this.alto = alto;
		pared = new Imagen(rutaPared);
		pared2 = new Imagen(rutaPared);
		pared2.s.setY(alto);
	}
	
//	public abstract void moverPared();
	
	public void moverPared() {
		pared.s.setY(pared.s.getY() - (indiceMovimiento));//se mueve la distancia exacta hacia abajo entre letra y letra
		
		if(pared.s.getY() < pared2.s.getY()) {
			pared2.s.setY(pared.s.getY() + alto);
		}else {
			pared2.s.setY(pared.s.getY() - alto);
		}
		if(pared.s.getY() < -alto) {
			pared.s.setY(pared2.s.getY() + alto);
		}
		if(pared2.s.getY() < -alto) {
			pared2.s.setY(pared.s.getY() + alto);
		}
	}
	
	public void dibujar() {
		Render.b.draw(pared.t, (Config.ANCHO/4), pared.s.getY(), ancho, alto);//se debe manipular el ancho y el alto de la imagen
		Render.b.draw(pared2.t, (Config.ANCHO/4), pared2.s.getY(), ancho, alto);
	}
	
	public int getAlto() {
		return alto;
	}
	
	public int getAncho() {
		return ancho;
	}
	
	public Imagen getPared() {
		return pared;
	}
	
	public Imagen getPared2() {
		return pared2;
	}
	
}
