package model.states;

import view.CameraList;
import view.MyPanel;
import model.states.State;

public class Action1 implements State {

	private MyPanel myPanel;

	public Action1(MyPanel panel) {
		this.myPanel = panel;
	}

	public void drawComponent(int X, int Y, char panel) {
		if (panel == 'a') {
			CameraList myWidg = CameraList.getInstance();

			if (myWidg == null) {
				System.out.println("NULL - VEC DODATA - POMJERI");
			}

			System.out.println("ACTION1");
			myWidg.setBounds(X, Y, 180, 240);

			myPanel.add(myWidg);
			myPanel.widgList.add(myWidg);
			
			myPanel.revalidate();
			myPanel.repaint();
		} else
			System.out.println("\nWrong panel!\n");

	} // kraj metode drawComponent

	@Override
	public String toString() {
		return "Action1 State";
	}
}
