package com.hospital.apihospital.Model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.Model.Enum.CargoEnum;
import com.hospital.apihospital.Model.Enum.RoleEnum;
import jakarta.persistence.Enumerated;
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

    @NotBlank(message = "A role é obrigatória")
    @Pattern(regexp = "^[^0-9]+$", message = "O role não deve conter números")
    private RoleEnum role;

    public UsersDTO(Long id, String nome, String cpf, String rg, Date dataNascimento, String genero,
                    String email, String telefone, Date dataRegistro, String planoPaciente, RoleEnum role,
                    CargoEnum cargo, Double salario) {
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

        return usersDTO;
    }
}
