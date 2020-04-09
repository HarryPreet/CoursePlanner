package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Section class holds the data of the section
 * (instructor and <Type>enrollment information)
 */
public class Section {
    private List<String> instructors = new ArrayList<>();
    private List<Type> enrollments = new ArrayList<>();

    // Constructor
    public Section() {
    }

    public Section(CourseData cd) {
        this.instructors.add(cd.getInstructors());
        this.enrollments.add(new Type(cd));
    }

    // Accessor and mutator
    public List<String> getInstructors() {
        return instructors;
    }

    public void addInstructors(String instructors) {
        this.instructors.add(instructors);
    }

    public List<Type> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Type> enrollments) {
        this.enrollments = enrollments;
    }

    @Override
    public String toString() {
        String prettyPrint = "";
        for(String s: instructors) {
            prettyPrint = prettyPrint + s + ","  ;
        }
        return prettyPrint + '\n'+ enrollments;
    }
}