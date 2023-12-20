package com.hospital.apihospital.Model.Entity;

import com.hospital.apihospital.Model.Enum.CargoEnum;
import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
@Entity
@Table(name = "cadastrarFuncionario")
@Data
public class CadastrarFuncionario {
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

    @Column(name = "cargo")
    @Enumerated(EnumType.STRING)
    @NotBlank
    private CargoEnum cargo;

    @Column(name = "salario")
    @NotBlank
    private float salario;

    @Column(name = "data_registro")
    @Temporal(TemporalType.TIMESTAMP)
    @NotBlank(message = "A data de registro é obrigatória")
    private Date dataRegistro;

    public void setCargo(CargoEnum cargo){
        this.cargo = cargo;

        if (cargo != null){
            switch (cargo){
                case GERENTE:
                    this.salario = 5000.0f;
                    break;
                case MEDICO:
                    this.salario = 7000.0f;
                    break;
                case ADMINISTRATIVO:
                    this.salario = 3000.0f;
                    break;
                case FAXINEIRO:
                    this.salario = 1200.0f;
                    break;
                case ENFERMEIRO:
                    this.salario = 4000.0f;
                    break;
                case OPERARIO:
                    this.salario = 1200.0f;
                    break;
                default:
                    this.salario = 0.0f;
            }
        }
    }



}
