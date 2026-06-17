import java.sql.*;
import java.util.Dictionary;
import java.util.Vector;

public abstract class dbSQLite {

    public static String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\db\\TaskData.db";


    public static <T extends DataModel> void select(String sql, Vector<Object> params, Vector<T> data, Class<T> type) {

        System.out.println(url);

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt  = conn.prepareStatement(sql)) {

            for (int i = 1; i <= params.size(); i++)
            {
                Object object = params.elementAt(i - 1);
                if (object instanceof Integer integer)
                    stmt.setInt(i, integer);
                else if (object instanceof  String string)
                    stmt.setString(i, string);
                else if (object instanceof  Date date)
                    stmt.setDate(i, date);
                else if (object instanceof Double doublee)
                    stmt.setDouble(i, doublee);
                else if (object instanceof  Boolean bool)
                    stmt.setBoolean(i, bool);
            }

            try (ResultSet rs = stmt.executeQuery())
            {
                while (rs.next()) {
                    System.out.println(String.format("%d, %s, %s, %s, %s", rs.getInt("UID"), rs.getString("TaskName"), rs.getString("Description"), rs.getString("CreationDate"), rs.getString("DueDate")));

                    T task = type.getDeclaredConstructor().newInstance();
                    task.Init(rs);

                    data.add(task);
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
