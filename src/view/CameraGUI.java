package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import model.CameraConfig;
import model.CameraDevice;

/**
 * Klasa zaduzena za prikaz jedne kamere.<br>
 * View dio za kameru.<br>
 * @author Aleksandar
 */
@SuppressWarnings("serial")
public class CameraGUI extends JComponent {
	public CameraDevice cd;
	
	/** Velicina okvira kamere. */
	private static int cameraFrame = 80;
	
	/** Velicina sociva kamere. */
	private static int cameraLense = 10;
	
	public CameraGUI() {
	}
	
	public CameraGUI(CameraDevice cd) {
		this.cd = cd;
		setOpaque(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawMe(g);
	}
	
	/**
	 * Iscrtava sve elemente koji cine jednu kameru:
	 * <ul>
	 * <li> kvadrat (okvir) </li>
	 * <li> krug (socivo) </li>
	 * <li> luk (vidno polje) </li>
	 * </ul>
	 * @param g grafika preko koje iscrtavamo kameru.
	 */
	public void drawMe(Graphics g) {
		int x = (int) cd.position.getX();
		int y = (int) cd.position.getY();
		Graphics ge = getParent().getGraphics(); // OVO NESTO RADIII !
		//Graphics ge = g;
		System.out.println("drawMe says: \"position: ("+ cd.position + ")\"."+ this.isVisible());
		
		CameraConfig ccf = cd.camConf;
		ge.setColor(Color.YELLOW);
		ge.fillArc(x, y, cameraFrame, cameraFrame, ccf.sightStart, ccf.sightWidth); // SW * ccf.rotationDirection.direction?
		ge.setColor(Color.RED);
		ge.drawArc(x, y, cameraFrame, cameraFrame, ccf.limitStart, ccf.limitWidth); // mozda +360 ?
		ge.setColor(Color.MAGENTA);
		ge.drawRect(x, y, cameraFrame, cameraFrame);
		ge.fillOval(x+cameraFrame/2-cameraLense/2, y+cameraFrame/2-cameraLense/2, cameraLense, cameraLense);
		ge.drawString("start "+ ccf.sightStart+" width "+ccf.sightWidth, x, y);
	}
}
