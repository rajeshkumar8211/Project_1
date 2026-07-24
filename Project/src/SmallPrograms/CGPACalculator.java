package SmallPrograms;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class CGPACalculator {
    private final Connection con;
    private final GPACalculator gpaCalculator;
    private final SemesterService semesterService;
    private final Scanner sc = new Scanner(System.in);
    public CGPACalculator(Connection con) {
        this.con = con;
        this.gpaCalculator = new GPACalculator();
        this.semesterService = new SemesterService(con);
    }
    private final int[] semesterCredits = {
            0,22,26,23,22,20,23,16,10
    };
    private final int[] cumulativeCredits = {
            0,22,48,71,93,113,136,152,162
    };
    public void calculateCGPA(Student student) throws SQLException {
        System.out.print("Enter Current Semester : ");
        int currentSemester = sc.nextInt();
        double totalGradePoints = 0;
        for(int sem=1; sem<=currentSemester; sem++){
            System.out.println();
            System.out.println("Semester " + sem);
            System.out.println("-----------------------");
            System.out.print("Do you know Semester GPA (Y/N) : ");
            char choice = sc.next().toUpperCase().charAt(0);
            double semesterGpa;
            if(choice=='Y'){
                System.out.print("Enter GPA : ");
                semesterGpa=sc.nextDouble();
            }
            else{
                semesterGpa=calculateSemester(student,sem);
            }
            totalGradePoints += semesterGpa * semesterCredits[sem];
        }
        double cgpa =
                totalGradePoints /cumulativeCredits[currentSemester];
        saveCGPA(student,cgpa);
        System.out.printf("\nFinal CGPA : %.2f\n",cgpa);
    }
    private double calculateSemester(Student student,
                                     int semester)
            throws SQLException {
        String[] subjects =
                SubjectData.SUBJECTS[semester];
        double[] credits =
                SubjectData.CREDITS[semester];
        int totalCredits =
                semesterCredits[semester];
        double gpa =
                gpaCalculator.calculateGPA(
                        subjects,
                        credits,
                        totalCredits,
                        semester
                );
        semesterService.saveSemester(
                SubjectData.COLUMN_NAMES[semester],
                student,
                gpa
        );
        return gpa;
    }
    private void saveCGPA(Student student,
                          double cgpa) throws SQLException {
        String checkQuery =
                "SELECT register_no FROM cgpa WHERE register_no = ?";
        PreparedStatement checkPs =
                con.prepareStatement(checkQuery);
        checkPs.setString(1, student.getRegisterNo());
        ResultSet rs = checkPs.executeQuery();
        if (rs.next()) {
            updateCGPA(student, cgpa);
        } else {
            insertCGPA(student, cgpa);
        }
    }
    private void updateCGPA(Student student,
                            double cgpa) throws SQLException {
        String sql =
                "UPDATE cgpa SET stud_name = ?, cgpa = ? WHERE register_no = ?";
        PreparedStatement ps =
                con.prepareStatement(sql);
        ps.setString(1, student.getName());
        ps.setDouble(2, cgpa);
        ps.setString(3, student.getRegisterNo());
        ps.executeUpdate();
        System.out.println("CGPA Updated Successfully.");
    }
    private void insertCGPA(Student student,
                            double cgpa) throws SQLException {
        String sql =
                "INSERT INTO cgpa(stud_name, register_no, cgpa) VALUES(?,?,?)";
        PreparedStatement ps =
                con.prepareStatement(sql);
        ps.setString(1, student.getName());
        ps.setString(2, student.getRegisterNo());
        ps.setDouble(3, cgpa);
        ps.executeUpdate();
        System.out.println("CGPA Saved Successfully.");
    }
}