package com.hospital.apihospital.controller;

import com.hospital.apihospital.Model.DTO.ConsultaDTO;
import com.hospital.apihospital.Model.Entity.CadastrarPaciente;
import com.hospital.apihospital.Model.Repository.MarcaConsultaRepository;
import com.hospital.apihospital.Model.Entity.MarcaConsulta;
import com.hospital.apihospital.services.*;
import com.hospital.apihospital.services.DescontoEmPlanos.PlanoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/v1/consultas")
public class MarcaConsultaController {
    @Autowired
    private MarcaConsultaRepository marcaConsultaRepository;
    @Autowired
    private PlanoService planoService;
    @Autowired
    private PacienteServices pacienteServices;

    /**
     * Obtém uma lista de todas as consultas.
     *
     * @return Uma lista de ConsultaDTO contendo informações das consultas registradas.
     */
    @GetMapping
    public List<ConsultaDTO> listarConsultas() {
        List<MarcaConsulta> consultas = marcaConsultaRepository.findAll();
        List<ConsultaDTO> consultaDTOS = consultas.stream()
                .map(ConsultaDTO::fromEntity)
                .collect(Collectors.toList());
        return consultaDTOS;
    }

    /**
     * Registra uma nova consulta.
     *
     * @param marcaConsulta O objeto MarcaConsulta representando a consulta a ser registrada.
     * @return Uma resposta com uma mensagem de sucesso ou erro.
     */
    @PostMapping("/registrar-consulta")
    public ResponseEntity<String> registrarConsulta(@RequestBody MarcaConsulta marcaConsulta) {
        Long idPaciente = marcaConsulta.getPaciente().getId();
        CadastrarPaciente paciente = pacienteServices.obterPacienteID(idPaciente);

        if (paciente != null) {
            String tipoPlano = paciente.getPlano_paciente();
            double valorConsulta = marcaConsulta.getValorConsulta();

            valorConsulta = planoService.calcularValorComDesconto(paciente, valorConsulta);
            marcaConsulta.setValorConsulta(valorConsulta);

            try {
                MarcaConsulta consultaSalva = salvarConsultaNoBanco(marcaConsulta);

                if (consultaSalva != null) {
                    return ResponseEntity.ok("Consulta registrada com sucesso.\nID : " + paciente.getId() + " Plano do paciente : " + tipoPlano);
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar a consulta.");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar a consulta: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Paciente não encontrado com o ID fornecido.");
        }
    }

    private MarcaConsulta salvarConsultaNoBanco(MarcaConsulta marcaConsulta) {
        try {
            return marcaConsultaRepository.save(marcaConsulta);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
