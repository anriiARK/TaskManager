import javax.swing.*;
import java.awt.*;

import java.sql.*;

import java.util.Vector;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

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