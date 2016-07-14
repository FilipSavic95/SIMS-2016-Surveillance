package gui.model;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
	
	private static final long serialVersionUID = -2212852051935683711L;
	
	public List<WidgetGUI> widgList;
	
	public MyPanel() {
		// jako bitno za sprečavanje djelovanja layout manager-a na raspored prilikom resize-a
		setLayout(null);
		widgList = new ArrayList<WidgetGUI>();
		setMinimumSize(new Dimension(100, 50));
	}

	public MyPanel(FlowLayout flowLayout) {
		// TODO Auto-generated constructor stub
		super(flowLayout);
		widgList = new ArrayList<WidgetGUI>();
	}
	
	public MyPanel(LayoutManager layout) {
		super(layout);
		widgList = new ArrayList<WidgetGUI>();
	}
}
