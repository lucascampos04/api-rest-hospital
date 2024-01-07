package com.hospital.apihospital.Model.Repository;

import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<CadastrarUsers, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByRg(String rg);
    Optional<CadastrarUsers> findById(Long id);
    void deleteById(Long id);
    boolean existsByEmail(String email);
    boolean existsByTelefone(String telefone);
    CadastrarUsers findByEmail(String email);
}
