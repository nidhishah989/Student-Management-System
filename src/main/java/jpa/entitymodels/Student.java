package jpa.entitymodels;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table (name="Student")
public class Student {

    public Student(){

    }
    public Student(String sEmail, String sName, String sPass) {
        this.sEmail = sEmail;
        this.sName = sName;
        this.sPass = sPass;
        this.eCourses = new ArrayList<>();
    }

    @Id
    @Column(name="email")
    private String sEmail;
    @Column(name="name")
    private String sName;
    @Column(name="password")
    private String sPass;
    @ManyToMany(targetEntity = Course.class, fetch = FetchType.LAZY)
    private List<Course> eCourses;

    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsPass() {
        return sPass;
    }

    public void setsPass(String sPass) {
        this.sPass = sPass;
    }

    public List<Course> geteCourses() {
        return eCourses;
    }

    public void seteCourses(List<Course> eCourses) {
        this.eCourses = eCourses;
    }
}
