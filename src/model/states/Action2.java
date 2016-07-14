package model.states;

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

import view.ConditionListGUI;
import view.MyPanel;

public class Action2 implements State {

	private MyPanel myPanel;

	public Action2(MyPanel panel) {
		this.myPanel = panel;
	}

	@Override
	public void drawComponent(int X, int Y, char panel) {
		if (panel == 'a') {
			ConditionListGUI myWidg = ConditionListGUI.getInstance();

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
