package com.hospital.apihospital.Model.Repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hospital.apihospital.Model.Entity.CadastrarPaciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<CadastrarPaciente, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByRg(String rg);
    Optional<CadastrarPaciente>  findByRg(String rg);
    Optional<CadastrarPaciente>  findByCpf(String rg);
    @Query("SELECT p " +
            "FROM CadastrarPaciente p " +
            "WHERE " +
            "REPLACE(p.nome, ' ', '-' ) = :nomeFormatado")
    Optional<CadastrarPaciente>  findByNomeFormatado(@Param("nomeFormatado") String nomeFormatado);
    @Query("SELECT p " +
            "FROM CadastrarPaciente p " +
            "WHERE (p.nome IS NULL OR p.nome = '' OR p.rg IS NULL OR p.rg = '' OR p.cpf IS NULL OR p.cpf = '')")
    List<CadastrarPaciente> findByCamposNulos();

}
