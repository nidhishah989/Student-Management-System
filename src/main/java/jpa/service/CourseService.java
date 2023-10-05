package jpa.service;

import jpa.dao.CourseDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.util.ConnectionFactory;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class CourseService implements CourseDAO {
    ConnectionFactory connectionfactory = ConnectionFactory.GET_SESSION.getInstance();
    @Override
    public List<Course> getAllCourses() {

        try{
            TypedQuery<Course> typedQuery= connectionfactory.getSession()
                    .createQuery("FROM Course", Course.class);
            return typedQuery.getResultList();
        }catch (NoResultException ne){
            throw new NoResultException();
        }
    }
}
