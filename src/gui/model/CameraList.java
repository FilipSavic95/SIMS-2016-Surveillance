package gui.model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ListCellRenderer;
import javax.swing.Scrollable;
import javax.swing.UIManager;

public class CameraList extends ActionWidget {

	private static final long serialVersionUID = 7455335893012389408L;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// g.drawString("CAMERA LIST", 0, 0);
	}

	private static CameraList instance = null;

	private MyCheckBox all;
	private List<MyCheckBox> checkBoxes;

	protected CameraList() {

		String options[] = { "Item 1", "Item 2", "Item 3", "Item 4" };

		checkBoxes = new ArrayList<MyCheckBox>();

		setLayout(new BorderLayout());
		MyPanel header = new MyPanel(new FlowLayout(FlowLayout.LEFT, 1, 1));
		all = new MyCheckBox("Select All...");
		all.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (MyCheckBox cb : checkBoxes) {
					cb.setSelected(all.isSelected());
				}
			}
		});
		header.add(all);
		add(header, BorderLayout.NORTH);

		ComponentMover CamListHeaderCM = new ComponentMover(this, header);
		
		MyPanel content = new ScrollablePane(new GridBagLayout());
		
		content.setBackground(UIManager.getColor("List.background"));
		if (options.length > 0) {

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.anchor = GridBagConstraints.NORTHWEST;
			gbc.weightx = 1;
			for (int index = 0; index < options.length - 1; index++) {
				MyCheckBox cb = new MyCheckBox(options[index]);
				cb.setOpaque(false);
				checkBoxes.add(cb);
				content.add(cb, gbc);
			}
			// ovu dodajemo posebno jer je potrebno da joj se odredi i
			// weightY - atribut koji govori
			/*
			 * Ako je rezultujuci lejaut manji po vertikali nego povrsina u koju
			 * treba da stane, dodatni prostor se dijeli medju redovima u skladu
			 * sa njihovom weightY. Red koji ima weightY = 0 ne dobija dodatni
			 * prostor.
			 */
			MyCheckBox cb = new MyCheckBox(options[options.length - 1]);
			cb.setOpaque(false);
			checkBoxes.add(cb);
			gbc.weighty = 1;
			content.add(cb, gbc);
		}
		add(new JScrollPane(content));

		ComponentMover CamListContentCM = new ComponentMover(this, content);
//		myCompMov.registerComponent(components);
	}
	
	public static CameraList getInstance() {
		if (instance == null) {
			instance = new CameraList();
		}
		return instance;
	}

	public class CheckboxListCellRenderer extends MyCheckBox implements
			ListCellRenderer {

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {

			setComponentOrientation(list.getComponentOrientation());
			setFont(list.getFont());
			setBackground(list.getBackground());
			setForeground(list.getForeground());
			setSelected(isSelected);
			setEnabled(list.isEnabled());

			return this;
		}
	}
}
