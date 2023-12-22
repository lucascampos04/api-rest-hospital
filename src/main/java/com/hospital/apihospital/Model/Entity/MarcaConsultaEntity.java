package com.hospital.apihospital.Model.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Entity(name = "marca_consulta")
@Data
public class MarcaConsultaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "tipo_consulta")
    private String tipoConsulta;

    @Column(name = "data")
    private Date data;

    @Column(name = "valor")
    private Double valor;

    @ManyToOne
    @JoinColumn(name = "cadastrarUsers_id")
    private CadastrarUsers cadastrarUsers;
}
