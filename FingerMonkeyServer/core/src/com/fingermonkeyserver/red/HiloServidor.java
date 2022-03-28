package com.fingermonkeyserver.red;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.fingermonkeyserver.pantallasJuegos.Multijugador;
import com.fingermonkeyserver.utilidades.Config;
import com.fingermonkeyserver.utilidades.Multiuso;
import com.fingermonkeyserver.utilidades.Utiles;

public class HiloServidor extends Thread {
		
	private DatagramSocket conexion;
	private boolean fin = false;
	private DireccionRed[] cliente = new DireccionRed[2];
	private int cantClientes = 0;
	private Multijugador app;
	private int arrayLength = 7;
	
	public HiloServidor(Multijugador app) {
		this.app = app;
		try {
			conexion = new DatagramSocket(6666);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void enviarMensaje(String msg, InetAddress ip, int puerto) {
		byte[] data = msg.getBytes();
		DatagramPacket dp = new DatagramPacket(data, data.length, ip, puerto);
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
		
		int nroCliente = -1;
		
		if(cantClientes > 1) {
			for (int i = 0; i < cliente.length; i++) {
				if(dp.getPort() == cliente[i].getPuerto() && dp.getAddress().equals(cliente[i].getIp())) {
					nroCliente = i;
				}
			}
		}

		if (cantClientes < 2) {
			if (msg.equals("Conexion")) {
				cliente[cantClientes] = new DireccionRed(dp.getAddress(), dp.getPort());
				enviarMensaje("Ok%" + cantClientes, cliente[cantClientes].getIp(), cliente[cantClientes++].getPuerto());

				if (cantClientes == 2) {
					for (int i = 0; i < cliente.length; i++) {
						enviarMensaje("Preparado", cliente[i].getIp(), cliente[i].getPuerto());
						for (int j = 0; j < arrayLength; j++) {
							enviarMensaje("Letra%" + j + "%" + app.getLetra(j), cliente[i].getIp(), cliente[i].getPuerto());
						}
						Multiuso.inicio = true;
					}
				}
			}
		} else {
			if(nroCliente != -1) {
				if(msg.equals("Enter")) {
					for (int i = 0; i < cliente.length; i++) {
						enviarMensaje("Start", cliente[i].getIp(), cliente[i].getPuerto());
					}
				}
				if(msgParametrizado[0].equals("Aprete")) {//"Aprete-Letra-IndiceSv(algo como el score del jugador)-mano
					
					if(msgParametrizado[1].equals(app.getLetra(Integer.valueOf(msgParametrizado[2])))) {
						
						enviarMensaje("nextLetra%" + app.getLetra(Integer.valueOf(msgParametrizado[2]) + arrayLength)
						+ "%" + (arrayLength-1),
								cliente[nroCliente].getIp(),cliente[nroCliente].getPuerto());
						
						if(nroCliente == 0) {
							app.getJugador1().getPersonaje().setMano(Integer.valueOf(msgParametrizado[3]));
							app.getJugador2().getPersonaje().setPosX(
									Utiles.r.nextInt((Config.ANCHO / 2) - 120) + (Config.ANCHO / 4) + 60
									);
							app.getJugador1().setScore(Integer.valueOf(msgParametrizado[2])+1);
							
							enviarMensaje("scoreRival%" + (Integer.valueOf(msgParametrizado[2])+1), 
									cliente[1].getIp(),cliente[1].getPuerto());
						}else if(nroCliente == 1) {
							app.getJugador2().getPersonaje().setMano(Integer.valueOf(msgParametrizado[3]));
							
							app.getJugador1().getPersonaje().setPosX(
									Utiles.r.nextInt((Config.ANCHO / 2) - 120) + (Config.ANCHO / 4) + 60
									);
							app.getJugador2().setScore(Integer.valueOf(msgParametrizado[2])+1);
							
							enviarMensaje("scoreRival%" + (Integer.valueOf(msgParametrizado[2])+1), 
									cliente[0].getIp(),cliente[0].getPuerto());
						}
					}
				}
				if(msg.equals("Perdi")) {
					if(nroCliente==0) {
						app.getJugador1().setCayo(true);
					}else if(nroCliente==1) {
						app.getJugador2().setCayo(true);
					}
				}
			}
		}
	}
	
	public void enviarMensaje2(String msg, int nroJugador) {
		enviarMensaje(msg,cliente[nroJugador].getIp(),cliente[nroJugador].getPuerto());
	}
}
