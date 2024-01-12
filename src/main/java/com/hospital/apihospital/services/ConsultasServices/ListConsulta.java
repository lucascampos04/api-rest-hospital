package com.hospital.apihospital.services.ConsultasServices;

import com.hospital.apihospital.Model.DTO.ConsultaDTO;
import com.hospital.apihospital.Model.Entity.ConsultaEntity;
import com.hospital.apihospital.Model.Repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListConsulta {
    private final ConsultaRepository consultaRepository;
    @Autowired
    public ListConsulta(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public List<ConsultaDTO> getAllConsultas() {
        List<ConsultaEntity> consultas = consultaRepository.findAllWithUsers();
        return consultas.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    private ConsultaDTO convertEntityToDTO(ConsultaEntity entity) {
        ConsultaDTO dto = new ConsultaDTO();
        dto.setId(entity.getId());
        dto.setTipoConsulta(entity.getTipoConsulta());
        dto.setData(entity.getData());
        dto.setValor(entity.getValor());
        dto.setUserId(entity.getUsers().getId());
        dto.setUserName(entity.getUsers().getNome());
        dto.setEmail(entity.getUsers().getEmail());
        dto.setRole(entity.getUsers().getRole());
        dto.setTelefone(entity.getUsers().getTelefone());
        return dto;
    }
}
