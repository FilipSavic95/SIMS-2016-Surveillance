package view;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.CameraConfig;
import model.CameraConfig.RotationDirection;
import model.CameraConfig.RotationSpeed;
import model.MonitoringModel;

/**
 * Osnovna klasa za prikaz monitoring widget-a.
 * <b>napomena:</b>
 * Default Layout je FLOW. Potrebno je postaviti ga na NULL, da se komponente ne bi pomijerale
 * prilikom promjene velicine kontejnera i ostalih 'opasnih' dogadjaja. 
 * @author Aleksandar
 *
 */
@SuppressWarnings("serial")
public class MonitoringPanel extends MyPanel {
	MonitoringModel mm;
	
	public MonitoringPanel() {
		super();
		/** FlowLayout fl = new FlowLayout();     // UPOZORENJE : NULL LAYOUT ! UPOZORENJE : NULL LAYOUT !
		/** BorderLayout bl = new BorderLayout(); // UPOZORENJE : NULL LAYOUT ! UPOZORENJE : NULL LAYOUT !
		/** this.setLayout(null); // http://javadude.com/articles/layouts/#sin1 <== objasnjenje LAYOUTA ! */
	}
	
	public MonitoringPanel(MonitoringModel mModel) {
		this(); // poziv podrazumijevanog konstruktora radi preglednosti
		this.mm = mModel;
	}
	
	/**
	 * Unos konfiguracije kamere preko prilagođenog dijalog-prozora. 
	 * @return konfiguracija preuzeta od korisnika. Ako je unos parametara otkazan, biće {@code null}.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static CameraConfig getCameraConfig() {
JLabel labelCombo = new JLabel("Brzina: "); // labelCombo.setAlignmentX(RIGHT_ALIGNMENT);
		
		String[] items = {"SLOW", "MEDIUM", "FAST"};
        JComboBox combo = new JComboBox(items);
        combo.setSelectedIndex(2); // default je MEDIUM
        
        JLabel labelSightStart = new JLabel("Početni ugao okretanja: ");
        JTextField fSightStart = new JTextField("60");
        JLabel labelSightWidth = new JLabel("Širina opsega kamere:");
        JTextField fSightWidth = new JTextField("60");
        JLabel labelLimitStart = new JLabel("Najmanji dozvoljeni ugao: ");
        JTextField fLimitStart = new JTextField("0");
        JLabel labelLimitWidth = new JLabel("Najveći dozvoljeni ugao:");
        JTextField fLimitWidth = new JTextField("180");
        /*===== labele i polja za end1 i end2 uglove ====*/
        
        // šest redova - jedan za razmak nakon dodavanja svih pet parametara
        JPanel panel = new JPanel(new GridLayout(6, 1));
        panel.add(labelCombo);       	panel.add(combo);
        panel.add(labelSightStart);		panel.add(fSightStart);
        panel.add(labelSightWidth);		panel.add(fSightWidth);
        //dodavanje parametara početnih i krajnjih uglova
        panel.add(labelLimitStart);		panel.add(fLimitStart);
        panel.add(labelLimitWidth);		panel.add(fLimitWidth); 
        
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
        int newSightStart = 0;   // podrazumijevane vrijednosti
		int newSightWidth = 90;  // za parametre kamere
		int newLimitStart = 0;
		int newLimitWidth = 180;
		
		StringBuilder sb = new StringBuilder("Postoje nevalidne vrijednosti parametara.\n");
		
		newSightStart = validateInput(fSightStart.getText(), sb, "početni ugao", 0);
		newSightWidth = validateInput(fSightWidth.getText(), sb, "opseg", 90);
		newLimitStart = validateInput(fLimitStart.getText(), sb, "početak granice", 0);
		newLimitWidth = validateInput(fLimitWidth.getText(), sb, "opseg granice", 180);
		
		if (sb.length() > 53)
			JOptionPane.showMessageDialog(panel, sb.toString());
		
		// nova podesavanja su dostupna
		CameraConfig ccfg = new CameraConfig(rs, RotationDirection.COUNTER_CLK, newLimitStart, newLimitWidth, newSightStart, newSightWidth); 
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
	private static int validateInput (String input, StringBuilder errorSB, String inputName, int defaultValue) {
		int retval = defaultValue;
		try {
			retval = Integer.parseInt(input);
		} catch (Exception exc) {
			errorSB.append("Parametar \"" + inputName + "\" postavljen na "+ defaultValue + ".\n");
		}
		return retval;
	}
	
	public void reactToTimer() {
		/**
		 * Tells the layout manager to recalculate the layout (which is necessary when adding components).
		 * This should cause children of the panel to repaint, but may not cause the panel itself to do so.
		 */
		this.validate();
		this.repaint();
	}
}
