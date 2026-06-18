import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            String _CreationDate = hasColumn(rs, "CreationDate") ? rs.getString("CreationDate") : null;
            String _DueDate = hasColumn(rs, "DueDate") ? rs.getString("DueDate") : null;

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            try {
                CreationDate = formatter.parse(_CreationDate);
                DueDate = formatter.parse(_DueDate);
            } catch (ParseException e) {
                System.out.println("Error: String does not match the expected pattern.");
                e.printStackTrace();
            }
        }
        catch (SQLException ignored) {}

    }
}
