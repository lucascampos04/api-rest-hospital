package com.hospital.apihospital.Model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.Model.Enum.CargoEnum;
import com.hospital.apihospital.Model.Enum.RoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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

    @Column(name = "password")
    @NotBlank(message = "A senha é obrigatoria")
    private String password;

    @Column(name = "password_antiga")
    private String passwordBefore;

    @NotBlank(message = "O CPF é obrigatório")
    private String cpf;

    @NotBlank(message = "O RG é obrigatório")
    private String rg;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotBlank(message = "A data de nascimento é obrigatória")
    private Date dataNascimento;

    @NotBlank(message = "Genero obrigatório")
    @Pattern(regexp = "^[^0-9]+$", message = "O genero não deve conter números")
    private String genero;

    @NotBlank
    private String email;

    @NotBlank
    private String telefone;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @NotBlank(message = "A data de registro é obrigatória")
    private Date dataRegistro;

    @NotBlank
    private String planoPaciente;

    @Enumerated
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CargoEnum cargo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double salario;

    @Column(name = "role", length = 20)
    @NotBlank(message = "A role é obrigatória")
    @Pattern(regexp = "^[^0-9]+$", message = "O role não deve conter números")
    private RoleEnum role;

    public UsersDTO(Long id, String nome, String cpf, String rg, Date dataNascimento,
                    String genero, String email, String telefone, Date dataRegistro,
                    String planoPaciente, RoleEnum role, CargoEnum cargo, Double salario,
                    String password, String passwordBefore) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.email = email;
        this.telefone = telefone;
        this.dataRegistro = dataRegistro;
        this.planoPaciente = planoPaciente;
        this.role = role;
        this.cargo = cargo;
        this.salario = salario;
        this.password = password;
        this.passwordBefore = passwordBefore;
    }

    public UsersDTO(Long idUser, String nomeUsuario, String emailUsuario, RoleEnum roleEnum) {
    }

    public static UsersDTO fromEntity(CadastrarUsers user) {
        return new UsersDTO(
                user.getId(),
                user.getNome(),
                user.getCpf(),
                user.getRg(),
                user.getDataNascimento(),
                user.getGenero(),
                user.getEmail(),
                user.getTelefone(),
                user.getDataRegistro(),
                user.getPlano_paciente(),
                user.getRole(),
                user.getCargo(),
                user.getSalario(),
                user.getPassword(),
                user.getPasswordBefore()
        );
    }
}
