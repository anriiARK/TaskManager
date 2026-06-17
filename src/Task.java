import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Task extends DataModel {

    public String TaskName;
    public int Status;
    public Date CreationDate;
    public Date DueDate;

    @Override
    public void Init(ResultSet rs) {
        super.Init(rs);

        try  {

            TaskName = hasColumn(rs, "TaskName") ? rs.getString("TaskName") : "";
            Status = hasColumn(rs, "Status") ? rs.getInt("Status") : 0;
            CreationDate = hasColumn(rs, "CreationDate") ? rs.getDate("CreationDate") : null;
            DueDate = hasColumn(rs, "DueDate") ? rs.getDate("DueDate") : null;

        }
        catch (SQLException ignored) {}

    }
}
