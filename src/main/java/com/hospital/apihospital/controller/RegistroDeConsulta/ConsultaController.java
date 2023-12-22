package com.hospital.apihospital.controller.RegistroDeConsulta;

import com.hospital.apihospital.Model.DTO.MarcaConsultaDTO;
import com.hospital.apihospital.Model.DTO.UsersDTO;
import com.hospital.apihospital.services.ConsultasServices.CadastroConsulta.CadastroConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/consultas")
public class ConsultaController {
    @Autowired
    private CadastroConsultaService cadastroConsultaService;
    @GetMapping("")
    private ResponseEntity<List<MarcaConsultaDTO>> listaUsuarios(){
        List<MarcaConsultaDTO> usuarios = cadastroConsultaService.listConsultas();
        return ResponseEntity.ok().body(usuarios);
    }
    @PostMapping("/add/consulta")
    public ResponseEntity<String> cadastrar(@Valid @RequestBody MarcaConsultaDTO marcaConsultaDTO, BindingResult result) {
        ResponseEntity<String> response = cadastroConsultaService.cadastrarConsulta(marcaConsultaDTO, result);
        if (response.getStatusCode() == HttpStatus.OK){
            return ResponseEntity.ok().body(response.getBody());

        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }
}
