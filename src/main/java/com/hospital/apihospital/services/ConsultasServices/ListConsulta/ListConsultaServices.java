package com.hospital.apihospital.services.ConsultasServices.ListConsulta;

import com.hospital.apihospital.Model.DTO.MarcaConsultaDTO;
import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.Model.Entity.MarcaConsultaEntity;
import com.hospital.apihospital.Model.Enum.RoleEnum;
import com.hospital.apihospital.Model.Repository.MarcaConsultaRepository;
import com.hospital.apihospital.Model.Repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListConsultaServices {
    private final MarcaConsultaRepository marcaConsultaRepository;

    private final UsersRepository usersRepository;

    public ListConsultaServices(MarcaConsultaRepository marcaConsultaRepository, UsersRepository usersRepository) {
        this.marcaConsultaRepository = marcaConsultaRepository;
        this.usersRepository = usersRepository;
    }

    @Transactional
    public List<MarcaConsultaDTO> listConsultas() {
        try {
            List<MarcaConsultaEntity> consultas = marcaConsultaRepository.findAll();
            System.out.println("Tamanho da lista de consultas: " + consultas.size());

            return consultas.stream()
                    .map(consulta -> {
                        CadastrarUsers usuario = consulta.getCadastrarUsers();
                        return new MarcaConsultaDTO(
                                consulta.getId(),
                                consulta.getTipoConsulta(),
                                consulta.getData(),
                                consulta.getValor(),
                                usuario != null ? usuario.getId() : null,
                                usuario != null ? usuario.getNome() : null,
                                usuario != null ? usuario.getEmail() : null,
                                usuario != null ? usuario.getTelefone() : null,
                                usuario != null ? usuario.getRole().toString() : null
                        );
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar consultas. ", e);
        }
    }

}
