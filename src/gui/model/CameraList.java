package gui.model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class CameraList extends ActionWidget {
	
	private static final long serialVersionUID = 7455335893012389408L;
	
	@Override
  protected void paintComponent(Graphics g) {
      super.paintComponent(g);
//      g.drawString("CAMERA LIST", 0, 0);
  }
	
	private static CameraList instance = null;
	
	protected CameraList() {
		// TODO Auto-generated constructor stub

		/*
		 * String	listData[] =
				{
					"Item 1",
					"Item 2",
					"Item 3",
					"Item 4"
				};

			  // Create a new listbox control
			  JList listbox = new JList( listData );
			  JPanel newPanel = new JPanel(new BorderLayout());
			  newPanel.setBackground(Color.red);
			  newPanel.setBounds(20, 20, 30, 30);
			  newPanel.add(listbox);
			  myPanel.add(newPanel);
			  myPanel.repaint();
		 */
		JList list = new JList();
		list.setCellRenderer(new CheckboxListCellRenderer());
		this.add(list);
	}
	
	public static CameraList getInstance() {
		if (instance == null) {
			instance = new CameraList();
		}
		return instance;
	}
	
	public class CheckboxListCellRenderer extends JCheckBox implements ListCellRenderer {

	    public Component getListCellRendererComponent(JList list, Object value, int index, 
	            boolean isSelected, boolean cellHasFocus) {

	        setComponentOrientation(list.getComponentOrientation());
	        setFont(list.getFont());
	        setBackground(list.getBackground());
	        setForeground(list.getForeground());
	        setSelected(isSelected);
	        setEnabled(list.isEnabled());

	        setText(value == null ? "" : value.toString());  

	        return this;
	    }
	}
	
}
