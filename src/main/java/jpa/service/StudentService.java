package jpa.service;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.util.ConnectionFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
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
          //get student
          Student student = getStudentByEmail(email);
          //check student already register or not
          if(student!=null && !student.geteCourses().isEmpty()){
              for(Course course:student.geteCourses()){
                  if (course.getCid()==cid){throw new Exception("You are already registered in that course!");}
              }
          }
          // Register the course for student
          //add course in courselist in student entity
          //call merge on student
      } catch (NoResultException ne){
          throw new NoResultException("Student is not present");
      } catch(Exception e){
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

