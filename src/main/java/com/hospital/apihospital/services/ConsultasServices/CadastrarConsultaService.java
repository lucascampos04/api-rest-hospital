package com.hospital.apihospital.services.ConsultasServices;

import com.hospital.apihospital.Model.DTO.ConsultaDTO;
import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.Model.Entity.ConsultaEntity;
import com.hospital.apihospital.Model.Repository.ConsultaRepository;
import com.hospital.apihospital.Model.Repository.UsersRepository;
import com.hospital.apihospital.services.SendEmail.NotificationConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastrarConsultaService {
    private final ConsultaRepository consultaRepository;
    private final NotificationConsulta notificationConsulta;
    private final UsersRepository usersRepository;

    @Autowired
    public CadastrarConsultaService(ConsultaRepository consultaRepository, NotificationConsulta notificationConsulta,
                                    UsersRepository usersRepository) {
        this.consultaRepository = consultaRepository;
        this.notificationConsulta = notificationConsulta;
        this.usersRepository = usersRepository;
    }

    public ConsultaDTO cadastrarConsulta(ConsultaDTO consultaDTO) {
        ConsultaEntity consultaEntity = new ConsultaEntity();
        consultaEntity.setTipoConsulta(consultaDTO.getTipoConsulta());
        consultaEntity.setData(consultaDTO.getData());
        consultaEntity.setValor(consultaDTO.getValor());

        CadastrarUsers users = usersRepository.findById(consultaDTO.getUserId()).orElse(null);
        consultaEntity.setUsers(users);

        ConsultaEntity consultaSalva = consultaRepository.save(consultaEntity);

        return convertEntityToDTO(consultaSalva);
    }

    private ConsultaDTO convertEntityToDTO(ConsultaEntity entity) {
        ConsultaDTO dto = new ConsultaDTO();
        dto.setId(entity.getId());
        dto.setTipoConsulta(entity.getTipoConsulta());
        dto.setData(entity.getData());
        dto.setValor(entity.getValor());
        if (entity.getUsers() != null) {
            dto.setUserId(entity.getUsers().getId());
            dto.setEmail(entity.getUsers().getEmail());
            dto.setUserName(entity.getUsers().getNome());
            dto.setRole(entity.getUsers().getRole());
            dto.setTelefone(entity.getUsers().getTelefone());
        }

        System.out.println("Email dto " + entity.getUsers().getEmail());
        if (entity.getUsers().getEmail() != null){
            System.out.println("E-mail antes de enviar: " + entity.getUsers().getEmail());
            notificationConsulta.sendNotificationConsultaMarcada(entity.getUsers().getEmail(),
                    entity.getUsers().getNome(),
                    dto.getTipoConsulta(),
                    dto.getData());
        } else {
            System.out.println("Email nulo");
        }
        return dto;
    }

}
