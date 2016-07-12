

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

import view.SurveilanceDisplay;
import model.CameraConfig.RotationDirection;
import model.CameraConfig.RotationSpeed;
import model.CameraDevice;
import model.SecuritySystem;
import controler.Observer;

@SuppressWarnings("serial")
public class SystemTest extends JFrame {
	public static SecuritySystem ss; 
	public static SurveilanceDisplay sd;
	
	SystemTest() {
		initializeSystem();
	}
	
	void initializeSystem () {
		ss = new SecuritySystem();
		sd = new SurveilanceDisplay(ss);
		ss.setSD(sd);
		
		addTestData();
		
		/**
		 * Tajmer rucno zaustavljamo, da se ne desi beskonacno drzanje resursa.
		 * EXIT_ON_CLOSE iskljucuje JVM i sve njene niti, pa ovo nije neophodno.
		 */
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ss.stopTimer();
				System.exit(0); // visak?
			}
		});

		setTitle("Sigurnosni sistem v1.0");
		setSize(400, 400);
		setLocationRelativeTo(null); // centers the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(sd); // dodajemo panel za prikaz kamera
	}
	
	/** Dodavanje test podataka... */
	private void addTestData() {
		//create observers
		Observer obj1 = new CameraDevice(new Point2D.Double(150, 150), RotationSpeed.FAST, RotationDirection.CLOCKWISE, -20, 180, 0, 60);
		Observer obj3 = new CameraDevice(new Point2D.Double( 50, 50), RotationSpeed.FAST, RotationDirection.COUNTER_CLK, -50, 180, 0, 60);
		
		ss.register(obj1, RotationSpeed.FAST.speed-1);
		ss.register(obj3, RotationSpeed.FAST.speed-1);
		
		obj1.setSubject(ss);
		obj3.setSubject(ss);
	}

	public static void main(String[] args) {
		/**
		 * invokeLater() dodaje zadatak aplikacije u Swing Event Queue.
		 * Ovim osiguravamo da su sva azuriranja UI-a konkurentno-sigurna.
		 */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				SystemTest st = new SystemTest();
				st.setVisible(true);
			}
		});
	}

}
