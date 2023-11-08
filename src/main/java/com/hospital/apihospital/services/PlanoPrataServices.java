package com.hospital.apihospital.services;

import com.hospital.apihospital.Model.Entity.CadastrarPaciente;
import org.springframework.stereotype.Service;

@Service
public class PlanoPrataServices {
    public double calcularValorComDesconto(CadastrarPaciente paciente, double valorConsulta){
        String tipoPlano = paciente.getPlano_paciente();

        if ("prata".equalsIgnoreCase(tipoPlano)) {
            double desconto = valorConsulta * 0.1;
            return valorConsulta - desconto;
        }

        return valorConsulta;
    }
}
