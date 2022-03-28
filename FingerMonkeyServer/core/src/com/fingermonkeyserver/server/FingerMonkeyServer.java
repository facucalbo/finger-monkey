package com.fingermonkeyserver.server;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fingermonkeyserver.pantallasJuegos.Multijugador;
import com.fingermonkeyserver.screens.ScreenManager;
import com.fingermonkeyserver.utilidades.Render;

public class FingerMonkeyServer extends Game {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		ScreenManager.app = this;
		Render.b = new SpriteBatch();
		this.setScreen(new Multijugador());
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		
	}
}
