package gui.model;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class WidgetGUI extends JPanel {
    /**
	 * auto generated serialVersionUID
	 */
	private static final long serialVersionUID = -1163547952273601297L;
	
	/** Ako zatreba, a trebace kasnije za dugmice kojima se prelazi u stanja...mozda ne ovdje... */
	//private static final Dimension dim = new Dimension(40, 80); 
	
	/** If sets <b>TRUE</b> this component is draggable */
    private boolean draggable = true;
    /** 2D Point representing the coordinate where mouse is, relative parent container */
    protected Point anchorPoint;
    /** Default mouse cursor for dragging action */
    protected Cursor draggingCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    /** If sets <b>TRUE</b> when dragging component, it will be painted over each other (z-Buffer change) */
    protected boolean overbearing = false;
    
    public WidgetGUI() {
        addDragListeners();
        draggable = true;
        overbearing = false;
        addClickListeners();
    }

    /**
     * Add Mouse Motion Listener with drag function
     */
    private void addDragListeners() {
        /** This handle is a reference to THIS beacause in next Mouse Adapter "this" is not allowed */
        final WidgetGUI handle = this;
        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                anchorPoint = e.getPoint();
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int anchorX = anchorPoint.x;
                int anchorY = anchorPoint.y;

                Point parentOnScreen = getParent().getLocationOnScreen();
                Point mouseOnScreen = e.getLocationOnScreen();
                Point position = new Point(mouseOnScreen.x - parentOnScreen.x - anchorX, mouseOnScreen.y - parentOnScreen.y - anchorY);
                setLocation(position);
                
                if (overbearing) {
                	System.out.println("EVO overbearing 11\n");
                    getParent().setComponentZOrder(handle, 0);
                    repaint();
                }
            }
        });
    }


    /**
     * Remove all Mouse Motion Listener. Freeze component.
     */
    private void removeDragListeners() {
        for (MouseMotionListener listener : this.getMouseMotionListeners()) {
            removeMouseMotionListener(listener);
        }
        setCursor(Cursor.getDefaultCursor());
    }

    /**
     * Get the value of draggable
     *
     * @return the value of draggable
     */
    public boolean isDraggable() {
        return draggable;
    }

    /**
     * Set the value of draggable
     *
     * @param draggable new value of draggable
     */
    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
        if (draggable) {
            addDragListeners();
        } else {
            removeDragListeners();
        }

    }

    /**
     * Get the value of draggingCursor
     *
     * @return the value of draggingCursor
     */
    public Cursor getDraggingCursor() {
        return draggingCursor;
    }

    /**
     * Set the value of draggingCursor
     *
     * @param draggingCursor new value of draggingCursor
     */
    public void setDraggingCursor(Cursor draggingCursor) {
        this.draggingCursor = draggingCursor;
    }

    /**
     * Get the value of overbearing
     *
     * @return the value of overbearing
     */
    public boolean isOverbearing() {
        return overbearing;
    }

    /**
     * Set the value of overbearing
     * @param overbearing new value of overbearing
     */
    public void setOverbearing(boolean overbearing) {
        this.overbearing = overbearing;
    }
    
    /**
     * Add Mouse Clicked Listener with function which opens JOptionPane
     */
    protected void addClickListeners() {
    	/** This handle is a reference to THIS beacause in next Mouse Adapter "this" is not allowed */
//        final WidgetGUI handle = this;
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked (number of clicks: " + e.getClickCount() + ") "+ (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)));

				if (SwingUtilities.isLeftMouseButton(e)) {
					System.out.println("Lijevi klik\n");
				}
				
				if (SwingUtilities.isRightMouseButton(e)) {
					System.out.println("Desni klik\n");
				}
				if (SwingUtilities.isMiddleMouseButton(e)) {
					System.out.println("Srednji klik\n");
				}

                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                	popoutPane();
                }
                revalidate();
                repaint();
             }
        });
    }
    
    private void popoutPane() {
        JLabel messageLabel = new JLabel("Prikaz parametara widget-a nad kojim je izvršen dvoklik.");
        JPanel panel = new JPanel();
        panel.add(messageLabel); 
        
        // postavljanje 'OK' i 'Cancel' dugmadi
        int result = JOptionPane.showConfirmDialog(null, panel, "Properties",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            System.out.println("Završena izmjena parametara.");
        } else {
            System.out.println("Otkazana izmjena parametara.");
        }
    }
}

