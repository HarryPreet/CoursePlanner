package ca.cmpt213.as5.model.WrapperClasses;

public class ApiGraphPointWrapper {
    private long semesterCode;
    private long totalCoursesTaken;

    public ApiGraphPointWrapper() {
        semesterCode = 0;
        totalCoursesTaken = 0;
    }

    public ApiGraphPointWrapper(long semesterCode, long totalCoursesTaken) {
        this.semesterCode = semesterCode;
        this.totalCoursesTaken = totalCoursesTaken;
    }

    public long getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(long semesterCode) {
        this.semesterCode = semesterCode;
    }

    public long getTotalCoursesTaken() {
        return totalCoursesTaken;
    }

    public void setTotalCoursesTaken(long totalCoursesTaken) {
        this.totalCoursesTaken = totalCoursesTaken;
    }
}
