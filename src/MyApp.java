
/* S T A C K   O V E R   F L O W  LOW */

import gui.model.ActionPanel;
import gui.model.MyMenuBar;
import model.states.Initial;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import view.MonitoringView;
import model.states.Action1;
import model.states.Action2;
import model.states.Action3;
import model.states.Monitor1;
import model.states.Monitor2;
import model.states.State;

/* S T A C K   O V E R   F L O W  LOW */

/**
 * Klasa za testiranje rada aplikacije.<br>
 * Inicijalizuje sve potrebne vrijednosti i pokreće aplikaciju.
 * Ovu klasu treba napraviti kao nasljednicu JFrame-a, a sve funkcije koje
 * popunjavaju panele prebaciti u odgovarajuće klase (tih panela).
 * @author Filip
 *
 */
public class MyApp {
	private static final int NONE = -1;

	private static final int BORDER = 3;

	private MyMenuBar myMBar;
	
	private State currentState;
	// osnovni prozor
	private JFrame frame;

	private JSplitPane mainTbSplitPane, mainPanel;

	private JToolBar actionToolbar, monitorToolbar;

	// kanvas 1 i 2
	private JSplitPane actionMonitorPanel;

	// kanvas1
	private ActionPanel actionPanel;

	// kanvas2
	private MonitoringView monitorPanel;

	private int startX, startY, prevX, prevY;

	private boolean resize;

	public MyApp() {

		currentState = new Initial();
		
		mainPanel = null;
		mainTbSplitPane = null;
		actionMonitorPanel = null;
		
		actionToolbar = new JToolBar("ActionToolbar");
		monitorToolbar = new JToolBar("MonitorToolbar");
		
		actionPanel = new ActionPanel();
		monitorPanel = new MonitoringView();
		
		startX = startY = prevX = prevY = NONE;
		resize = false;
		
		frame = new JFrame("Surveillance System");		
		frame.setBounds(300, 100, 800, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

		buildToolbox();
		buildMainPanel();
		myMBar = new MyMenuBar();
		frame.setJMenuBar(myMBar.menuBar);
		frame.setVisible(true);
	}

/**
   * Mijenja stanje aplikacije u zavisnosti od kliknutog dugmeta (tj. njegovog naziva).
   * POBOLjSANJE (jedno od ovo dvoje):
   * 1) promijeniti imena dugmadi u nazive stanja
   * 2) napraviti mapu koja za kljuceve ima nazive dugmadi, a za vrijednosti nazive
   * odgovarajucih klasa-stanja.
   * 
   * RJESENjE (FINALLY): izbjegavanje switch-case-a pozivom Class.forName(..)
   * http://stackoverflow.com/a/7495850 // na oba linka je isto rjesenje,
   * http://stackoverflow.com/a/1268885 // i oba rade :)
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
	  		currentState = new Monitor1(monitorPanel);
	  		break;
		case "CameraGUI":
			currentState = new Monitor2(monitorPanel);
			break;
	  	default:
	  		System.out.println("\n ======== Default ======== \n");
	  		System.out.println("BUTTON NAME: " + buttonName);
	  		System.out.println("\n ======== Default ======== \n");
	  		break;
	  }
  }
  
  private void addButtonToToolbar(JToolBar toolbar, String buttonName, String componentName) {
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
	  
	  //Minimalna velicina za komponente u SplitPane-u
	  Dimension minimumSize = new Dimension(100, 50);
	  actionPanel.setMinimumSize(minimumSize);
	  monitorPanel.setMinimumSize(minimumSize);
	  
	  actionPanel.setBackground(new Color(255, 255, 102));
	  monitorPanel.setBackground(new Color(204, 255, 102));
	  
	  actionPanel.addMouseListener(new MouseAdapter() {
	      public void mouseReleased(MouseEvent e) {
	        startX = NONE;
	        startY = NONE;
	      }
	      public void mousePressed(MouseEvent e) {
	    	System.out.println("-action-");
	        startX = e.getX();
	        startY = e.getY();
	        
	        if (currentState != null) 
	        	currentState.drawComponent(startX, startY, 'a');
	      }
	    });
	  
	  monitorPanel.addMouseListener(new MouseAdapter() {
	      public void mouseReleased(MouseEvent e) {
	        startX = NONE;
	        startY = NONE;
	      }
	      public void mousePressed(MouseEvent e) {
	    	System.out.println("-monitor-");
	        startX = e.getX();
	        startY = e.getY();
	        if (currentState != null) 
	        	currentState.drawComponent(startX, startY, 'm');
	      }
	    });
	  
	  actionMonitorPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
              actionPanel, monitorPanel);
	  
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
      JMenuItem source = (JMenuItem)(e.getSource());
      String s = "Opcija menija:"
                 + source.getText();
      System.out.println(s);
      if (source.getText().equals("Exit"))
        System.exit(0);
  }

  public void itemStateChanged(ItemEvent e) {
      JMenuItem source = (JMenuItem)(e.getSource());
      String s = "Item event se dogodio:"
                 + source.getText()
                 + ". Stanje: "
                 + ((e.getStateChange() == ItemEvent.SELECTED) ?
                   "selected":"unselected");
      System.out.println(s);
  }
  
  /*
   * frame.addComponentListener(this);
	Finally, catch the different events of these components by using four methods of Component Listener as shown below:
	public void componentHidden(ComponentEvent e) {
	        displayMessage(e.getComponent().getClass().getName() + " --- Hidden");
	    }
	
	    public void componentMoved(ComponentEvent e) {
	        displayMessage(e.getComponent().getClass().getName() + " --- Moved");
	    }
	
	    public void componentResized(ComponentEvent e) {
	        displayMessage(e.getComponent().getClass().getName() + " --- Resized ");            
	    }
   */
  /* nepotrebna funkcija */
  @SuppressWarnings("unused")
private void addComponent(JComponent comp) {
    comp.setBounds(10, 10, 80, 24);

    comp.addMouseListener(new MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
        startX = NONE;
        startY = NONE;
        ((JComponent) e.getSource()).setCursor(Cursor.getDefaultCursor());
      }

      public void mousePressed(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
      }
    });
    
    /* S T A C K   O V E R   F L O W  LOW */
    comp.addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {
        JComponent source = (JComponent) e.getSource();  // komponenta nad kojom radimo
        int x = e.getX();
        int y = e.getY();
        Rectangle bounds = source.getBounds();  // granice komponente nad kojom radimo
        // risajz -=- ako je x ili y u minimalnoj granici ( BORDER-u ) -=- OR-ovi nam govore sa koje strane pomijeramo
        resize = x < BORDER || y < BORDER || Math.abs(bounds.width - x) < BORDER || Math.abs(bounds.height - y) < BORDER;
        if (resize) {
          // TODO: there are a lot of resize cursors here, this is just of proof of concept
          source.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
        } else { // pomijeramo
          source.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        }
      }

      public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (startX != NONE && startY != NONE) { // znaci da je PRITISNUT klik misa.
        										// kad se klik PUSTI, start-ovi su NONE.
          JComponent source = (JComponent) e.getSource();  // komponenta nad kojom radimo
          Rectangle bounds = source.getBounds();  // granice komponente nad kojom radimo
          int deltaX = x - startX;
          int deltaY = y - startY;
          if (resize) {
            // TODO: handle all resize cases, left, right,...
            source.setSize(Math.max(10, bounds.width + x - prevX), Math.max(10, bounds.height + y - prevY));
          } else { // pomijeramo
            source.setLocation(bounds.x + deltaX, bounds.y + deltaY);
          }
          // TODO: make sure you don't resize it as much as it disappears
          // TODO: make sure you don't move it outside the main panel
        } else { // znaci da JE PUSTEN (NIJE PRITISNUT) klik misa i da su start-ovi NONE
          startX = x;
          startY = y;
        }
        prevX = x;
        prevY = y;
      }
    });
    System.out.println("CurrState 318: " + currentState + "\n");
    
  }

  public static void main(String[] args) {
	//SwingWorker<String, MyApp>swingWorker; // parametri ne valjaju vjerovatno...
	  
	/**
	 * Now we want to find the first N prime numbers and display the results in a JTextArea. 
	 * While this is computing, we want to update our progress in a JProgressBar. 
	 * Finally, we also want to print the prime numbers to System.out. 
	 class PrimeNumbersTask extends SwingWorker<List<Integer>, Integer> {
	     PrimeNumbersTask(JTextArea textArea, int numbersToFind) {
	         //initialize
	     }
	
	      @Override
	     public List<Integer> doInBackground() {
	         while (! enough && ! isCancelled()) {
	                 number = nextPrimeNumber();
	                 publish(number);
	                 setProgress(100 * numbers.size() / numbersToFind);
	             }
	         }
	         return numbers;
	     }
	
	      @Override
	     protected void process(List<Integer> chunks) {
	         for (int number : chunks) {
	             textArea.append(number + "\n");
	         }
	     }
	 }
	
	 JTextArea textArea = new JTextArea();
	 final JProgressBar progressBar = new JProgressBar(0, 100);
	 PrimeNumbersTask task = new PrimeNumbersTask(textArea, N);
	 task.addPropertyChangeListener(
	     new PropertyChangeListener() {
	         public  void propertyChange(PropertyChangeEvent evt) {
	             if ("progress".equals(evt.getPropertyName())) {
	                 progressBar.setValue((Integer)evt.getNewValue());
	             }
	         }
	     });
	
	 task.execute();
	 System.out.println(task.get()); //prints all prime numbers we have got

	 */
	  
	new MyApp();
  }

}

/* S T A C K   O V E R   F L O W  LOW */