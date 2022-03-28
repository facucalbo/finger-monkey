package com.fingermonkey.pantallasJuegos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.fingermonkey.elementos.BarraTiempo;
import com.fingermonkey.elementos.Letra;
import com.fingermonkey.elementos.Texto;
import com.fingermonkey.inouts.Entradas;
import com.fingermonkey.jugadores.Jugador;
import com.fingermonkey.objetosEscalables.ClimbingWall;
import com.fingermonkey.objetosEscalables.Tronco;
import com.fingermonkey.personajes.MonoVerde;
import com.fingermonkey.utilidades.Config;
import com.fingermonkey.utilidades.Multiuso;
import com.fingermonkey.utilidades.Recursos;
import com.fingermonkey.utilidades.Render;

public class Nivel1 extends Juego {
	
	private int sizeLetra = 26;
	protected Letra letra[] = new Letra[7];
	protected Letra aux = new Letra(sizeLetra);
	protected Entradas entradas = new Entradas();
	protected int[] letraPosX = new int[letra.length];
	protected int[] letraPosY = new int[letra.length];
	protected float avance = 57f/*distancia entre letras*/; 
//	protected float disminucionVida = 1.35f/*porcentaje de reduccion de tiempo para tecla*/;
//	protected float limitToPress = 2f;
	protected int codeLetra = 0, score = 0;
	protected boolean presionado = false;
	protected ClimbingWall tronco;
	protected Jugador jugador = new Jugador(new MonoVerde());
	private BarraTiempo barraTiempo;
	private Texto txtScore;
	
	
	public Nivel1() {
		tronco = new Tronco(Recursos.TRONCO, (Config.ANCHO/2), 650, avance);
		super.letra = letra;
		super.aux = aux;
		super.entradas = entradas;
		super.avance = avance;
		super.codeLetra = codeLetra;
		super.presionado = presionado;
		super.letraPosX = letraPosX;
		super.letraPosY = letraPosY;
		super.pared = tronco;
		super.jugador = jugador;
	}
	
	@Override
	public void show() {
		for (int i = 0; i < letra.length; i++) {
			letra[i] = new Letra(sizeLetra);
			letraPosX[i] = getRandPosX(i);
			letraPosY[i] = (int) (135 + avance * i);
		}
		
//		crearOpcionesKO();
		
		txtScore = new Texto(Recursos.FUENTEMENU, 30, Color.YELLOW);
		txtScore.setTxt("SCORE: 0");
		txtScore.setPosicion((Config.ANCHO/2) + (Config.ANCHO /4),Config.ALTO);
		
		asignarPersonaje(jugador.getPersonaje());
		
		barraTiempo = new BarraTiempo(Multiuso.limitToPress);
		
		jugador.getPersonaje().setIndice(1);//asignar indice primero
		jugador.getPersonaje().setAltoPj(jugador.getPersonaje().getAltoPj()*2);
		jugador.getPersonaje().setAnchoPj(jugador.getPersonaje().getAnchoPj()*2);
		jugador.getPersonaje().setPosX(Config.ANCHO/2 - jugador.getPersonaje().getAnchoPj());
		jugador.getPersonaje().setPosY(0);
		
		Gdx.input.setInputProcessor(entradas);
	}

	@Override
	public void render(float delta) {//ERROR AL APRETAR TECLAS SIN KEYCODE
		Render.limpiarPantalla(0.3f, 0.6f, 1);
		
		Render.b.begin();
		
		tronco.dibujar();
		txtScore.dibujar();
		barraTiempo.dibujar(Multiuso.limitToPress,tiempoVida);
		jugador.getPersonaje().dibujarPersonaje();
		
		dibujarLetras();
		if (!presionarTecla(delta, Multiuso.limitToPress)) {
			if (seEquivoca == 1) {
				jugador.setScore(jugador.getScore() + 1);
				txtScore.setTxt("SCORE: " + jugador.getScore());
			} else if (seEquivoca == 2) {//tecla incorrecta
				tiempoVida = tiempoVida * Multiuso.disminucionVida;
			}
		} else {
			super.perdiste.dibujar();
			jugador.getPersonaje().setIndice(3);
			dibujarOpcionesKO();
		}
		Render.b.end();
	}

}
