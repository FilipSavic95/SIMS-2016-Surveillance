package model;

import java.awt.geom.Point2D;

import model.CameraConfig.RotationDirection;
import model.CameraConfig.RotationSpeed;
import controler.Observer;
import controler.Subject;

public class CameraDevice implements Observer {
	/** Sigurnosni sistem kome kamera pripada. */
	private Subject sSystem;
	
	/** pozicija kamere na shemi */
	public Point2D position;

	/**
	 * konfiguracija kamere<br>
	 * @see CameraConfig
	 * */
	public CameraConfig camConf;

	public CameraDevice() {
	}

	public CameraDevice(Point2D position, CameraConfig camAtrs) {
		this.position = position;
		this.camConf = camAtrs;
	}

	/**
	 * Za svaki slucaj.. bolje je praviti novi CameraAtrs pa postaviti atribut.
	 */
	public CameraDevice(Point2D position, RotationSpeed rotationSpeed,
			RotationDirection rotationDirection, int limitStart,
			int limitWidth, int sightStart, int sightWidth) {
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
