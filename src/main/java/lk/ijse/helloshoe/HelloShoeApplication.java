package lk.ijse.helloshoe;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HelloShoeApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloShoeApplication.class, args);

    }

//    @component


    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();

    }

}
