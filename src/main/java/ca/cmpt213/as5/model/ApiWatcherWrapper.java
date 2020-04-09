package ca.cmpt213.as5.model;

import ca.cmpt213.as5.controllers.CoursePlannerController;

import java.util.ArrayList;
import java.util.List;

public class ApiWatcherWrapper {
    public long id;
    public ApiDepartmentWrapper department;
    public ApiCourseWrapper course;
    public List<String> events;

    public ApiWatcherWrapper(long id,int deptID, long courseID) {
        for(ApiDepartmentWrapper d : CourseSummary.getDepartmentWrapperList()){
            if(d.getDeptId() == deptID){
                this.department = new ApiDepartmentWrapper(d.getName(),deptID);
                break;
            }
        }
        for(Courses c : CourseSummary.getAllCourses()){
            if(c.getCourseId() == courseID && c.getDepartmentId() == deptID){
                this.course = new ApiCourseWrapper(courseID,c.getCourseCatalogNumber());
            }
        }
        if(department == null || course == null){
            throw new CoursePlannerController.badIdExceptionHandler("No such id");
        }
        this.id = id;
        this.events = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ApiDepartmentWrapper getDepartment() {
        return department;
    }

    public void setDepartment(ApiDepartmentWrapper department) {
        this.department = department;
    }

    public ApiCourseWrapper getCourse() {
        return course;
    }

    public void setCourse(ApiCourseWrapper course) {
        this.course = course;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }
}
