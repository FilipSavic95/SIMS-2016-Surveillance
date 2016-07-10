package gui.model;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/** @see http://stackoverflow.com/a/13919878/230513 */
public class CheckTable extends CameraList {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7676099587250710265L;
	
	private static final CheckModel model = new CheckModel(50);
    private static final JTable table = new JTable(model) {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1714880095875355040L;

		@Override
        public Dimension getPreferredScrollableViewportSize() {
            return new Dimension(150, 300);
        }
    };

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame f = new JFrame("CheckTable");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setLayout(new GridLayout(1, 0));
                f.add(new JScrollPane(table));
                f.add(new DisplayPanel(model));
                f.pack();
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            }
        });
    }

    private static class DisplayPanel extends JPanel {

        /**
		 * 
		 */
		private static final long serialVersionUID = -2894117513673170894L;
		
		private DefaultListModel dlm = new DefaultListModel();
        private JList list = new JList(dlm);

        public DisplayPanel(final CheckModel model) {
            super(new GridLayout());
            this.setBorder(BorderFactory.createTitledBorder("Checked"));
            this.add(new JScrollPane(list));
            model.addTableModelListener(new TableModelListener() {

                @Override
                public void tableChanged(TableModelEvent e) {
                    dlm.removeAllElements();
                    for (Integer integer : model.checked) {
                        dlm.addElement(integer);
                    }
                }
            });
        }
    }

    private static class CheckModel extends AbstractTableModel {

        /**
		 * 
		 */
		private static final long serialVersionUID = -5607989857473044101L;
		
		private final int rows;
        private List<Boolean> rowList;
        private Set<Integer> checked = new TreeSet<Integer>();

        public CheckModel(int rows) {
            this.rows = rows;
            rowList = new ArrayList<Boolean>(rows);
            for (int i = 0; i < rows; i++) {
                rowList.add(Boolean.FALSE);
            }
        }

        @Override
        public int getRowCount() {
            return rows;
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public String getColumnName(int col) {
            return "Column " + col;
        }

        @Override
        public Object getValueAt(int row, int col) {
            if (col == 0) {
                return row;
            } else {
                return rowList.get(row);
            }
        }

        @Override
        public void setValueAt(Object aValue, int row, int col) {
            boolean b = (Boolean) aValue;
            rowList.set(row, b);
            if (b) {
                checked.add(row);
            } else {
                checked.remove(row);
            }
            fireTableRowsUpdated(row, row);
        }

        @Override
        public Class<?> getColumnClass(int col) {
            return getValueAt(0, col).getClass();
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return col == 1;
        }
    }
}