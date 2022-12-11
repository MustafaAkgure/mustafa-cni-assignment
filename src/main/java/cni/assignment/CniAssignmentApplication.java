package cni.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CniAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CniAssignmentApplication.class, args);
    }

}
