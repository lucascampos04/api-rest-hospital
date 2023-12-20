package com.hospital.apihospital.services.CadastroService;

import com.hospital.apihospital.Model.DTO.UsersDTO;
import com.hospital.apihospital.Model.Enum.CargoEnum;
import com.hospital.apihospital.Model.Enum.RoleEnum;
import com.hospital.apihospital.Model.Repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CadastrarUser {
    @Autowired
    private UsersRepository usersRepository;

    @Transactional
    public ResponseEntity<String> cadastrarPaciente(UsersDTO usersDTO){
        try{
            ResponseEntity<String> validationResult = validateDataOfUser(usersDTO);
            if (validationResult != null){
                return validationResult;
            }

        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
        return null;
    }

    private ResponseEntity<String> validateRoleIsUserOrEmployees(UsersDTO usersDTO){
        CargoEnum cargo = usersDTO.getCargo();
        RoleEnum role = usersDTO.getRole();

        // verificação se esta sendo inserido um paciente
        if (cargo == null){
            if (RoleEnum.PACIENTE.equals(role)){
                return ResponseEntity.ok().build();
            }
        }

        // verificação se esta sendo inserido um funcionario
        if (cargo != null){
            if (RoleEnum.FUNCIONARIO.equals(role)){
                return ResponseEntity.ok().build();
            }

            ResponseEntity<String> validateEmployeesFinally = validateDataEmployees(usersDTO);

            try {
                return validateEmployeesFinally;
            } catch (Exception e){
                return ResponseEntity.badRequest().body("Erro ao validadar funcionario");
            }
        }

        return null;
    }
    private ResponseEntity<String> validateDataOfUser(UsersDTO usersDTO){
        if (usersRepository.existsByRg(usersDTO.getRg())){
            return ResponseEntity.badRequest().body("Dados incorreto. Tente Novamente");
        }

        if (usersRepository.existsByCpf(usersDTO.getRg())){
            return ResponseEntity.badRequest().body("Dados incorreto. Tente Novamente");
        }

        if (!"Masculino".equalsIgnoreCase(usersDTO.getGenero()) && !"Feminino".equalsIgnoreCase(usersDTO.getGenero())) {
            return ResponseEntity.badRequest().body("Erro: Gênero inválido. Escolha 'Masculino' ou 'Feminino'.");
        }

        return null;
    }

    private ResponseEntity<String> validateDataEmployees(UsersDTO usersDTO){
        CargoEnum cargo = usersDTO.getCargo();
        Double salario = usersDTO.getSalario();

        // validando cargo e salario.
        if (salario == null){
            return ResponseEntity.badRequest().body("Erro: Salário é Obrigatorio");
        }

        switch (cargo){
            case GERENTE:
                if (salario < 5000.0){
                    return ResponseEntity.badRequest().body("Erro: Gerentes devem ter um salário mínimo de 5000.0.");
                }
                break;
            case MEDICO:
                if (salario < 10000.0){
                    return ResponseEntity.badRequest().body("Erro: Medicos devem ter um salário mínimo de 10000.0.");
                }
                break;
            case ADMINISTRATIVO:
                if (salario < 3000.0){
                    return ResponseEntity.badRequest().body("Erro: Adminstradores e funcionarios relacionados ao RH devem ter um salário mínimo de 3000.0.");
                }
                break;
            case ENFERMEIRO:
                if (salario < 2500.0){
                    return ResponseEntity.badRequest().body("Erro: Enfermeiros devem ter um salário mínimo de 2500.0.");
                }
                break;
            case FAXINEIRO:
                if (salario < 2000.0){
                    return ResponseEntity.badRequest().body("Erro: Faxineiros devem ter um salário mínimo de 2000.0.");
                }
                break;
            case OPERARIO:
                if (salario < 2000.0){
                    return ResponseEntity.badRequest().body("Erro: Operarios devem ter um salário mínimo de 2000.0.");
                }
                break;
            default:
                return ResponseEntity.notFound().build();
        }




       return null;
    }
}
