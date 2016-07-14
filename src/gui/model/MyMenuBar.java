package gui.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import myAppPackage.MyApp;

public class MyMenuBar implements ActionListener, ItemListener {
	private MyApp myApp;
	public JMenuBar menuBar;
	private JMenu menu, submenu;
	private JMenuItem menuItem;
	private JButton startButton;
	private JButton stopButton;
	private JRadioButtonMenuItem rbMenuItem;
	private JCheckBoxMenuItem cbMenuItem;

	public MyMenuBar(MyApp myMainApp) {
		myApp = myMainApp;
		createMenu();
	}

	private void createMenu() {
		// Glavni meni
		menuBar = new JMenuBar();

		// File podmeni
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menu);

		// grupa JMenuItem-a
		menuItem = new JMenuItem("Open", KeyEvent.VK_O);
		// menuItem.setMnemonic(KeyEvent.VK_O); //moze i ovako
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.CTRL_MASK));
		// obratiti paznju na to da implementiramo ActionListener i ItemListener
		// interfejse
		menuItem.addActionListener(this);
		menu.add(menuItem);

		// grupa JMenuItema
		menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,
				ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		// Edit podmeni
		menu = new JMenu("Edit");
		menu.setMnemonic(KeyEvent.VK_E);
		menuBar.add(menu);

		// grupa JMenuItema
		menuItem = new JMenuItem("Copy", KeyEvent.VK_C);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		// grupa JMenuItema
		menuItem = new JMenuItem("Paste", KeyEvent.VK_P);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		// View podmeni
		menu = new JMenu("View");
		menu.setMnemonic(KeyEvent.VK_V);
		menuBar.add(menu);

		// grupa JMenuItema
		menuItem = new JMenuItem("Change Theme", KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
				ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// izbaci JOptionPane za promjenu teme
				Object[] possibilities = { "Dark", "Bright", "Random" };
				String theme = null;
				theme = (String) JOptionPane.showInputDialog(menu,
						"Odaberite jednu od ponudjenih tema:\n",
						"Theme Chooser", JOptionPane.PLAIN_MESSAGE, null,
						possibilities, "Random");

				if ((theme != null) && (theme.length() > 0)) {
					myApp.changeTheme(theme);
				} else {
					System.out.println("");
				}
			}
		});
		menu.add(menuItem);

		// Help podmeni
		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_H);
		menuBar.add(menu);

		// grupa JMenuItema
		menuItem = new JMenuItem("About", KeyEvent.VK_A);
		menuItem.addActionListener(this);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// izbaci JOptionPane
				String about = "\n";
				about = "SurveillanceSystem\n"
						+ "GraphicalUserInterface(GUI) aplikacija."
						+ "\nVerzija: SSGUI (1.1) \n"
						+ "\n(c) Sva prava zadržana.";
				JOptionPane.showMessageDialog(menu, about);
			}
		});
		//
		menu.add(menuItem);

		// Dugme za start
		startButton = new JButton("►");
		startButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("runSimulaton clicked...");
				startButton.setVisible(false);
				stopButton.setVisible(true);
				myApp.runSimulation();
			}
		});
		menuBar.add(startButton);

		// Dugme za stop
		stopButton = new JButton("| |");
		stopButton.setVisible(false);
		stopButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("stopSimulaton clicked...");
				stopButton.setVisible(false);
				startButton.setVisible(true);
				myApp.stopSimulation();
			}
		});

		menuBar.add(stopButton);

	}

	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
		String s = "Opcija menija:" + source.getText();
		System.out.println(s);
		if (source.getText().equals("Exit"))
			System.exit(0);
	}

	public void itemStateChanged(ItemEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
		String s = "Item event se dogodio:"
				+ source.getText()
				+ ". Stanje: "
				+ ((e.getStateChange() == ItemEvent.SELECTED) ? "selected"
						: "unselected");
		System.out.println(s);
	}
}
