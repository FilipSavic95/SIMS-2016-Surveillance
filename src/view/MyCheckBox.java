package view;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class MyCheckBox extends JCheckBox {

	public MyCheckBox() {
		super();
		addClickListeners();
	}
	
	public MyCheckBox(String string) {
		super();
		addClickListeners();
	}

	protected void addClickListeners() {
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
        });
    }
	
}
