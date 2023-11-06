package com.hospital.apihospital.controller;

import com.hospital.apihospital.Model.Entity.CadastrarPaciente;
import com.hospital.apihospital.Model.Repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pr;

    @GetMapping
    public List<CadastrarPaciente> listarPacientes(){
        List<CadastrarPaciente> pacientes = pr.findAll();
        return pacientes;
    }

    @PostMapping("/addpaciente")
    public ResponseEntity<String> cadastrar(@Valid @RequestBody CadastrarPaciente cadastrar, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body("Erro de validação: " + result.getAllErrors());
        }
        if (cadastrar.getNome() == null || cadastrar.getNome().trim().isEmpty() ||
                cadastrar.getCpf() == null || cadastrar.getCpf().trim().isEmpty() ||
                cadastrar.getRg() == null || cadastrar.getRg().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Campos obrigatórios não podem estar vazios (Nome, CPF, RG).");
        }


        if (pr.existsByRg(cadastrar.getRg())){
            return ResponseEntity.badRequest().body("RG já existe no banco de dados.");
        }

        CadastrarPaciente paciente = pr.save(cadastrar);
        return ResponseEntity.ok("redirect:api/pacientes"+"Paciente inserido com sucesso! ID: " + cadastrar.getId());
    }

    @DeleteMapping("deletepaciente/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id){
       if (pr.existsById(id)){
           pr.deleteById(id);
           return ResponseEntity.ok().build();
       } else {
           return ResponseEntity.notFound().build();
       }
    }


}
