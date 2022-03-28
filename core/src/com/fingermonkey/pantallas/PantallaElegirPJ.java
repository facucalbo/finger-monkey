package com.fingermonkey.pantallas;

import com.badlogic.gdx.Screen;
import com.fingermonkey.elementos.Imagen;
import com.fingermonkey.jugadores.Jugador;
import com.fingermonkey.personajes.MonoRGB;
import com.fingermonkey.personajes.Personaje;
import com.fingermonkey.utilidades.Config;
import com.fingermonkey.utilidades.Recursos;
import com.fingermonkey.utilidades.Render;

public class PantallaElegirPJ implements Screen{
	
	private Jugador jugador;
	private Imagen[] recuadro = new Imagen[4];
	private Personaje monos = new MonoRGB();
	
	public PantallaElegirPJ() {
		
	}
	@Override
	public void show() {
		monos.crearPersonaje();
		
		float avance = 1.27f;
		
		for (int i = 0; i < recuadro.length; i++) {
			recuadro[i] = new Imagen(Recursos.RECUADROPERSONAJE);
			recuadro[i].s.setPosition(Config.ANCHO/8 + recuadro[i].s.getWidth() * (avance*i) ,
					(Config.ALTO/2) - (recuadro[i].s.getHeight()/2));
			monos.setIndice(i);
		}
	}

	@Override
	public void render(float delta) {
		Render.limpiarPantalla(1f, 0.5f, 1f);
		
		Render.b.begin();
		
		for (int i = 0; i < recuadro.length; i++) {
			recuadro[i].dibujar();
			Render.b.draw(monos.getPersonaje(i), ((recuadro[i].s.getX()) + (recuadro[i].s.getWidth()/4) - 4),
					(Config.ALTO/2) - (monos.getAltoPj()),
					monos.getAnchoPj()*2,monos.getAltoPj()*2);
		}
		monos.dibujarPersonaje();
		
		Render.b.end();
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
