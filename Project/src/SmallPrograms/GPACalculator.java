package SmallPrograms;
import java.util.Scanner;
public class GPACalculator {
    private final Scanner sc = new Scanner(System.in);
    public double calculateGPA(String[] subjects,
                               double[] credits,
                               int totalCredits,
                               int semester) {
        double totalGradePoints = 0;
        System.out.println("\nSemester " + semester + " GPA Calculation");
        System.out.println("----------------------------------");
        for (int i = 0; i < subjects.length; i++) {
            System.out.println("Enter Grade for " + subjects[i] + " : ");
            String grade = sc.next().toUpperCase();
            totalGradePoints += gradeToPoint(grade) * credits[i];
        }
        double gpa = totalGradePoints / totalCredits;
        System.out.printf("Semester GPA : %.2f%n", gpa);
        return gpa;
    }
    private double gradeToPoint(String grade) {
        switch (grade) {
            case "O":
                return 10;
            case "A+":
                return 9;
            case "A":
                return 8;
            case "B+":
                return 7;
            case "B":
                return 6;
            case "C":
                return 5;
            case "U":
                return 0;
            default:
                System.out.println("Invalid Grade... Taking 0");
                return 0;
        }
    }
}