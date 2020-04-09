package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Courses class contains only the course name,
 * department and the list of all the courses offered.
 */
public class Courses {
    private String courseName;
    private String department;
    private List<CourseOffering> courseOfferings = new ArrayList<>();

    // Constructor
    public Courses(CourseData cd) {
        this.courseName = cd.getCourseName();
        courseOfferings.add(new CourseOffering(cd));
        this.department = cd.getSubject();
    }

    // Accessor and mutator
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<CourseOffering> getCourseOfferings() {
        return courseOfferings;
    }

    public void setCourseOfferings(List<CourseOffering> courseOfferings) {
        this.courseOfferings = courseOfferings;
    }


    public void addToGroup(CourseData cd){
        if(courseOfferings.isEmpty()){
            courseOfferings.add(new CourseOffering(cd));
        }
        for(CourseOffering co : courseOfferings){
            if(co.getSemester()==cd.getSemester() && co.getLocation().equals(cd.getLocation())){
                co.addToSection(cd);
                return;
            }
        }
        courseOfferings.add(new CourseOffering(cd));
    }

    @Override
    public String toString() {
        String prettyPrint = courseName + courseOfferings;
        prettyPrint = prettyPrint.replace("[", "");
        prettyPrint = prettyPrint.replace("]", "");
        prettyPrint = prettyPrint.replace(",\n", "\n");
        prettyPrint = prettyPrint.replace(", ", " ");
        return prettyPrint;
    }
}