package com.fingermonkey.pantallasJuegos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.fingermonkey.elementos.BarraTiempo;
import com.fingermonkey.elementos.Indicador;
import com.fingermonkey.elementos.Letra;
import com.fingermonkey.elementos.Texto;
import com.fingermonkey.inouts.Entradas;
import com.fingermonkey.jugadores.Jugador;
import com.fingermonkey.objetosEscalables.ClimbingWall;
import com.fingermonkey.objetosEscalables.Tronco;
import com.fingermonkey.personajes.MonoDefault;
import com.fingermonkey.personajes.MonoRosa;
import com.fingermonkey.red.HiloCliente;
import com.fingermonkey.utilidades.Config;
import com.fingermonkey.utilidades.Multiuso;
import com.fingermonkey.utilidades.Recursos;
import com.fingermonkey.utilidades.Render;

public class Multijugador extends Juego{

	private int sizeLetra = 26, cantClientes = 0;
	protected Letra letra[] = new Letra[7];
	protected Letra aux = new Letra(sizeLetra);
	protected Entradas entradas = new Entradas();
	protected int[] letraPosX = new int[letra.length];
	protected int[] letraPosY = new int[letra.length];
	protected float avance = 57/*distancia entre letras*/;
//	protected float disminucionVida = 1.35f/*porcentaje de reduccion de tiempo para tecla*/;
//	protected float limitToPress = 3f;
	protected int codeLetra = 0, score = 0;
	protected boolean presionado = false;
	protected ClimbingWall tronco;
	protected Jugador jugador1;
	private Jugador jugador2;
	private BarraTiempo barraTiempo;
	private Texto txtScore,txtScoreJ2, espera;
	private HiloCliente hc;
	private int indiceLetra = 0;
	protected Texto gameOver;
	private boolean termino = false;
	private Indicador indicador;
	
	public Multijugador() {
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
		super.jugador = jugador1;
		this.gameOver = super.perdiste;
		hc = new HiloCliente(this);
	}
	
	@Override
	public void show() {
			for (int i = 0; i < letra.length; i++) {
				letra[i] = new Letra(sizeLetra);
				letraPosX[i] = getRandPosX(i);
				letraPosY[i] = (int) (135 + avance * i);
			}

			perdiste = new Texto(Recursos.FUENTEMENU, 75, Color.GREEN);
			perdiste.setTxt("PERDISTE");
			perdiste.setPosicion((Config.ANCHO / 2) - perdiste.getAncho() / 2,
					(Config.ALTO / 2) + perdiste.getAlto() / 2);

			txtScore = new Texto(Recursos.FUENTEMENU, 30, Color.YELLOW);
			txtScore.setTxt("SCORE: 0");
			txtScore.setPosicion((Config.ANCHO / 2) + (Config.ANCHO / 4), Config.ALTO);
			
			txtScoreJ2 = new Texto(Recursos.FUENTEMENU, 30, Color.PINK);
			txtScoreJ2.setTxt("SCORE: 0");
			txtScoreJ2.setPosicion((Config.ANCHO / 2) + (Config.ANCHO / 4), 
					Config.ALTO - txtScore.getAlto() - 75);

			espera = new Texto(Recursos.FUENTEMENU, 75, Color.GREEN);
			espera.setTxt("Esperando Jugadores...");
			espera.setPosicion((Config.ANCHO / 2) - espera.getAncho() / 2,
					(Config.ALTO / 2) + espera.getAlto() / 2);
			
			jugador1 = new Jugador(new MonoDefault());
			jugador2 = new Jugador(new MonoRosa());
			
			asignarPersonaje(jugador1.getPersonaje());

			barraTiempo = new BarraTiempo(Multiuso.limitToPress);
			indicador = new Indicador();
			
			jugador2.getPersonaje().crearPersonaje();
			
			dimensionPj(jugador1,Config.ANCHO / 2 - Config.ANCHO/6);
			dimensionPj(jugador2,Config.ANCHO / 2 + Config.ANCHO/6);
			
			Gdx.input.setInputProcessor(entradas);		
			hc.start();
	}

	@Override
	public void render(float delta) {//ERROR AL APRETAR TECLAS SIN KEYCODE ASCII
		Render.limpiarPantalla(0.3f, 0.6f, 1);
		Render.b.begin();
		
		if(!Multiuso.inicio) {
			espera.dibujar();
			if(entradas.isEnter()) {
				hc.enviarMensaje("Enter");
			}
		} else {
			tronco.dibujar();
			txtScore.dibujar();
			txtScoreJ2.setTxt("SCORE P2: " + jugador2.getScore());
			txtScoreJ2.dibujar();
			barraTiempo.dibujar(Multiuso.limitToPress, tiempoVida);
			jugador1.getPersonaje().dibujarPersonaje();
			jugador2.getPersonaje().dibujarPersonaje();
			
			if(jugador2.getPersonaje().getPosY() <= -jugador2.getPersonaje().getAltoPj()) {
				indicador.dibujar(0, jugador2.getPersonaje().getPosX(), 0);
			}
			if(jugador2.getPersonaje().getPosY() > Config.ALTO) {
				indicador.dibujar(1, jugador2.getPersonaje().getPosX(), Config.ALTO - (int) indicador.getAlto());
			}
			
//			System.out.println("Jugador 1: " + jugador1.getPersonaje().getPosY() + " jugador 2: " + jugador2.getPersonaje().getPosY());
//			System.out.println(jugador2.getPersonaje().getMano());
			dibujarLetras();
			if (!this.presionarTecla(delta, Multiuso.limitToPress) && !termino) {
				if (seEquivoca == 1) {
					jugador1.setScore(jugador1.getScore() + 1);
					txtScore.setTxt("SCORE: " + jugador1.getScore());
				} else if (seEquivoca == 2) {// tecla incorrecta
					tiempoVida = tiempoVida * Multiuso.disminucionVida;
				}
			} else if(!termino){
				hc.enviarMensaje("Perdi");
			}else {
				gameOver.dibujar();
				jugador1.getPersonaje().setIndice(3);
				dibujarOpcionesKO();
			}
		}
		Render.b.end();
	}
	
	@Override
	protected boolean presionarTecla(float delta, float limite) {
		seEquivoca = 0;// Si seEquivoca = 0: no presiono tecla / = 1: presiono tecla correcta / = 2: se equivoco

		boolean perdio = false;

		tiempoVida += delta;

		if (tiempoVida >= limite) {
			perdio = true;
		}

		if (Gdx.input.isKeyPressed(Keys.ANY_KEY) && !perdio) {
			if (codeLetra != entradas.getKeycode()) {
				presionado = false;
			}

			if (letra[0].getLetra() == (Input.Keys.toString(entradas.getKeycode())).charAt(0)
					&& !presionado) {
				seEquivoca = 1;
				codeLetra = entradas.getKeycode();
				presionado = true;
				pared.moverPared();
				
				hc.enviarMensaje("Aprete%" + Input.Keys.toString(entradas.getKeycode()).charAt(0) + "%" + indiceLetra++ + 
						"%" + jugador1.getPersonaje().getMano());
				
				super.movimientoEscalaPersonaje(jugador1);
				ordenarArray();
			} else if (!presionado) {
				presionado = true;
				codeLetra = entradas.getKeycode();
				seEquivoca = 2;
			}
		}
		if (entradas.getKeycode() == 0)
			presionado = false;

		return perdio;
	}
	
	@Override
	public void movimientoEscalaPersonaje(Jugador jugador) {
		switch(jugador.getPersonaje().getMano()) {
		case 0:
			jugador.getPersonaje().setMano(2);
			break;
		case 2:
			jugador.getPersonaje().setMano(0);
			break;
		}
		jugador.getPersonaje().setIndice(jugador.getPersonaje().getMano());
	}
	
	@Override
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
		letraPosX[letra.length - 1] = auxAncho;
	}
	
	private void dimensionPj(Jugador jugador, int posX) {
		jugador.getPersonaje().setIndice(1);// asignar indice primero (para los movimientos)
		jugador.getPersonaje().setAltoPj(jugador.getPersonaje().getAltoPj() * 2);
		jugador.getPersonaje().setAnchoPj(jugador.getPersonaje().getAnchoPj() * 2);
		jugador.getPersonaje().setPosX(posX);
		jugador.getPersonaje().setPosY(0);
	}
	
	public Jugador getJugador1() {
		return jugador1;
	}
	
	public Jugador getJugador2() {
		return jugador2;
	}
	
	public void setLetra(int indice, String arrayLetra) {
		letra[indice].setLetra(arrayLetra.charAt(0));
	}

	public Texto getGameOver() {
		return gameOver;
	}
	
	public void setTermino(boolean termino) {
		this.termino = termino;
	}
	
	public Texto getEspera() {
		return espera;
	}
	
//	public void setCantClientes(int cantClientes) {
//		this.cantClientes = cantClientes;
//	}
}
