package com.fingermonkeyserver.red;

import java.net.InetAddress;

public class DireccionRed {
	
	private InetAddress ip;
	private int puerto;
	
	public DireccionRed(InetAddress ip, int puerto) {
		this.puerto = puerto;
		this.ip = ip;
	}
	
	public InetAddress getIp() {
		return ip;
	}
	
	public int getPuerto() {
		return puerto;
	}
	
}
