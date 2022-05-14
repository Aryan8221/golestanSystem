package amoozeshKol;


import professor.professorAccount;
import student.daneshjooAccount;

import java.sql.*;
import java.util.*;

public class amoozeshKolAccount {

    public static ArrayList<Message> requests = new ArrayList<>(); // for student requests

    public static ArrayList<String> faculties = new ArrayList<>();// names of faculties
    public static ArrayList<Course> semesterCourses = new ArrayList<>();// information of courses

    public static ArrayList<professorAccount> professors = new ArrayList<>(); // information of professors
    public static ArrayList<daneshjooAccount> students = new ArrayList<>();// information of students

    public static HashMap<String, String> emailPassForProfessors = new HashMap<>(); // mapping emails to password (Professors)
    public static HashMap<String, String> emailPassForStudents = new HashMap<>(); // mapping emails to password (students)
    public static HashMap<String, String> emailPassForAmoozeshKol = new HashMap<>(); // mapping emails to password (amoozesh kol)

    private static String inputs(){ // for inputs needed in this class
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    public static void makeFaculty(){
        boolean run = true;
        while (run) {
            System.out.println("new faculty name: ");
            String sampleFaculty = inputs();
            if (searchingForDuplicateFaculty(sampleFaculty)){
                System.out.println("This faculty was already defined!");
            } else {
                faculties.add(sampleFaculty);

                SQLConnection sqlConnection = new SQLConnection();
                sqlConnection.insertFaculty(sampleFaculty);
                sqlConnection.closeConnection();
            }
            System.out.println("Do you want to add new faculty? (y/n): ");
            if (inputs().equals("n")){
                run = false;
            }
        }
    }

    public static void newSemester(){
        newCourse();
    }

    public static void newCourse(){
        Random random = new Random();
        while(true) {
            int lectureCode = random.nextInt(100000);
            System.out.println("lecture name: ");
            String lecture = inputs();
            System.out.println("Professor name: ");
            String professor = inputs();

            if (!checkIfProfessorIsValid(professor)){
                System.out.println("professor was NOT defined!");
            } else {
                System.out.println("faculty name: ");
                String faculty = inputs();
                if (!checkIfFacultyIsValid(faculty)) {
                    System.out.println("Faculty doesn't exist!");
                    break;
                }

                System.out.println("number of units: ");
                int units = Integer.parseInt(inputs());

                Course e = new Course(lecture, professor, faculty, lectureCode, units); // temporary object
                semesterCourses.add(e);

                SQLConnection sqlConnection = new SQLConnection();// adding course to DB
                sqlConnection.insertCourses(e);
                sqlConnection.closeConnection();
            }

            System.out.println("Do you want to add new course? (y/n): ");

            if (inputs().equals("n")) {
                break;
            }

        }
    }

    public static void showRequests () {
        for (int i = 1; i <= requests.size() ; i++) {
            System.out.println(i + ". " + requests.get(i - 1).txt + " ---> " + requests.get(i - 1).sender );
        }
    }

    private static boolean checkIfFacultyIsValid(String faculty) {
        for (String sampleFaculty: amoozeshKolAccount.faculties) {
            if (sampleFaculty.equals(faculty)){
                return true;
            }
        }
        return false;
    }

    private static boolean checkIfProfessorIsValid (String professorName) {
        for (professorAccount sampleProfessor: amoozeshKolAccount.professors) {
            if (sampleProfessor.name.equals(professorName)){
                return true;
            }
        }
        return false;
    }

    private static boolean searchingForDuplicateFaculty (String faculty) {
        return amoozeshKolAccount.faculties.contains(faculty);
    }
}
