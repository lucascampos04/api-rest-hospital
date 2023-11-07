package com.hospital.apihospital.Model.DTO;

import com.hospital.apihospital.Model.Entity.CadastrarPaciente;

public record PacienteDTO(Long id, String nome, String cpf, String rg) {
    public static PacienteDTO fromEntity(CadastrarPaciente paciente){
        return new PacienteDTO(paciente.getId(), paciente.getNome(), paciente.getCpf(), paciente.getRg());
    }
    public PacienteDTO(Long id, String nome){
        this(id, nome, null, null);
    }

    public PacienteDTO(Long id, String nome, String cpf, String rg) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
    }
    public Long getId(){
        return id;
    }
}
