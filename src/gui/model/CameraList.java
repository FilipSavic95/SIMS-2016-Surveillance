package gui.model;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class CameraList extends ActionWidget {
	
	private static final long serialVersionUID = 7455335893012389408L;
	
	@Override
  protected void paintComponent(Graphics g) {
      super.paintComponent(g);
//      if (isOpaque()) {
//          g.setColor(getBackground());
//          g.fillRect(0, 0, getWidth(), getHeight());
//      }
      
  }
	
	private static CameraList instance = null;
	
	protected CameraList() {
		// TODO Auto-generated constructor stub

		JList list = new JList();
		list.setCellRenderer(new CheckboxListCellRenderer());
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
