package com.hospital.apihospital.Model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hospital.apihospital.Model.Entity.MarcaConsulta;

import java.util.Date;

public record ConsultaDTO(Long id, Date data, String horario, String tipoConsulta, String formaDePagamento, Double valorConsulta, Long idPaciente) {

    public static ConsultaDTO fromEntity(MarcaConsulta marcaConsulta) {
        return new ConsultaDTO(
                marcaConsulta.getId(),
                marcaConsulta.getData(),
                marcaConsulta.getHorario(),
                marcaConsulta.getTipoConsulta(),
                marcaConsulta.getFormaDePagamento(),
                marcaConsulta.getValorConsulta(),
                marcaConsulta.getPaciente().getId()
        );
    }
}

