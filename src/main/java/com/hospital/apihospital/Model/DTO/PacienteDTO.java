package com.hospital.apihospital.Model.DTO;

import com.hospital.apihospital.Model.Entity.CadastrarPaciente;

import java.util.Date;

public record PacienteDTO(Long id, String nome, String cpf, String rg, Date dataNascimento, Character genero, Date dataRegistro) {
    public static PacienteDTO fromEntity(CadastrarPaciente paciente){
        return new PacienteDTO(paciente.getId(), paciente.getNome(), paciente.getCpf(), paciente.getRg(), paciente.getDataNascimento(), paciente.getGenero(), paciente.getDataRegistro());
    }
    public PacienteDTO(Long id, String nome){
        this(id, nome, null, null, null, null, null);
    }

    public PacienteDTO(Long id, String nome, String cpf, String rg, Date dataNascimento, Character genero, Date dataRegistro) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.dataRegistro = dataRegistro;
    }
    public Long getId(){
        return id;
    }
}
