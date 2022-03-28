package com.fingermonkey.jugadores;

import com.fingermonkey.personajes.Personaje;

public class Jugador {

	private int score = 0;
	private Personaje personaje;
	
	public Jugador(Personaje personaje) {
		this.personaje = personaje;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public Personaje getPersonaje() {
		return personaje;
	}
}
