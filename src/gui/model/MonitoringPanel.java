package gui.model;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

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
import view.CameraGUI;

public class MonitoringPanel extends MyPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6620834481538984824L;
	
	MonitoringModel mm;
	
public MonitoringPanel() {
		
	}
	
	public MonitoringPanel(MonitoringModel mModel) {
		super();
		this.mm = mModel;
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				System.out.println("\nPRITISNUU\n");
				
				// pravimo model
				CameraConfig ccfg = getCameraConfig();
				if (ccfg == null) return; // prekid dodavanja u slucaju Cancel, tj. otkaza
				CameraDevice cd = new CameraDevice(new Point(e.getX(), e.getY()), ccfg);
				
				// pravimo view
				CameraGUI cg = new CameraGUI( (CameraDevice) cd);
				
				System.out.println("\npocinje uvezivanje....");
				mm.register(cd, 0); // 0 ----> brzina
				widgList.add(cg);
				
				System.out.println("velicina kameraa PRIJE:  "+ getComponentCount() + toString());
				add(cg);
				System.out.println("velicina kameraa POSLIJE:"+ getComponentCount());
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
	 * @return konfiguracija preuzeta od korisnika. Ako je unos parametara
	 * otkazan (kliknuto na Cancel), biće {@code null}.
	 */
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
			// apsolutna vrijednost radi sigurnosti unosa --- ERROR ALERT ! --- ERROR ALERT ! --- ERROR ALERT !
			/*******  --- ERROR ALERT ! --- ERROR ALERT ! --- *******/
			/*******  --- ERROR ALERT ! --- ERROR ALERT ! --- *******/
			retval = Math.abs( Integer.parseInt(input) );
		} catch (Exception exc) {
			errorSB.append("Parametar \"" + inputName + "\" postavljen na "+ defaultValue + ".\n");
		}
		return retval;
	}
	
	public void reactToTimer() {
		/** Tells the layout manager to recalculate the layout (which is necessary when adding components).
		 * This will cause children of the panel to repaint, but may not cause the panel itself to do so.*/
		this.validate();
		this.repaint();
	}
}
