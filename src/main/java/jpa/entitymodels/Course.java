package jpa.entitymodels;

import javax.persistence.*;

@Entity
@Table(name="Course")
public class Course {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cid;
    @Column(name="name")
    private String cName;
    @Column(name="Instructor")
    private String cInstructorName;

    Course(){

    }

    public Course(String cName, String cInstructorName) {
        this.cName = cName;
        this.cInstructorName = cInstructorName;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcInstructorName() {
        return cInstructorName;
    }

    public void setcInstructorName(String cInstructorName) {
        this.cInstructorName = cInstructorName;
    }

    @Override
    public String toString() {
//        return "|" + cid +
//                "|" + cName +
//                "|" + cInstructorName +"|";
//        System.out.printf("%-5s %-10s %-20s%n", "ID", "COURSE_NAME", "INSTRUCTOR_NAME");
        return String.format("|%-5s|%-30s|%-20s|", cid, cName, cInstructorName);
    }
}
