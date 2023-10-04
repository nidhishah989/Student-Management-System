package jpa.mainrunner;

import jpa.entitymodels.Student;
import jpa.service.StudentService;
import jpa.util.ConnectionFactory;
import jpa.util.SetUp;
import net.bytebuddy.implementation.bytecode.Throw;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SMSRunner {
    private Scanner scan;
    private ConnectionFactory connectionfactory;

    private StudentService stuservice;

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
        switch(runner.MainMenuSelection()){
            case 1: System.out.println("Choice 1");
                    boolean userauthenticated=false;
                    do{
                        //login menu call until user not authenticated
                        List<String> logininput = new ArrayList<>();
                        logininput= runner.LoginMenu();
                        userauthenticated = runner.studentAuthenticated(logininput);
                    }while(!userauthenticated);
                break;
            case 2: System.out.println("Choice 2");
                break;
        }

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
            // check the email entry present or not
            Student currentstu= stuservice.getStudentByEmail(loginData.get(0));
            //found student, check password
            if(currentstu != null){
                if(currentstu.getsPass().equals(loginData.get(1))){
                    authenticated = true;
                }
                else{
                    throw new Exception();
                }
            }
            else {throw new Exception();}
        } catch(NoResultException ne){
            System.out.println("Invalid email and password.");
        } catch(Exception e){
            System.out.println("Invalid email and password.");
        }
        return authenticated;
    }
}
