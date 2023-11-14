package com.hospital.apihospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiHospitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiHospitalApplication.class, args);
        System.out.println("Api rodando na url : http://localhost:8080/");
        System.out.println("Rotas da API : ");
        System.out.println("Rota de ver pacientes : http://localhost:8080/api/v1/pacientes");
        System.out.println("Rota de adicionar paciente : http://localhost:8080/api/v1/pacientes/addpaciente");
    }

}
