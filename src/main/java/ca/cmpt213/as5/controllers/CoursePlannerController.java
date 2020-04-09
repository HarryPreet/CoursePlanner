package ca.cmpt213.as5.controllers;

import ca.cmpt213.as5.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoursePlannerController {


    @GetMapping("/api/dump-model")
    public void getSummary() {
        CourseSummary.dumpModel();
    }

    @PostMapping("/api/addoffering")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOffering(@RequestBody ApiOfferingDataWrapper od){
        String data = od.getSemester() + "," +
                        od.getSubjectName() + "," +
                        od.getCatalogNumber() + "," +
                        od.getLocation() + "," +
                        od.getEnrollmentCap() + "," +
                        od.getEnrollmentTotal()+"," +
                        od.getInstructor() +"," +
                        od.getComponent();
        String[] dataElements = data.split(",");
        CourseData cd = new CourseData(dataElements);
        CourseSummary.addCourseToDatabase(cd);
    }

    @PostMapping("/api/watchers")
    @ResponseStatus(HttpStatus.CREATED)
    public void addWatcher(@RequestBody ApiWatcherWrapper wd){
        CourseSummary.addToWatcherList(wd);
    }

    @GetMapping("/api/watchers")
    public List<ApiWatcherWrapper> getWatcherList(){
        return CourseSummary.getWatcherList();
    }

}