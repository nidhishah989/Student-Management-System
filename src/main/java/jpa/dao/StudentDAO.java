package jpa.dao;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;

import java.util.List;

public interface StudentDAO {

    public List<Student> getAllStudents();
    public Student getStudentByEmail(String email);
    public boolean validateStudent(String email,String password);
    public void registerStudentToCourse(String email,int cid);
    public List<Course> getStudentCourses(String email);

}
