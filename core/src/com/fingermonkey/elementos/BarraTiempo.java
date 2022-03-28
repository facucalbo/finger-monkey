package com.fingermonkey.elementos;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.fingermonkey.utilidades.Config;
import com.fingermonkey.utilidades.Recursos;
import com.fingermonkey.utilidades.Render;

public class BarraTiempo {
	private Imagen barraTiempo;
	private TextureRegion barraRegion;
	private TextureRegion[] barraFrame;
	private Animation timeBar;
	private float duracion = 0;
	private int indiceBarra = 0;
	
	
	public BarraTiempo(float limitToPress) {
		barraTiempo = new Imagen(Recursos.BARRATIEMPO);
		barraRegion = new TextureRegion(barraTiempo.t);
		TextureRegion[][] temp = barraRegion.split((int) barraTiempo.s.getWidth()/2, (int) barraTiempo.s.getHeight()/7);
		barraFrame = new TextureRegion[(temp.length * temp[0].length)-1];
		int indice = 0;
		for (int i = 0; i < temp[i].length; i++) {
			for (int j = 0; j < temp.length; j++) {
				if(!(j==6 && i == 0)) {
					barraFrame[indice++] = temp[j][i];
				}
			}
		}
		timeBar = new Animation(limitToPress/barraFrame.length, barraFrame);//se crea la animacion
	}
	
	public void dibujar(float limitToPress, float tiempoVida) {
		TextureRegion frame = (TextureRegion) timeBar.getKeyFrame(tiempoVida,false);//se dibuja el frame
		Render.b.draw(frame, (Config.ANCHO/2) + (Config.ANCHO /4), Config.ALTO - 60, 100, 33);
	}
	
	public TextureRegion[] getBarraFrame() {
		return barraFrame;
	}
	
}
