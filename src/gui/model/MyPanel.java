package gui.model;

import java.awt.Graphics;

import javax.swing.JPanel;

public abstract class MyPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2212852051935683711L;

	abstract void redrawElements();
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		redrawElements();		// ako nesto ne bude radilo, prvo prebaci da svaka klasa nasljednica
								// overajduje paintComponent() i da pozove redrawElements()...
								// Ovo cemo mozda raditi jer je cudno da pozovemo apstraktnu metodu
	}
	
}
