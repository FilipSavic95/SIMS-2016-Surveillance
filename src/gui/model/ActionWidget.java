package gui.model;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ActionWidget extends Widget {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5728626107152845617L;

	public ActionWidget() {
		super();
		addClickListeners();
	}
	
	/*
	 * - Poslacu ti i kod od option panela, pa ti to 
		samo zapakuj u funkciju i ubaci u eventlistener 
		kad se klikne (mouse clicked, a ne samo MousePressed)
		Jos bolje bi bilo da se taj dijalog koji ti 
		posaljem (za sad samo stavi JOptionPane, lak je 
		poziv, pa ces kasnije zamijeniti) pokrece na DVOklik,
		pa ako hoces proguglucni (nemoj dugo!) kako bi to islo
	 */
	/**
     * Add Mouse Clicked Listener with function which
     * opens JOptionPane
     */
    private void addClickListeners() {
    	/** This handle is a reference to THIS beacause in next Mouse Adapter "this" is not allowed */
//        final Widget handle = this;
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked (number of clicks: " + e.getClickCount() + ")\n");
                if (e.getClickCount() == 2) {
                	popoutPane();
//                	JOptionPane JOptPane = new JOptionPane();
                }
             }

        });
    }
    
    private void popoutPane() {
    	JLabel labelCombo = new JLabel("Brzina: "); // labelCombo.setAlignmentX(RIGHT_ALIGNMENT);
		
		String[] items = {"SLOW", "MEDIUM", "FAST"};
        JComboBox combo = new JComboBox(items);
        combo.setSelectedIndex(2); // default je Medium
        
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
//            return null; // valja ovo?
        }
    }
    	
}
