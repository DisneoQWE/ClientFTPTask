import JsonParser.Students;
import JsonParser.StudentsParser;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Client {
    /*Канал общения между клента с сервером*/
    public Socket client = null;

    /*Данные классы нужны для отправки/получения String значений и для улучшения работы патоков*/
    public OutputStreamWriter outputStreamWriter = null;
    public BufferedReader bufferedReader = null;
    public BufferedWriter bufferedWriter = null;

    /*В качестве списка студентов был использован Set, т.к. хранит уникальные значеня*/
    public Set<Students> studentSet;

    public String inputFromUser="";
    public String massageToSend;

    public static void main(String[] args) {
        Client c = new Client();
        c.doConnections();
    }

    public void doConnections() {
        StudentsParser studentsParser = new StudentsParser();
        studentSet = studentsParser.parse();
        try {
            client = new Socket("127.0.0.1", 14148); //подключение к ftp серверу и установка патоков общения с сервером
            InputStreamReader inputStreamReader = new InputStreamReader(client.getInputStream());
            outputStreamWriter = new OutputStreamWriter(client.getOutputStream());
            bufferedReader = new BufferedReader(inputStreamReader); //улучшает скорость работы патоков ввода и вывода засчет стеков
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            Scanner scanner = new Scanner(System.in);
            while (true) { //принимание значений от пользователя в постоянном цикле
                System.out.println("Server. Make a choice: \n1. Get students list \n2. Get info about student with id" +
                        "\n3.Add student \n4.Remove student \n5. Close work ");
                massageToSend = scanner.nextLine(); //считывание значения пользователя и отправка на сервер с последствуещей его реализацией
                inputFromUser = bufferedReader.readLine();
                int i = Integer.parseInt(inputFromUser);
                bufferedWriter.write(massageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                switch (i) {
                    case (1):
                        /*1.	Получение списка студентов по имени*/
                        getStudents();
                        break;
                    case (2):
                        /*2.	Получение информации о студенте по id */
                        System.out.println("Write student's id ");
                        inputFromUser = bufferedReader.readLine();
                        int i1 = Integer.parseInt(inputFromUser);
                        getInfo(i1);
                        break;
                    case (3):
                        /*3.	Добавление студента ( id генерируется автоматически)*/
                        /*Добавление студента не добавляет в самом json нового студента и
                        * и после рестарта проекта нового пользователя не будет*/
                        System.out.println("Write your new student's id ");
                        inputFromUser = bufferedReader.readLine();
                        int i2 = Integer.parseInt(inputFromUser);
                        System.out.println("Write your new student's name ");
                        bufferedReader.readLine();
                        String s = new String(bufferedReader.readLine());
                        addStudent(i2, s);
                        break;
                    case(4):
                        /*4.	Удаление студента по id*/
                        System.out.println("Enter your student's id ");
                        inputFromUser = bufferedReader.readLine();
                        int i3 = Integer.parseInt(inputFromUser);
                        removeStudent(i3);
                    case(5):
                        /*5.	Завершение работы*/
                        bufferedWriter.write("Mister Stark... i cannot feel my leg");
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        break;
                    default:
                        System.out.println("Invalid");
                        break;
                }
            }

        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            try {
                if (client != null) client.close();
                if (outputStreamWriter != null) outputStreamWriter.close();
                if(bufferedReader!=null) bufferedReader.close();
                if(bufferedWriter!=null) bufferedWriter.close();
            } catch (IOException e) {
                System.out.println("Error in doConnection method ");
            }
        }
    }
    public void removeStudent(Integer i){
        try {
            for (Students students : studentSet) {
                if (students.equals(i))
                    studentSet.remove(students);
            }
            for (Students students:
                 studentSet) {
                bufferedWriter.write(students.getStudentID() + " , " + students.getStudentName());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }
        catch (IOException e){
            System.out.println("Error in remove student method");
        }
    }
    public void addStudent(Integer i, String s){
        try {
            studentSet.add(new Students(i, s));
            bufferedWriter.write("New student is added");
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        catch (IOException e){
            System.out.println("Error in addStudent method");
        }
    }
    public void getInfo(Integer i){
        for(Students students: studentSet){
            if(students.getStudentID().equals(i)){
                try {
                    outputStreamWriter.write(students.getStudentName());
                } catch (IOException e) {
                    System.out.println("Error in get information about student method");
                }
            }
        }
    }
    public void getStudents(){
        for(Students i : studentSet){
            try {
                bufferedWriter.write(i.getStudentID() + " , "+i.getStudentName());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                System.out.println("Error in get students   method");
            }
        }
    }
}
