package student;


import amoozeshKol.Course;
import amoozeshKol.Message;
import amoozeshKol.amoozeshKolAccount;

import java.util.ArrayList;
import java.util.HashMap;

public class daneshjooAccount {
    public String name;
    public int studentID;
    public String field;
    public String faculty;
    public int semester;
    public String email;
    public String passWord;
    public double lastSemesterGPA;
    public boolean submitCourses;
    public boolean emergencyRemoveUsed;
    public ArrayList<Course> courses = new ArrayList<>();// for "Schedule" function
    public ArrayList<Message> messages= new ArrayList<>();

    public daneshjooAccount(String name, int studentID, String field, String faculty, int semester, String email, String passWord, double lastSemesterGPA) {
        this.name = name;
        this.studentID = studentID;
        this.field = field;
        this.faculty = faculty;
        this.semester = semester;
        this.email = email;
        this.passWord = passWord;
        this.lastSemesterGPA = lastSemesterGPA;
        this.submitCourses = false;
        this.emergencyRemoveUsed = false;
    }

    public void scores(){
        for (Course course: courses) {
            System.out.println(course.lecture + "--->" + course.score);
        }
    }

}
