package com.fingermonkey.utilidades;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.fingermonkey.elementos.Texto;
import com.fingermonkey.inouts.Entradas;

public class Utiles {

	public static Random r = new Random();
	
	private static int opc = 0, cont = 15;

	public static int seleccionarOpcion(Entradas entrada, Texto[] opciones, Color color) {
		if (entrada.isRight() || entrada.isAbajo()) {

			if (cont % 15 == 0) {
				opc++;
			}
			for (int i = 0; i < opciones.length; i++) {
				if (i != opc) {
					opciones[i].setColor(color);
				} else {
					opciones[i].setColor(Color.WHITE);
				}
			}

			if (opc > opciones.length - 1)
				opc = 0;

			cont++;

		} else if (entrada.isLeft() || entrada.isArriba()) {
			if (cont % 15 == 0) {
				opc--;
			}
			for (int i = 0; i < opciones.length; i++) {
				if (i != opc) {
					opciones[i].setColor(color);
				}
			}

			if (opc < 0)
				opc = opciones.length - 1;

			opciones[opc].setColor(Color.WHITE);
			cont++;
		}

		if ((!entrada.isAbajo() && !entrada.isArriba()) && (!entrada.isRight() && !entrada.isLeft())) {
			cont = 15;
		}
		return opc;
	}

}
