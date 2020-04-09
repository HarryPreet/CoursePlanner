package ca.cmpt213.as5.model;

import com.sun.tools.javac.util.DefinedBy;

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
    private static List<ApiWatcherWrapper> watcherList = new ArrayList<>();

    public static void addToWatcherList(ApiWatcherWrapper wd){
        watcherList.add(wd);
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
    }
    public static void addDepartment() {
        int i = 1;
        for (Courses c : allCourses) {
            if (!departmentTracker.containsKey(c.getDepartment())) {
                departmentTracker.put(c.getDepartment(), i);
                i++;
            }
        }

        for (HashMap.Entry<String, Integer> entry : departmentTracker.entrySet()) {
            String department = entry.getKey();
            Integer id = entry.getValue();
            ApiDepartmentWrapper d = new ApiDepartmentWrapper(department,id);
            departmentWrapperList.add(d);
        }
        Collections.sort(departmentWrapperList, (ApiDepartmentWrapper a1, ApiDepartmentWrapper a2) -> a1.getId()-a2.getId());

    }

    public static void addCourseToDatabase(CourseData cd){
        FileReaderCSV.addToFile(cd);
    }

    public static void dumpModel(){
        createModel();
        for(Courses c : allCourses){
            System.out.println(c);
        }
    }
    public static List<ApiWatcherWrapper> getWatcherList() {
        return watcherList;
    }

    public static void setWatcherList(List<ApiWatcherWrapper> watcherList) {
        CourseSummary.watcherList = watcherList;
    }





    public static List<ApiDepartmentWrapper> getDepartmentWrapperList() {
        return departmentWrapperList;
    }



}