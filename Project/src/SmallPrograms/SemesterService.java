package SmallPrograms;
import java.sql.*;
public class SemesterService {
    private Connection con;
    public SemesterService(Connection con) {
        this.con = con;
    }
    public boolean studentExists(String registerNo) throws SQLException {
        String sql = "SELECT register_no FROM gpa WHERE register_no = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, registerNo);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
    public void saveSemester(String columnName,
                             Student student,
                             double gpa) throws SQLException {
        if (studentExists(student.getRegisterNo())) {
            updateSemester(columnName, student, gpa);
        } else {
            insertSemester(columnName, student, gpa);

        }
    }
    private void updateSemester(String columnName,
                                Student student,
                                double gpa) throws SQLException {
        String sql = "UPDATE gpa SET "
                + columnName +
                " = ?, stud_name = ? WHERE register_no = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDouble(1, gpa);
        ps.setString(2, student.getName());
        ps.setString(3, student.getRegisterNo());
        int rows = ps.executeUpdate();
        if (rows > 0) {
            System.out.println("Semester Updated Successfully.");
        }
    }
    private void insertSemester(String columnName,
                                Student student,
                                double gpa) throws SQLException {
        String sql = "INSERT INTO gpa(register_no,stud_name,"
                + columnName +
                ") VALUES(?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, student.getRegisterNo());
        ps.setString(2, student.getName());
        ps.setDouble(3, gpa);
        ps.executeUpdate();
        System.out.println("Student Added Successfully.");
    }
}