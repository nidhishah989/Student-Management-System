package jpa.mainrunner;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.service.CourseService;
import jpa.service.StudentService;
import jpa.util.ConnectionFactory;
import jpa.util.SetUp;
import net.bytebuddy.implementation.bytecode.Throw;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.*;

public class SMSRunner {
    private Scanner scan;
    private ConnectionFactory connectionfactory;

    private StudentService stuservice;
    private CourseService cservice;

    private boolean quit=false;

    SMSRunner() {
        this.scan = new Scanner(System.in);
        this.connectionfactory = ConnectionFactory.GET_SESSION.getInstance();
        this.stuservice = new StudentService();
    }
    public static void main(String[] args){
        SMSRunner runner = new SMSRunner();
        // insert all data
//        SetUp setUp = new SetUp(connectionFactory);
//        setUp.setDatabaseSchema();
        do{
            switch(runner.MainMenuSelection()){
                case 1: System.out.println("Choice 1");
                    boolean userauthenticated=false;
//                    do{
                        //login menu call until user not authenticated
                        List<String> logininput;
                        //get user's inputs for login
                        logininput= runner.LoginMenu();
                        //check user authenticated or not
                        userauthenticated = runner.studentAuthenticated(logininput);
                        if (userauthenticated) {
                            System.out.println("You are authenticated.");
                            //show list of all registered courses
                            System.out.println("---------------------------------");
                            System.out.println("List of Your Registered Courses.");
                            System.out.println("---------------------------------");
                            runner.printStudentRegisterCourses(logininput.get(0));
                            //show second Menu
                            switch (runner.SecondMenuSelection()){
                                case 1: //Register the courses
                                    Course nRegisterCourse= runner.getRegisterCourseChoice();
                                    //Now call register function

                                    break;
                                case 2: //logout
                                    break;
                            }

                        }
//                    }while(!userauthenticated);
                    break;
                case 2: System.out.println("Choice 2");
                        runner.quit=true;
                    break;
            }
        }while(!runner.quit);


    }

    public int MainMenuSelection(){

        int mainchoice=-1;
        do{
            try{
                System.out.println("Are you a(n)\n 1.Student \n 2.quit \n Please, enter 1 or 2.");
                mainchoice=scan.nextInt();
            }catch(InputMismatchException e){
                scan.next();
                System.out.println("Invalid input.");
            }

        }while(mainchoice!=1 && mainchoice!=2);

        return mainchoice;
    }

    public List<String> LoginMenu() {
        List<String> logindata = new ArrayList<>();
        try {
            System.out.println("Provide your Login Information:");
            System.out.println("Enter Your Email");
            // do email validation here
            String emailInput = scan.next();
            System.out.println("Enter Your Password");
            String passwordInput = scan.next();
            logindata.add(emailInput);
            logindata.add(passwordInput);

        } catch (Exception e) {

        }
        return logindata;
    }
    public boolean studentAuthenticated(List<String> loginData){
        boolean authenticated=false;
        try{
           authenticated = stuservice.validateStudent(loginData.get(0),loginData.get(1));
        }catch (RuntimeException e){
              System.out.println("Invalid email or password. Try Again.");
          }
        return authenticated;
    }

    public void printStudentRegisterCourses(String email) {
        try {
            List<Course> registeredcourses = stuservice.getStudentCourses(email);
            if(registeredcourses.isEmpty()){
                System.out.println("You have not registered any course yet.");
            }
            for (Course course : registeredcourses) {
                System.out.println(course);
            }
        } catch (NoResultException ne) {
            System.out.println("Please Sign In first.");
        }
    }

    public int SecondMenuSelection(){

        int choice=-1;
        do{
            try{
                System.out.println("1. Register to Class \n 2.Logout \n Please Enter 1 or 2: ");
                choice=scan.nextInt();
            }catch(InputMismatchException e){
                scan.next();
                System.out.println("Invalid input.");
            }

        }while(choice!=1 && choice!=2);

        return choice;
    }

    private Course getRegisterCourseChoice() {
        //show all courses
        cservice = new CourseService();
        List<Integer> courseIds =new ArrayList<Integer>();
        List<Course> courses = cservice.getAllCourses();
        System.out.println("ID COURSE_NAME INSTRUCTOR_NAME");
        for(Course course:courses){
            courseIds.add(course.getCid());
            System.out.println(course);
        }
        Integer selection;
        do{System.out.println("Which Course?");
            selection = scan.nextInt();
            if(!courseIds.contains(selection)){System.out.println("Invalid Input.");}
        }while(!courseIds.contains(selection));
        //selection is done and good
        for(Course course:courses){
            if(course.getCid()==selection){
                return course;
            }
        }
        return null;
    }
}
