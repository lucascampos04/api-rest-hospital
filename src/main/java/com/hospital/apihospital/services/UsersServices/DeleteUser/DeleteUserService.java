package com.hospital.apihospital.services.UsersServices.DeleteUser;

import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.Model.Repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class DeleteUserService {
    private final UsersRepository usersRepository;

    public DeleteUserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * Exclui um usuário existente.
     *
     * @param userId ID do usuário a ser excluído.
     * @return Uma resposta com a mensagem de sucesso ou erro.
     */
    @Transactional
    public ResponseEntity<String> excluirUsuario(@PathVariable Long userId) {
        System.out.println("Recebida solicitação para excluir usuário com ID: " + userId);

        Optional<CadastrarUsers> optionalUser = usersRepository.findById(userId);
        if (optionalUser.isPresent()) {
            usersRepository.deleteById(userId);
            return ResponseEntity.ok().body("Usuário excluído com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }
}
