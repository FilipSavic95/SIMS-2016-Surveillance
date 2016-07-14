package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import controler.ComponentMover;

public class ConditionListGUI extends AWidgetGUI {

	private static final long serialVersionUID = 7455335893012389408L;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// g.drawString("CAMERA LIST", 0, 0);
	}

	private static ConditionListGUI instance = null;

	private JCheckBox all;
	private List<JCheckBox> checkBoxes;

	protected ConditionListGUI() {

		String options[] = { "Item 1", "Item 2", "Item 3", "Item 4" };

		checkBoxes = new ArrayList<JCheckBox>(); // promijenio sam ja ovdje sa
													// ovoga:
		// checkBoxes = new ArrayList<>(25);

		setLayout(new BorderLayout());
		JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 1));
		all = new JCheckBox("ConditionListGUI 2...");
		all.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (JCheckBox cb : checkBoxes) {
					cb.setSelected(all.isSelected());
				}
			}
		});
		header.add(all);
		add(header, BorderLayout.NORTH);

//		ComponentMover CamListHeaderCM = new ComponentMover(this, header);
//		myCompMov.registerComponent(components);
		JPanel content = new ScrollablePane(new GridBagLayout());
		content.setBackground(UIManager.getColor("List.background"));
		if (options.length > 0) {

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.anchor = GridBagConstraints.NORTHWEST;
			gbc.weightx = 1;
			for (int index = 0; index < options.length - 1; index++) {
				JCheckBox cb = new JCheckBox(options[index]);
				cb.setOpaque(false);
				checkBoxes.add(cb);
				content.add(cb, gbc);
			}
			// ovu dodajemo posebno jer je potrebno da joj se odredi i weightY
			/**
			 * weightY - atribut koji govori
			 * Ako je rezultujuci lejaut manji po vertikali nego povrsina u koju
			 * treba da stane, dodatni prostor se dijeli medju redovima u skladu
			 * sa njihovom weightY. Red koji ima weightY = 0 ne dobija dodatni
			 * prostor.
			 */
			JCheckBox cb = new JCheckBox(options[options.length - 1]);
			cb.setOpaque(false);
			checkBoxes.add(cb);
			gbc.weighty = 1;
			content.add(cb, gbc);
		}
		add(new JScrollPane(content));

		new ComponentMover(this, content);
//		myCompMov.registerComponent(components);
	}

	public static ConditionListGUI getInstance() {
		if (instance == null) {
			instance = new ConditionListGUI();
		}
		return instance;
	}

	@SuppressWarnings({ "serial", "rawtypes" })
	public class CheckboxListCellRenderer extends JCheckBox implements
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
