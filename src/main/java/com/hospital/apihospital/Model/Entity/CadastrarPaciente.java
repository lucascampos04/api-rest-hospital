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
    @NotBlank
    private String nome;

    @Column(name = "rg", length = 15)
    @NotBlank
    private String rg;

    @Column(name = "cpf", length = 20)
    @NotBlank
    private String cpf;
}
