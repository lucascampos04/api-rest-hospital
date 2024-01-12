package com.hospital.apihospital.Model.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity(name = "consultas")
@Data
public class ConsultaEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "tipoConsulta")
    private String tipoConsulta;

    @Column(name = "data")
    private Date data;

    @Column(name = "valor")
    private Double valor;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private CadastrarUsers users;
}
