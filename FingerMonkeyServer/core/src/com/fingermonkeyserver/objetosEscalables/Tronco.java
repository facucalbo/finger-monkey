package com.fingermonkeyserver.objetosEscalables;

public class Tronco extends ClimbingWall {

	public Tronco(String rutaPared, int ancho, int alto, float indiceMovimiento) {
		super(rutaPared, ancho, alto, indiceMovimiento);
	}
 
//	public void moverPared() {
//		pared.s.setY(pared.s.getY() - (indiceMovimiento));//se mueve la distancia exacta hacia abajo entre letra y letra
//		
//		if(pared.s.getY() < pared2.s.getY()) {
//			pared2.s.setY(pared.s.getY() + alto);
//		}else {
//			pared2.s.setY(pared.s.getY() - alto);
//		}
//		if(pared.s.getY() < -alto) {
//			pared.s.setY(pared2.s.getY() + alto);
//		}
//		if(pared2.s.getY() < -alto) {
//			pared2.s.setY(pared.s.getY() + alto);
//		}
//	}
}
