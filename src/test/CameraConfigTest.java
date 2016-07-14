package test;

import static org.junit.Assert.*;

import java.awt.Point;

import model.CameraConfig.RotationDirection;
import model.CameraConfig.RotationSpeed;
import model.CameraDevice;
import model.MonitoringModel;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import view.MonitoringPanel;

public class CameraConfigTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMove() {
		MonitoringModel mm = new MonitoringModel();
		MonitoringPanel mv = new MonitoringPanel(mm);
		mm.setMV(mv);
		int sightStart = 60;
		int waitForTicks = 30;
		CameraDevice camDev = new CameraDevice(new Point(100, 100), RotationSpeed.FAST, RotationDirection.COUNTER_CLK, 0, 180, sightStart, 60);
		mm.register(camDev, camDev.camConf.rotationSpeed.speed - 1);
		mm.startTimer();
		
		// cekamo da prodje odredjen broj tick-ova
		while(mm.otkucaj <= waitForTicks)
			System.out.println("");
			
		// provjerim da li je trenutni startSight = broju 
		// otkucaja koji smo cekali + zapamceni_start_sight + 1
		assertEquals(camDev.camConf.sightStart, waitForTicks + sightStart + 1);
	}
	
	@Test
	public void testTurnAround() {
		MonitoringModel mm = new MonitoringModel();
		MonitoringPanel mv = new MonitoringPanel(mm);
		mm.setMV(mv);
		int sightStart = 45;
		int waitForTicks = 71; // 71 ?
		CameraDevice camDev = new CameraDevice(new Point(100, 100), RotationSpeed.FAST, RotationDirection.COUNTER_CLK, 45, 45, sightStart, 10);
		mm.register(camDev, camDev.camConf.rotationSpeed.speed - 1);
		mm.startTimer();
		// cekamo da prodje odredjen broj tick-ova
		while(mm.otkucaj <= waitForTicks) {
			System.out.println("");
		}
		
		// sacekam neko vrijeme da dodje do okretanja, tj. 
		// poziva turnAround metode...
		// provjerim da li je sadasnja vrijednost startSight-a 
		// na odgovarajucem "mjestu"
		assertEquals(camDev.camConf.sightStart, sightStart);
	}

}
