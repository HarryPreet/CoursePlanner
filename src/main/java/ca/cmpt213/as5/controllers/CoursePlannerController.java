package ca.cmpt213.as5.controllers;

import ca.cmpt213.as5.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoursePlannerController {

    private Name name = new Name("H&S APP", "Shresth and Harry");

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
    public List<ApiCourseWrapper> getCourses(@PathVariable ("id") String Id) {
        Integer departId = Integer.valueOf(Id);
        List<ApiCourseWrapper> catNum = CourseSummary.accessCourses(departId);

        return catNum;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("api/departments/{deptId}/courses/{courseId}/offerings")
    public List<ApiCourseOfferingWrapper> getOfferings(@PathVariable ("deptId") int deptId, @PathVariable ("courseId") int courseId) {
        List<ApiCourseOfferingWrapper> offCourses = CourseSummary.accessOfferings(deptId, courseId);

        return offCourses;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("api/departments/{deptId}/courses/{courseId}/offerings/{courseOffer}")
    public List<ApiOfferingSectionWrapper> getSectionsOffered(@PathVariable ("deptId") int deptId, @PathVariable ("courseId") int courseId,
                                                              @PathVariable ("courseOffer") int courseOffer) {
        List<ApiOfferingSectionWrapper> sectionOffer = CourseSummary.accessOfferingSection(deptId,courseId,courseOffer);

        return sectionOffer;
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