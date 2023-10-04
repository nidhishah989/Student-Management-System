package jpa.service;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.util.ConnectionFactory;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class StudentService implements StudentDAO {

    ConnectionFactory connectionfactory = ConnectionFactory.GET_SESSION.getInstance();
    @Override
    public List<Student> getAllStudents() {
        return null;
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
        return false;
    }

    @Override
    public void registerStudentToCourse(String email, int cid) {

    }

    @Override
    public List<Course> getStudentCourses(String email) {
        return null;
    }
}
