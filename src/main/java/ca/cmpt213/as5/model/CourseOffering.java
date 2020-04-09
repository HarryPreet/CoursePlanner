package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.List;

/**
 * CourseOffering class contains the data of
 * all the courses (semester, location, Section)
 * offered this semester.
 */
public class CourseOffering {

    private long semester;
    private String location;
    private Section section;

    // Constructor
    public CourseOffering() {
    }

    public CourseOffering(CourseData cd) {
        this.semester = cd.getSemester();
        this.location = cd.getLocation();
        this.section = new Section(cd);
    }

    // Accessor and mutator
    public long getSemester() {
        return semester;
    }
    public void setSemester(long semester) {
        this.semester = semester;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public Section getSections() {
        return section;
    }
    public void setSections(Section section) {
        this.section = section;
    }


    public void addToSection(CourseData cd) {
        boolean flag = false;
        for (int i = 0; i < section.getInstructors().size(); ++i) {
            if (section.getInstructors().get(i).equals(cd.getInstructors())) {
                flag = true;
                break;
            }
        }
        if(!flag){
            section.getInstructors().add(cd.getInstructors());
        }
        for (Type t : section.getEnrollments()) {
            if (t.getType().equals(cd.getCode())) {
                t.modifyEnrollmentCapacity(cd.getEnrolmentCapacity());
                t.modifyEnrollmentTotal(cd.getEnrolmentTotal());
                return;
            }
        }
        section.getEnrollments().add(new Type(cd));
    }


    @Override
    public String toString() {
        return  "\n\t" + semester +
                " in "+ location +
                " by "+ section;
    }
}