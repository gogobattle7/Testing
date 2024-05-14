package com.example.demo;

import com.example.demo.compiler.JasperCompiler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Demo1Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
    }

//    @Bean
//    public CommandLineRunner compileJRXMLToJasper() {
//        return args -> {
//            JasperCompiler.compileJRXMLToJasper("src/main/resources/report.jrxml");
//        };
//    }

}
