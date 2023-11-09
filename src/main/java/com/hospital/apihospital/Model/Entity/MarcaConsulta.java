package com.hospital.apihospital.Model.Entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
@Entity
@Table(name = "marcaConsulta")
@Data
public class MarcaConsulta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "data")
    @NotBlank
    private Date data;

    @Column(name = "horario", length = 20)
    @NotBlank
    private String horario;

    @Column(name = "tipoConsulta", length = 50)
    @NotBlank
    private String tipoConsulta;

    @Column(name = "formaDePagamento", length = 50)
    @NotBlank
    private String formaDePagamento;

    @Column(name = "valor_consulta")
    @NotBlank
    private Double valorConsulta;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private CadastrarPaciente paciente;

}
