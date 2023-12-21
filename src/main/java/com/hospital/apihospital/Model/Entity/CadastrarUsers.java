package com.hospital.apihospital.Model.Entity;

import com.hospital.apihospital.Model.Enum.CargoEnum;
import com.hospital.apihospital.Model.Enum.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cadastrarUsers")
@Data
public class CadastrarUsers {
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

    @Column(name = "genero", length = 30)
    @NotBlank(message = "Genero obrigatorio")
    private String genero;

    @Column(name = "plano_paciente", length = 50)
    @NotBlank
    private String plano_paciente;

    @Column(name = "email")
    @NotBlank
    private String email;

    @Column(name = "telefone")
    @NotBlank
    private String telefone;

    @Column(name = "data_registro")
    @Temporal(TemporalType.TIMESTAMP)
    @NotBlank(message = "A data de registro é obrigatória")
    private Date dataRegistro;

    @NotBlank
    @Column(name = "cargo")
    @Enumerated(EnumType.STRING)
    private CargoEnum cargo;

    @Column(name = "salario")
    @NotBlank
    private Double salario;

    @Column(name = "role", length = 20)
    @NotBlank(message = "A role é obrigatória")
    private RoleEnum role;



}
