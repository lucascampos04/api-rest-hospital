package com.hospital.apihospital.Model.Repository;

import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.Model.Entity.ConsultaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<ConsultaEntity, Long> {
    @Query("SELECT c FROM consultas c JOIN FETCH c.users")
    List<ConsultaEntity> findAllWithUsers();

    List<ConsultaEntity> findByUsers(CadastrarUsers user);

    @Modifying
    @Query("DELETE FROM consultas c WHERE c.users.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
