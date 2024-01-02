package com.hospital.apihospital.services.UsersServices.UpdateUsers;

import com.hospital.apihospital.Model.DTO.UsersDTO;
import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.Model.Repository.UsersRepository;
import com.hospital.apihospital.services.SendEmail.MessageCreateAccountInSendOfPassword;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class UpdateUserService {
    private final UsersRepository usersRepository;
    private final MessageCreateAccountInSendOfPassword messageUpdatePassword;

    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UpdateUserService(UsersRepository usersRepository, MessageCreateAccountInSendOfPassword messageUpdatePassword, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.messageUpdatePassword = messageUpdatePassword;
        this.passwordEncoder = passwordEncoder;
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
            user.setPasswordBefore(passwordEncoder.encode(user.getPassword()));
            user.setPassword(passwordEncoder.encode(usersDTO.getPassword()));

            if (!user.getPasswordBefore().equals(user.getPassword())){
                try {
                    messageUpdatePassword.sendPasswordChangeNotification(user.getEmail(), user.getNome());
                    System.out.println("Senha alterada. Nova senha: " + user.getPassword() + ", Senha antiga: " + user.getPasswordBefore());
                } catch (Exception e) {
                    System.err.println("Erro ao enviar e-mail de notificação: " + e.getMessage());
                }
            }

            usersRepository.save(user);


        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        return ResponseEntity.ok("Usuário atualizado com sucesso");
    }
}
