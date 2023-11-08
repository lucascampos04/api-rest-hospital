package com.hospital.apihospital.services;

import com.hospital.apihospital.Model.Entity.CadastrarPaciente;
import org.springframework.stereotype.Service;

@Service
public class PlanoVipServices {
    public double calcularValorComDesconto(CadastrarPaciente paciente, double valorConsulta){
        String tipoPlano = paciente.getPlano_paciente();

        if ("vip".equalsIgnoreCase(tipoPlano)) {
            double desconto = valorConsulta * 10.0;
            return valorConsulta - desconto;
        }

        return valorConsulta;
    }
}
