import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class ManagerForm {

    private JPanel pnlManager;
    private JTable tblCalendar;
    private JButton btnNewEvent;
    private JButton btnDay;
    private JButton btnWeek;
    private JButton btnMonth;
    private JToolBar jtbMenu;
    private JScrollPane jspCalendar;
    private JPanel MenuPanel;

    ManagerForm() {

        tblCalendar = new JTable(dataModel);
        jspCalendar = new JScrollPane(tblCalendar);
    }

    TableModel dataModel = new AbstractTableModel() {
        @Override
        public int getRowCount() {
            return 10;
        }

        @Override
        public int getColumnCount() {
            return 10;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return new Integer(rowIndex * columnIndex);
        }
    };

    public JPanel getManagerPanel()
    {
        return pnlManager;
    }


}
