package gui.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

public class MyMenuBar implements ActionListener, ItemListener {
	public JMenuBar menuBar;
	private JMenu menu, submenu;
    private JMenuItem menuItem;
    private JRadioButtonMenuItem rbMenuItem;
    private JCheckBoxMenuItem cbMenuItem;
    
    public MyMenuBar() {
    	createMenu();
    }
    
    private void createMenu() {
		// TODO Auto-generated method stub
	  
      //Glavni meni
      menuBar = new JMenuBar();

      //File podmeni
      menu = new JMenu("File");
      menu.setMnemonic(KeyEvent.VK_F);
      menuBar.add(menu);

      //grupa JMenuItem-a
      menuItem = new JMenuItem("Open",
                               KeyEvent.VK_O);
      //menuItem.setMnemonic(KeyEvent.VK_O); //moze i ovako
      menuItem.setAccelerator(KeyStroke.getKeyStroke(
              KeyEvent.VK_O, ActionEvent.CTRL_MASK));
      // obratiti paznju na to da implementiramo ActionListener i ItemListener interfejse
      menuItem.addActionListener(this);
      menu.add(menuItem);

      //grupa JMenuItema
      menuItem = new JMenuItem("Exit",
                               KeyEvent.VK_X);
      menuItem.setAccelerator(KeyStroke.getKeyStroke(
              KeyEvent.VK_F4, ActionEvent.ALT_MASK));
      menuItem.addActionListener(this);
      menu.add(menuItem);

      //Edit podmeni
      menu = new JMenu("Edit");
      menu.setMnemonic(KeyEvent.VK_E);
      menuBar.add(menu);

      //grupa JMenuItema
      menuItem = new JMenuItem("Copy",
                               KeyEvent.VK_C);
      menuItem.setAccelerator(KeyStroke.getKeyStroke(
              KeyEvent.VK_C, ActionEvent.CTRL_MASK));
      menuItem.addActionListener(this);
      menu.add(menuItem);
      
      //grupa JMenuItema
      menuItem = new JMenuItem("Paste",
                               KeyEvent.VK_P);
      menuItem.setAccelerator(KeyStroke.getKeyStroke(
              KeyEvent.VK_V, ActionEvent.CTRL_MASK));
      menuItem.addActionListener(this);
      menu.add(menuItem);
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
}
