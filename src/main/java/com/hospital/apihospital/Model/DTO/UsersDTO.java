package com.hospital.apihospital.Model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.Model.Enum.CargoEnum;
import com.hospital.apihospital.Model.Enum.RoleEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@NoArgsConstructor
@Data
public class UsersDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String rg;
    private Date dataNascimento;
    private String genero;
    private String email;
    private String telefone;
    private Date dataRegistro;
    private String plano_paciente;
    private RoleEnum role;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CargoEnum cargo;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double salario;

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
