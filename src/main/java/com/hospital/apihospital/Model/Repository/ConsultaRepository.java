package com.hospital.apihospital.Model.Repository;

import com.hospital.apihospital.Model.Entity.ConsultaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<ConsultaEntity, Long> {
    @Query("SELECT c FROM consultas c JOIN FETCH c.users")
    List<ConsultaEntity> findAllWithUsers();

}
