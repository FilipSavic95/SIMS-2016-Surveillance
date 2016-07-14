package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import model.CameraConfig;
import model.CameraDevice;

/**
 * Klasa zaduzena za prikaz jedne kamere.<br>
 * View dio za kameru.<br>
 * @author Aleksandar
 */
@SuppressWarnings("serial")
public class CameraGUI extends WidgetGUI {
	public CameraDevice cd;
	
	/** Velicina okvira kamere. */
	private static int cameraFrame = 80;
	
	/** Velicina sociva kamere. */
	private static int cameraLense = 10;
	
	public CameraGUI() {
		setOpaque(true); // neprozirna komponenta
	}
	
	public CameraGUI(CameraDevice cd) {
		this.cd = cd;
		setBounds((int)cd.position.getX(), (int)cd.position.getY(), cameraFrame, cameraFrame);
		setOpaque(true);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("kliknuto na komponentu: "+ cd.position);
				if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                	 MonitoringPanel.getCameraConfig(); // dodati parametre sa default vrijednostima !
                }
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawMe(g);
	}
	
	/**
	 * Iscrtava sve elemente koji čine prikaz jedne kamere:
	 * <ul>
	 * <li> luk (vidno polje) </li>
	 * <li> krug (sočivo) </li>
	 * <li> kvadrat (okvir) </li>
	 * </ul>
	 * @param g grafika preko koje iscrtavamo kameru.
	 */
	public void drawMe(Graphics g) {
		CameraConfig ccf = cd.camConf;
		g.setColor(Color.BLACK);
		// trenutno vidno polje
		g.setColor(Color.BLACK);
		g.fillArc(8, 8, cameraFrame-2*8, cameraFrame-2*8, ccf.sightStart, ccf.sightWidth);
		// ograničenje vidnog polja
		g.drawArc(8, 8, cameraFrame-2*8, cameraFrame-2*8, ccf.limitStart, ccf.limitWidth);
		// okvir - ivice objekta
		g.drawRect(cameraFrame/2-cameraLense/2-5, cameraFrame/2-cameraLense/2-5, cameraLense+10, cameraLense+10);
		// sočivo
		g.setColor(Color.RED);
		g.fillOval(cameraFrame/2-cameraLense/2, cameraFrame/2-cameraLense/2, cameraLense, cameraLense);
		// parametri za praćenje kamere - može se dodati NAZIV..
		g.drawString("s: "+ ccf.sightStart+" w: "+ccf.sightWidth, 0, cameraFrame-5);
		// prikaz veličine komponente - border...
		
		// Graphics2D objekat omogućava dodatne opcije za crtanje
		// kao što je podebljavanje linija
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		g2.drawRect(0, 0, cameraFrame, cameraFrame);
	}
}
