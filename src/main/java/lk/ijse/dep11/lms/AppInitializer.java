package lk.ijse.dep11.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppInitializer {
    public static void main(String[] args) {
        System.setProperty("application.title","LMS");
        System.setProperty("application.version", "v1.0.0");
        SpringApplication.run(AppInitializer.class,args);
    }
}
