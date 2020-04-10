package ca.cmpt213.as5.model;

import ca.cmpt213.as5.model.WrapperClasses.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * CourseSummary class contains the main function
 * just for part1 of this assignment (to print
 * to the terminal)
 */
public class CourseSummary {
    private static List<CourseData> allData;
    private static List<Courses> allCourses = new ArrayList<>();;
    private static List<ApiWatcherWrapper> allWatchers = new ArrayList<>();
    private static HashMap<String, Integer> departmentTracker ;
    private static List<ApiDepartmentWrapper> departmentWrapperList ;
    private static Date date = new Date();


    public static List<ApiDepartmentWrapper> getDepartmentWrapperList() {
        return departmentWrapperList;
    }

    public static void createModel() {
        allData = FileReaderCSV.readFromFile();
        departmentTracker = new HashMap<>();
        departmentWrapperList = new ArrayList<>();
        if (allCourses.isEmpty()) {
            allCourses.add(new Courses(allData.get(0)));
        }
        for (int j = 1; j < allData.size(); j++) {
            boolean flag = true;
            addEntry(allData.get(j));

        }
        addDepartmentId();
        addCourseId();
    }

    public static void addEntry(CourseData cd){
        boolean flag =  true;
        for (Courses allCourse : allCourses) {
            if (cd.getCourseName().equals(allCourse.getCourseName())) {
                allCourse.addToGroup(cd);
                flag = false;
            }
        }
        if (flag) {
            allCourses.add(new Courses(cd));
        }


    }

    public static void addCourseId() {
        long courseId = 1;
        for (Courses c : allCourses) {
            c.setCourseId(courseId);
            courseId++;
        }
    }

    public static void addDepartmentId() {

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
            ApiDepartmentWrapper d = new ApiDepartmentWrapper(department, id);
            departmentWrapperList.add(d);
        }
        Collections.sort(departmentWrapperList, (ApiDepartmentWrapper a1, ApiDepartmentWrapper a2) -> a1.getDeptId() - a2.getDeptId());
    }

    public static void addCourseToDatabase(CourseData cd) {
        for(Courses c : allCourses){
            if(cd.getCourseName().equals(c.getCourseName())){
                LocalDateTime myDateObj = LocalDateTime.now();
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss");
                String formattedDate = myDateObj.format(myFormatObj);
                c.notifyObservers(formattedDate+": "+"Added section " +
                                    cd.getCode()+" with enrollment ([" +
                                    cd.getEnrolmentTotal() +"]/" +
                                    cd.getEnrolmentCapacity()+")"+ " to offering "+
                                    getTerm(cd.getSemester()) + " "+
                                    getYear(cd.getSemester()));
                break;
            }
        }
        FileReaderCSV.addToFile(cd);
        addEntry(cd);
    }

    public static List<ApiCourseWrapper> coursesByDepartmentID(int departId) {
        List<ApiCourseWrapper> courses = new ArrayList<>();
        for (Courses c : allCourses) {
            if (c.getDepartmentId() == departId) {
                ApiCourseWrapper newCourse = new ApiCourseWrapper(c.getCourseId(), c.getCourseCatalogNumber());
                courses.add(newCourse);
            }
        }
        return courses;
    }

    public static List<ApiCourseOfferingWrapper> courseOfferingsByCourseID(int deptId, int courseId) {
        List<ApiCourseOfferingWrapper> offerings = new ArrayList<>();

        for (Courses c : allCourses) {
            if (c.getDepartmentId() == deptId && c.getCourseId() == courseId) {
                for (CourseOffering co : c.getCourseOfferings()) {
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

    public static String getTerm(long semester) {
        int n = (int) (semester % 10);
        if (n == 1) {
            return "Spring";
        } else if (n == 4) {
            return "Summer";
        } else {
            return "Fall";
        }
    }

    public static int getYear(long semester) {
        int temp = (int) semester;
        int x;
        int y;
        int z;
        x = temp / 1000;
        temp = temp % 1000;
        y = temp / 100;
        temp = temp % 100;
        z = temp / 10;
        semester = (int) semester / 100;
        return 1900 + 100 * x + 10 * y + z;
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
        return sections;

    }

    public static void dumpModel() {
        for (Courses c : allCourses) {
            System.out.println(c);
        }

    }
    public static HashMap<String, Integer> getDepartmentTracker() {
        return departmentTracker;
    }

    public static void setDepartmentTracker(HashMap<String, Integer> departmentTracker) {
        CourseSummary.departmentTracker = departmentTracker;
    }
    public static List<Courses> getAllCourses() {
        return allCourses;
    }

    public static void setAllCourses(List<Courses> allCourses) {
        CourseSummary.allCourses = allCourses;
    }

    public static List<ApiWatcherWrapper> getAllWatchers() {
        return allWatchers;
    }

    public static List<ApiGraphPointWrapper> getGraphPoints(int deptId){
        List<ApiGraphPointWrapper> graphPoints = new ArrayList<>();

        for(Courses c :allCourses){
            if(c.getDepartmentId() == deptId){
               for(CourseOffering co : c.getCourseOfferings()){
                   boolean flag = true;
                   ApiGraphPointWrapper graph = new ApiGraphPointWrapper();
                   for(Type t : co.getSections().getEnrollments()){
                       if(t.getType().equals("LEC")){
                           graph.setSemesterCode(co.getSemester());
                           graph.setTotalCoursesTaken(t.getEnrollmentTotal());
                       }
                   }
                   for(ApiGraphPointWrapper g :graphPoints){
                       if(g.getSemesterCode()==graph.getSemesterCode()){
                           g.setTotalCoursesTaken(g.getTotalCoursesTaken() + graph.getTotalCoursesTaken());
                           flag = false;
                       }
                   }
                   if(flag){
                       graphPoints.add(graph);
                   }
               }

            }

        }
        return graphPoints;
    }

}