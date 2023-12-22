package com.hospital.apihospital.Model.Repository;

import com.hospital.apihospital.Model.Entity.MarcaConsultaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarcaConsultaRepository extends JpaRepository<MarcaConsultaEntity, Long> {
    List<MarcaConsultaEntity> findByCadastrarUsers_Id(Long cadastrarUsersId);
}