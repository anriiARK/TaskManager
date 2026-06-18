import java.sql.*;
import java.util.Vector;

public abstract class dbSQLite {

    public static String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\db\\TaskData.db";


    public static <T1 extends DataModel, T2 extends DataModel> void select(String sql, Vector<Object> params, Vector<T1> data, Class<T2> type)
    {
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt  = conn.prepareStatement(sql))
        {
            fillPreparedStatement(stmt, params);

            try (ResultSet rs = stmt.executeQuery())
            {
                while (rs.next()) {
                    //System.out.println(String.format("%d, %s, %s, %s, %s", rs.getInt("UID"), rs.getString("TaskName"), rs.getString("Description"), rs.getString("CreationDate"), rs.getString("DueDate")));

                    T2 task = type.getDeclaredConstructor().newInstance();
                    task.Init(rs);

                    data.add((T1) task);
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

    public static int insert(String sql, Vector<Object> params)
    {
        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            fillPreparedStatement(stmt, params);

            try (ResultSet rs = stmt.executeQuery())
            {
                rs.next();
                return rs.getInt("UID");
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    // update da delete
    public static boolean executeQuery(String sql, Vector<Object> params) {

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            fillPreparedStatement(stmt, params);
            return stmt.execute();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static void fillPreparedStatement(PreparedStatement stmt, Vector<Object> params)
    {
        try {
            for (int i = 1; i <= params.size(); i++) {
                Object object = params.elementAt(i - 1);
                if (object instanceof Integer integer)
                    stmt.setInt(i, integer);
                else if (object instanceof String string)
                    stmt.setString(i, string);
                else if (object instanceof Date date)
                    stmt.setDate(i, date);
                else if (object instanceof Double doublee)
                    stmt.setDouble(i, doublee);
                else if (object instanceof Boolean bool)
                    stmt.setBoolean(i, bool);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
