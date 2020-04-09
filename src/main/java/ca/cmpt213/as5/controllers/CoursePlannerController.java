package ca.cmpt213.as5.controllers;

import ca.cmpt213.as5.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoursePlannerController {

    private Name name = new Name("H&S APP", "Shresth and Harry");
    private  CourseSummary courseSummary = new CourseSummary();

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
    public List<String> getCourses(@PathVariable ("id") int departId) {
        List<String> catNum = CourseSummary.accessCourses(departId);

        return catNum;
    }

    @PostMapping("/api/addoffering")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOffering(){
        String data ="1247,CMPT,150,BURNABY,130,129,harry,LEC";
        String[] dataElements = data.split(",");
        CourseData cd = new CourseData(dataElements);
        CourseSummary.addCourseToDatabase(cd);
    }
}