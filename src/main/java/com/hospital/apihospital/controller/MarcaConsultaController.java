package com.hospital.apihospital.controller;

import com.hospital.apihospital.Model.DTO.ConsultaDTO;
import com.hospital.apihospital.Model.Entity.CadastrarPaciente;
import com.hospital.apihospital.Model.Repository.MarcaConsultaRepository;
import com.hospital.apihospital.Model.Entity.MarcaConsulta;
import com.hospital.apihospital.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/consultas")
public class MarcaConsultaController {
    @Autowired
    private MarcaConsultaRepository marcaConsultaRepository;
    @Autowired
    private PacienteServices pacienteServices;
    @Autowired
    private PlanoBronzeServices planoBronzeServices;
    @Autowired
    private PlanoPrataServices planoPrataServices;
    @Autowired
    private PlanoOuroServices planoOuroServices;
    @Autowired
    private PlanoVipServices planoVipServices;

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

            valorConsulta = aplicarDescontoDoPlano(tipoPlano, paciente, valorConsulta);
            marcaConsulta.setValorConsulta(valorConsulta);

            try {
                MarcaConsulta consultaSalva = salvarConsultaNoBanco(marcaConsulta);

                if (consultaSalva != null) {
                    return ResponseEntity.ok("Consulta registrada com sucesso");
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

    private double aplicarDescontoDoPlano(String tipoPlano, CadastrarPaciente paciente, double valorConsulta) {
        if ("bronze".equalsIgnoreCase(tipoPlano)) {
            valorConsulta = planoBronzeServices.calcularValorComDesconto(paciente, valorConsulta);
        } else if ("prata".equalsIgnoreCase(tipoPlano)) {
            valorConsulta = planoPrataServices.calcularValorComDesconto(paciente, valorConsulta);
        } else if ("ouro".equalsIgnoreCase(tipoPlano)) {
            valorConsulta = planoOuroServices.calcularValorComDesconto(paciente, valorConsulta);
        } else if ("vip".equalsIgnoreCase(tipoPlano)) {
            valorConsulta = planoVipServices.calcularValorComDesconto(paciente, valorConsulta);
        }
        return valorConsulta;
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
