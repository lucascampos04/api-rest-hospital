package com.hospital.apihospital.Model.DTO;

import com.hospital.apihospital.Model.Enum.RoleEnum;
import lombok.Data;

import java.util.Date;

@Data
public class ConsultaDTO {
    private Long id;
    private String tipoConsulta;
    private Date data;
    private Double valor;
    private Long userId;
    private String userName;
    private String email;
    private RoleEnum role;
    private String telefone;
}
