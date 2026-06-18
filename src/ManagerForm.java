import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import com.github.lgooddatepicker.components.DatePicker;

public class ManagerForm {

    private JPanel pnlManager;
    private JToolBar jtbMenu;
    //gilakebi
    private JButton btnNewEvent;
    private JButton btnDay;
    private JButton btnWeek;
    private JButton btnMonth;
    //cxrili
    private JScrollPane jspTasks;
    private JTable tblTasks;

    public JPanel getManagerPanel()
    {
        return pnlManager;
    }

    public ManagerForm() {

        String sql = "SELECT * FROM Tasks WHERE UID > ?";
        Vector<Object> params = new Vector<>();
        params.add(0);

        Vector<DataModel> data = new Vector<>();
        dbSQLite.select(sql, params, data, Task.class);
        DataModelLists.getInstance().addDataModelList(DataModelListsEnum.TaskData , data);

        // eventebi
        btnNewEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnNewEvent_Clicked();
            }
        });

        btnDay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnDay_Clicked();
            }
        });

        btnWeek.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnWeek_Clicked();
            }
        });

        btnMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnMonth_Clicked();
            }
        });

        // resize eventi
        // ujris zomebis morgeba fanjris zomis shesabamisad
        jspTasks.getViewport().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                jspTasks_Resized();
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        String[] columns = new String[] { "ორშაბათი", "სამშაბათი", "ოთხშაბათი", "ხუთშაბათი", "პარასკევი", "შაბათი", "კვირა" };

        // tableModelis gamocxadeba da ujrebis cvlileba = false;
        DefTableModel tblModel = new DefTableModel(columns);
        Vector<Vector<Integer>> Days = KalenadrisAwyoba_tve();
        for (Vector<Integer> row : Days)
        {
            tblModel.addRow(row);
        }

        tblTasks = new JTable(tblModel);
        jspTasks = new JScrollPane(tblTasks);

        // svetebis gadaadgileba/resize = false;
        tblTasks.getTableHeader().setReorderingAllowed(false);
        tblTasks.getTableHeader().setResizingAllowed(false);

        // erti ujris archeva
        tblTasks.setCellSelectionEnabled(true);
        tblTasks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //

        // ujrashi defaultad shuashi iwereba data
        DefaultTableCellRenderer topLeft = new DefaultTableCellRenderer();
        topLeft.setVerticalAlignment(SwingConstants.TOP);
        topLeft.setHorizontalAlignment(SwingConstants.LEFT);
        for (int i = 0; i < tblTasks.getColumnCount(); i++)
        {
            tblTasks.getColumnModel().getColumn(i).setCellRenderer(topLeft);
        }
    }

    private void btnNewEvent_Clicked()
    {
        Dimension dialogSize = new Dimension(300, 350);

        NewEventDialog damateba = new NewEventDialog();
        damateba.setSize(dialogSize);
        damateba.setResizable(false);
        damateba.setLocationRelativeTo(null);

        damateba.setVisible(true);
    }

    private void btnDay_Clicked()
    {

    }

    private void btnWeek_Clicked()
    {

    }

    private void btnMonth_Clicked()
    {

    }

    private void jspTasks_Resized()
    {
        int rows = tblTasks.getRowCount();

        if (rows > 0)
        {
            int viewportH = jspTasks.getViewport().getHeight();
            int rowHeight = viewportH / rows;
            tblTasks.setRowHeight(rowHeight);
        }
    }

    private static class DefTableModel extends DefaultTableModel {
        public DefTableModel(String[] columns) {
            super(columns, 0);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    private static class arkTable extends JTable {
        public arkTable(DefTableModel defTableModel) {
            super(defTableModel);
        }

        @Override
        public void changeSelection(int row, int col, boolean ctrl, boolean shift) {
            // ?
        }
    }

    private Vector<Vector<Integer>> KalenadrisAwyoba_tve()
    {
        Calendar calendar = new GregorianCalendar();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.APRIL, 1);
        int tvis_pirveli_dge = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (tvis_pirveli_dge == 0)
            tvis_pirveli_dge = 7;
        int tvis_bolo_ricxvi = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Kalendaris shevseba;
        int pirv = tvis_pirveli_dge;
        int ricxvi = 1;
        Vector<Vector<Integer>> Days = new Vector<>();
        while(ricxvi <= tvis_bolo_ricxvi) {

            Days.add(new Vector<>());
            for (int i = 1; i <= 7; i++)
            {
                if (i >= pirv && ricxvi <= tvis_bolo_ricxvi) {
                    Days.lastElement().add(ricxvi);
                    ricxvi++;
                }
                else
                {
                    // Calendar cal = new GregorianCalendar();
                    // cal.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    Calendar cal = (Calendar) calendar.clone();

                    if (i < pirv)
                        cal.add(Calendar.DAY_OF_MONTH, i - pirv);
                    else {
                        cal.add(Calendar.DAY_OF_MONTH, ricxvi - tvis_bolo_ricxvi);
                        ricxvi++;
                    }
                    Days.lastElement().add(cal.get(Calendar.DAY_OF_MONTH));
                }
            }

            pirv = 0;
            calendar.set(Calendar.DAY_OF_MONTH, tvis_bolo_ricxvi);
        }

        return Days;
    }
}
