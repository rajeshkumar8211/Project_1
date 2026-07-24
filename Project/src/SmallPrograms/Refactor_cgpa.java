package SmallPrograms;
import java.sql.Connection;
import java.util.Scanner;
public class Refactor_cgpa {
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            Connection con = DBConnection.getConnection();
            if (con == null) {
                System.out.println("Unable to connect to Database.");
                return;
            }
            SemesterService semesterService = new SemesterService(con);
            GPACalculator gpaCalculator = new GPACalculator();
            CGPACalculator cgpaCalculator = new CGPACalculator(con);
            try {
                System.out.println("==============================");
                System.out.println("      GPA / CGPA SYSTEM");
                System.out.println("==============================");
                System.out.print("Enter Student Name : ");
                String name = sc.nextLine();
                System.out.print("Enter Register Number : ");
                String registerNo = sc.nextLine();
                Student student = new Student(name, registerNo);
                while (true) {
                    System.out.println("\n========== MENU ==========");
                    System.out.println("1. Calculate GPA");
                    System.out.println("2. Calculate CGPA");
                    System.out.println("3. Exit");
                    System.out.print("\nEnter Choice : ");
                    int choice = sc.nextInt();
                    switch (choice) {
                        case 1:
                            System.out.print("Enter Semester (1-8) : ");
                            int sem = sc.nextInt();
                            if (sem < 1 || sem > 8) {
                                System.out.println("Invalid Semester.");
                                break;
                            }
                            double gpa =
                                    gpaCalculator.calculateGPA(
                                            SubjectData.SUBJECTS[sem],
                                            SubjectData.CREDITS[sem],
                                            getTotalCredits(sem),
                                            sem
                                    );
                            semesterService.saveSemester(
                                    SubjectData.COLUMN_NAMES[sem],
                                    student,
                                    gpa
                            );
                            break;
                        case 2:
                            cgpaCalculator.calculateCGPA(student);
                            break;
                        case 3:
                            DBConnection.closeConnection(con);
                            System.out.println("Thank You!");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid Choice.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DBConnection.closeConnection(con);
            }
        }
        private static int getTotalCredits(int sem) {
            int[] credits = {
                    0,
                    22,
                    26,
                    23,
                    22,
                    20,
                    23,
                    16,
                    10
            };
            return credits[sem];
        }
}