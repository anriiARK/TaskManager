import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

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
    private JButton btnNextMonth;
    private JButton btnPrevMonth;
    private JLabel lblCalendarDate;

    private static int Weli;
    private static int Tve;
    private static int Dge;
    private static final String[] columns = new String[] { "ორშაბათი", "სამშაბათი", "ოთხშაბათი", "ხუთშაბათი", "პარასკევი", "შაბათი", "კვირა" };
    private static final String[] MonthsGE = new String[] { "იანვარი", "თებერვალი", "მარტი", "აპრილი", "მაისი", "ივნისი", "ივლისი", "აგვისტო", "სექტემბერი", "ოქტომბერი", "ნოემბერი", "დეკემბერი" };
    private static DefTableModel tblModel;

    public ManagerForm() {

        String sql = "SELECT * FROM Tasks WHERE Status = ?";
        Vector<Object> params = new Vector<>();
        params.add(1);

        Vector<DataModel> data = new Vector<>();
        dbSQLite.select(sql, params, data, Task.class);
        DataModelLists.getInstance().addDataModelList(DataModelListsEnum.Task, data);

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

        btnNextMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnNextMonth_Clicked();
            }
        });

        btnPrevMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnPrevMonth_Clicked();
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

    public JPanel getManagerPanel()
    {
        return pnlManager;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here


        Weli = LocalDate.now().getYear();;
        Tve = LocalDate.now().getMonthValue() - 1;
        Dge = 1;
        lblCalendarDate = new JLabel(String.format("%d - %s   ", Weli, MonthsGE[Tve])); // sanam kalendars ar vcvli manam ratomgac ar chans es labeli

        // tableModelis gamocxadeba da ujrebis cvlileba = false;
        tblModel = new DefTableModel(columns);
        tblTasks = new JTable(tblModel);
        jspTasks = new JScrollPane(tblTasks);

        updateCalendar();

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

    private void updateCalendar() {
        tblModel.setRowCount(0);
        Vector<Vector<Integer>> Days = KalenadrisAwyoba_tve();
        for (Vector<Integer> row : Days)
        {
            tblModel.addRow(row);
        }
        tblTasks.setModel(tblModel);
        lblCalendarDate.setText(String.format("%d - %s   ", Weli, MonthsGE[Tve]));
    }

    private void btnNewEvent_Clicked()
    {
        Dimension dialogSize = new Dimension(340, 220);

        NewEventDialog damateba = new NewEventDialog();
        damateba.setSize(dialogSize);
        damateba.setResizable(false);
        damateba.setLocationRelativeTo(null);

        damateba.setVisible(true);

        Task task = damateba.getDialogResult();

        damateba.dispose();

        if (task != null) {
            DataModelLists.getInstance().insertToList(DataModelListsEnum.Task, task);
        }
    }

    private void btnDay_Clicked() { return; }
    private void btnWeek_Clicked() { return; }
    private void btnMonth_Clicked() { return; }

    private void btnNextMonth_Clicked() {
        Tve++;
        if (Tve >= 12) {
            Tve = 0;
            Weli++;
        }
        updateCalendar();
        jspTasks_Resized();
    }

    private void btnPrevMonth_Clicked() {
        Tve--;
        if (Tve < 0) {
            Tve = 11;
            Weli--;
        }
        updateCalendar();
        jspTasks_Resized();
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

    private Vector<Vector<Integer>> KalenadrisAwyoba_tve()
    {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Weli, Tve, Dge);

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
