package jpa.service;

import jpa.dao.CourseDAO;
import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.util.ConnectionFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class StudentService implements StudentDAO {

    ConnectionFactory connectionfactory = ConnectionFactory.GET_SESSION.getInstance();
    @Override
    public List<Student> getAllStudents() {
        try{
            TypedQuery<Student> typedQuery= connectionfactory.getSession()
                    .createQuery("FROM Student", Student.class);
            return typedQuery.getResultList();
        }catch (NoResultException ne){
            throw new NoResultException();
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        try{
            TypedQuery<Student> typedQuery= connectionfactory.getSession()
                    .createQuery("from Student where email =:email", Student.class);
            typedQuery.setParameter("email",email);
            return typedQuery.getSingleResult();
        }
        catch (NoResultException exception)
        {
            throw new NoResultException();
        }
    }

    @Override
    public boolean validateStudent(String email, String password) {
        boolean authenticated=false;
        try{
            // check the email entry present or not
            TypedQuery<Student> typedQuery= connectionfactory.getSession()
                    .createQuery("from Student where email =:email", Student.class);
            typedQuery.setParameter("email",email);
            Student currentstu= typedQuery.getSingleResult();
            //found student, check password
            if(currentstu != null){
                if(currentstu.getsPass().equals(password)){
                    authenticated = true;
                }
            }
        } catch(NoResultException ne){
            throw new NoResultException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return authenticated;
    }

    @Override
    public void registerStudentToCourse(String email, int cid) {
      try{
          Course newCourse;
//          List<Course> rgisteredCourses;
          //get student registered courses
          Student student = getStudentByEmail(email);
          List<Course> rgisteredCourses = student.geteCourses();
          //check student already register given course or not
          if(student!=null && rgisteredCourses!=null && !rgisteredCourses.isEmpty()){
              for(Course course:student.geteCourses()){
                  if (course.getCid()==cid){throw new Exception("You are already registered in that course!");}
              }
          }

          else if(rgisteredCourses==null){
              rgisteredCourses = new ArrayList<>();
          }
          // Register the course for student
          //get course from courseservice
          CourseDAO cservice = new CourseService();
          List<Course> avaialableCourses= cservice.getAllCourses();
          for (Course acourse: avaialableCourses ){
              if(acourse.getCid()==cid){
                  rgisteredCourses.add(acourse);
                  break;
              }
          }
          //add course in courselist in student entity
          student.seteCourses(rgisteredCourses);
          connectionfactory.getSession().merge(student);
//          connectionfactory.makeCommit();
          //call merge on student
      } catch (NoResultException ne){
          throw new NoResultException("Student is not present");
      } catch (Exception e) {
          throw new RuntimeException(e.getMessage());
      }
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        try{
            TypedQuery<Student> typedQuery= connectionfactory.getSession()
                    .createQuery("from Student where email =:email", Student.class);
            typedQuery.setParameter("email",email);
            Student student = typedQuery.getSingleResult();
            return student.geteCourses();
        }
        catch (NoResultException exception)
        {
            throw new NoResultException();
        }
    }
    }

