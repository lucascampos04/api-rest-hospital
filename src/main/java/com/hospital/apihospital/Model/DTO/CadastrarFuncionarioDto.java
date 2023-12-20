package com.hospital.apihospital.Model.DTO;

import com.hospital.apihospital.Model.Entity.CadastrarFuncionario;
import com.hospital.apihospital.Model.Enum.CargoEnum;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link com.hospital.apihospital.Model.Entity.CadastrarFuncionario}
 */
@Value
@Data
public class CadastrarFuncionarioDto implements Serializable {
    Long id;
    String nome;
    String rg;
    String cpf;
    Date dataNascimento;
    String genero;
    CargoEnum cargo;
    float salario;
    Date dataRegistro;

    public static CadastrarFuncionarioDto fromEntity(CadastrarFuncionario funcionario) {
        return new CadastrarFuncionarioDto(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getRg(),
                funcionario.getCpf(),
                funcionario.getDataNascimento(),
                funcionario.getGenero(),
                funcionario.getCargo(),
                funcionario.getSalario(),
                funcionario.getDataRegistro()
        );
    }

    public CadastrarFuncionarioDto(Long id, String nome, String rg, String cpf, Date dataNascimento, String genero, CargoEnum cargo, float salario, Date dataRegistro) {
        this.id = id;
        this.nome = nome;
        this.rg = rg;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.cargo = cargo;
        this.salario = salario;
        this.dataRegistro = dataRegistro;
    }
}
