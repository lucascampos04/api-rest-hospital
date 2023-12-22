package com.hospital.apihospital.Model.DTO;

import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.Model.Entity.MarcaConsultaEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class MarcaConsultaDTO {
    private Long id;

    @NotBlank(message = "O tipo de consulta é obrigatório")
    private String tipoConsulta;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date data;

    private Double valor;

    private Long cadastrarUsersId;
    private String nomeUsuario;
    private String emailUsuario;
    private String telefoneUsuario;
    private String roleUsuario;

    public MarcaConsultaDTO(Long id, String tipoConsulta, Date data, Double valor, Long cadastrarUsersId, String nomeUsuario, String emailUsuario, String telefoneUsuario, String roleUsuario) {
        this.id = id;
        this.tipoConsulta = tipoConsulta;
        this.data = data;
        this.valor = valor;
        this.cadastrarUsersId = cadastrarUsersId;
        this.nomeUsuario = nomeUsuario;
        this.emailUsuario = emailUsuario;
        this.telefoneUsuario = telefoneUsuario;
        this.roleUsuario = roleUsuario;
    }
}
