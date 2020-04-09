package ca.cmpt213.as5.model;

/**
 * Type class holds the component code of the section
 * and counts the enrollmentCapacity and enrollmentTotal
 * of a particular section.
 */
public class Type {
    private String type;
    private int enrollmentCapacity;
    private int enrollmentTotal;

    // Constructor
    public Type() {
    }

    public Type(CourseData cd) {
        this.type = cd.getCode();
        this.enrollmentCapacity = cd.getEnrolmentCapacity();
        this.enrollmentTotal= cd.getEnrolmentTotal();
    }

    // Accessor
    public String getType() {
        return type;
    }
    public int getEnrollmentCapacity() {
        return enrollmentCapacity;
    }
    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    // Mutator
    public void setType(String type) {
        this.type = type;
    }
    public void setEnrollmentCapacity(int enrollmentCapacity) {
        this.enrollmentCapacity = enrollmentCapacity;
    }
    public void setEnrollmentTotal(int enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }

    public void modifyEnrollmentCapacity(int newEC){
        this.enrollmentCapacity = enrollmentCapacity + newEC;
    }
    public void modifyEnrollmentTotal(int newET){
        this.enrollmentTotal = enrollmentTotal + newET;
    }

    @Override
    public String toString() {
        return "\t\tType=" + type +
                ", Enrollment=" + enrollmentTotal +
                "/" + enrollmentCapacity + "\n" ;
    }
}