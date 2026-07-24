package SmallPrograms;
public class Student {
    private String name;
    private String registerNo;
    public Student() {
    }
    public Student(String name, String registerNo) {
        this.name = name;
        this.registerNo = registerNo;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRegisterNo() {
        return registerNo;
    }
    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }
    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", registerNo='" + registerNo + '\'' +
                '}';
    }
}