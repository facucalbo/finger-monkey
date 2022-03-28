package com.fingermonkeyserver.pantallasJuegos;

import com.badlogic.gdx.graphics.Color;
import com.fingermonkeyserver.elementos.Letra;
import com.fingermonkeyserver.elementos.Texto;
import com.fingermonkeyserver.jugadores.Jugador;
import com.fingermonkeyserver.objetosEscalables.ClimbingWall;
import com.fingermonkeyserver.objetosEscalables.Tronco;
import com.fingermonkeyserver.personajes.MonoDefault;
import com.fingermonkeyserver.personajes.MonoRosa;
import com.fingermonkeyserver.red.HiloServidor;
import com.fingermonkeyserver.utilidades.Config;
import com.fingermonkeyserver.utilidades.Multiuso;
import com.fingermonkeyserver.utilidades.Recursos;
import com.fingermonkeyserver.utilidades.Render;

//CREAR UN VECTOR EN EL SERVIDOR Y PASARLE AL CLIENTE ESE VECTOR, PERO SOLO PARA QUE VEA LAS LETRAS QUE TIENE QUE 
//APRETAR, EL SERVER LE TIENE QUE MANDAR UN MSJ AL CLIENTE CON LA LETRA QUE TIENE QUE PRESIONAR,
//DESPUES EL CLIENTE CUANDO LE MANDA LA LETRA QUE APRETO, EL SV SE FIJA SI ES IGUAL A LA QUE TENIA QUE APRETAR.

//para que termine podemos hacer que si un jugador le saca x letras de ventaja al otro, gana.




//se puede hacer que el cliente le pase el score, el score indica la posicion del array, entonces, cuando le pasa el score
// el server le devuelve la letra en posicion 7 a partir de la posicion del array que le mando el cliente
//cuando uno de los jugadores llega a 90 puntos, el vector se actualiza a partir de los 90 puntos
//en vez de score le pasamos un contador para que al formatear el vector, no se pierda el score, ya que lo 
//tendremos que igualar a 0

public class Multijugador extends Juego {

	private int sizeLetra = 26;
	protected Letra letra[] = new Letra[160];
	protected Letra aux = new Letra(sizeLetra);
	protected int[] letraPosX = new int[letra.length];
	protected int[] letraPosY = new int[letra.length];
	protected float avance = 57/* distancia entre letras */,
			disminucionVida = 1.35f/* porcentaje de reduccion de tiempo para tecla */;
	protected int codeLetra = 0, score = 0;
	protected ClimbingWall tronco;
	protected Jugador jugador1;
	protected Jugador jugador2;
	private Texto perdiste, txtScore, espera;
	private HiloServidor hs;
	private int mano1 = 0, mano2 = 0;

	public Multijugador() {
		tronco = new Tronco(Recursos.TRONCO, (Config.ANCHO / 2), 650, avance);
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
		hs = new HiloServidor(this);
		hs.start();
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
		perdiste.setPosicion((Config.ANCHO / 2) - perdiste.getAncho() / 2, (Config.ALTO / 2) + perdiste.getAlto() / 2);

		txtScore = new Texto(Recursos.FUENTEMENU, 30, Color.YELLOW);
		txtScore.setTxt("SCORE: 0");
		txtScore.setPosicion((Config.ANCHO / 2) + (Config.ANCHO / 4), Config.ALTO);

		espera = new Texto(Recursos.FUENTEMENU, 75, Color.GREEN);
		espera.setTxt("Esperando Jugadores...");
		espera.setPosicion((Config.ANCHO / 2) - espera.getAncho() / 2, (Config.ALTO / 2) + espera.getAlto() / 2);

		jugador1 = new Jugador(new MonoRosa());
		jugador2 = new Jugador(new MonoDefault());

		asignarPersonaje(jugador1.getPersonaje());

		jugador2.getPersonaje().crearPersonaje();

		dimensionesPj(jugador1);
		dimensionesPj(jugador2);

	}
	
	private int diferenciaJ1, diferenciaJ2;
	
	@Override
	public void render(float delta) {// ERROR AL APRETAR TECLAS SIN KEYCODE
		Render.limpiarPantalla(0.3f, 0.6f, 1);
		Render.b.begin();

		if(!Multiuso.inicio) {
			espera.dibujar();
		} else {
		tronco.dibujar();
		txtScore.dibujar();
		jugador2.getPersonaje().dibujarPersonaje();
		jugador1.getPersonaje().dibujarPersonaje();

		
		diferenciaJ2 = jugador1.getScore()-jugador2.getScore();
		diferenciaJ1 = jugador2.getScore()-jugador1.getScore();
		
		jugador1.getPersonaje().setPosY((int) (avance * diferenciaJ1));
		jugador2.getPersonaje().setPosY((int) (avance * diferenciaJ2));
		
		hs.enviarMensaje2("posicionJ2%" + jugador2.getPersonaje().getPosX() + "%" + jugador2.getPersonaje().getPosY() + "%" + jugador1.getPersonaje().getMano(), 1);
		hs.enviarMensaje2("posicionJ2%" + jugador1.getPersonaje().getPosX() + "%" + jugador1.getPersonaje().getPosY() + "%" + jugador2.getPersonaje().getMano(), 0);
		
		if(jugador1.getCayo()) {
			if(jugador1.getScore()<jugador2.getScore()) {
				hs.enviarMensaje2("Perdiste",0);
				hs.enviarMensaje2("Ganaste", 1);
			}else if(jugador2.getCayo()) {
				hs.enviarMensaje2("Ganaste",0);
			}
		}else if(jugador2.getCayo()) {
			if(jugador2.getScore()<jugador1.getScore()) {
				hs.enviarMensaje2("Perdiste", 1);
				hs.enviarMensaje2("Ganaste", 0);
			}else if(jugador1.getCayo()) {
				hs.enviarMensaje2("Ganaste", 1);
			}
		}
		dibujarLetras();
		}
		Render.b.end();
	}
	
	@Override
	public void ordenarArray() {
		super.ordenarArray();
	}

	private void dimensionesPj(Jugador jugador) {
		jugador.getPersonaje().setIndice(1);// asignar indice primero (para los movimientos)
		jugador.getPersonaje().setAltoPj(jugador.getPersonaje().getAltoPj() * 2);
		jugador.getPersonaje().setAnchoPj(jugador.getPersonaje().getAnchoPj() * 2);
		jugador.getPersonaje().setPosX(Config.ANCHO / 2 - jugador.getPersonaje().getAnchoPj());
		jugador.getPersonaje().setPosY(0);
	}

	public void setLetra(Letra[] letra) {
		this.letra = letra;
	}

	public String getLetra(int indice) {
		return letra[indice].getLetraTxt().getTxt();
	}

	public Jugador getJugador1() {
		return jugador1;
	}
	
	public Jugador getJugador2() {
		return jugador2;
	}
	
	public int getMano1() {
		switch(mano1) {
		case 0:
			mano1=2;
			break;
		case 2:
			mano1=0;
			break;
		}
		return mano1;
	}
	
	public int getMano2() {
		switch(mano2) {
		case 0:
			mano2=2;
			break;
		case 2:
			mano2=0;
			break;
		}
		return mano2;
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
