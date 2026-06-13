import javax.swing.*;
import java.awt.*;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class Main {

    public static void main(String[] args) {

        dbSQLite.select();

        Calendar cal = new GregorianCalendar();

        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
        int dge = cal.get(Calendar.DAY_OF_WEEK);

        System.out.println(dge);
        //System.out.println();

        JFrame TaskManager = new JFrame("Task Manager");
        ManagerForm TaskManagerForm = new ManagerForm();

        TaskManager.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        TaskManager.setContentPane(TaskManagerForm.getManagerPanel());

        Dimension minSize = new Dimension();
        minSize.width = 800;
        minSize.height = 600;
        TaskManager.setSize(minSize);
        TaskManager.setLocationRelativeTo(null);
        TaskManager.setMinimumSize(minSize);

        TaskManager.setVisible(true);

    }
}