package gui.model;

import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
	public List<Widget> widgList;
	
	public MyPanel() {
		setLayout(null); // OTAC MAJKA TROJE MALE DJECE I JEDNO NERODJENOOO !
		widgList = new ArrayList<Widget>();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2212852051935683711L;

	/*void redrawElements(Graphics g) {
		for (Widget widg : widgList) {
			widg.repaint();
		}
	}*/
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
//		redrawElements(g);		// ako nesto ne bude radilo, prvo prebaci da svaka klasa nasljednica
								// overajduje paintComponent() i da pozove redrawElements()...
								// Ovo cemo mozda raditi jer je cudno da pozovemo apstraktnu metodu
	}
	
}
