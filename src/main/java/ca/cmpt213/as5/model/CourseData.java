package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CourseData class contains all the data related
 * to the course and additional helpful functions.
 */
public class CourseData {
    private long semester;
    private String subject;
    private String catalogNumber;
    private String location;
    private int enrolmentCapacity;
    private int enrolmentTotal;
    private String instructors;
    private String code;
    private String courseName;

    // Constructor
    public CourseData(String[] dataElements) {
        this.setSemester(dataElements[0]);
        this.setSubject(dataElements[1]);
        this.setCatalogNumber(dataElements[2]);
        this.setLocation(dataElements[3]);
        this.setEnrolmentCapacity(dataElements[4]);
        this.setEnrolmentTotal(dataElements[5]);
        this.setInstructors(dataElements[6]);
        this.setCode(dataElements[7]);
        this.setCourseName();
    }

    // Accessor
    public long getSemester() {
        return semester;
    }
    public String getSubject() {
        return subject;
    }
    public String getCatalogNumber() {
        return catalogNumber;
    }
    public String getLocation() {
        return location;
    }
    public int getEnrolmentCapacity() {
        return enrolmentCapacity;
    }
    public int getEnrolmentTotal() {
        return enrolmentTotal;
    }
    public String getInstructors() {
        return instructors;
    }
    public String getCode() {
        return code;
    }
    public String getCourseName() {
        return courseName;
    }

    // mutator
    public void setSemester(String semester) {
        try {
            this.semester = Integer.parseInt(semester);
        }
        catch (NumberFormatException e){
            this.semester = 0;
        }
    }
    public void setSubject(String subject) {
        subject = subject.trim();
        this.subject = subject;
    }
    public void setCatalogNumber(String catalogNumber) {
        catalogNumber = catalogNumber.trim();
        this.catalogNumber = catalogNumber;
    }
    public void setLocation(String location) {
        location = location.trim();
        this.location = location;
    }
    public void setEnrolmentCapacity(String enrolmentCapacity) {
        try {
            this.enrolmentCapacity = Integer.parseInt(enrolmentCapacity);
        }
        catch(NumberFormatException e){
            this.enrolmentCapacity = 0;
        }
    }
    public void setEnrolmentTotal(String enrolmentTotal) {
        try {
            this.enrolmentTotal = Integer.parseInt(enrolmentTotal);
        } catch (NumberFormatException e) {
            this.enrolmentTotal = 0;
        }
    }
    public void setInstructors(String instructors) {
        instructors = instructors.trim();
        this.instructors = instructors;
    }
    public void setCode(String code) {
        code = code.trim();
        this.code = code;
    }
    public void setCourseName() {
        this.courseName = this.subject + " "+ this.catalogNumber;
    }


    @Override
    public String toString() {
        return semester +
                subject +
                catalogNumber +
                location +
                enrolmentCapacity +
                 enrolmentTotal +
                instructors +
                code +
                courseName;
    }
}