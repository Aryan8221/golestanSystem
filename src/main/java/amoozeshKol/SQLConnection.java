package amoozeshKol;

import professor.professorAccount;
import student.daneshjooAccount;

import java.sql.*;

public class SQLConnection {
    private Connection connection;


    private void establishConnection() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GolestanSystem", "root", "Aryan123");;
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public SQLConnection()
    {
        this.establishConnection();
    }

    public void insertAmoozeshKol(String email, String password) {
        String sql = "insert into amoozeshKol (email, password) Values (?, ?);";
        try
        {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void insertProfessor(professorAccount professor){
        String sql = "insert into professors (name, faculty, email, passWord) Values (?, ?, ?, ?);";
        try
        {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, professor.name);
            preparedStatement.setString(2, professor.faculty);
            preparedStatement.setString(3, professor.email);
            preparedStatement.setString(4, professor.passWord);

            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void insertStudent(daneshjooAccount student){
        String sql = "insert into students (name, studentId, field, faculty, semester, email, passWord, lastSemesterGPA) VALUE (?, ?, ?, ?, ?, ?, ?, ?);";
        try
        {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, student.name);
            preparedStatement.setInt(2, student.studentID);
            preparedStatement.setString(3, student.field);
            preparedStatement.setString(4, student.faculty);
            preparedStatement.setInt(5, student.semester);
            preparedStatement.setString(6, student.email);
            preparedStatement.setString(7, student.passWord);
            preparedStatement.setDouble(8, student.lastSemesterGPA);

            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void insertFaculty(String faculty) {
        String sql = "insert into faculties (name) VALUES (?)";
        try
        {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, faculty);

            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void insertCourses(Course course) {
        String sql = "insert into courses (lectureName, professor, faculty, lectureCode, units) Values (?, ?, ?, ?, ?);";
        try
        {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, course.lecture);
            preparedStatement.setString(2, course.professor);
            preparedStatement.setString(3, course.faculty);
            preparedStatement.setInt(4, course.lectureCode);
            preparedStatement.setInt(5, course.units);

            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void DBAmoozeshKol() {
        try {
            Statement stmt = connection.createStatement();

            ResultSet am = stmt.executeQuery("select * from GolestanSystem.amoozeshKol");

            while(am.next()){
                amoozeshKolAccount.emailPassForAmoozeshKol.put(am.getString("email"), am.getString("password"));
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void DBProfessors() {
        try {
            Statement stmt = connection.createStatement();

            ResultSet pr = stmt.executeQuery("select * from GolestanSystem.professors");

            while(pr.next()){
                professorAccount prof = new professorAccount(pr.getString("name"), pr.getString("faculty"),
                        pr.getString("email"), pr.getString("password"));
                amoozeshKolAccount.professors.add(prof);
                amoozeshKolAccount.emailPassForProfessors.put(prof.email, prof.passWord);
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void DBStudents() {
        try {
            Statement stmt = connection.createStatement();

            ResultSet st = stmt.executeQuery("select * from GolestanSystem.students");

            while(st.next()){
                daneshjooAccount student = new daneshjooAccount(st.getString("name"), st.getInt("studentId"),
                        st.getString("field"), st.getString("faculty"), st.getInt("semester"),
                        st.getString("email"), st.getString("passWord"), st.getDouble("lastSemesterGPA"));
                amoozeshKolAccount.students.add(student);
                amoozeshKolAccount.emailPassForStudents.put(student.email, student.passWord);
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void DBFaculties() {
        try {
            Statement stmt = connection.createStatement();

            ResultSet fa = stmt.executeQuery("select * from GolestanSystem.faculties");

            while(fa.next()){
                amoozeshKolAccount.faculties.add(fa.getString("name"));
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void DBCourses() {
        try {
            Statement stmt = connection.createStatement();

            ResultSet cr = stmt.executeQuery("select * from GolestanSystem.courses");

            while (cr.next()) {
                Course course = new Course(cr.getString("lectureName"), cr.getString("professor"), cr.getString("faculty"),
                        cr.getInt("lectureCode"), cr.getInt("units"));
                amoozeshKolAccount.semesterCourses.add(course);
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void changingProfessorEmail (String email, String newEmail) {
        String sql = "UPDATE GolestanSystem.professors SET email = ? where email = ?";
        try
        {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, newEmail);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void changingProfessorPass (String email, String newPass) {
        String sql = "UPDATE GolestanSystem.professors SET passWord = ? where email = ?";
        try
        {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, newPass);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void changingStudentEmail (String email, String newEmail) {
        String sql = "UPDATE GolestanSystem.students SET email = ? where email = ?";
        try
        {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, newEmail);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void changingStudentPass (String email, String newPass) {
        String sql = "UPDATE GolestanSystem.students SET passWord = ? where email = ?";
        try
        {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, newPass);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public void closeConnection() {
        try
        {
            this.connection.close();

        }catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
}
