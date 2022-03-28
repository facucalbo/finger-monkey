package com.fingermonkey.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.fingermonkey.elementos.Imagen;
import com.fingermonkey.elementos.Texto;
import com.fingermonkey.inouts.Entradas;
import com.fingermonkey.pantallasJuegos.Multijugador;
import com.fingermonkey.pantallasJuegos.Nivel1;
import com.fingermonkey.screens.ScreenManager;
import com.fingermonkey.utilidades.Config;
import com.fingermonkey.utilidades.Recursos;
import com.fingermonkey.utilidades.Render;
import com.fingermonkey.utilidades.Utiles;

public class PantallaMenu implements Screen{

	private Imagen fondo;
	private String[] textos = {"Un Jugador", "Multijugador", "Personajes" ,"Opciones", "Salir"};
	private Texto[] opciones = new Texto[textos.length];
	private Entradas entradas = new Entradas();
	private int opc = 0, cont = 15;
	private Color colorFuente;
	
	@Override
	public void show() {
		fondo = new Imagen(Recursos.FONDOMENU);
		fondo.s.setBounds(0, 0, Config.ANCHO, Config.ALTO);
		fondo.s.setAlpha(1f);
		
		colorFuente = new Color(0.1f,0.87f,0.4f,0.72f);
		
		Gdx.input.setInputProcessor(entradas);
		
		float avance = 1.5f;
		
		for (int i = 0; i < opciones.length; i++) {
			opciones[i] = new Texto(Recursos.FUENTEMENU, 42, colorFuente);
			opciones[i].setTxt(textos[i]);
			opciones[i].setPosicion((Config.ANCHO / 2) - (opciones[i].getAncho() / 2), 
					(Config.ALTO / 1.8f) - (opciones[0].getAlto() / 2) - (opciones[i].getAlto()*(avance*i)));
		}
		opciones[0].setColor(Color.WHITE);
	}

	@Override
	public void render(float delta) {
		Render.b.begin();
		
		fondo.dibujar();

		for (int i = 0; i < opciones.length; i++) {
			opciones[i].dibujar();
		}
		
		Render.b.end();

		opc = Utiles.seleccionarOpcion(entradas,opciones,colorFuente);
		
		switch(opc) {
		case 0:
			if(entradas.isEnter()) {
				ScreenManager.app.setScreen(new Nivel1());
			}
			break;
		case 1:
			if(entradas.isEnter()) {
				ScreenManager.app.setScreen(new Multijugador());
			}
			break;
		case 2:
			if(entradas.isEnter()) ScreenManager.app.setScreen(new PantallaElegirPJ());
			break;
		case 3:
			break;
		case 4:
			break;
		}
		
	}

//	private void seleccionarOpcion() {
//		if (entradas.isAbajo()) {
//			
//			if (cont % 15 == 0) {
//				opc++;
//			}
//			for (int i = 0; i < opciones.length; i++) {
//				if (i != opc) {
//					opciones[i].setColor(colorFuente);
//				}else {
//					opciones[i].setColor(Color.WHITE);
//				}
//			}
//
//			if (opc > opciones.length-1) opc = 0;
//			
//			cont++;
//			
//		} else if (entradas.isArriba()) {
//			if (cont % 15 == 0) {
//				opc--;
//			}
//			for (int i = 0; i < opciones.length; i++) {
//				if (i != opc) {
//					opciones[i].setColor(colorFuente);
//				}
//			}
//
//			if (opc < 0) opc = opciones.length-1;
//			
//			opciones[opc].setColor(Color.WHITE);
//			cont++;
//		}
//		
//		if(!entradas.isAbajo()&&!entradas.isArriba()) {
//			cont = 15;
//		}
//	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}

}
