package myAppPackage;
/* S T A C K   O V E R   F L O W  LOW */

import gui.model.ActionPanel;
import gui.model.MyMenuBar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import model.MonitoringModel;
import model.states.Action1;
import model.states.Action2;
import model.states.Action3;
import model.states.Initial;
import model.states.Monitor1;
import model.states.Monitor2;
import model.states.State;
import view.MonitoringView;

/* S T A C K   O V E R   F L O W  LOW */

/**
 * Klasa za testiranje rada aplikacije.<br>
 * Inicijalizuje sve potrebne vrijednosti i pokreće aplikaciju. Ovu klasu treba
 * napraviti kao nasljednicu JFrame-a, a sve funkcije koje popunjavaju panele
 * prebaciti u odgovarajuće klase (tih panela).
 * 
 * @author Filip
 *
 */
public class MyApp {
	public static MyApp instance = null;
	
	public static MyApp getInstance() {
		if (instance == null)
			instance = new MyApp();
		return instance;
	}
	
	private State currentState;

	// osnovni prozor
	private JFrame frame;

	private JSplitPane mainTbSplitPane, mainPanel;
	private MyMenuBar myMBar;
	private JToolBar actionToolbar, monitorToolbar;

	// kanvas 1 i 2
	private JSplitPane actionMonitorPanel;

	// kanvas1
	private ActionPanel actionPanel;

	// kanvas2
	private MonitoringView monitoringPanel;
	// model za kanvas2
	public MonitoringModel mModel;

	private int startX, startY;

	public MyApp() {

		currentState = new Initial();
		
		mainPanel = null;
		mainTbSplitPane = null;
		actionMonitorPanel = null;

		actionToolbar = new JToolBar("ActionToolbar");
		monitorToolbar = new JToolBar("MonitorToolbar");

		actionPanel = new ActionPanel();
		mModel = new MonitoringModel();
		monitoringPanel = new MonitoringView(mModel);
		mModel.setMV(monitoringPanel);

		frame = new JFrame("Surveillance System");
		frame.setBounds(300, 100, 800, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

		buildToolbox();
		buildMainPanel();
		myMBar = new MyMenuBar(this);
		frame.setJMenuBar(myMBar.menuBar);
		frame.setVisible(true);
	}

	public void changeTheme(String theme) {
		switch (theme) {
		case "Dark":
			actionPanel.setBackground(Color.BLACK);
			monitoringPanel.setBackground(Color.DARK_GRAY);
			monitorToolbar.setBackground(Color.DARK_GRAY);
			actionToolbar.setBackground(Color.DARK_GRAY);
			break;
		case "Bright":
			actionPanel.setBackground(Color.WHITE);
			monitoringPanel.setBackground(Color.LIGHT_GRAY);
			monitorToolbar.setBackground(new Color(240, 240, 240));
			actionToolbar.setBackground(new Color(240, 240, 240));
			break;
		case "Random":
			actionPanel.setBackground(new Color(255, 255, 102));
			monitoringPanel.setBackground(new Color(204, 255, 102));
			monitorToolbar.setBackground(new Color(230, 230, 230));
			actionToolbar.setBackground(new Color(230, 230, 230));
			break;
		default:
			System.out.println("Tema: " + theme + "\n");
			break;
		}
	}
	
	/**
	 * Mijenja stanje aplikacije u zavisnosti od kliknutog dugmeta (tj. njegovog
	 * naziva). POBOLjSANJE (jedno od ovo dvoje): 1) promijeniti imena dugmadi u
	 * nazive stanja 2) napraviti mapu koja za kljuceve ima nazive dugmadi, a za
	 * vrijednosti nazive odgovarajucih klasa-stanja.
	 * 
	 * RJESENjE (FINALLY): izbjegavanje switch-case-a pozivom Class.forName(..)
	 * http://stackoverflow.com/a/7495850 // na oba linka je isto rjesenje,
	 * http://stackoverflow.com/a/1268885 // i oba rade :)
	 * 
	 * @param buttonName
	 */
	private void createState(String buttonName) {
		switch (buttonName) {
		case "Widg11":
			currentState = new Action1(actionPanel);
			break;
		case "Widg12":
			currentState = new Action2(actionPanel);
			break;
		case "Widg13":
			currentState = new Action3(actionPanel);
			break;
		case "Widg21":
			currentState = new Monitor1(monitoringPanel);
			break;
		case "CameraGUI":
			currentState = new Monitor2(monitoringPanel);
			break;
		default:
			System.out.println("\n ======== Default ======== \n");
			System.out.println("BUTTON NAME: " + buttonName);
			System.out.println("\n ======== Default ======== \n");
			break;
		}
	}

	private void addButtonToToolbar(JToolBar toolbar, String buttonName,
			String componentName) {
		JButton button = new JButton(buttonName);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked on: " + buttonName);

				createState(componentName);
				System.out.println("CUrrent state: " + currentState + "\n");
			}
		});
		toolbar.add(button);
	}

	/* Funkcija u kojoj dodajemo Button box-ove na toolbar */
	private void buildToolbox() {
		addButtonToToolbar(actionToolbar, "AddW11", "Widg11");
		addButtonToToolbar(actionToolbar, "AddW12", "Widg12");
		addButtonToToolbar(actionToolbar, "AddW13", "Widg13");
		addButtonToToolbar(monitorToolbar, "AddW21", "Widg21");
		addButtonToToolbar(monitorToolbar, "Add a camera", "CameraGUI");
	}

	private void buildMainPanel() {

		// Minimalna velicina za komponente u SplitPane-u
		Dimension minimumSize = new Dimension(100, 50);
		actionPanel.setMinimumSize(minimumSize);
		monitoringPanel.setMinimumSize(minimumSize);

		actionPanel.setBackground(new Color(255, 255, 102));
		monitoringPanel.setBackground(new Color(204, 255, 102));

		actionPanel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				System.out.println("-action-");
				startX = e.getX();
				startY = e.getY();

		        if (SwingUtilities.isRightMouseButton(e))
					  currentState = new Initial();
		        
				if (currentState != null)
					currentState.drawComponent(startX, startY, 'a');
			}
		});

		monitoringPanel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				System.out.println("-monitor-");
				startX = e.getX();
				startY = e.getY();

		        if (SwingUtilities.isRightMouseButton(e))
					  currentState = new Initial();
		        
				if (currentState != null)
					currentState.drawComponent(startX, startY, 'm');
			}
		});

		actionMonitorPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				actionPanel, monitoringPanel);

		actionMonitorPanel.setDividerLocation(250);
		actionMonitorPanel.setDividerSize(5);

		actionToolbar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		actionToolbar.setLayout(new FlowLayout(FlowLayout.LEADING, 1, 1));
		actionToolbar.setFloatable(false);

		monitorToolbar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		monitorToolbar.setLayout(new FlowLayout(FlowLayout.LEADING, 1, 1));
		monitorToolbar.setFloatable(false);

		mainTbSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				actionToolbar, monitorToolbar);

		mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				actionMonitorPanel, mainTbSplitPane);

		mainTbSplitPane.setBackground(Color.black);
		mainTbSplitPane.setDividerLocation(200);
		mainTbSplitPane.setDividerSize(5);

		mainPanel.setDividerLocation(450);
		mainPanel.setDividerSize(5);

		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
		String s = "Opcija menija:" + source.getText();
		System.out.println(s);
		if (source.getText().equals("Exit"))
			System.exit(0);
	}

	public void runSimulation() {
		mModel.startTimer();
	}

	public void stopSimulation() {
		mModel.stopTimer();
	}

	public static void main(String[] args) {
		/**
		 * invokeLater() dodaje zadatak aplikacije u Swing Event Queue.
		 * Ovim osiguravamo da su sva ažuriranja UI-a konkurentno-sigurna.
		 */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				MyApp.getInstance();
			}
		});
	}
}

/* S T A C K O V E R F L O W LOW */