package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import model.MonitoringModel;
import model.states.Initial;
import myAppPackage.MyApp;

/**
 * Sadrži:
 * <ul>
 * <li> jedan Action panel, </li>
 * <li> jedan Monitoring panel, </li>
 * <li> panel sa dugmadima (toolBar) za action i monitoring widget-e.</li>
 * </ul>
 * @author Aleksandar
 *
 */
@SuppressWarnings("serial")
public class MainPanel extends JSplitPane {
	public WidgetGUI selectedWidget;
	
	private JSplitPane mainTbSplitPane;
	private JToolBar actionToolbar, monitorToolbar;
	
	// kanvas 1 i 2
	private JSplitPane actionMonitorPanel;

	// kanvas1
	public ActionPanel actionPanel;

	// kanvas2
	public MonitoringPanel monitoringPanel;
	// model za kanvas2
	public MonitoringModel mModel;

	private int startX, startY;
	
	public MainPanel() {
		super();
		selectedWidget = null;
		initContent();
	}
	
	public MainPanel(Dimension size) {
		this();
		// glavni panel daje 70% sirine panelima, a 30% toolbar-ovima
		int mainPanelDivision = (int) Math.round(size.width*0.7);
		// paneli i toolbarovi zauzimaju polovinu sirine, odnosno visine
		actionMonitorPanel.setDividerLocation((int) Math.round(mainPanelDivision/2));
		mainTbSplitPane.setDividerLocation((int) Math.round(size.height/2) );
		setDividerLocation(mainPanelDivision);
	}

	private void initContent() {
		actionToolbar = new JToolBar("ActionToolbar");
		monitorToolbar = new JToolBar("MonitorToolbar");

		actionPanel = new ActionPanel();
		mModel = new MonitoringModel();
		monitoringPanel = new MonitoringPanel(mModel);
		mModel.setMV(monitoringPanel);
		
		buildToolbox();
		buildMainPanel();
		this.setDividerLocation(0.85);
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
	
	public void addButtonToToolbar(JToolBar toolbar, String buttonName,
			String componentName) {
		JButton button = new JButton(buttonName);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked on: " + buttonName);

				MyApp.getInstance().createState(componentName);
				System.out.println("CUrrent state: " + MyApp.getInstance().getCurrentState() + "\n");
			}
		});
		toolbar.add(button);
	}

	/* Funkcija u kojoj dodajemo Button box-ove na toolbar */
	private void buildToolbox() {
		addButtonToToolbar(actionToolbar, "Add Condition List", "Condition list");
		addButtonToToolbar(actionToolbar, "Add Camera List", "Camera List");
		addButtonToToolbar(actionToolbar, "Add angle changer", "Angle Changer");
		addButtonToToolbar(monitorToolbar, "Add a line", "Line Widget");
		addButtonToToolbar(monitorToolbar, "Add a camera", "Camera Widget");
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
		        	MyApp.getInstance().setCurrentState(new Initial());
		        
				if (MyApp.getInstance().getCurrentState() != null)
					MyApp.getInstance().getCurrentState().drawComponent(startX, startY, 'a');
			}
		});

		monitoringPanel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				System.out.println("-monitor-");
				startX = e.getX();
				startY = e.getY();

		        if (SwingUtilities.isRightMouseButton(e))
		        	MyApp.getInstance().setCurrentState(new Initial());
		        
				if (MyApp.getInstance().getCurrentState() != null)
					MyApp.getInstance().getCurrentState().drawComponent(startX, startY, 'm');
			}
		});

		actionMonitorPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				actionPanel, monitoringPanel);

		actionMonitorPanel.setDividerSize(5);
		actionMonitorPanel.setMinimumSize(new Dimension(200,100));

		actionToolbar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		monitorToolbar.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		actionToolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
		monitorToolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
		actionToolbar.setFloatable(false);
		monitorToolbar.setFloatable(false);

		mainTbSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				actionToolbar, monitorToolbar);

		mainTbSplitPane.setBackground(Color.black);
		mainTbSplitPane.setDividerSize(5);
		mainTbSplitPane.setMinimumSize(new Dimension(100, 200));
		this.setDividerSize(5);
		
		this.setLeftComponent(actionMonitorPanel);
		this.setRightComponent(mainTbSplitPane);
	}
}
