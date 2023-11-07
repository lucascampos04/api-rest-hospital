package com.hospital.apihospital.Model.Repository;

import com.hospital.apihospital.Model.Entity.CadastrarPaciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<CadastrarPaciente, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByRg(String rg);
    Optional<CadastrarPaciente>  findByRg(String rg);
    Optional<CadastrarPaciente>  findByCpf(String rg);

}
