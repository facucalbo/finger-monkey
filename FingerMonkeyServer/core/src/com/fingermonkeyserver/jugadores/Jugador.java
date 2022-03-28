package com.fingermonkeyserver.jugadores;

import com.fingermonkeyserver.personajes.Personaje;

public class Jugador {

	private int score;
	private Personaje personaje;
	private boolean cayo = false;
	
	public Jugador(Personaje personaje) {
		this.personaje = personaje;
	}
	
	public Personaje getPersonaje() {
		return personaje;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void setCayo(boolean cayo) {
		this.cayo = cayo;
	}
	
	public boolean getCayo() {
		return cayo;
	}
	
}
