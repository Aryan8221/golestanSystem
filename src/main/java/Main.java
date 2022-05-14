import amoozeshKol.Course;
import amoozeshKol.Message;
import amoozeshKol.amoozeshKolAccount;
import amoozeshKol.SQLConnection;
import student.daneshjooAccount;
import professor.professorAccount;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        SQLConnection sqlConnection = new SQLConnection();// Loading DB

        sqlConnection.DBFaculties(); // adding faculties to ArrayList
        sqlConnection.DBProfessors(); // adding professors to ArrayList
        sqlConnection.DBStudents();// adding students to ArrayList
        sqlConnection.DBCourses();// adding courses to ArrayList
        sqlConnection.DBAmoozeshKol();// adding amoozeshKol account (if exists)

        sqlConnection.closeConnection();
        mainMenu();
    }

    public static void mainMenu(){
        System.out.println("1. Amoozesh kol");
        System.out.println("2. Professor");
        System.out.println("3. Student");

        switch (inputs()) {
            case "1" -> {
                amoozeshKolMenu();
                amoozeshKolDashboard();
            }
            case "2" -> {
                professorMenu();
                mainMenu();
            }
            case "3" -> {
                daneshjooMenu();
                mainMenu();
            }
        }
    }

    public static  void amoozeshKolMenu (){
        if (amoozeshKolAccount.emailPassForAmoozeshKol.size() == 0){ // this "if" statement is for unity of amoozeshKol account
            System.out.println("1. Sing up");
            System.out.println("2. back");

            switch (inputs()) {
                case "1" -> {
                    System.out.println("email: ");
                    String email = inputs();
                    System.out.println("password: ");
                    String password = inputs();

                    SQLConnection sqlConnection = new SQLConnection(); // adding new information to DB
                    sqlConnection.insertAmoozeshKol(email, password);
                    sqlConnection.closeConnection();

                    System.out.println("Account successfully created!");

                    amoozeshKolAccount.emailPassForAmoozeshKol.put(email, password);

                    amoozeshKolMenu();
                }
                case "2" -> mainMenu();
                default -> {
                    System.out.println("Not valid!");
                    amoozeshKolMenu();
                }
            }
        }
        else {
            System.out.println("1. Login");
            System.out.println("2. back");

            switch (inputs()) {
                case "1" -> { // login process
                    System.out.println("email: ");
                    String email = inputs();
                    if (!amoozeshKolAccount.emailPassForAmoozeshKol.containsKey(email)) {
                        System.out.println("NOT valid email! please sign up first");
                        amoozeshKolMenu();
                    } else {
                        System.out.println("password: ");
                        String password = inputs();
                        if (amoozeshKolAccount.emailPassForAmoozeshKol.get(email).equals(password)) {
                            System.out.println("Welcome back president!");
                            amoozeshKolDashboard();
                        } else {
                            System.out.println("Not valid Password!");
                            amoozeshKolMenu();
                        }
                    }
                }
                case "2" -> mainMenu();
                default -> {
                    System.out.println("Not valid!");
                    amoozeshKolMenu();
                }
            }
        }
    }

    public static void amoozeshKolDashboard(){
        System.out.println("1. Make faculty");
        System.out.println("2. New semester");
        System.out.println("3. Messages");
        System.out.println("4. New course");
        System.out.println("5. Log out");

        String input = inputs();

        switch (input) {
            case "1" -> amoozeshKolAccount.makeFaculty();
            case "2" -> {
                amoozeshKolAccount.newSemester(); // System.out.println("new semester name: ");// it must be a number e.g. "1" for first semester and ...
                amoozeshKolDashboard();
            }
            case "3" -> {
                if (amoozeshKolAccount.requests.size() == 0) {
                    System.out.println("You have no request");
                    amoozeshKolDashboard();
                } else {
                    System.out.println("===============");
                    amoozeshKolAccount.showRequests();
                    System.out.println("===============");

                    System.out.println("1. respond");
                    System.out.println("2. back");

                    switch (inputs()) {
                        case "1" -> {
                            System.out.println("which request do you want to respond?");
                            String number = inputs();

                            String index = findingRequestAmoozeshKol(number);

                            if (Integer.parseInt(index) > amoozeshKolAccount.requests.size()) {
                                System.out.println("Not valid number!");
                                amoozeshKolDashboard();
                            }

                            amoozeshKolAccount.requests.remove(Integer.parseInt(number) - 1);
//                    System.out.println("Your message: ");
//                    String respond = inputs();

                            System.out.println("Request number " + number + " successfully responded");
                        }
                        case "2" -> amoozeshKolDashboard();
                    }
                }
            }
            case "4" -> amoozeshKolAccount.newCourse();
            case "5" -> mainMenu();
        }
    }

    public static void professorMenu() {
        System.out.println("1. Sing up");
        System.out.println("2. Login");
        System.out.println("3. back");

        professorAccount sampleProfessor;

        switch (inputs()) {
            case "1" -> { // signup process
                System.out.println("professor name: ");
                String name = inputs();
                System.out.println("professor faculty: ");
                String faculty = inputs();
                if (!checkIfFacultyIsValid(faculty)){
                    System.out.println("Faculty doesn't exist!");
                    professorMenu();
                }
                System.out.println("email: ");
                String email = inputs();

                if (searchingForDuplicateEmails(email, amoozeshKolAccount.emailPassForProfessors)){
                    System.out.println("This email was already taken!");
                } else {
                    System.out.println("password: ");
                    String password = inputs();

                    System.out.println("Account successfully created!");

                    amoozeshKolAccount.emailPassForProfessors.put(email, password);// save email and password in the RAM!
                    sampleProfessor = new professorAccount(name, faculty, email, password);
                    amoozeshKolAccount.professors.add(sampleProfessor); // adding professor to professors list

                    SQLConnection sqlConnection = new SQLConnection();// adding professor to DB
                    sqlConnection.insertProfessor(sampleProfessor);
                    sqlConnection.closeConnection();
                }

                professorMenu();

            }
            case "2" -> { // login process
                System.out.println("email: ");
                String email = inputs();
                if (!amoozeshKolAccount.emailPassForProfessors.containsKey(email)) {
                    System.out.println("NOT valid email! please sign up first");
                    professorMenu();
                } else {
                    System.out.println("password: ");
                    String password = inputs();
                    if (amoozeshKolAccount.emailPassForProfessors.get(email).equals(password)) {
                        System.out.println("Welcome Professor!");
                        professorDashboard(email);
                    } else {
                        System.out.println("Not valid Password!");
                        professorMenu();
                    }
                }
            }
            case "3" -> mainMenu();
        }

    }

    public static void professorDashboard (String email){
        professorAccount sampleProfessor = findProfessorNameFromEmail(email);

        System.out.println("1. My courses' list");
        System.out.println("2. logout");
        System.out.println("3. edit info");
        String input = inputs();

        switch (input) {
            case "1" -> {
                assert sampleProfessor != null;
                if (sampleProfessor.professorCourses.size() != 0) {
                    System.out.println("-----------------");
                    for (professorAccount professor : amoozeshKolAccount.professors) {
                        if (professor.name.equals(sampleProfessor.name)) {
                            professor.coursesList(); // printing professor's courses
                        }
                    }
                    System.out.println("-----------------");
                    System.out.println("1. editing Students scores");
                    System.out.println("2. back");
                    switch (inputs()) {
                        case "1" -> {
                            System.out.println("In which course do you want to edit students' scores?");
                            String selectedCourse = inputs();

                            System.out.printf("the students of %s course : %n", selectedCourse);
                            assert sampleProfessor != null;
                            ArrayList<daneshjooAccount> courseStudents = printingAndCreatingArrayOfSelectedCourseStudents(selectedCourse, sampleProfessor.name);

                            System.out.println("which student's score do you want to edit? ");
                            String studentName = inputs();
                            if (!checkIfStudentIsValid(studentName, courseStudents)) {
                                System.out.println("NOT valid student!");
                                professorDashboard(email); // returning to professor dashboard!
                            }
                            System.out.println("type the score:");
                            int score = Integer.parseInt(inputs());

                            submittingScore(score, studentName, selectedCourse, sampleProfessor.name);
                            System.out.printf("%d for %s in %s course %n", score, studentName, selectedCourse);
                            professorDashboard(email);
                        }
                        case "2" -> professorDashboard(email);
                    }
                } else {
                    System.out.println("you have 0 course!");
                    professorDashboard(email);
                }
            }

            case "2" -> professorMenu();

            case "3" -> {
                System.out.println("1. changing email");
                System.out.println("2. changing password");
                System.out.println("3. back");
                switch (inputs()) {
                    case "1" -> {
                        assert sampleProfessor != null;
                        System.out.println("your previous email: " + sampleProfessor.email);
                        System.out.println("New email: ");

                        String newEmail = inputs();
                        sampleProfessor.email = newEmail; // changing email in professors array

                        amoozeshKolAccount.emailPassForProfessors.put(newEmail, sampleProfessor.passWord); // adding new email with the same pass
                        amoozeshKolAccount.emailPassForProfessors.remove(email); // removing old email

                        SQLConnection sqlConnection = new SQLConnection();// changing email in DB
                        sqlConnection.changingProfessorEmail(email, newEmail);
                        sqlConnection.closeConnection();

                        professorDashboard(newEmail);
                    }
                    case "2" -> {
                        System.out.println("Old password: ");
                        String oldPass = inputs();
                        assert sampleProfessor != null;

                        if (!sampleProfessor.passWord.equals(oldPass)){
                            System.out.println("Wrong password!");
                            professorDashboard(email);
                        }

                        System.out.println("New password: ");
                        String newPass = inputs();
                        System.out.println("repeat new password: ");
                        String repeatNewPass = inputs();

                        if (newPass.equals(repeatNewPass)) {
                            sampleProfessor.passWord = newPass; // changing password in professors array

                            amoozeshKolAccount.emailPassForProfessors.remove(sampleProfessor.email);
                            amoozeshKolAccount.emailPassForProfessors.put(sampleProfessor.email, newPass);

                            SQLConnection sqlConnection = new SQLConnection();// changing password in DB
                            sqlConnection.changingProfessorPass(email, newPass);
                            sqlConnection.closeConnection();
                        } else{
                            System.out.println("Not matched!");
                        }

                        professorDashboard(email);
                    }
                    case "3" -> professorDashboard(email);
                }
            }
        }
    }

    public static void daneshjooMenu () {
        Random random = new Random();

        System.out.println("1. Sing up");
        System.out.println("2. Login");
        System.out.println("3. back");

        String name;// used in "else if" statement
        daneshjooAccount sampleStudent;

        switch (inputs()) {
            case "1" -> {
                double previousGPA = -1;

                System.out.println("Student name: ");
                name = inputs();
                int studentID = Integer.parseInt("400" + String.format("%d", random.nextInt(10000)));
                System.out.println("Student field: ");
                String field = inputs();
                System.out.println("Student faculty: ");
                String faculty = inputs();

                if (!checkIfFacultyIsValid(faculty)) {
                    System.out.println("Faculty doesn't exist!");
                    daneshjooMenu();
                }

                System.out.println("Semester: ");
                int semester = Integer.parseInt(inputs());

                if (semester > 1) {
                    System.out.println("What was your previous semester's GPA? :"); // needed for register process and units' limitation
                    previousGPA = Integer.parseInt(inputs());
                }


                System.out.println("email: ");
                String email = inputs();

                if (searchingForDuplicateEmails(email, amoozeshKolAccount.emailPassForStudents)){
                    System.out.println("This email was already used!");
                } else {
                    System.out.println("password: ");
                    String passWord = inputs();

                    System.out.println("Account successfully created!");

                    amoozeshKolAccount.emailPassForStudents.put(email, passWord); // adding student email and pass to ArrayList
                    sampleStudent = new daneshjooAccount(name, studentID, field, faculty, semester, email, passWord, previousGPA);
                    amoozeshKolAccount.students.add(sampleStudent); // adding student to students list

                    SQLConnection sqlConnection = new SQLConnection(); // adding new information to DB
                    sqlConnection.insertStudent(sampleStudent);
                    sqlConnection.closeConnection();
                }

                daneshjooMenu();

            }
            case "2" -> { // login process
                System.out.println("email: ");
                String email = inputs();
                if (!amoozeshKolAccount.emailPassForStudents.containsKey(email)) {
                    System.out.println("NOT valid email! please sign up first");
                    daneshjooMenu();
                } else {
                    System.out.println("password: ");
                    String password = inputs();
                    if (amoozeshKolAccount.emailPassForStudents.get(email).equals(password)) {
                        System.out.println("Welcome back " + findStudentNameFromEmail(email));

                        int maxCourseUnit = 0;
                        int minCourseUnit = 0;

                        for (daneshjooAccount student : amoozeshKolAccount.students) { // finding student!
                            if (student.email.equals(email)) {
                                maxCourseUnit = listOfCoursesUnitsLimitation(student.semester, student.lastSemesterGPA).get(0); // initializing maxCourseUnit
                                minCourseUnit = listOfCoursesUnitsLimitation(student.semester, student.lastSemesterGPA).get(1); // initializing minCourseUnit
                                break;
                            }
                        }

                        daneshjooDashboard(email, maxCourseUnit, minCourseUnit);

                    } else {
                        System.out.println("Not valid Password!");
                        daneshjooMenu();
                    }
                }
            }
            case "3" -> mainMenu();
        }
    }

    public static void daneshjooDashboard (String email, int maxCourseUnit, int minCourseUnit){

        System.out.println("1. Register");
        System.out.println("2. Remove course");
        System.out.println("3. Emergency remove");
        System.out.println("4. Scores");
        System.out.println("5. Schedule");
        System.out.println("6. Send message");
        System.out.println("7. My messages");
        System.out.println("8. Edit info");
        System.out.println("9. Logout");

        daneshjooAccount sampleStudent = null;// It can't be empty after passing the below "for" statement!
        for (daneshjooAccount student: amoozeshKolAccount.students) {
            if (student.email.equals(email)){
                sampleStudent = student;
            }
        }

        switch (inputs()) {
            case "1" -> {
                assert sampleStudent != null;
                if (sampleStudent.submitCourses == false) { // not submitted yet
                    int studentUnits = InitializingStudentUnit(sampleStudent);

                    if (sampleStudent.courses.size() > 0) {
                        System.out.println("do you want to submit these courses? (y/n)");

                        if (inputs().equals("y")) {
                            if (studentUnits >= minCourseUnit) { // check if student reaches the min units
                                sampleStudent.submitCourses = true;
                                daneshjooDashboard(email, maxCourseUnit, minCourseUnit);
                            } else {
                                System.out.println("you didnt reach the min units.....There are " + (minCourseUnit - studentUnits) + " units left!");
                            }
                        }
                    }


                    for (Course course : amoozeshKolAccount.semesterCourses) {
                        System.out.println(course.lecture + " ---> " + course.professor + " ---> " + course.units);
                    }
                    do {
                        if (studentUnits >= maxCourseUnit) {
                            System.out.println("You can't add another course to your courses because you exceed the max units!");
                            break;
                        }
                        if (studentUnits < minCourseUnit) {
                            System.out.printf("there are %d units left to reach the min units! %n", minCourseUnit - studentUnits);
                        }
                        System.out.println("course: ");
                        String MyCourse = inputs();
                        System.out.println("professor: ");
                        String professor = inputs();

                        if (!checkIfCourseIsValid(MyCourse, professor)) {
                            System.out.println("NOT valid course or professor!");
                        } else {
                            int index = findIndexOfCourseInSemesters(MyCourse, professor);

                            if (checkIfCourseWasAlreadyTaken(MyCourse, professor, sampleStudent)) { //checking for duplicate courses
                                System.out.println("This course was already taken");
                                System.out.println("Student units: " + studentUnits);// printing studentUnits
                            } else {
                                if (studentUnits + amoozeshKolAccount.semesterCourses.get(index).units > maxCourseUnit) {
                                    System.out.println("You can't add this course because of unit limitation!");
                                } else if (studentUnits + amoozeshKolAccount.semesterCourses.get(index).units == maxCourseUnit){

                                    sampleStudent.courses.add(amoozeshKolAccount.semesterCourses.get(index));// adding chosen course and its attributes to student courses
                                    studentUnits += amoozeshKolAccount.semesterCourses.get(index).units;// adding units to studentUnits
                                    System.out.println("Student units: " + studentUnits);// printing studentUnits

                                    System.out.printf("You reach the max units (%d)%n", maxCourseUnit);
                                    break;
                                } else {
                                    sampleStudent.courses.add(amoozeshKolAccount.semesterCourses.get(index));// adding chosen course and its attributes to student courses
                                    studentUnits += amoozeshKolAccount.semesterCourses.get(index).units;// adding units to studentUnits
                                    System.out.println("Student units: " + studentUnits);// printing studentUnits
                                }
                            }
                        }
                        System.out.println("Do you want to add new course? (y/n): ");

                    } while (!inputs().equals("n"));

                    System.out.println("do you want to submit these courses? (y/n)");
                    if (inputs().equals("y")) {
                        if (studentUnits >= minCourseUnit) { // check if student reaches the min units
                            sampleStudent.submitCourses = true;
                        }
                        else {
                            System.out.println("you dont reach the min units.....There are " + (minCourseUnit - studentUnits) + " units left!");
                        }
                    }
                } else {
                    System.out.println("You've submitted your courses");
                }
                daneshjooDashboard(email, maxCourseUnit, minCourseUnit);// returning to student dashboard
            }
            case "2" -> { // remove before submitting
                assert sampleStudent != null;
                if (!sampleStudent.submitCourses) {
                    for (Course course : sampleStudent.courses) {
                        System.out.printf("units of %s : %d%n", course.lecture, course.units);
                    }
                    if (sampleStudent.courses.size() == 0) {
                        System.out.println("Nothing to remove!");
                    } else {
                        do {
                            System.out.println("which course do you want to remove? ");
                            String course = inputs();
                            System.out.println("which professor? ");
                            String professor = inputs();

                            removeCourseIfExists(sampleStudent, course, professor);

                            System.out.println("Do you want to remove another course? (y/n): ");
                        } while (!inputs().equals("n"));
                    }
                }
                else{
                    System.out.println("You've submitted your courses");
                }
                daneshjooDashboard(email, maxCourseUnit, minCourseUnit);// returning to dashboard
            }
            case "3" -> { //emergency removal
                assert sampleStudent != null;
                if (sampleStudent.submitCourses == true) {
                    if (sampleStudent.emergencyRemoveUsed == false) {
                        System.out.println("you can only remove one course from your courses!");
                        for (Course course : sampleStudent.courses) {
                            System.out.println(course.lecture + " ---> " + course.professor);
                        }
                        System.out.println("course:");
                        String course = inputs();
                        System.out.println("professor: ");
                        String professor = inputs();

                        if (!checkIfCourseHasBeenTaken(sampleStudent, course, professor)) {
                            System.out.println("Not valid course or professor!");
                        } else {
                            removeCourseIfExists(sampleStudent, course, professor);
                            sampleStudent.emergencyRemoveUsed = true;
                        }
                    } else {
                        System.out.println("You've used your emergency removal!");
                    }
                } else{
                    System.out.println("Submit your courses first");
                }
                daneshjooDashboard(email, maxCourseUnit, minCourseUnit);
            }
            case "4" -> {
                String studentName = findStudentNameFromEmail(email);
                for (daneshjooAccount student : amoozeshKolAccount.students) {
                    if (student.name.equals(studentName)) {
                        student.scores();
                    }
                }
                System.out.println("1. back");
                if (inputs().equals("1")) {
                    daneshjooDashboard(email, maxCourseUnit, minCourseUnit);
                }
            }
            case "5" -> {
                assert sampleStudent != null;
                if (sampleStudent.courses.size() == 0){
                    System.out.println("Register first!");
                } else{
                    for (Course course : sampleStudent.courses) {
                        System.out.printf("units of %s : %d%n",course.lecture, course.units);
                    }
                }
                daneshjooDashboard(email, maxCourseUnit, minCourseUnit);// returning to student dashboard
            }
            case "6" -> {
                System.out.println("write your message: ");
                String message = inputs();

                System.out.println("To whom do you want to send message?");
                String receiver = inputs();

                assert sampleStudent != null;
                Message message1 = new Message(message, sampleStudent.name, receiver);

                if (receiver.equals("amoozeshkol")) {
                    amoozeshKolAccount.requests.add(message1);
                } else {
                    if (findStudentFromName(receiver) != null){
                        daneshjooAccount sampleReceiver = findStudentFromName(receiver);
                        assert sampleReceiver != null;
                        sampleReceiver.messages.add(message1);

                    } else {
                        System.out.println("Not valid name!");
                        daneshjooDashboard(email, maxCourseUnit, minCourseUnit);
                    }
                }
                daneshjooDashboard(email, maxCourseUnit, minCourseUnit);
            }
            case "7" -> {
                assert sampleStudent != null;
                if (sampleStudent.messages.size() == 0) {
                    System.out.println("You have no request");
                    daneshjooDashboard(email, maxCourseUnit, minCourseUnit);
                } else {
                    System.out.println("===============");
                    for (int i = 1; i <= sampleStudent.messages.size(); i++) {
                        System.out.println(i + ". " + sampleStudent.messages.get(i - 1).txt + " ---> " + sampleStudent.messages.get(i - 1).sender );
                    }
                    System.out.println("===============");

                    System.out.println("1. respond");
                    System.out.println("2. back");

                    switch (inputs()) {
                        case "1" -> {
                            System.out.println("which request do you want to respond?");
                            String number = inputs();

                            String index = findingMessage(sampleStudent, number);

                            if (Integer.parseInt(index) > sampleStudent.messages.size()) {
                                System.out.println("Not valid number!");
                                daneshjooDashboard(email, maxCourseUnit, minCourseUnit);
                            }

                            sampleStudent.messages.remove(Integer.parseInt(number) - 1);
//                    System.out.println("Your message: ");
//                    String respond = inputs();

                            System.out.println("Request number " + number + " successfully responded");

                            daneshjooDashboard(email, maxCourseUnit, minCourseUnit);
                        }
                        case "2" -> amoozeshKolDashboard();
                    }
                }
            }
            case "8" -> {
                System.out.println("1. changing email");
                System.out.println("2. changing password");
                switch (inputs()) {
                    case "1" -> {
                        assert sampleStudent != null;
                        System.out.println("your previous email: " + sampleStudent.email);
                        System.out.println("New email: ");

                        String newEmail = inputs();
                        sampleStudent.email = newEmail; // changing email

                        amoozeshKolAccount.emailPassForStudents.put(newEmail, sampleStudent.passWord); // adding new email with the same pass
                        amoozeshKolAccount.emailPassForStudents.remove(email); // removing old email

                        SQLConnection sqlConnection = new SQLConnection();
                        sqlConnection.changingStudentEmail(email, newEmail);
                        sqlConnection.closeConnection();

                        daneshjooDashboard(newEmail, maxCourseUnit, minCourseUnit);

                    }
                    case "2" -> {
                        System.out.println("Old password: ");
                        String oldPass = inputs();
                        assert sampleStudent != null;

                        if (!sampleStudent.passWord.equals(oldPass)){
                            System.out.println("Wrong password!");
                            daneshjooDashboard(email, maxCourseUnit, minCourseUnit);
                        }

                        System.out.println("New password: ");
                        String newPass = inputs();
                        System.out.println("repeat new password: ");
                        String repeatNewPass = inputs();

                        if (newPass.equals(repeatNewPass)) {
                            sampleStudent.passWord = newPass; // changing password

                            amoozeshKolAccount.emailPassForStudents.remove(sampleStudent.email);
                            amoozeshKolAccount.emailPassForStudents.put(sampleStudent.email, newPass);

                            SQLConnection sqlConnection = new SQLConnection();
                            sqlConnection.changingStudentPass(email, newPass);
                            sqlConnection.closeConnection();
                        }

                        daneshjooDashboard(email, maxCourseUnit, minCourseUnit);
                    }
                }
            }
            case "9" -> daneshjooMenu();
        }
    }

    public static boolean checkIfCourseWasAlreadyTaken (String MyCourse, String professor, daneshjooAccount sampleStudent) {
        for (Course course: sampleStudent.courses) {
            if (course.lecture.equals(MyCourse) && course.professor.equals(professor)){
                return true;
            }
        }
        return false;
    }

    public static boolean searchingForDuplicateEmails (String email, HashMap<String, String> emailPassHashMap) {
        return emailPassHashMap.containsKey(email);
    }

    private static int InitializingStudentUnit(daneshjooAccount sampleStudent){ // if someone wants to add some courses to the previous courses
        int studentUnits = 0;
        for (int i = 0; i < sampleStudent.courses.size(); i++) {
            studentUnits += sampleStudent.courses.get(i).units;
        }

        return studentUnits;
    }

    private static boolean checkIfFacultyIsValid(String faculty) {
        for (String sampleFaculty: amoozeshKolAccount.faculties) {
            if (sampleFaculty.equals(faculty)){
                return true;
            }
        }
        return false;
    }

    private static ArrayList<Integer> listOfCoursesUnitsLimitation (int semester, double previousGPA) { // list[0] = max | list[1] = min
        ArrayList<Integer> limitationsList = new ArrayList<>();

        limitationsList.add(20);
        limitationsList.add(12);

        if (semester > 1) {
            if (previousGPA >= 17){
                limitationsList.set(0, 24);
            } else if (previousGPA < 14) {
                limitationsList.set(0, 12);
            }
        }

        return limitationsList;
    }

    private static void submittingScore(int score, String studentName, String selectedCourse, String sampleProfessor) {
        for (daneshjooAccount student: amoozeshKolAccount.students) {
            if (student.name.equals(studentName)){
                for (int i = 0; i < student.courses.size(); i++) {
                    if (student.courses.get(i).lecture.equals(selectedCourse) && student.courses.get(i).professor.equals(sampleProfessor)){
                        student.courses.get(i).score = score;
                    }
                }
            }
        }
    }

    private static ArrayList<daneshjooAccount> printingAndCreatingArrayOfSelectedCourseStudents (String selectedCourse, String sampleProfessor){ // printing students and creating a list of course's students
        ArrayList<daneshjooAccount> courseStudents = new ArrayList<>();

        for (int i = 0; i < amoozeshKolAccount.students.size(); i++) { // searching in students
            for (int j = 0; j < amoozeshKolAccount.students.get(i).courses.size(); j++) { //searching in student's courses
                if (amoozeshKolAccount.students.get(i).courses.get(j).lecture.equals(selectedCourse) && amoozeshKolAccount.students.get(i).courses.get(j).professor.equals(sampleProfessor)){
                    System.out.println(amoozeshKolAccount.students.get(i).name);
                    courseStudents.add(amoozeshKolAccount.students.get(i));
                }
            }
        }

        return  courseStudents;
    }

    private static boolean checkIfStudentIsValid (String studentName, ArrayList<daneshjooAccount> courseStudents) {
        for (daneshjooAccount courseStudent : courseStudents) {
            if (courseStudent.name.equals(studentName)) {
                return true;
            }
        }
        return false;
    }

    private static professorAccount findProfessorNameFromEmail(String email){
        for (professorAccount professor: amoozeshKolAccount.professors) {
            if (professor.email.equals(email)){
                return professor;
            }
        }
        return null;
    }

    private static String findStudentNameFromEmail(String email){
        for (daneshjooAccount student: amoozeshKolAccount.students) {
            if (student.email.equals(email)){
                return student.name;
            }
        }
        return "Student NOT found!";
    }

    private static boolean checkIfCourseIsValid(String course, String professor) {
        for (Course courseSample: amoozeshKolAccount.semesterCourses) {
            if (courseSample.lecture.equals(course) && courseSample.professor.equals(professor)){
                return true;
            }
        }
        return false;
    }

    private static boolean checkIfCourseHasBeenTaken (daneshjooAccount student, String course, String professor) {
        for (Course sampleCourse: student.courses) {
            if (sampleCourse.lecture.equals(course) && sampleCourse.professor.equals(professor)){
                return true;
            }
        }
        return false;
    }

    private static void removeCourseIfExists(daneshjooAccount student, String course, String professor) {
        for (Course courseSample: student.courses) {
            if (courseSample.lecture.equals(course) && courseSample.professor.equals(professor)){
                student.courses.remove(courseSample);
                System.out.println("Course has successfully removed!");
                return;
            }
        }
        System.out.println("NOT valid course!");
    }

    private static int findIndexOfCourseInSemesters (String course, String professor) {
        for (int i = 0; i < amoozeshKolAccount.semesterCourses.size(); i++) {
            if (amoozeshKolAccount.semesterCourses.get(i).lecture.equals(course) && amoozeshKolAccount.semesterCourses.get(i).professor.equals(professor)){
                return i;
            }
        }
        return 110;// ???? It's not reachable :)
    }

    private static daneshjooAccount findStudentFromName (String name) {
        for (daneshjooAccount student: amoozeshKolAccount.students) {
            if (student.name.equals(name)){
                return student;
            }
        }
        return null;
    }

    private static String findingRequestAmoozeshKol (String number) {
        for (Message number1: amoozeshKolAccount.requests) {
            if (Objects.equals(number1.txt.charAt(0), number)){
                return number;
            }
        }
        return "-1";
    }

    private static String findingMessage (daneshjooAccount student, String number) {
        for (Message number1: student.messages) {
            if (Objects.equals(number1.txt.charAt(0), number)){
                return number;
            }
        }
        return "-1";
    }

    public static String inputs(){
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }
}
