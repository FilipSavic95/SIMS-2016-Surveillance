package view;

import gui.model.MyPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.CameraConfig;
import model.CameraConfig.RotationDirection;
import model.CameraConfig.RotationSpeed;
import model.CameraDevice;
import model.MonitoringModel;

/**
 * Osnovna klasa za prikaz monitoring widget-a. Trenutno nasljedjuje {@code JPanel},
 * ali ce kasnije to biti {@code MyPanel}.
 * 
 * <b>napomena:</b>
 * Default Layout je FLOW. Potrebno je postaviti ga na NULL, da se komponente ne bi pomijerale
 * prilikom promjene velicine kontejnera i ostalih 'opasnih' dogadjaja. 
 * @author Aleksandar
 *
 */
@SuppressWarnings("serial")
public class MonitoringView extends MyPanel {
	
	public ArrayList<CameraGUI> kamere;
	MonitoringModel mm;
	
	public MonitoringView() {
		super();
		kamere = new ArrayList<CameraGUI>();
		//FlowLayout fl = new FlowLayout();
		//BorderLayout bl = new BorderLayout();
		//this.setLayout(null);// http://javadude.com/articles/layouts/#sin1 <<==== objasnjenje LAYOUTA !
		//http://docs.oracle.com/javase/tutorial/uiswing/layout/custom.html <<==== MOJ LAYOUT DA PRAVIM QQ
		//https://docs.oracle.com/javase/tutorial/uiswing/layout/none.html  <<==== NULL layout
//http://stackoverflow.com/questions/24530246/placing-multiple-objects-in-a-region-of-borderlayout <==extends BorderLayout
		//http://stackoverflow.com/questions/19122416/java-swing-jpanel-vs-jcomponent <<==== PREDJI NA JPANEL !!
		//http://stackoverflow.com/questions/3567190/displaying-a-jcomponent-inside-a-jpanel-on-a-jframe <<=== PREDJI !
		//http://www.java2s.com/Questions_And_Answers/Swing/JPanel/JComponent.htm
		//
		// TOP ANSWER
		// http://stackoverflow.com/questions/2155351/swing-jpanel-wont-repaint
	}
	
	public MonitoringView(MonitoringModel mModel) {
		this(); // poziv podrazumijevanog konstruktora radi preglednosti
		this.mm = mModel;
		
		addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) {
				System.out.println("\nPRITISNUU\n");
				
				CameraConfig ccfg = getCameraConfig();
				if (ccfg == null) return; // prekid dodavanja u slucaju Cancel, tj. otkaza
				
				// pravimo model
				CameraDevice cd = new CameraDevice(new Point(e.getX(), e.getY()), ccfg);
				// pravimo view
				CameraGUI cg = new CameraGUI( (CameraDevice) cd);
				
				System.out.println("\npocinje uvezivanje....");
				mm.register(cd, 0); // 0 ----> BRZINAAA !!!
				kamere.add(cg);
				
				//System.out.println("velicina kameraa PRIJE:   "+ getComponentCount() + toString());
				add(cg); // RAZLIKA ?! ((JPanel)e.getComponent()).add(cg); // mora cast jer se reaguje na klik na panel
				//System.out.println("velicina kameraa POSLIJE: "+ getComponentCount());
			}
			
			// ostale metode.. ostavljene za kasnije.. ili nikad.. :)
			public void mouseReleased(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
		});
	}
	
	/**
	 * Unos konfiguracije kamere preko prilagođenog dijalog-prozora. 
	 * @return konfiguracija preuzeta od korisnika. Ako je unos parametara otkazan, biće {@code null}.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CameraConfig getCameraConfig() {
JLabel labelCombo = new JLabel("Brzina: "); // labelCombo.setAlignmentX(RIGHT_ALIGNMENT);
		
		String[] items = {"SLOW", "MEDIUM", "FAST"};
        JComboBox combo = new JComboBox(items);
        combo.setSelectedIndex(2); // default je MEDIUM
        
        JLabel labelSightStart = new JLabel("Pocetni ugao okretanja: ");
        JTextField fSightStart = new JTextField("0 - 360");
        JLabel labelSightWidth = new JLabel("Sirina opsega kamere:");
        JTextField fSightWidth = new JTextField("1 - 359");
        /*===== labele i polja za end1 i end2 uglove ====*/
        
        // sest redova - jedan ce ostati za razmak nakon dodavanja svih pet parametara !
        JPanel panel = new JPanel(new GridLayout(6, 1));
        panel.add(labelCombo);       	panel.add(combo);
        panel.add(labelSightStart);		panel.add(fSightStart);
        panel.add(labelSightWidth);		panel.add(fSightWidth);
        /*//dodavanje parametara pocetnih i krajnjih uglova
        panel.add(labelEndAngle1);		panel.add(fEndAngle1);
        panel.add(labelEndAngle2);		panel.add(fEndAngle2); // kasnije cemo dodati OVA DVA PARAMETRA */ 
        
        // postavljanje 'OK' i 'Cancel' dugmadi
        int result = JOptionPane.showConfirmDialog(null, panel, "Test",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            System.out.println(combo.getSelectedItem()+ " " +
            		fSightStart.getText()+ " " + fSightWidth.getText());
        } else {
            System.out.println("Otkazano dodavanje kamere.");
            return null; // valja ovo?
        }
        
		// pokupimo vrijednosti parametara
        RotationSpeed rs = RotationSpeed.valueOf(combo.getSelectedItem().toString());
        int newSightStart = 0;	// podrazumijevane vrijednosti
		int newSightWidth = 90; // za parametre kamere
		
		StringBuilder sb = new StringBuilder("Postoje nevalidne vrijednosti parametara.\n");
		
		newSightStart = validateInput(fSightStart.getText(), sb, "pocetni ugao", 0);
		newSightWidth = validateInput(fSightWidth.getText(), sb, "opseg", 90);
		// ostali parametri....
		// ostali parametri....
		
		if (sb.length() > 53)
			JOptionPane.showMessageDialog(null, sb.toString());
		
		// nova podesavanja su dostupna
		CameraConfig ccfg = new CameraConfig(rs, RotationDirection.COUNTER_CLK, -50, 180, newSightStart, newSightWidth); 
		return ccfg;
	}
	
	/**
	 * Provjera ispravnosti unesenih vrijednosti.
	 * @param input - sadržaj odgovarajućeg text polja
	 * @param errorSB - {@code stringBuilder} koji sadrži dosadašnju poruku o grešci
	 * @param inputName - naziv parametra koji trenutno provjeravamo
	 * @param defaultValue - podrazumijevana vrijednost koja će biti dodijeljena
	 * ovom parametru ako se ustanovi da {@code input} nije ispravan.
	 * @return
	 */
	private int validateInput (String input, StringBuilder errorSB, String inputName, int defaultValue) {
		int retval = defaultValue;
		try {
			// apsolutna vrijednost radi sigurnosti unosa -- ERROR ALERT ! ERROR ALERT !
			// retval = Math.abs( Integer.parseInt(input) );
			retval = Integer.parseInt(input);
		} catch (Exception exc) {
			errorSB.append("Parametar \"" + inputName + "\" postavljen na "+ defaultValue + ".\n");
		}
		return retval;
	}
	
	@SuppressWarnings("unused")
	private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i < kamere.size(); i++) {
			kamere.get(i).drawMe(g);
		}
	}
	
	/**
	 * paintComponent() vrsi custom crtanje. Da bismo iscrtali nas objekat 
	 * na pravi nacin, pozvacemo istu metodu od roditeljske klase, cime
	 * pripremamo nas objekat za iscrtavanje dodatnih komponenti. 
	 * To delegiramo doDrawing() metodi.
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 *
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//doDrawing(g); // trebalo bi bez ovoga ! ! ! ! ! !
	} // */
	
	public void reactToTimer() {
		//System.out.println("reakcija na tajmer! ");
		// Trazimo kontejner koji ima velicinu 2, cak i poslije dodavanja novih komponenti
		// this.getComponents().length 								je 2 + broj novih
		// getRootPane().getContentPane().getComponents().length	je 1
		// getRootPane().getContentPane().getComponentCount()		je 1
		
		/**
		 * Tells the layout manager to recalculate the layout (which is necessary when adding components).
		 * This should cause children of the panel to repaint, but may not cause the panel itself to do so.
		 */
		this.validate();
		this.repaint();
	}
}
