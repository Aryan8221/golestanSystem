package professor;

import amoozeshKol.amoozeshKolAccount;

import java.sql.*;
import java.util.ArrayList;

public class professorAccount {

    public String name;
    public String faculty;
    public String email;
    public String passWord;
    public ArrayList<String> professorCourses = new ArrayList<>();

    public professorAccount(String name, String faculty,  String email, String passWord) {
        this.name = name;
        this.faculty = faculty;
        this.email = email;
        this.passWord = passWord;
    }

    public void coursesList(){
        for (int i = 0; i < amoozeshKolAccount.semesterCourses.size(); i++) {
            if (amoozeshKolAccount.semesterCourses.get(i).professor.equals(this.name)){
                professorCourses.add(amoozeshKolAccount.semesterCourses.get(i).lecture);
            }
        }

        for (String professorCourse : professorCourses) {
            System.out.println(professorCourse);
        }

    }
}
