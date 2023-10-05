package jpa.entitymodels;

import javax.persistence.*;

@Entity
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
        this.cid=0;
        this.cName = null;
        this.cInstructorName=null;
    }

    public Course(int cid, String cName, String cInstructorName) {
        this.cid = cid;
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
        return "|" + cid +
                "|" + cName +
                "|" + cInstructorName +"|";
    }
}
