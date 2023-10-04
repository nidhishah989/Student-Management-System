package jpa.util;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.List;
import org.hibernate.Transaction;

public class SetUp {

    private static List<Course> courseList = Arrays.asList(
            new Course(1, "English", "Anderea Scamaden"),
            new Course(2, "Mathematics", "Eustace Niemetz"),
            new Course(3, "Anatomy", "Reynolds Pastor"),
            new Course(4, "Organic Chemistry", "Odessa Belcher"),
            new Course(5, "Physics", "Dani Swallow"),
            new Course(6, "Digital Logic", "Glenden Reilingen"),
            new Course(7, "Object Oriented Programming", "Giselle Ardy"),
            new Course(8, "Data Structures", "Carolan Stoller"),
            new Course(9, "Politics", "Carmita De Maine"),
            new Course(10, "Art", "Kingsly Doxsey")
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

    ConnectionFactory connectionFactory = ConnectionFactory.GET_SESSION.getInstance();

    public SetUp(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void setDatabaseSchema(){
      //get a current connection
//
        //Insert coursedata into course table
        for (Course course: courseList){
            connectionFactory.getSession().persist(course);
        }

        //Insert students data into student table
        for (Student student:studentList){
            connectionFactory.getSession().persist(student);
        }

    }
}
