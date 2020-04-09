package ca.cmpt213.as5.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * FileReaderCSV class reads the given .csv file
 * and returns the list of all the data of the
 * courses in the form of ArrayList
 */
public class FileReaderCSV {
    private static List<CourseData> dataList = null;
    private static final String csvFile = "data/small_data.csv";
    private static String[] dataElements;

    public static List<CourseData> readFromFile(){
        try {
            dataList = new ArrayList<>();
            File inputFile = new File(csvFile);
            Scanner inputStream = new Scanner(inputFile);
            String data = inputStream.nextLine() ;
            while(inputStream.hasNext()){
                data = inputStream.nextLine();
                dataElements = data.split(",");
                CourseData courseData = new CourseData(dataElements);
                dataList.add(courseData);
            }
            inputStream.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return dataList;
    }
    public static void addToFile(CourseData cd){
        //SEMESTER,SUBJECT,CATALOGNUMBER,LOCATION,ENROLMENTCAPACITY,ENROLMENTTOTAL,INSTRUCTORS,COMPONENTCODE
        try{
            FileWriter outputFile = new FileWriter(csvFile,true);
            String data ="\n"+
                    cd.getSemester() + ","+
                    cd.getSubject() + "," +
                    cd.getCatalogNumber()+ ","+
                    cd.getLocation()+ "," +
                    cd.getEnrolmentCapacity() +","+
                    cd.getEnrolmentTotal() + ","+
                    cd.getInstructors() + ","+
                    cd.getCode() ;
            outputFile.append(data);
            outputFile.flush();
            outputFile.close();

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}