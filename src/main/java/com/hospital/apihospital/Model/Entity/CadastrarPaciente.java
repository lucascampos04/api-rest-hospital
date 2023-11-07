package com.hospital.apihospital.Model.Entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;

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
}
