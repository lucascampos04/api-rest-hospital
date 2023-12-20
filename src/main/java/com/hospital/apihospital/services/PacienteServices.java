package com.hospital.apihospital.services;

import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.Model.Repository.UsersRepository;
import com.hospital.apihospital.exception.PacienteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PacienteServices {
    @Autowired
    private UsersRepository pacienteRepository;

    public CadastrarUsers obterPacienteID(Long id){
        Optional<CadastrarUsers> pacienteOptional = pacienteRepository.findById(id);

        if (pacienteOptional.isPresent()){
            return pacienteOptional.get();
        } else {
            throw new PacienteNotFoundException("Paciente com ID " + id + " não encontrado");
        }
    }
}
