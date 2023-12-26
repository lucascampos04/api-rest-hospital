package com.hospital.apihospital.services.UsersServices.UpdateUsers;

import com.hospital.apihospital.Model.DTO.UsersDTO;
import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.Model.Repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;
@Service
public class UpdateUserService {
    private final UsersRepository usersRepository;

    public UpdateUserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * Atualiza um usuário existente.
     *
     * @param userId     ID do usuário a ser atualizado.
     * @param usersDTO   O objeto UsersDTO contendo as novas informações do usuário.
     * @param result     O objeto BindingResult para validação.
     * @return Uma resposta com a mensagem de sucesso ou erro.
     */
    @Transactional
    public ResponseEntity<String> atualizarUsuario(Long userId, UsersDTO usersDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Erro de validação");
        }

        Optional<CadastrarUsers> optionalUser = usersRepository.findById(userId);
        if (optionalUser.isPresent()) {
            CadastrarUsers user = optionalUser.get();
            user.setNome(usersDTO.getNome());
            user.setCpf(usersDTO.getCpf());
            user.setRg(usersDTO.getRg());
            user.setEmail(usersDTO.getEmail());


            usersRepository.save(user);
            return ResponseEntity.ok().body("Usuário atualizado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }

}
