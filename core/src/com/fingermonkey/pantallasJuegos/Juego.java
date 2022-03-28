package com.fingermonkey.pantallasJuegos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Screen;
import com.fingermonkey.elementos.Letra;
import com.fingermonkey.elementos.Texto;
import com.fingermonkey.inouts.Entradas;
import com.fingermonkey.jugadores.Jugador;
import com.fingermonkey.objetosEscalables.ClimbingWall;
import com.fingermonkey.pantallas.PantallaMenu;
import com.fingermonkey.personajes.Personaje;
import com.fingermonkey.screens.ScreenManager;
import com.fingermonkey.utilidades.Config;
import com.fingermonkey.utilidades.Recursos;
import com.fingermonkey.utilidades.Utiles;

public abstract class Juego implements Screen{

	protected Letra letra[];
	protected Letra aux;
	protected Entradas entradas;
	protected int[] letraPosX;
	protected int[] letraPosY;
	protected float avance;
	protected int codeLetra;
	protected boolean presionado = false;
	protected float tiempoVida;
	protected Personaje personaje;
	protected int seEquivoca;
	protected ClimbingWall pared;
	protected Texto perdiste;
	private Texto opciones[] = new Texto[2];
	
	protected Jugador jugador;
	
	public Juego() {
		perdiste = new Texto(Recursos.FUENTEMENU, 75, Color.GREEN);
		perdiste.setTxt("PERDISTE");
		perdiste.setPosicion((Config.ANCHO/2) - perdiste.getAncho()/2, (Config.ALTO/2) + perdiste.getAlto()/2);
		crearOpcionesKO();
	}
	
	protected boolean presionarTecla(float delta, float limite) {
		
		seEquivoca = 0;//Si seEquivoca = 0: no presiono tecla / = 1: presiono tecla correcta / = 2: se equivoco
		
		boolean perdio = false;
		
		tiempoVida += delta;
		
		if(tiempoVida >= limite) {
			perdio = true;
		}
		
		if (Gdx.input.isKeyPressed(Keys.ANY_KEY) && !perdio) {
			if (codeLetra != entradas.getKeycode()) {
				presionado = false;
			}
			
			if (letra[0].getLetra() == (Input.Keys.toString(entradas.getKeycode())).charAt(0) && !presionado) {
				seEquivoca = 1;
				codeLetra = entradas.getKeycode();
				presionado = true;
				pared.moverPared();
				
				movimientoEscalaPersonaje(jugador);
				ordenarArray();

			}else if(!presionado){
				presionado = true;
				codeLetra = entradas.getKeycode();
				seEquivoca = 2;
			}
		}
		
		if (entradas.getKeycode() == 0)
			presionado = false;
		
		return perdio;
	}
	
	protected void crearOpcionesKO() {
		for (int i = 0; i < opciones.length; i++) {
			opciones[i] = new Texto(Recursos.FUENTEMENU,24, Color.YELLOW);
			opciones[0].setTxt("Reintentar");
			
			if(i==1) opciones[1].setTxt("Menu principal");
			opciones[i].setPosicion(Config.ANCHO/2 - opciones[i].getAncho()*1.2f + (opciones[i].getAncho() *1.2f*i), Config.ALTO/2 - perdiste.getAlto());
		}
		opciones[0].setColor(Color.GREEN);
	}

	protected void dibujarOpcionesKO() {
		for (int i = 0; i < opciones.length; i++) {opciones[i].dibujar();}
		
		int opc = 0;
		opc = Utiles.seleccionarOpcion(entradas, opciones, new Color(0.1f,0.87f,0.4f,0.72f));
		
		switch(opc) {
		case 0:
			if(entradas.isEnter()) {
				ScreenManager.app.setScreen(new PantallaMenu());
			}
			break;
		case 1:
			if(entradas.isEnter()) {
				ScreenManager.app.setScreen(new Nivel1());
			}
			break;
		}
	}
	
	protected void movimientoEscalaPersonaje(Jugador jugador) {
		tiempoVida = 0;
		
		switch(jugador.getPersonaje().getMano()) {
		case 0:
			jugador.getPersonaje().setMano(2);
			break;
		case 2:
			jugador.getPersonaje().setMano(0);
			break;
		}
		jugador.getPersonaje().setIndice(jugador.getPersonaje().getMano());
		
		if(jugador.getPersonaje().getMano()==0) {
			jugador.getPersonaje().setPosX((int) letra[0].getLetraTxt().getX());
		}else {
			jugador.getPersonaje().setPosX((int) letra[0].getLetraTxt().getX() - jugador.getPersonaje().getAnchoPj()
					- ((int) letra[0].getLetraTxt().getAncho()));
		}
	}
	
	
	protected void ordenarArray() {
		letra[0].setY(Config.ALTO);
		letraPosX[0] = getRandPosX(0);
		
		aux = letra[0];
		int auxAncho = letraPosX[0];

		for (int i = 1; i < letra.length; i++) {// ordenar el vector una posicion mas adelante
			letra[i-1] = letra[i];
			letraPosX[i-1] = letraPosX[i];
		}
		letra[letra.length - 1] = aux;
		letra[letra.length - 1].asignarLetra();
		letraPosX[letra.length - 1] = auxAncho;
	}

	protected int getRandPosX(int indice) {//se le asigna una posicion en x aleatoria a cada letra
		return Utiles.r.nextInt((Config.ANCHO / 2) - (int) letra[indice].getLetraTxt().getAncho() - 120) + (Config.ANCHO / 4) + 60;
	}
	
	protected void dibujarLetras() {//se muestran las letras en pantalla
		for (int i = 0; i < letra.length; i++) {
			letra[i].dibujar(letraPosX[i],letraPosY[i]);
		}
	}
	
	public void asignarPersonaje(Personaje personaje) {
		this.personaje = personaje;
		this.personaje.crearPersonaje();
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
