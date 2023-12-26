package com.hospital.apihospital.controller.RegistrosPacientes;

import javax.validation.Valid;

import com.hospital.apihospital.services.UsersServices.CadastroService.CadastroService.CadastrarUserService;
import com.hospital.apihospital.services.UsersServices.DeleteUser.DeleteUserService;
import com.hospital.apihospital.services.UsersServices.ListUsersServices.ListUserServices;
import com.hospital.apihospital.services.UsersServices.UpdateUsers.UpdateUserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.hospital.apihospital.Model.DTO.UsersDTO;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/v1/usuarios")
public class UsersController {

    private final CadastrarUserService cadastrarUserService;

    private final UpdateUserService updateUserService;

    private final ListUserServices listUserServices;
    private final DeleteUserService deleteUserService;

    public UsersController(CadastrarUserService cadastrarUserService, UpdateUserService updateUserService, ListUserServices listUserServices, DeleteUserService deleteUserService) {
        this.cadastrarUserService = cadastrarUserService;
        this.updateUserService = updateUserService;
        this.listUserServices = listUserServices;
        this.deleteUserService = deleteUserService;
    }

    @GetMapping("/list/users")
    private ResponseEntity<List<UsersDTO>> listaUsuarios(){
        List<UsersDTO> usuarios = listUserServices.listUsers();
        return ResponseEntity.ok().body(usuarios);
    }

    /**
     * Cadastra um novo paciente.
     *
     * @param usersDTO O objeto PacienteDTO contendo informações do paciente a
     *                    ser cadastrado.
     * @param result      O objeto BindingResult para validação.
     * @return Uma resposta com a mensagem de sucesso ou erro.
     */
    @PostMapping("/add/users")
    public ResponseEntity<String> cadastrar(@Valid @RequestBody UsersDTO usersDTO, BindingResult result) {
        ResponseEntity<String> response = cadastrarUserService.cadastrarPaciente(usersDTO, result);
        if (response.getStatusCode() == HttpStatus.OK){
            return ResponseEntity.ok().body(response.getBody());

        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }
    /**
     * Atualiza um usuário existente.
     *
     * @param userId     ID do usuário a ser atualizado.
     * @param usersDTO   O objeto UsersDTO contendo as novas informações do usuário.
     * @param result     O objeto BindingResult para validação.
     * @return Uma resposta com a mensagem de sucesso ou erro.
     */
    @PutMapping("/update/users/{userId}")
    public ResponseEntity<String> atualizarUsuario(
            @PathVariable Long userId,
            @Valid @RequestBody UsersDTO usersDTO,
            BindingResult result) {
        ResponseEntity<String> response = updateUserService.atualizarUsuario(userId, usersDTO, result);
        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok().body(response.getBody());
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }

    /**
     * Exclui um usuário existente.
     *
     * @param userId ID do usuário a ser excluído.
     * @return Uma resposta com a mensagem de sucesso ou erro.
     */
    @DeleteMapping("/delete/users/{userId}")
    public ResponseEntity<String> excluirUsuario(@PathVariable Long userId) {
        ResponseEntity<String> response = deleteUserService.excluirUsuario(userId);
        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok().body(response.getBody());
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }
}