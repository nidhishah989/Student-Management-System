package jpa.mainrunner;

import jpa.dao.CourseDAO;
import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.service.CourseService;
import jpa.service.StudentService;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SMSController {

    private Scanner scan = new Scanner(System.in);
    private StudentDAO stuservice = new StudentService();

    private CourseDAO cservice = new CourseService();
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
            if(registeredcourses==null || registeredcourses.isEmpty()){
                System.out.println("You have not registered any course yet.");
            }
            else {
                for (Course course : registeredcourses) {
                    System.out.println(course);
                }
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

    public int getRegisterCourseChoice() {
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

        return selection;
    }
}
