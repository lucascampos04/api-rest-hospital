package com.hospital.apihospital.Model.DTO;

import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.Model.Enum.CargoEnum;
import com.hospital.apihospital.Model.Enum.RoleEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Date dataRegistro;
    private String plano_paciente;
    private RoleEnum role;
    private CargoEnum cargo;
    private Double salario;


    public static UsersDTO fromEntity(CadastrarUsers users) {
        return new UsersDTO(
                        users.getId(),
                        users.getNome(),
                        users.getCpf(),
                        users.getRg(),
                        users.getDataNascimento(),
                        users.getGenero(),
                        users.getDataRegistro(),
                        users.getPlano_paciente(),
                users.getRole(),
                users.getCargo(),
                        users.getSalario()
                );
    }




    public UsersDTO(Long id, String nome, String cpf, String rg, Date dataNascimento, String genero, Date dataRegistro, String plano_paciente, RoleEnum role, CargoEnum cargo, Double salario) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.dataRegistro = dataRegistro;
        this.plano_paciente = plano_paciente;
        this.cargo = cargo;
        this.role = role;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }
}
