package com.hospital.apihospital.Model.Entity.AreasDoctorWorksModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Table(name = "area_medico")
@Entity
public class AreaWorkModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(name = "formacao")
    private String formacao;

    @NotBlank
    @Column(name = "setor")
    private String setor;

    @OneToOne
    @JoinColumn(name = "user_id")
    private CadastrarUsers user;

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }
}
