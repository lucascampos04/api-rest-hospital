package com.hospital.apihospital.Model.Repository;

import com.hospital.apihospital.Model.Entity.AreasDoctorWorksModel.AreaWorkModel;
import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaWorkModelRepository extends JpaRepository<AreaWorkModel, Long> {
    @Query("SELECT u FROM CadastrarUsers u LEFT JOIN FETCH u.areaWorkModel")
    List<CadastrarUsers> findAllWithAreaWorkModel();
}
