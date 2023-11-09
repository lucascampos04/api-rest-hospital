package com.hospital.apihospital.Model.Repository;

import com.hospital.apihospital.Model.Entity.MarcaConsulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarcaConsultaRepository extends JpaRepository<MarcaConsulta, Long> {
    List<MarcaConsulta> findByPacienteId(Long pacienteID);
    void deleteByPacienteId(Long pacienteId);

}
