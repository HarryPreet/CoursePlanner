package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * CourseSummary class contains the main function
 * just for part1 of this assignment (to print
 * to the terminal)
 */
public class CourseSummary {
    private static List<CourseData> allData;
    private static List<Courses> allCourses;
    private static HashMap<String, Integer> departmentTracker = new HashMap<>();
    private static List<ApiDepartmentWrapper> departmentWrapperList = new ArrayList<>();


    public static List<ApiDepartmentWrapper> getDepartmentWrapperList() {
        return departmentWrapperList;
    }

    public static void createModel(){
        allData = FileReaderCSV.readFromFile();
        allCourses = new ArrayList<>();
        if(allCourses.isEmpty()){
            allCourses.add(new Courses(allData.get(0)));
        }
        for (int j = 1; j < allData.size() ; j++){
            boolean flag = true;
            for (Courses allCourse : allCourses) {
                if (allData.get(j).getCourseName().equals(allCourse.getCourseName())) {
                    allCourse.addToGroup(allData.get(j));
                    flag = false;
                }
            }
            if(flag) {
                allCourses.add(new Courses(allData.get(j)));
            }
        }
        addDepartment();
        addCourseId();
    }
    public static void addCourseId(){
        long courseId = 1;
        for(Courses c : allCourses){
            c.setCourseId(courseId);
            courseId++;
        }
    }

    public static void addDepartment() {

        int i = 1;
        for (Courses c : allCourses) {
            if (!departmentTracker.containsKey(c.getDepartment())) {
                departmentTracker.put(c.getDepartment(), i);
                c.setDepartmentId(i);
                i++;
            }
            c.setDepartmentId(departmentTracker.get(c.getDepartment()));
        }

       for (HashMap.Entry<String, Integer> entry : departmentTracker.entrySet()) {
            String department = entry.getKey();
            Integer id = entry.getValue();
            ApiDepartmentWrapper d = new ApiDepartmentWrapper(department,id);
            departmentWrapperList.add(d);
        }
        Collections.sort(departmentWrapperList, (ApiDepartmentWrapper a1, ApiDepartmentWrapper a2) -> a1.getDeptId()-a2.getDeptId());

    }

    public static void addCourseToDatabase(CourseData cd){
        FileReaderCSV.addToFile(cd);
    }

    public static List<ApiCourseWrapper> accessCourses(int departId) {

        List<ApiCourseWrapper> courses  = new ArrayList<>();

        for  (Courses c : allCourses) {
            if  (c.getDepartmentId() == departId) {
                ApiCourseWrapper newCourse  = new ApiCourseWrapper(c.getCourseId(),c.getCourseCatalogNumber());
                courses.add(newCourse);
            }
        }

        return courses;
    }

    public static List<ApiCourseOfferingWrapper> accessOfferings(int deptId, int courseId) {
        List<ApiCourseOfferingWrapper> offerings = new ArrayList<>();

        for (Courses c : allCourses) {
            if (c.getDepartmentId() == deptId && c.getCourseId() == courseId) {
                for(CourseOffering co :  c.getCourseOfferings()) {

                    ApiCourseOfferingWrapper newOffer = new ApiCourseOfferingWrapper(co.getCourseOfferingId(),
                            co.getLocation(),
                            co.getSections().combineInstructor(),
                            getTerm(co.getSemester()),
                            co.getSemester(), getYear(co.getSemester()));
                    offerings.add(newOffer);
                }
            }
        }
        return offerings;
    }

    public static String getTerm(long semester){
        int n = (int) (semester%10);
        if(n==1){
            return "Spring";
        }
        else if(n==4){
            return "Summer";
        }
        else {
            return   "Fall";
        }
    }
    public static int getYear(long semester){
        int temp = (int)semester;
        int x;
        int y;
        int z;
        x = temp/1000;
        temp = temp%1000;
        y= temp/100;
        temp = temp%100;
        z= temp/10;
        semester = (int)semester/100;
        return 1900 + 100*x + 10*y + z;
    }

    public static List<ApiOfferingSectionWrapper> accessOfferingSection(int deptId, int courseId, int courseOffer) {
        List<ApiOfferingSectionWrapper> sections = new ArrayList<>();

        for (Courses c : allCourses) {
            if (c.getDepartmentId() == deptId && c.getCourseId() == courseId) {
                for (CourseOffering co : c.getCourseOfferings()) {
                    if (co.getCourseOfferingId() == courseOffer) {
                        for (Type t : co.getSections().getEnrollments()) {
                            ApiOfferingSectionWrapper section = new ApiOfferingSectionWrapper(t.getType(), t.getEnrollmentCapacity(), t.getEnrollmentTotal());
                            sections.add(section);
                        }
                    }
                }
            }
        }
        return  sections;

    }

    public static void dumpModel(){
        createModel();
        for(Courses c : allCourses){
            System.out.println(c);
        }
//        for (ApiDepartmentWrapper d : departmentWrapperList){
//            System.out.println(d.getDepartment());
//            System.out.println(d.getId());
//        }
    }
}