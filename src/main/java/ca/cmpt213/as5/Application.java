package ca.cmpt213.as5;

import ca.cmpt213.as5.model.CourseSummary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        CourseSummary.createModel();
        SpringApplication.run(Application.class, args);
    }
}
