package com.hospital.apihospital.controller.ConsultasController;

import com.hospital.apihospital.Model.DTO.ConsultaDTO;
import com.hospital.apihospital.services.ConsultasServices.CadastrarConsultaService;
import com.hospital.apihospital.services.ConsultasServices.ListConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/consultas")
public class ConsultaController {

    private final ListConsulta listarConsultasService;
    private final CadastrarConsultaService cadastrarConsultaService;
    @Autowired
    public ConsultaController(ListConsulta listarConsultasService, CadastrarConsultaService cadastrarConsultaService) {
        this.listarConsultasService = listarConsultasService;
        this.cadastrarConsultaService = cadastrarConsultaService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ConsultaDTO>> listConsultas(){
        List<ConsultaDTO> consultaDtos = listarConsultasService.getAllConsultas();
        return ResponseEntity.ok(consultaDtos);
    }


    @PostMapping("/add/consulta")
    public ConsultaDTO cadastrarConsulta(@RequestBody ConsultaDTO consultaDto){
        return cadastrarConsultaService.cadastrarConsulta(consultaDto);
    }


}
