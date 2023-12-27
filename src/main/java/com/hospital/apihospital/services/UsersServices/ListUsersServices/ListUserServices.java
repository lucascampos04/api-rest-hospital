package com.hospital.apihospital.services.UsersServices.ListUsersServices;

import com.hospital.apihospital.Model.DTO.UsersDTO;
import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.Model.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListUserServices {
    @Autowired
    private UsersRepository usersRepository;

    /**
     * Obtém a lista de usuários.
     *
     * @return Uma lista de objetos UsersDTO contendo informações dos usuários.
     */
    public List<UsersDTO> listUsers() {
        try {
            List<CadastrarUsers> users = usersRepository.findAll();
            System.out.println("Tamanho da lista de usuários: " + users.size());

            return users.stream()
                    .map(user -> UsersDTO.fromEntity(user))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar usuários. ", e);
        }
    }
    public CadastrarUsers searchUsers(Long userID){
        try{
            return usersRepository.findById(userID)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID : " + String.valueOf(userID)));
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar o usuários com o ID: " + e.getMessage());
        }
    }
}
