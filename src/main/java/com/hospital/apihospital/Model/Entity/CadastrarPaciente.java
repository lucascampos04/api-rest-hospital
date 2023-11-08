package com.hospital.apihospital.Model.Entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "cadastrarPaciente")
@Data
public class CadastrarPaciente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome", length = 80)
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Column(name = "rg", length = 15, unique = true, nullable = false)
    @NotBlank(message = "O RG é obrigatório")
    private String rg;

    @Column(name = "cpf", length = 20, unique = true, nullable = false)
    @NotBlank(message = "O CPF é obrigatório")
    private String cpf;

    @Column(name = "data_nascimento")
    @Temporal(TemporalType.DATE)
    @NotBlank(message = "A data de nascimento é obrigatorio")
    private Date dataNascimento;

    @Column(name = "genero", columnDefinition = "CHAR(1)")
    @NotBlank(message = "Genero obrigatorio")
    private Character genero;

    @Column(name = "plano_paciente", length = 50)
    @NotBlank
    private String plano_paciente;

    @Column(name = "data_registro")
    @Temporal(TemporalType.TIMESTAMP)
    @NotBlank(message = "A data de registro é obrigatória")
    private Date dataRegistro;

}
