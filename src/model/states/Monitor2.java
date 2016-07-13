package model.states;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import view.CameraGUI;
import view.MonitoringView;
import model.CameraConfig;
import model.CameraDevice;

public class Monitor2 implements State {
	private JPanel myPanel;
	MouseEvent me; // za pomijeranje
	
	public Monitor2(JPanel panel) {
		this.myPanel = panel;
	}

	@Override
	public void drawComponent(int X, int Y, char panel) {
		if (panel == 'm') {
			System.out.println("CameraGUI");
			CameraConfig ccfg = null;
			try {
				ccfg = ((MonitoringView) this.myPanel).getCameraConfig();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			if (ccfg == null)
				return; // otkazano dodavanje

			CameraDevice cd = new CameraDevice(new Point(X, Y), ccfg);
			CameraGUI cg = new CameraGUI(cd); // button = new JButton("MONITOR2");
			
			cg.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
		        me = e;
		    }
		 
		    public void mouseDragged(MouseEvent e) {
		        Component component = me.getComponent();
		        cg.cd.position = component.getLocation(cg.cd.position);
		        int x = cg.cd.position.x - me.getX() + e.getX();
		        int y = cg.cd.position.y - me.getY() + e.getY();
		        component.setLocation(x, y);
		    }
		    public void mouseReleased(MouseEvent e) {
		    	cg.cd.position = e.getComponent().getLocation();
		    }
			});

			myPanel.add(cg);
			myPanel.repaint(); // da komponenta postane vidljiva
		} else
			System.out.println("\nWrong panel bro!\n");

	} // kraj metode drawComponent

	@Override
	public String toString() {
		return "CameraMonitorState State - CameraGUI";
	}
}
