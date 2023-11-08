package com.hospital.apihospital.services;

import com.hospital.apihospital.Model.Entity.CadastrarPaciente;
import com.hospital.apihospital.Model.Repository.PacienteRepository;
import com.hospital.apihospital.exception.PacienteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PacienteServices {
    @Autowired
    private PacienteRepository pacienteRepository;

    public CadastrarPaciente obterPacienteID(Long id){
        Optional<CadastrarPaciente> pacienteOptional = pacienteRepository.findById(id);

        if (pacienteOptional.isPresent()){
            return pacienteOptional.get();
        } else {
            throw new PacienteNotFoundException("Paciente com ID " + id + " não encontrado");
        }
    }
}
