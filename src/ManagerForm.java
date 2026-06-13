import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

public class ManagerForm {

    private JPanel pnlManager;
    private JButton btnNewEvent;
    private JButton btnDay;
    private JButton btnWeek;
    private JButton btnMonth;
    private JToolBar jtbMenu;
    private JTable tblTasks;
    private JScrollPane jspTasks;
    private JPanel MenuPanel;

    public JPanel getManagerPanel()
    {
        return pnlManager;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        String[] columns = new String[] { "ორშაბათი", "სამშაბათი", "ოთხშაბათი", "ხუთშაბათი", "პარასკევი", "შაბათი", "კვირა" };

        Calendar calendar = new GregorianCalendar();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        int tvis_pirveli_dge = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (tvis_pirveli_dge == 0)
            tvis_pirveli_dge = 7;
        int tvis_bolo_ricxvi = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // tableModelis gamocxadeba da ujrebis cvlileba = false;
        DefaultTableModel tblModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Kalendaris shevseba;
        int pirv = tvis_pirveli_dge;
        Integer ricxvi = 1;
        while(ricxvi <= tvis_bolo_ricxvi) {

            Vector<String> Days = new Vector<>();

            for (int i = 1; i <= 7; i++)
            {
                if (i >= pirv && ricxvi <= tvis_bolo_ricxvi) {
                    Days.add(ricxvi.toString());
                    ricxvi++;
                }
                else
                    Days.add("");
            }

            pirv = 0;
            tblModel.addRow(Days);
        }

        tblTasks = new JTable(tblModel);
        jspTasks = new JScrollPane(tblTasks);

        // svetebis gadaadgileba/resize = false;
        tblTasks.getTableHeader().setReorderingAllowed(false);
        tblTasks.getTableHeader().setResizingAllowed(false);

        // ujrashi defaultad shuashi aris data
        DefaultTableCellRenderer topLeft = new DefaultTableCellRenderer();
        topLeft.setVerticalAlignment(SwingConstants.TOP);
        topLeft.setHorizontalAlignment(SwingConstants.LEFT);
        for (int i = 0; i < tblTasks.getColumnCount(); i++)
        {
            tblTasks.getColumnModel().getColumn(i).setCellRenderer(topLeft);
        }

        // ujris zomebis morgeba shesabamisad
        jspTasks.getViewport().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                int rows = tblTasks.getRowCount();

                if (rows > 0)
                {
                    int viewportH = jspTasks.getViewport().getHeight();
                    int rowHeight = viewportH / rows;
                    tblTasks.setRowHeight(rowHeight);
                }
            }
        });
    }

}
