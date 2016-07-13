package gui.model;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;

public class MyCheckBox extends JCheckBox {

	public MyCheckBox() {
		super();
		addClickListeners();
	}

//	getSource()
	
	public MyCheckBox(String string) {
		// TODO Auto-generated constructor stub
		super();
		addClickListeners();
	}

	protected void addClickListeners() {
    	/** This handle is a reference to THIS beacause in next Mouse Adapter "this" is not allowed */
//        final Widget handle = this;
        addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked (number in CB: " + e.getClickCount() + ")\n");

				if (SwingUtilities.isLeftMouseButton(e)) {
					System.out.println("Left CB in CB\n");
				}
				
				if (SwingUtilities.isRightMouseButton(e)) {
					System.out.println("Right in CB\n");
				}
				if (SwingUtilities.isMiddleMouseButton(e)) {
					System.out.println("Middle in CB\n");
				}
				for (Component comp : getComponents()) {
		        	comp.revalidate();
		        	comp.repaint();
		        	System.out.println(comp.getName() + " je ime komponente.\n");
		        }
                revalidate();
                repaint();
             }
           
            public void mouseEntered(MouseEvent e) {
                System.out.println("Mouse entered in CB/n");
             }

            public void mouseExited(MouseEvent e) {
                System.out.println("Mouse exited in CB/n");
			}
        });
    }
	
}
