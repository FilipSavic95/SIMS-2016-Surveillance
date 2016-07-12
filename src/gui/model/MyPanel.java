package gui.model;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MyPanel extends JPanel {
	
	private static final long serialVersionUID = -2212852051935683711L;
	
	public List<Widget> widgList;
	
	public MyPanel() {
		setLayout(null); // OTAC MAJKA TROJE MALE DJECE I JEDNO NERODJENOOO !
		widgList = new ArrayList<Widget>();
		addClickListeners();
	}

	public MyPanel(FlowLayout flowLayout) {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public MyPanel(ScrollablePane scP) {
		super();
	}
	
	public MyPanel(LayoutManager layout) {
		super(layout);
	}

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
	
	 /* Add Mouse Clicked Listener with function which
     * opens JOptionPane
     */
    protected void addClickListeners() {
    	/** This handle is a reference to THIS beacause in next Mouse Adapter "this" is not allowed */
//        final Widget handle = this;
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked (number of clicks: " + e.getClickCount() + ")\n");

				if (SwingUtilities.isLeftMouseButton(e)) {
					System.out.println("Left\n");
				}
				
				if (SwingUtilities.isRightMouseButton(e)) {
					System.out.println("Right\n");
				}
				if (SwingUtilities.isMiddleMouseButton(e)) {
					System.out.println("Middle\n");
				}
                
                revalidate();
                repaint();
                
             }

        });
    }
	
}
