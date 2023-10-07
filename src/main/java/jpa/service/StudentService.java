package jpa.service;

import jpa.dao.CourseDAO;
import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.util.ConnectionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class StudentService implements StudentDAO {

    ConnectionFactory connectionfactory = ConnectionFactory.GET_SESSION.getInstance();
    @Override
    public List<Student> getAllStudents() { //get all students
        try{
            TypedQuery<Student> typedQuery= connectionfactory.getSession()
                    .createQuery("FROM Student", Student.class);
            return typedQuery.getResultList();
        }catch (NoResultException ne){
            throw new NoResultException();
        }
    }

    @Override
    public Student getStudentByEmail(String email) { //get student by email
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
        //student authentication
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
        //register course for authenticated user and check if already registered or not
      try{
          CourseDAO cservice = new CourseService();
          List<Course> avaialableCourses= cservice.getAllCourses();
          //get the course from available
          Course course = avaialableCourses.stream()
                  .filter(acourse -> acourse.getCid() == cid)
                  .findFirst()
                  .orElse(null);
          //get student registered courses
          Student student = getStudentByEmail(email);
          List<Course> rgisteredCourses = student.geteCourses();
          // now
          //check student already register given course or not
          if(student!=null && !rgisteredCourses.isEmpty() && rgisteredCourses.contains(course)){
              throw new Exception("You are already registered in that course!");
          }

          Session session = connectionfactory.getSession();
          if(!session.getTransaction().isActive()){
              connectionfactory.openTrasaction();
          }
//          System.out.println(session.getTransaction().isActive());
          student.geteCourses().add(course);
          session.merge(student);
          connectionfactory.makeCommit();
          //call merge on student
      } catch (NoResultException ne){
          throw new NoResultException("Student is not present");
      } catch (Exception e) {
          throw new RuntimeException(e.getMessage());
      }

    }

    @Override
    public List<Course> getStudentCourses(String email) {
        // get all courses that are registered for authenticated user
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

