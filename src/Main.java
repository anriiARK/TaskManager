import javax.swing.*;
import java.awt.*;

import java.sql.DriverManager;
import java.sql.SQLException;


public class Main {

    public static void connect() {

        String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\db\\TaskData.db";

        String sql = "SELECT * FROM Tasks";

        System.out.println(url);

        try (var conn = DriverManager.getConnection(url);
             var stmt  = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {

            while(rs.next())
            {
                System.out.println(rs.getInt("UID") + " " + rs.getString("TaskName") + " " + rs.getString("TaskDescription"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        connect();

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