
/* S T A C K   O V E R   F L O W  LOW */

import gui.model.states.Action1;
import gui.model.states.Action2;
import gui.model.states.Action3;
import gui.model.states.Context;
import gui.model.states.Monitor1;
import gui.model.states.Monitor2;
import gui.model.states.State;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
// za niti
import javax.swing.SwingWorker;

/* S T A C K   O V E R   F L O W  LOW */

public class MyApp {
  private static final int NONE = -1;

  private static final int BORDER = 3;

  private State currentState;
  // osnovni prozor
  private JFrame frame = new JFrame("Surveillance System");
  
  //int counter = 0;
  
  private JSplitPane mainTbSplitPane = null;
  
  private JToolBar actionToolbar = new JToolBar("ActionToolbar");

  private JToolBar monitorToolbar = new JToolBar("MonitorToolbar");

  // kanvas0
  private JSplitPane mainPanel = null;

  // kanvas 1 i 2
  private JSplitPane actionMonitorPanel = null;
  
  // kanvas1
  private JPanel actionPanel = new JPanel();
  
  // kanvas2
  private JPanel monitorPanel = new JPanel();

  private int startX = NONE;

  private int startY = NONE;

  private int prevX = NONE;

  private int prevY = NONE;

  private boolean resize = false;

  public MyApp() {
	  /*
	   * TODO: 
	   * Treba dodati actionListener tj. mouseClickedListener
	   * na frame. 
	   * Kada se klik dogodi, onda dodamo na kliknuto mjesto
	   * onaj objekat koji je trenutno selektovan, tj. onaj
	   * u kom je stanju sada nasa aplikacija.
	   */
    frame.setBounds(100, 100, 600, 450);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    frame.getContentPane().setLayout(new BorderLayout());
    
    // ne treba njemu nego svakom panelu po jedan maus lisener
    
    buildToolbox();
    buildMainPanel();
    
    frame.setVisible(true);
  }

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
	  	case "Widg22":
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
          System.out.println("CUrrent state: " + currentState.toString() + "\n");
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
	addButtonToToolbar(monitorToolbar, "AddW22", "Widg22");  
  }

  private void buildMainPanel() {
	  
	  //Provide minimum sizes for the two components in the split pane
	  Dimension minimumSize = new Dimension(100, 50);
	  actionPanel.setMinimumSize(minimumSize);
	  monitorPanel.setMinimumSize(minimumSize);
	  
	  actionPanel.setBackground(Color.BLUE);
	  monitorPanel.setBackground(Color.RED);
	  
	  actionPanel.addMouseListener(new MouseAdapter() {
	      public void mouseReleased(MouseEvent e) {
	        startX = NONE;
	        startY = NONE;
	      }
	      public void mousePressed(MouseEvent e) {
	    	System.out.println("-action-");
	        startX = e.getX();
	        startY = e.getY();
	        
	        if (currentState != null) currentState.drawComponent(startX, startY, 'a');
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
//	        if (startX )
	        if (currentState != null) currentState.drawComponent(startX, startY, 'm');
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

  private void addComponent(JComponent comp) {
	// treba da joj stavim
	//  			x,  y  ==> na mjesto gdje je kliknuo
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
    /*
    if (currentState == State.ACTION1) {
    	actionPanel.add(comp);   
	    //actionPanel.validate();
	    actionPanel.repaint();
    }  
    else {
    	monitorPanel.add(comp);   
    	//monitorPanel.validate();
    	monitorPanel.repaint();
    }
    */
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