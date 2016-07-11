package gui.model.states;

import gui.model.ActionWidget;
import gui.model.CameraList;
import gui.model.MyPanel;
import gui.model.Widget;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;

public class Action1 implements State {
	
	private	MyPanel myPanel;
	private static final int NONE = -1;
	private static final int BORDER = 3;
	private int startX = NONE;
	private int startY = NONE;
	private int prevX = NONE;
	private int prevY = NONE;
	private boolean resize = false;
	
	public Action1(MyPanel panel) {
		this.myPanel = panel;
	}
	
	@Override
	public void drawComponent(int X, int Y, char panel) {
		// TODO Auto-generated method stub
		if (panel == 'a') {
			
			String	listData[] =
				{
					"Item 1",
					"Item 2",
					"Item 3",
					"Item 4"
				};

			  // Create a new listbox control
//			  JList listbox = new JList( listData );
			  JPanel newPanel = new JPanel(new BorderLayout());
			  newPanel.setBackground(Color.red);
			  newPanel.setBounds(20, 20, 50, 50);
			  
//			  newPanel.add(listbox);
			  newPanel.repaint();
			  newPanel.addMouseMotionListener(new MouseMotionAdapter() {
			      public void mouseMoved(MouseEvent e) {
			          JComponent source = (JComponent) e.getSource();  // komponenta nad kojom radimo
			          int x = e.getX();
			          int y = e.getY();
			          Rectangle bounds = source.getBounds();  // granice komponente nad kojom radimo
			          // risajz -=- ako je x ili y u minimalnoj granici ( BORDER-u ) -=- OR-ovi nam govore sa koje strane pomijeramo
			          resize = x < BORDER || y < BORDER || Math.abs(bounds.width - x) < BORDER || Math.abs(bounds.height - y) < BORDER;
			          if (resize) {
			            // TODO: there are a lot of resize cursors here, this is just of proof of concept
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
			              // TODO: handle all resize cases, left, right,...
			              source.setSize(Math.max(10, bounds.width + x - prevX), Math.max(10, bounds.height + y - prevY));
			            } else { // pomijeramo
			              source.setLocation(bounds.x + deltaX, bounds.y + deltaY);
			            }
			            // TODO: make sure you don't resize it as much as it disappears
			            // TODO: make sure you don't move it outside the main panel
			          } else { // znaci da JE PUSTEN (NIJE PRITISNUT) klik misa i da su start-ovi NONE
			            startX = x;
			            startY = y;
			          }
			          prevX = x;
			          prevY = y;
			        }
			      });
			  myPanel.add(newPanel);
			  myPanel.repaint();
			  
			
			CameraList myWidg = CameraList.getInstance();
			
			if (myWidg == null) {
				System.out.println("NULL - VEC DODATA - POMJERI");
			}
		
			System.out.println("ACTION1");
			myWidg.setBounds(X, Y, 80, 24);
			
		    myPanel.add(myWidg);
		    myPanel.widgList.add(myWidg);
		    myPanel.repaint();
		}
		else System.out.println("\nWrong panel bro!\n");
	
	} // kraj metode drawComponent

	@Override
	public String toString() {
		return "Action1 State";
	}
}
