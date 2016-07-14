package model;

import java.awt.Point;

import model.CameraConfig.RotationDirection;
import model.CameraConfig.RotationSpeed;
import controler.Observer;
import controler.Subject;

public class CameraDevice extends MonitoringWidget {
	
	public static int globalCamID = 0;
	
	public int camID;
	
	/** Sigurnosni sistem kome kamera pripada. */
	private Subject sSystem;
	
	/** pozicija kamere na shemi */
	public Point position;

	/**
	 * konfiguracija kamere<br>
	 * @see CameraConfig
	 * */
	public CameraConfig camConf;

	public CameraDevice() {
		camID = globalCamID;
		globalCamID++;
	}

	public CameraDevice(Point position, CameraConfig camAtrs) {
		this();
		this.position = position;
		this.camConf = camAtrs;
	}

	/**
	 * Za svaki slucaj.. bolje je praviti novi CameraAtrs pa postaviti atribut.
	 */
	public CameraDevice(Point position, RotationSpeed rotationSpeed,
			RotationDirection rotationDirection, int limitStart,
			int limitWidth, int sightStart, int sightWidth) {
		this();
		this.position = position;
		this.camConf = new CameraConfig(rotationSpeed, rotationDirection,
				limitStart, limitWidth, sightStart, sightWidth);
	}

	@Override
	public String toString() {
		return "Kamera na: " + position + ", konfiguracija:\n" + camConf;
	}
	
	/**
	 * Preklapanje metode pomijera kameru kada bude obavijestena.
	 */
	public void update() {
		long tickNo = (Long) sSystem.getUpdate(this);
		if (tickNo == -1) {
			System.out.println("nije mu vrijeme jos.." + this.camConf.rotationSpeed);
		}
		else {
			System.out.println("Brojcic: "+ tickNo +", "+ this.camConf.rotationSpeed);
			this.camConf.move();
		}
	}

	public void setSubject(Subject sub) {
		sSystem = sub;
	}
}
