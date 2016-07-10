package gui.model.states;

import gui.model.ActionWidget;
import gui.model.CameraList;
import gui.model.MyPanel;
import gui.model.Widget;

import java.awt.BorderLayout;
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
			  JList listbox = new JList( listData );
			  JPanel newPanel = new JPanel(new BorderLayout());
			  myPanel.add( newPanel, BorderLayout.WEST );
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
