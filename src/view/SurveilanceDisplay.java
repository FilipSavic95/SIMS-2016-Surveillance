package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
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
import model.SecuritySystem;

/**
 * Osnovna klasa za prikaz monitoring widget-a. Trenutno nasljeđuje {@code JPanel},
 * ali će kasnije to biti {@code MyPanel}.
 * 
 * <b>napomena:</b>
 * Default Layout je FLOW. Potrebno je postaviti ga na NULL, da se komponente ne bi pomijerale
 * prilikom promjene veličine kontejnera i ostalih 'opasnih' događaja. 
 * @author Aleksandar
 *
 */
@SuppressWarnings("serial")
public class SurveilanceDisplay extends JPanel {
	
	public ArrayList<CameraGUI> kamere;
	SecuritySystem ss;
	
	public SurveilanceDisplay() {
		kamere = new ArrayList<CameraGUI>();
	}
	
	public SurveilanceDisplay(SecuritySystem sSys) {
		this(); // poziv podrazumijevanog konstruktora radi preglednosti
		this.ss = sSys;
		
		addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) {
				System.out.println("\nPRITISNUU\n");
				
				CameraConfig ccfg = getCameraConfig();
				if (ccfg == null) return; // prekid dodavanja u slucaju Cancel, tj. otkaza
				
				CameraDevice cd = new CameraDevice(new Point2D.Double(e.getX(), e.getY()), ccfg);
				
				// uvežemo je u sistem -- model..view??
				// Razgraničeno i popravljeno u drugom lokalnom paketu !
				System.out.println("\npocinje uvezivanje....");
				ss.register(cd, ccfg.rotationSpeed.speed - 1);
				cd.setSubject(ss);
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected CameraConfig getCameraConfig() {
		JLabel labelCombo = new JLabel("Brzina: "); // labelCombo.setAlignmentX(RIGHT_ALIGNMENT);
		
		String[] items = {"SLOW", "MEDIUM", "FAST"};
        JComboBox combo = new JComboBox(items);
        combo.setSelectedIndex(2); // default je MEDIUM
        
        JLabel labelSightStart = new JLabel("Pocetni ugao okretanja: ");
        JTextField fSightStart = new JTextField("0 - 360");
        JLabel labelSightWidth = new JLabel("Sirina opsega kamere:");
        JTextField fSightWidth = new JTextField("1 - 359");
        /*===== labele i polja za end1 i end2 uglove ====*/
        
        // šest redova - jedan će ostati za razmak nakon dodavanja svih pet parametara !
        JPanel panel = new JPanel(new GridLayout(6, 1));
        panel.add(labelCombo);       	panel.add(combo);
        panel.add(labelSightStart);		panel.add(fSightStart);
        panel.add(labelSightWidth);		panel.add(fSightWidth);
        /*//dodavanje parametara početnih i krajnjih uglova
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
            return null;
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
		
		// nova podešavanja su dostupna
		CameraConfig ccfg = new CameraConfig(rs, RotationDirection.COUNTER_CLK, -50, 180, newSightStart, newSightWidth); 
		return ccfg;
	}
	
	/**
	 * Provjera ispravnosti unesenih vrijednosti.
	 * @param input - sadržaj odgovarajućeg text polja
	 * @param errorSB - {@code stringBuilder} koji sadrži dosadašnju poruku o gređci
	 * @param inputName - naziv parametra koji trenutno provjeravamo
	 * @param defaultValue - podrazumijevana vrijednost koja će biti dodijeljena
	 * ovom parametru ako se ustanovi da {@code input} nije ispravan.
	 * @return
	 */
	private int validateInput (String input, StringBuilder errorSB, String inputName, int defaultValue) {
		int retval = defaultValue;
		try {
			// apsolutna vrijednost radi sigurnosti unosa --- ERROR ALERT !
			retval = Math.abs( Integer.parseInt(input) );
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
	 * paintComponent() vrsi custom crtanje. Da bismo iscrtali naš objekat 
	 * na pravi nacin, pozvaćemo istu metodu od roditeljske klase, čime
	 * pripremamo naš objekat za iscrtavanje dodatnih komponenti.
	 * To delegiramo doDrawing() metodi. --- za sada je izbačeno ! IZBAČENO
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 *
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
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
		System.out.println(getParent().toString());
	}
}
