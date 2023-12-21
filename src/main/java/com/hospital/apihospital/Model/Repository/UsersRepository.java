package com.hospital.apihospital.Model.Repository;

import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<CadastrarUsers, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByRg(String rg);
    Optional<CadastrarUsers>  findByRg(String rg);
    Optional<CadastrarUsers>  findByCpf(String rg);
    @Query("SELECT p " +
            "FROM CadastrarUsers p " +
            "WHERE " +
            "REPLACE(p.nome, ' ', '-' ) = :nomeFormatado")
    Optional<CadastrarUsers>  findByNomeFormatado(@Param("nomeFormatado") String nomeFormatado);
    @Query("SELECT p " +
            "FROM CadastrarUsers p " +
            "WHERE (p.nome IS NULL OR p.nome = '' OR p.rg IS NULL OR p.rg = '' OR p.cpf IS NULL OR p.cpf = '')")
    List<CadastrarUsers> findByCamposNulos();

    Optional<CadastrarUsers> findById(Long id);

    void deleteById(Long id);
}