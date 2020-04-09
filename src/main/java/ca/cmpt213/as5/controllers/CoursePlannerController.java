package ca.cmpt213.as5.controllers;

import ca.cmpt213.as5.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoursePlannerController {

    private Name name = new Name("H&S APP", "Shresth and Harry");
    private int watcherCount = 1;

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/api/about")
    public Name getName() {
        return name;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/api/dump-model")
    public void getSummary() {
        CourseSummary.dumpModel();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/api/departments")
    public List<ApiDepartmentWrapper> getDepartment() {
        return CourseSummary.getDepartmentWrapperList();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("api/departments/{id}/courses")
    public List<ApiCourseWrapper> getCourses(@PathVariable("id") int departId) {
        List<ApiCourseWrapper> departmentCourses = CourseSummary.coursesByDepartmentID(departId);
        return departmentCourses;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("api/departments/{deptId}/courses/{courseId}/offerings")
    public List<ApiCourseOfferingWrapper> getCourseOfferings(@PathVariable("deptId") int deptId, @PathVariable("courseId") int courseId) {
        List<ApiCourseOfferingWrapper> courseOfferings = CourseSummary.courseOfferingsByCourseID(deptId, courseId);
        return courseOfferings;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("api/departments/{deptId}/courses/{courseId}/offerings/{courseOffer}")
    public List<ApiOfferingSectionWrapper> getSectionsOfCourse(@PathVariable("deptId") int deptId, @PathVariable("courseId") int courseId,
                                                               @PathVariable("courseOffer") int courseOffer) {
        List<ApiOfferingSectionWrapper> sectionsOffered = CourseSummary.accessOfferingSection(deptId, courseId, courseOffer);
        return sectionsOffered;
    }

    @PostMapping("/api/addoffering")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOffering(@RequestBody ApiOfferingDataWrapper od) {

        String data = od.getSemester() + "," +
                od.getSubjectName() + "," +
                od.getCatalogNumber() + "," +
                od.getLocation() + "," +
                od.getEnrollmentCap() + "," +
                od.getEnrollmentTotal() + "," +
                od.getInstructor() + "," +
                od.getComponent();
        String[] dataElements = data.split(",");
        CourseData cd = new CourseData(dataElements);
        CourseSummary.addCourseToDatabase(cd);
    }

    @PostMapping("/api/watchers")
    @ResponseStatus(HttpStatus.CREATED)
    public void addWatcher(@RequestBody Watcher w){

        ApiWatcherWrapper wd = new ApiWatcherWrapper(watcherCount,w.getDeptId(),w.getCourseId());
        CourseSummary.getAllWatchers().add(wd);
        watcherCount++;
        for(Courses c : CourseSummary.getAllCourses()){
            if(c.getCourseId() == wd.getCourse().getCourseId()){
                c.getObservers().add(wd);
            }
        }

    }
    @GetMapping("/api/watchers")
    public List<ApiWatcherWrapper> getAllWatchers(){

        return CourseSummary.getAllWatchers();
    }
    @GetMapping("/api/watchers/{id}")
    public ApiWatcherWrapper getWatcherByID(@PathVariable("id") int id){

        for(ApiWatcherWrapper wd :CourseSummary.getAllWatchers()){
            if(wd.getId() == id){
                return wd;
            }
        }
        return null;
    }

}