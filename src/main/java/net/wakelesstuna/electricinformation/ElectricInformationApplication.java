package net.wakelesstuna.electricinformation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ElectricInformationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectricInformationApplication.class, args);
    }

}
