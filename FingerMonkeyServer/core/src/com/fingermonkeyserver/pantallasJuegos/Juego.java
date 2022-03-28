package com.fingermonkeyserver.pantallasJuegos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.fingermonkeyserver.elementos.Letra;
import com.fingermonkeyserver.inouts.Entradas;
import com.fingermonkeyserver.jugadores.Jugador;
import com.fingermonkeyserver.objetosEscalables.ClimbingWall;
import com.fingermonkeyserver.personajes.Personaje;
import com.fingermonkeyserver.utilidades.Config;
import com.fingermonkeyserver.utilidades.Utiles;

public abstract class Juego implements Screen{

	protected Letra letra[];
	protected Letra aux;
	protected Entradas entradas;
	protected int[] letraPosX;
	protected int[] letraPosY;
	protected float avance;
	protected int codeLetra, vidas;
	protected boolean presionado = false;
	protected float tiempoVida;
	protected Personaje personaje;
	protected int seEquivoca;
	protected ClimbingWall pared;
	
	protected Jugador jugador;
	private int mano = 0;
	
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
				
				movimientoEscalaPersonaje();
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

	private void movimientoEscalaPersonaje() {
		tiempoVida = 0;
		
		switch(mano) {
		case 0:
			mano = 2;
			break;
		case 2:
			mano = 0;
			break;
		}
		personaje.setIndice(mano);
		
		if(mano==0) {
			personaje.setPosX((int) letra[0].getLetraTxt().getX());
		}else {
			personaje.setPosX((int) letra[0].getLetraTxt().getX() - personaje.getAnchoPj()
					- ((int) letra[0].getLetraTxt().getAncho()));
		}
	}
	
	protected void ordenarArray() {
		letra[0].setY(Config.ALTO);
		letraPosX[0] = getRandPosX(letra.length - 1);
		
		aux = letra[0];
		int auxAncho = letraPosX[0];

		for (int i = 1; i < letra.length; i++) {// ordenar el vector una posicion mas adelante
			letra[i-1] = letra[i];
			letraPosX[i-1] = letraPosX[i];
		}
		letra[letra.length -1] = aux;
		letra[letra.length -1].asignarLetra();
		letraPosX[letra.length -1] = auxAncho;
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
