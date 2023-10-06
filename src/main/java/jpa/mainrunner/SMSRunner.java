package jpa.mainrunner;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Student;
import jpa.service.StudentService;
import jpa.util.ConnectionFactory;
import jpa.util.SetUp;
import java.util.*;

public class SMSRunner {

    public static void main(String[] args){
        ConnectionFactory connectionFactory = ConnectionFactory.GET_SESSION.getInstance();
        boolean quit = false;
        StudentDAO stservice = new StudentService();
        SMSController controller = new SMSController();
        // insert all data
        SetUp setUp = new SetUp();
        List<Student> studentlist= stservice.getAllStudents();
        if(studentlist==null || studentlist.isEmpty()) {
            setUp.setDatabaseSchema();
        }
        do{
            switch(controller.MainMenuSelection()){
                case 1: // student mode
                    boolean userauthenticated=false;
                        //login menu call until user not authenticated
                        List<String> logininput;
                        //get user's inputs for login
                        logininput= controller.LoginMenu();
                        //check user authenticated or not
                        userauthenticated = controller.studentAuthenticated(logininput);
                        if (userauthenticated) {
                            System.out.println("--------------------------------------");
                            System.out.println("You are authenticated.");
                            //print courses that are for current student registered.
                            controller.printStudentRegisterCourses(logininput.get(0));
                            //show second Menu

                            boolean logout =false;
                            do {
                                switch (controller.SecondMenuSelection()) {
                                    case 1: //Register the courses
                                        int nRegisterCourse = controller.getRegisterCourseChoice();
                                        //Now call register function
                                        try{
                                            stservice.registerStudentToCourse(logininput.get(0), nRegisterCourse);
                                            System.out.println("Course Registered Successfully");
                                        }catch (RuntimeException re){
                                            System.out.println(re.getMessage());
                                        }
                                        //print courses that are for current student registered.
                                        controller.printStudentRegisterCourses(logininput.get(0));

                                        break;
                                    case 2: // logout
                                        logout=true;
                                        System.out.println("--------------------------------------");
                                        System.out.println("You have been signed Out.");
                                        break;
                                }
                            }while(!logout);
                        }
                    break;
                case 2: // Quit mode
                        connectionFactory.closeSession();
                        quit=true;
                    break;
            }
        }while(!quit);
    }
}
