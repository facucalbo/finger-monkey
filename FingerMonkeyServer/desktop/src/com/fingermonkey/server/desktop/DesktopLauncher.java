package com.fingermonkey.server.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.fingermonkeyserver.utilidades.Config;
import com.fingermonkeyserver.server.FingerMonkeyServer;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Config.ANCHO;
		config.height = Config.ALTO;
//		config.resizable = false;
		config.foregroundFPS = 60;
		config.title = "Finger Monkey 0.0";
		new LwjglApplication(new FingerMonkeyServer(), config);
	}
}
