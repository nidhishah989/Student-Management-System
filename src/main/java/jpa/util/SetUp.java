package jpa.util;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.List;
import org.hibernate.Transaction;

public class SetUp {

    private static List<Course> courseList = Arrays.asList(

            new Course( "English", "Anderea Scamaden"),
            new Course( "Mathematics", "Eustace Niemetz"),
            new Course( "Anatomy", "Reynolds Pastor"),
            new Course( "Organic Chemistry", "Odessa Belcher"),
            new Course( "Physics", "Dani Swallow"),
            new Course( "Digital Logic", "Glenden Reilingen"),
            new Course( "Object Oriented Programming", "Giselle Ardy"),
            new Course( "Data Structures", "Carolan Stoller"),
            new Course( "Politics", "Carmita De Maine"),
            new Course( "Art", "Kingsly Doxsey")
    );
    private static List<Student> studentList = Arrays.asList(
            new Student("hluckham0@google.ru", "Hazel Luckham", "X1uZcoIh0dj"),
            new Student("sbowden1@yellowbook.com", "Sonnnie Bowden", "SJc4aWSU"),
            new Student("qllorens2@howstuffworks.com", "Quillan Llorens", "W6rJuxd"),
            new Student("cstartin3@flickr.com", "Clem Startin", "XYHzJ1S"),
            new Student("tattwool4@biglobe.ne.jp", "Thornie Attwool", "Hjt0SoVmuBz"),
            new Student("hguerre5@deviantart.com", "Harcourt Guerre", "OzcxzD1PGs"),
            new Student("htaffley6@columbia.edu", "Holmes Taffley", "xowtOQ"),
            new Student("aiannitti7@is.gd", "Alexandra Iannitti", "TWP4hf5j"),
            new Student("ljiroudek8@sitemeter.com", "Laryssa Jiroudek", "bXRoLUP"),
            new Student("cjaulme9@bing.com", "Cahra Jaulme", "FnVklVgC6r6")
    );

    ConnectionFactory connectionfactory = ConnectionFactory.GET_SESSION.getInstance();

    public SetUp() {

    }

    public void setDatabaseSchema(){
      //get a current connection
//
        //Insert coursedata into course table
        Session session = connectionfactory.getSession();
        for (Course course: courseList){
            Course attachedCourse = (Course) session.merge(course);
            session.persist(attachedCourse);
        }

        //Insert students data into student table
        for (Student student:studentList){
           session.getSession().persist(student);
        }


        connectionfactory.makeCommit();
//        System.out.println("IN SETUP before open: "+session.getTransaction().isActive());
//        connectionfactory.openTrasaction();
//        System.out.println("IN SETUP after open: "+session.getTransaction().isActive());
    }
}
