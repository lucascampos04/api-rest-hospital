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
    public List<UsersDTO> listUsers(){
        try{
            List<CadastrarUsers> users = usersRepository.findAll();
            return users.stream()
                    .map(UsersDTO::fromEntity)
                    .collect(Collectors.toList());
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar usuários. ", e);
        }
    }
}
