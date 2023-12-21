package com.hospital.apihospital.Model.DTO;

import com.hospital.apihospital.Model.Entity.AreasDoctorWorksModel.AreaWorkModel;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * DTO for {@link com.hospital.apihospital.Model.Entity.AreasDoctorWorksModel.AreaWorkModel}
 */
@Data
public class AreaWorkDto implements Serializable {
    @NotBlank
    @Column(name = "formacao")
    private String formacao;

    @NotBlank
    @Column(name = "setor")
    private String setor;

    public static AreaWorkDto fromEntity(AreaWorkModel areaWorkModel) {
        AreaWorkDto areaWorkDto = new AreaWorkDto();
        areaWorkDto.setFormacao(areaWorkModel.getFormacao());
        areaWorkDto.setSetor(areaWorkModel.getSetor());
        return areaWorkDto;
    }
}
