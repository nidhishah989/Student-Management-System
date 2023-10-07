package jpa.services;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.service.StudentService;
import jpa.util.SetUp;
import org.junit.jupiter.api.*;

import javax.persistence.NoResultException;
import java.util.List;

@DisplayName("Student Service Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentServiceTest {

    StudentService stservice;
    boolean runTest = false;

    @BeforeAll
    public static void setUp() {
        // Initialize the StudentService or use dependency injection
        StudentService stuservice = new StudentService();
        stuservice = new StudentService();
        SetUp dbsetup = new SetUp();
        List<Student> studentlist= stuservice.getAllStudents();
        if(studentlist==null || studentlist.isEmpty()) {
            dbsetup.setDatabaseSchema();
        }

    }

    @BeforeEach
    public void setUpClasses()
    {
        stservice = new StudentService();
    }
    @Test
    @Order(1)
    void testGetAllStudents() {
        // Test the getAllStudents() method
        List<Student> students = stservice.getAllStudents();
        Assertions.assertNotNull(students);
        Assertions.assertTrue(students.size() > 0);
    }
    @Test
    @Order(2)
    void testGetStudentByEmail_Positive() {
        // Test the getStudentByEmail() method with a correct email
        String email = "aiannitti7@is.gd";

        // Positive test: Check if the correct student is retrieved
        Student student = stservice.getStudentByEmail(email);

        // Assertions for the positive test
        Assertions.assertNotNull(student);
        Assertions.assertEquals(email, student.getsEmail());
    }

    @Test
    @Order(3)
    void testGetStudentByEmail_Negative() {
        // Test the getStudentByEmail() method with a wrong email
        String wrongEmail = "student@gmail.com";

        // Negative test: Check if the method throws a NoResultException for a wrong email
        Assertions.assertThrows(NoResultException.class, () -> {
            Student student = stservice.getStudentByEmail(wrongEmail);
            // You can add assertions here if the method doesn't throw an exception
        });
    }

    @Test
    @Order(4)
    void testValidateStudent() {
        // Test the validateStudent() method
        String email = "aiannitti7@is.gd";
        String validPassword = "TWP4hf5j";
        String invalidPassword = "incorrect_password";

        Assertions.assertTrue(stservice.validateStudent(email, validPassword));
        Assertions.assertFalse(stservice.validateStudent(email, invalidPassword));
    }


    @Test
    @Order(5)
    void testRegisterStudentForCourseWithNegativeCase() {
        // Test the registerStudentToCourse() method and check negative cases
        String email = "aiannitti7@is.gd";
        int courseId = 1;

        // Get the initial courses of the student
        List<Course> initialCourses = stservice.getStudentCourses(email);

        // Check if initialCourses is empty or if the course with courseId is not in initialCourses
        if (initialCourses.isEmpty() || !initialCourses.stream().anyMatch(course -> course.getCid() == courseId)) {
            // Register the student for the course
            try {
                stservice.registerStudentToCourse(email, courseId);

                // Verify that the student is registered for the course
                List<Course> updatedCourses = stservice.getStudentCourses(email);
                Assertions.assertTrue(updatedCourses.stream().anyMatch(course -> course.getCid() == courseId));
            } catch (RuntimeException e) {
                // If a RuntimeException occurs, ensure that it's due to already being registered
                Assertions.assertEquals("You are already registered in that course!", e.getMessage());
            }
        } else {
            // Attempt to register the student for the same course again and expect an exception
            Assertions.assertThrows(RuntimeException.class, () -> stservice.registerStudentToCourse(email, courseId));
        }
    }

    @Test
    @Order(6)
    void testRegisterStudentForSameCourseTwice() {
        // Test registering the student for the same course twice
        String email = "aiannitti7@is.gd";
        int courseId = 1;

        // Attempt to register the student for the same course again and expect an exception
        Assertions.assertThrows(RuntimeException.class, () -> stservice.registerStudentToCourse(email, courseId));
    }

    @Test
    @Order(7)
    void testGetStudentCourses() {
        // Test the getStudentCourses() method with a non-existing student email
        String email = "aiannitti7@is.gd";

        // Negative test: Check if the method returns null courses for a non-existing email
        List<Course> courses = stservice.getStudentCourses(email);
        Assertions.assertTrue(!courses.isEmpty());
    }
}
