package myAppPackage;

import gui.model.MyMenuBar;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;

import model.states.Action1;
import model.states.Action2;
import model.states.Action3;
import model.states.Initial;
import model.states.Monitor1;
import model.states.Monitor2;
import model.states.State;
import view.MainPanel;


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
	
	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	private State currentState;

	// osnovni prozor
	private JFrame frame;
	// meni aplikacije
	private MyMenuBar myMBar;
	
	// kontejner za action i montoring panele, te action i monitoring toolbar-ove
	public MainPanel mainPanel;

	public MyApp() {

		setCurrentState(new Initial());

		frame = new JFrame("Surveillance System");
		frame.setBounds(300, 100, 800, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		
		mainPanel = new MainPanel();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		myMBar = new MyMenuBar(this);
		frame.setJMenuBar(myMBar.menuBar);
		
		frame.setVisible(true);
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
	public void createState(String buttonName) {
		switch (buttonName) {
		case "Condition list":
			setCurrentState(new Action1(mainPanel.actionPanel));
			break;
		case "Camera List":
			setCurrentState(new Action2(mainPanel.actionPanel));
			break;
		case "Angle Changer":
			setCurrentState(new Action3(mainPanel.actionPanel));
			break;
		case "Line Widget":
			setCurrentState(new Monitor1(mainPanel.monitoringPanel));
			break;
		case "Camera Widget":
			setCurrentState(new Monitor2(mainPanel.monitoringPanel));
			break;
		default:
			System.out.println("\n ======== Default ======== \n");
			System.out.println("BUTTON NAME: " + buttonName);
			System.out.println("\n ======== Default ======== \n");
			break;
		}
	}

	
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
		String s = "Opcija menija:" + source.getText();
		System.out.println(s);
		if (source.getText().equals("Exit"))
			System.exit(0);
	}

	public void runSimulation() {
		mainPanel.mModel.startTimer();
	}

	public void stopSimulation() {
		mainPanel.mModel.stopTimer();
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