package acin_paint;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

@SuppressWarnings("serial")
/**
 * 
 * @author Aleksandar
 *
 */
public class DrawArea extends JComponent {
	private Image img; // slika u kojoj crtamo
	private Graphics2D g2; // grafika...
	
	private int currX, currY, oldX, oldY;
	
	public DrawArea() {
		setDoubleBuffered(false);
		addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				oldX = e.getX();
				oldY = e.getY();
			}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				// 
				currX = e.getX();
				currY = e.getY();
				
				if (g2 != null)
				{
					// kontekst mora biti != null
					g2.drawLine(oldX, oldY, currX, currY);
					repaint();
					// zapamtimo trenutne koordinate
					oldX = currX;
					oldY = currY;
				}
				
			}
		});
	}
	protected void paintComponent(Graphics g) {
		System.out.println("koliko cesto?");
		if (img == null)
		{
			img = createImage(getSize().width, getSize().height);
			g2 = (Graphics2D) img.getGraphics();
			// enable antialiasing
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			// clear draw area
			// clear();
		}
		g.drawImage(img, 0, 0, null);
		
	}
	
	public void clear() {
		g2.setPaint(Color.white);
		g2.fillRect(0, 0, getSize().width, getSize().height); // prelijepimo bijeli pravougaonik
		g2.setPaint(Color.black);
		repaint();
	}
	
	public void red() {
		g2.setPaint(Color.red);
	}
	
	public void black() {
		g2.setPaint(Color.black);
	}
	
	public void magenta() {
		g2.setPaint(Color.magenta);
	}
	
	public void green() {
		g2.setPaint(Color.green);
	}
	
	public void blue() {
		g2.setPaint(Color.blue);
	}
	
	// 
}
