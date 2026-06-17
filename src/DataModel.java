import java.sql.*;

public class DataModel {

    protected static boolean hasColumn(ResultSet rs, String columnName) {
        try {
            rs.findColumn(columnName);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public int UID;
    public String Description;

    public void Init(ResultSet rs)
    {
        try {
            UID = hasColumn(rs, "UID") ? rs.getInt("UID") : -1;
            Description = hasColumn(rs, "Description") ? rs.getString("Description") : "";
        }
        catch(SQLException ignored) {}
    }

}
