package com.fingermonkeyserver.utilidades;

import java.util.Random;

public class Utiles {

	public static Random r = new Random();
	
	public static void esperar(int milis) {
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
