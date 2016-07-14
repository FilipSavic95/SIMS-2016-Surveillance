package model.states;

import java.awt.Point;

import javax.swing.JPanel;

import model.CameraConfig;
import model.CameraDevice;
import myAppPackage.MyApp;
import view.CameraGUI;
import view.MonitoringView;

public class Monitor2 implements State {
	private JPanel myPanel;
	
	public Monitor2(JPanel panel) {
		this.myPanel = panel;
	}

	@Override
	public void drawComponent(int X, int Y, char panel) {
		if (panel == 'm') {
			System.out.println("CameraGUI");
			CameraConfig ccfg = null;
			try {
				ccfg = MonitoringView.getCameraConfig();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			if (ccfg == null)
				return; // otkazano dodavanje

			CameraDevice cd = new CameraDevice(new Point(X, Y), ccfg);
			CameraGUI cg = new CameraGUI(cd);

			myPanel.add(cg);
			// registrujemo posmatrača
			MyApp.getInstance().mModel.register(cd, ccfg.rotationSpeed.speed-1);
			
			myPanel.repaint(); // da komponenta odmah postane vidljiva
		} else
			System.out.println("\nWrong panel bro!\n");

	} // kraj metode drawComponent

	@Override
	public String toString() {
		return "CameraMonitorState State - CameraGUI";
	}
}
