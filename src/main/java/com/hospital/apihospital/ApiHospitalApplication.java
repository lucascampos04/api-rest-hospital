package com.hospital.apihospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiHospitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiHospitalApplication.class, args);
        System.out.println("Servidor rodando");
    }

}
