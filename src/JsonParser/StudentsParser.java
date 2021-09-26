package JsonParser;

import java.io.*;
import java.util.*;

/*"Парсировщик" json файла для нахождение значений из данных самого файла*/
public class StudentsParser {
    public Set<Students> studentsSet;
    Students students1 = new Students();
    Students students2 = new Students();
    Students students3 = new Students();
    String firstStudentName = "Student1";
    String secondStudentName = "Student2";
    String thirdStudentName = "Student3";

    public StudentsParser() {

    }

    public Set<Students> parse() {
        /*Путь к файлу*/
        String fileName = "C:\\Users\\User\\Desktop\\ClientTeskTask\\students.json";
        try {
            studentsSet = new HashSet<>(); //в качестве реализации интерфейса Set была выбрана коллекция HashSet
            FileReader fileReader = new FileReader(fileName); //подключение потоков к файлу и нахождение значений
            Scanner scannerFirst = new Scanner(new FileInputStream(fileName));
            while (scannerFirst.hasNextLine()) {
                String line = scannerFirst.nextLine();

                if (line.indexOf(firstStudentName)!=-1) {
                    Integer i = students1.setStudentID(1);
                    String s = students1.setStudentName("Student1");
                    students1 = new Students(i, s);
                    studentsSet.add(students1);
                }
                if (line.indexOf(secondStudentName)!=-1) {
                    Integer i2 = students2.setStudentID(2);
                    String s2 = students2.setStudentName("Student2");
                    students2 = new Students(i2, s2);
                    studentsSet.add(students2);
                }
                if (line.indexOf(thirdStudentName)!=-1) {
                    Integer i3 = students3.setStudentID(3);
                    String s3 = students3.setStudentName("Student3");
                    students3 = new Students(i3, s3);
                    studentsSet.add(students3);
                }
                System.out.println(line);

                fileReader.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*Вывод всех данных из studentsSet с помощью stream api*/
        studentsSet.stream().forEach(System.out::println);
        return studentsSet;
    }
}

/*Готовая версия парсинга, но со встроенной библиотекой JSon.simple оставшайся после первого чекаута проекта */
//    public Set<Students> parse(){
//        JSONParser jsonParser = new JSONParser();
//        try {
//            fileReader = new FileReader("students.json");
//            JSONObject jsonObject =(JSONObject) jsonParser.parse(fileReader);
//            JSONArray jsonArray = (JSONArray) jsonObject.get(TAG_STUDENTS);
//            for (Object i : jsonArray){
//                JSONObject studentsObject = (JSONObject) i;
//                String studentName = (String) studentsObject.get(TAG_STUDENTS_NAME);
//                Integer studentID = (Integer) studentsObject.get(TAG_STUDENTS_ID);
//                Students students = new Students(studentID, studentName);
//                studentsSet.add(students);
//            }
//        } catch (Exception e) {
//            System.out.println("Parser has error " + e.toString());
//        }
//        return studentsSet;
//    }
// }