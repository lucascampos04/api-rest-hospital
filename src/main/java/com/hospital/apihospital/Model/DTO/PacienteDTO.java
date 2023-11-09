package com.hospital.apihospital.Model.DTO;

import com.hospital.apihospital.Model.Entity.CadastrarPaciente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class PacienteDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String rg;
    private Date dataNascimento;
    private Character genero;
    private Date dataRegistro;
    private String plano_paciente;

    public static PacienteDTO fromEntity(CadastrarPaciente paciente) {
        return new PacienteDTO(
                paciente.getId(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getRg(),
                paciente.getDataNascimento(),
                paciente.getGenero(),
                paciente.getDataRegistro(),
                paciente.getPlano_paciente()
        );
    }

    public PacienteDTO(Long id, String nome) {
        this(id, nome, null, null, null, null, null, null);
    }

    public PacienteDTO(Long id, String nome, String cpf, String rg, Date dataNascimento, Character genero, Date dataRegistro, String plano_paciente) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.dataRegistro = dataRegistro;
        this.plano_paciente = plano_paciente;
    }

    public Long getId() {
        return id;
    }
}
