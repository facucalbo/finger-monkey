package com.fingermonkey.pantallas;

import com.badlogic.gdx.Screen;
import com.fingermonkey.elementos.Imagen;
import com.fingermonkey.screens.ScreenManager;
import com.fingermonkey.utilidades.Config;
import com.fingermonkey.utilidades.Render;

public class PantallaCarga implements Screen{

	private Imagen logo;
	private float a = 0;
	private boolean termina = false, fadeEnd = false;;
	private int tiempoEspera = 180,contTiempo = 0;
	
	@Override
	public void show() {
		logo = new Imagen("logoprueba.jpg");
		logo.s.setBounds(0, 0, Config.ANCHO, Config.ALTO);
	}

	@Override
	public void render(float delta) {
		Render.limpiarPantalla(0, 0, 0);
		
		Render.b.begin();
		
		procesarFade();
		logo.dibujar();
			
		Render.b.end();
	}

	private void procesarFade() {
		logo.s.setAlpha(a);
		if (!termina) {
			if (a < 0.99999) {
				a += 0.010f;
			}else {
				a = 1;
				termina = true;
			}
		} else {
			contTiempo++;
			if (contTiempo > tiempoEspera) {
				if (a > 0.01f) {
					a -= 0.01f;
				} else {
					a = 0;
					fadeEnd = true;
					contTiempo = 0;
					tiempoEspera = 120;
				}
			}
		}
		
		if (fadeEnd) {
			if(contTiempo > tiempoEspera-5) {
				ScreenManager.app.setScreen(new PantallaMenu());
			}
		}
	}

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
