import javax.swing.*;
import java.sql.*;

public abstract class dbSQLite {

    public static void select() {

        String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\db\\TaskData.db";

        String sql = "SELECT * FROM Tasks";

        System.out.println(url);

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            JList<Object[]> rows = new JList<>();
            while(rs.next())
            {
                System.out.println(String.format("%d, %s, %s, %s, %s", rs.getInt("UID"), rs.getString("TaskName"), rs.getString("TaskDescription"), rs.getString("CreationDate"), rs.getString("DueDate")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
