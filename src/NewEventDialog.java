import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Vector;

public class NewEventDialog extends JDialog {
    private JPanel contentPane;
    private JButton btnOK;
    private JButton btnCancel;
    private JPanel pnlDialog;
    private JTextArea txtDescription;
    private DatePicker dpDueDate;
    private JTextField txtTitle;
    private JScrollPane jspDescription;

    private Task result = null;

    public NewEventDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnOK);

        txtDescription.setLineWrap(true);

        btnOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {

        if (txtTitle.getText().trim().isEmpty())
        {
            JOptionPane.showMessageDialog(contentPane, "მიუთითეთ სახელი!");
            return;
        }

        String sql = "INSERT INTO Tasks (TaskName, Status, CreationDate, DueDate, Description) VALUES (?, ?, ?, ?, ?) RETURNING UID;";
        Vector<Object> params = new Vector<>();

        set_params(params);

//        for (Object obj : params) {
//            System.out.println(obj.toString());
//        }

        int uid = dbSQLite.insert(sql, params);
        setVisible(false);
        result = new Task();
        result.UID = uid;
        result.TaskName = txtTitle.getText();
        result.Status = 1;
        result.CreationDate = new Date();
        result.DueDate = new Date(dpDueDate.getDate().getYear(), dpDueDate.getDate().getMonth().getValue() - 1, dpDueDate.getDate().getDayOfMonth());
        result.Description = txtDescription.getText();
    }

    private void onCancel() {
        setVisible(false);
    }

    private void set_params(Vector<Object> params)
    {
        params.add(txtTitle.getText());
        params.add(1);
        params.add(LocalDate.now().toString());
        params.add(dpDueDate.getDate().toString());
        params.add(txtDescription.getText());
    }

    public Task getDialogResult() {
        return result;
    }

}
