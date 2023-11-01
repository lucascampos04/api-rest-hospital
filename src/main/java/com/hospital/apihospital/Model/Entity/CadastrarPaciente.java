package com.hospital.apihospital.Model.Entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Entity
@Table(name = "cadastrarPaciente")
@Data
public class CadastrarPaciente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome", length = 80)
    @NotEmpty
    private String nome;

    @Column(name = "rg", length = 15, unique = true, nullable = false)
    @NotEmpty
    private String rg;

    @Column(name = "cpf", length = 20, unique = true, nullable = false)
    @NotEmpty
    private String cpf;
}
