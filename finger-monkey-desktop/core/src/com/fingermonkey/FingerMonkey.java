package com.fingermonkey;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fingermonkey.pantallas.PantallaMenu;
import com.fingermonkey.pantallas.PantallaCarga;
import com.fingermonkey.screens.ScreenManager;
import com.fingermonkey.utilidades.Render;

public class FingerMonkey extends Game {
	
	@Override
	public void create () {
		ScreenManager.app = this;
		Render.b = new SpriteBatch();
//		this.setScreen(new PantallaCarga());
		this.setScreen(new PantallaMenu());
	}

	@Override
	public void render () {
		super.render();
//		Render.limpiarPantalla(1,1,0,1);
		
	}
	
	@Override
	public void dispose () {

	}
}
