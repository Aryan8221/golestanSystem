package amoozeshKol;

import java.time.LocalTime;

public class Course {
    public String lecture;
    public String professor;
    public String faculty;
    public int lectureCode;
    public int units;
    public int score;
    public String courseDay;
    public LocalTime startTime;
    public LocalTime ebdTime;

    public Course(String lecture, String professor, String faculty, int lectureCode, int units) {
        this.lecture = lecture;
        this.professor = professor;
        this.faculty = faculty;
        this.lectureCode = lectureCode;
        this.units = units;
        this.score = -1; //If it has not been scored!
    }
}
