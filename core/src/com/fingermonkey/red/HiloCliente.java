package com.fingermonkey.red;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.fingermonkey.pantallasJuegos.Multijugador;
import com.fingermonkey.utilidades.Multiuso;

public class HiloCliente extends Thread {

	private DatagramSocket conexion;
	private InetAddress ipServer;
	private int puerto = 6666;
	private boolean fin = false;
	private Multijugador app;
	
	public HiloCliente(Multijugador app) {
		this.app = app;
		try {
			ipServer = InetAddress.getByName("255.255.255.255");
			conexion = new DatagramSocket();
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
		
		enviarMensaje("Conexion");
	}
	
	public void enviarMensaje(String msg) {
		byte[] data = msg.getBytes();
		DatagramPacket dp = new DatagramPacket(data, data.length, ipServer, puerto);
		try {
			conexion.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		do {
			byte[] data = new byte[1024];
			DatagramPacket dp = new DatagramPacket(data, data.length);
			try {
				conexion.receive(dp);
			} catch (IOException e) {
				e.printStackTrace();
			}
			procesarMensaje(dp);
		} while (!fin);
	}
	
	private void procesarMensaje(DatagramPacket dp) {
		String msg = (new String(dp.getData())).trim();
		
		String[] msgParametrizado = msg.split("%");
		
		if (msgParametrizado.length < 2) {
			if (msg.equals("Preparado")) {
				app.getEspera().setTxt("PRESIONA ENTER");
			}
			if(msg.equals("Start")) {
				Multiuso.inicio = true;
			}
			if(msg.equals("Ganaste") || msg.equals("Perdiste")) {
				app.getGameOver().setTxt(msg);
				app.setTermino(true);
			}
		}else {
			if(msgParametrizado[0].equals("Ok")) {
					ipServer = dp.getAddress();
			}
			if(msgParametrizado[0].equals("Letra")) {
				app.setLetra(Integer.valueOf(msgParametrizado[1]), msgParametrizado[2]);
			}
			if(msgParametrizado[0].equals("nextLetra")) {
				app.setLetra(Integer.valueOf(msgParametrizado[2]),msgParametrizado[1]);
			}if(msgParametrizado[0].equals("scoreRival")) {
				app.getJugador2().setScore(Integer.valueOf(msgParametrizado[1]));
			}if(msgParametrizado[0].equals("posicionJ2")) {
				app.getJugador2().getPersonaje().setPosition(Integer.valueOf(msgParametrizado[1]), Integer.valueOf(msgParametrizado[2]));
				app.getJugador2().getPersonaje().setIndice(Integer.valueOf(msgParametrizado[3]));
			}
		}
//		if(msgParametrizado[0].equals("Clientes")) {
//			app.setCantClientes(Integer.parseInt(msgParametrizado[1]));
//		}
	}
}
