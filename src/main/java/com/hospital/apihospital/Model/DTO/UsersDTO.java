package com.hospital.apihospital.Model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.Model.Enum.CargoEnum;
import com.hospital.apihospital.Model.Enum.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@NoArgsConstructor
@Data
public class UsersDTO {
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Pattern(regexp = "^[^0-9]+$", message = "O nome não deve conter números")
    private String nome;

    @Column(name = "cpf", length = 19, unique = true)
    @NotBlank(message = "O CPF é obrigatório")
    private String cpf;

    @Column(name = "rg", length = 15, unique = true)
    @NotBlank(message = "O RG é obrigatório")
    private String rg;

    @Column(name = "data_nascimento")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotBlank(message = "A data de nascimento é obrigatorio")
    private Date dataNascimento;

    @Column(name = "genero", length = 30)
    @NotBlank(message = "Genero obrigatorio")
    @Pattern(regexp = "^[^0-9]+$", message = "O o genero não deve conter números")
    private String genero;

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

    @Column(name = "plano_paciente", length = 50)
    @NotBlank
    private String plano_paciente;

    @JsonIgnore
    @Column(name = "cargo")
    @Enumerated(EnumType.STRING)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CargoEnum cargo;

    @Column(name = "salario")
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double salario;

    @Column(name = "role", length = 20)
    @NotBlank(message = "A role é obrigatória")
    @Pattern(regexp = "^[^0-9]+$", message = "O role não deve conter números")
    private RoleEnum role;

    public UsersDTO(Long id,
                    String nome,
                    String cpf,
                    String rg,
                    Date dataNascimento,
                    String genero,
                    String email,
                    String telefone,
                    Date dataRegistro,
                    String planoPaciente,
                    RoleEnum role,
                    CargoEnum cargo,
                    Double salario) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.email = email;
        this.telefone = telefone;
        this.dataRegistro = dataRegistro;
        this.plano_paciente = planoPaciente;
        this.role = role;
        this.cargo = cargo;
        this.salario = salario;
    }

    public static UsersDTO fromEntity(CadastrarUsers users) {
        UsersDTO usersDTO = new UsersDTO(
                users.getId(),
                users.getNome(),
                users.getCpf(),
                users.getRg(),
                users.getDataNascimento(),
                users.getGenero(),
                users.getEmail(),
                users.getTelefone(),
                users.getDataRegistro(),
                users.getPlano_paciente(),
                users.getRole(),
                users.getCargo(),
                users.getSalario()
        );

        if (RoleEnum.PACIENTE.equals(users.getRole())) {
            usersDTO.setCargo(null);
            usersDTO.setSalario(null);
        }

        return usersDTO;
    }
}
