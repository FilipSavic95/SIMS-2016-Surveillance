package test;

import static org.junit.Assert.*;
import gui.model.CameraList;
import gui.model.MyPanel;

import java.awt.Component;

import model.CameraDevice;
import model.states.Action1;
import model.states.Monitor2;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import view.CameraGUI;
import view.MonitoringView;

public class Monitor2Test {

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
	
	/**
	 * Test podrazumijeva provjeru da li postoji
	 * komponenta na tackama koje su proslijedjene
	 * metodi drawComponent sa najnovijim id-om.
	 */
	@Test
	public void testDrawComponent() {
		MonitoringView myPane = new MonitoringView();
		Monitor2 act1 = new Monitor2(myPane);
		int X = 100;
		int Y = 100;
		char panel = 'm';
		boolean containsObjAtPoint = false;
		
		// poziv test metode
		act1.drawComponent(X, Y, panel);
		
		// provjera parametara i poziva
		for (Component comp : myPane.getComponents()) {
			if (comp instanceof CameraGUI) {
				CameraGUI compCameraGUI = (CameraGUI)comp;
				// Ako postoji komponenta na tackama X i Y
				// i ima najnoviji ID (asd) => test je u redu.
				if (compCameraGUI.getX() == X && 
						compCameraGUI.getY() == Y && 
						compCameraGUI.cd.camID == compCameraGUI.cd.globalCamID - 1) {
					containsObjAtPoint = true;
					break;		
				}
			}	
		}
		assertTrue(containsObjAtPoint);
	}

}
