package model.states;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class Monitor1 implements State {

	private	JPanel myPanel;
	private static final int NONE = -1;
	private static final int BORDER = 3;
	private int startX = NONE;
	private int startY = NONE;
	private int prevX = NONE;
	private int prevY = NONE;
	private boolean resize = false;
	
	public Monitor1(JPanel panel) {
		this.myPanel = panel;
	}
	
	@Override
	public void drawComponent(int X, int Y, char panel) {
		if (panel == 'm') {
			System.out.println("Line Widget");		
			JPanel linew = new JPanel();
			linew.setBounds(X, Y, 2, 100);
			linew.setBackground(Color.black);
	
			linew.addMouseListener(new MouseAdapter() {
		      public void mouseReleased(MouseEvent e) {
		        startX = NONE;
		        startY = NONE;
		        ((JComponent) e.getSource()).setCursor(Cursor.getDefaultCursor());
		      }
	
		      public void mousePressed(MouseEvent e) {
		        startX = e.getX();
		        startY = e.getY();
		      }
		    });
		    
			linew.addMouseMotionListener(new MouseMotionAdapter() {
		      public void mouseMoved(MouseEvent e) {
		        JComponent source = (JComponent) e.getSource();  // komponenta nad kojom radimo
		        int x = e.getX();
		        int y = e.getY();
		        Rectangle bounds = source.getBounds();  // granice komponente nad kojom radimo
		        // risajz -=- ako je x ili y u minimalnoj granici ( BORDER-u ) -=- OR-ovi nam govore sa koje strane pomijeramo
		        resize = x < BORDER || y < BORDER || Math.abs(bounds.width - x) < BORDER || Math.abs(bounds.height - y) < BORDER;
		        if (resize) {
		          source.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
		        } else { // pomijeramo
		          source.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		        }
		      }
	
		      public void mouseDragged(MouseEvent e) {
		        int x = e.getX();
		        int y = e.getY();
		        if (startX != NONE && startY != NONE) { // znaci da je PRITISNUT klik misa.
		        										// kad se klik PUSTI, start-ovi su NONE.
		          JComponent source = (JComponent) e.getSource();  // komponenta nad kojom radimo
		          Rectangle bounds = source.getBounds();  // granice komponente nad kojom radimo
		          int deltaX = x - startX;
		          int deltaY = y - startY;
		          if (resize) {
		            source.setSize(Math.max(5, bounds.width + x - prevX), Math.max(5, bounds.height + y - prevY));
		          } else { // pomijeramo
		            source.setLocation(bounds.x + deltaX, bounds.y + deltaY);
		          }
		        } else { // znaci da JE PUSTEN (NIJE PRITISNUT) klik misa i da su start-ovi NONE
		          startX = x;
		          startY = y;
		        }
		        prevX = x;
		        prevY = y;
		      }
		    });
			
		    myPanel.add(linew);
		    myPanel.repaint();
		}
		System.out.println("\nWrong panel!\n");
	
	} // kraj metode drawComponent

	@Override
	public String toString() {
		return "Monitor1 State";
	}
}
