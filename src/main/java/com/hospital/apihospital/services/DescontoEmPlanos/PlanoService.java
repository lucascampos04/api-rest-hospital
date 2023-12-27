package com.hospital.apihospital.services.DescontoEmPlanos;

import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PlanoService {

    @Value("${plano.bronze}")
    private double descontoPlanoBronze;

    @Value("${plano.ouro}")
    private double descontoPlanoOuro;

    @Value("${plano.prata}")
    private double descontoPlanoPrata;

    @Value("${plano.vip}")
    private double descontoPlanoVip;

    public double calcularValorComDesconto(CadastrarUsers paciente, double valorConsulta) {
        String tipoPlano = paciente.getPlano_paciente();

        switch (tipoPlano.toLowerCase()) {
            case "bronze":
                return aplicarDescontoBronze(valorConsulta);
            case "gold":
                return aplicarDescontoOuro(valorConsulta);
            case "silver":
                return aplicarDescontoPrata(valorConsulta);
            case "vip":
                return aplicarDescontoVip(valorConsulta);
            default:
                return valorConsulta;
        }
    }

    private double aplicarDescontoBronze(double valorConsulta) {
        return valorConsulta - (valorConsulta * descontoPlanoBronze);
    }

    private double aplicarDescontoOuro(double valorConsulta) {
        return valorConsulta - (valorConsulta * descontoPlanoOuro);
    }

    private double aplicarDescontoPrata(double valorConsulta) {
        return valorConsulta - (valorConsulta * descontoPlanoPrata);
    }

    private double aplicarDescontoVip(double valorConsulta) {
        if (descontoPlanoVip > 0 && descontoPlanoVip < 1) {
            double desconto = valorConsulta * descontoPlanoVip;
            return Math.max(valorConsulta - desconto, 0);
        }
        return valorConsulta;
    }
}
