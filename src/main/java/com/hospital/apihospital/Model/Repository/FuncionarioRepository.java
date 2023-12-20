package com.hospital.apihospital.Model.Repository;

import com.hospital.apihospital.Model.Entity.CadastrarFuncionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository  extends JpaRepository<CadastrarFuncionario, Long> {
    boolean existsByRg(String rg);

    boolean existsByCpf(String cpf);
}
