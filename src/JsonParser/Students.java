package JsonParser;

/*Класс Students в котором содержатся данные полученные из json файла, а именно studentID и studentName*/
public class Students {
    private Integer studentID;
    private String studentName;
    Students(){
        studentID = 0;
        studentName = "0";
    }
    public Students(Integer studentID, String studentName) {
        this.studentID = studentID;
        this.studentName = studentName;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public Integer setStudentID(Integer studentID) {
        this.studentID = studentID;
        return this.studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public String setStudentName(String studentName) {
        this.studentName = studentName;
        return this.studentName;
    }

    @Override
    public String toString() {
        return "Students{" +
                "studentID=" + studentID +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
