package com.hospital.apihospital.controller;

import com.hospital.apihospital.Model.DTO.PacienteDTO;
import com.hospital.apihospital.Model.Entity.CadastrarPaciente;
import com.hospital.apihospital.Model.Repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/v1/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pr;

    private boolean isNumeric(String str){
        if (str == null){
            return false;
        }
        return str.matches("\\d");
    }


    /**
     * Obtém a lista de pacientes.
     *
     * @return Uma lista de objetos PacienteDTO contendo informações dos pacientes.
     */
    @GetMapping
    public List<PacienteDTO> listarPacientes() {
        List<CadastrarPaciente> pacientes = pr.findAll();
        List<PacienteDTO> pacienteDTOs = pacientes.stream()
                .map(PacienteDTO::fromEntity)
                .collect(Collectors.toList());
        return pacienteDTOs;
    }

    /**
     * Obtém informações de um paciente pelo ID fornecido no corpo da solicitação.
     *
     * @param pesquisarPorIdRequest O objeto PesquisaPorIdRequest contendo o ID do paciente a ser pesquisado.
     * @return Os dados do paciente se encontrado, ou um erro se não encontrado.
     */
    @PostMapping("/buscarpaciente")
    public ResponseEntity<?> getPacienteByIdPost(@RequestBody PacienteDTO pesquisarPorIdRequest ){
        Long id = pesquisarPorIdRequest.getId();
        Optional<CadastrarPaciente> pacienteOptional = pr.findById(id);
        if (pacienteOptional.isPresent()){
            CadastrarPaciente cadastrarPaciente = pacienteOptional.get();
            PacienteDTO pacienteDTO = PacienteDTO.fromEntity(cadastrarPaciente);
            return ResponseEntity.ok(pacienteDTO);
        } else {
            System.out.println("Nada encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado com o ID fornecido.");
        }

    }

    /**
     * Obtém informações de um paciente pelo ID fornecido na URL.
     *
     * @param id O ID do paciente a ser pesquisado.
     * @return Os dados do paciente se encontrado, ou um erro se não encontrado.
     */
    @GetMapping("/buscarpaciente/{id}")
    public ResponseEntity<?> getPacientesByIdGet(@PathVariable Long id){
        Optional<CadastrarPaciente> pacienteOptional = pr.findById(id);

        if (pacienteOptional.isPresent()){
            CadastrarPaciente cadastrarPaciente = pacienteOptional.get();
            PacienteDTO pacienteDTO = PacienteDTO.fromEntity(cadastrarPaciente);
            return ResponseEntity.ok(pacienteDTO);
        } else{
            System.out.println("Nada encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado com o ID fornecido.");
        }
    }

    /**
     * Cadastra um novo paciente.
     *
     * @param pacienteDTO O objeto PacienteDTO contendo informações do paciente a ser cadastrado.
     * @param result      O objeto BindingResult para validação.
     * @return Uma resposta com a mensagem de sucesso ou erro.
     */
    @PostMapping("/addpaciente")
    public ResponseEntity<String> cadastrar(@Valid @RequestBody PacienteDTO pacienteDTO, BindingResult result) {
        if (result.hasErrors()) {
            // Lida com erros de validação
            StringBuilder errorMensagem = new StringBuilder("Erro de validação: ");
            result.getAllErrors().forEach(error -> {
                errorMensagem.append(error.getDefaultMessage()).append("; ");
            });
            return ResponseEntity.badRequest().body(errorMensagem.toString());
        }

        // Validação de RG e CPF
        boolean rgExiste = pr.existsByRg(pacienteDTO.rg());
        boolean cpfExiste = pr.existsByCpf(pacienteDTO.cpf());

        if (rgExiste && cpfExiste) {
            // Se ambos RG e CPF existirem, retorne um erro
            return ResponseEntity.badRequest().body("Erro: RG e CPF não podem ser iguais.");
        }

        if (rgExiste || cpfExiste) {
            // Se RG ou CPF existirem, retorne um erro
            return ResponseEntity.badRequest().body("Erro: RG e CPF já estão registrados no banco de dados.");
        }

        try {
            // Cria um novo paciente e o salva no banco de dados
            CadastrarPaciente paciente = new CadastrarPaciente();
            paciente.setNome(pacienteDTO.nome());
            paciente.setCpf(pacienteDTO.cpf());
            paciente.setRg(pacienteDTO.rg());

            CadastrarPaciente pacienteSave = pr.save(paciente);

            return ResponseEntity.ok("Paciente cadastrado com sucesso! ID: " + pacienteSave.getId());
        } catch (Exception e) {
            // Lida com erros inesperados
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar o paciente: " + e.getMessage());
        }
    }

    /**
     * Deleta um paciente pelo ID.
     *
     * @param id O ID do paciente a ser excluído.
     * @return Uma resposta com o status da operação (sucesso ou erro).
     */
    @DeleteMapping("deletepaciente/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        if (pr.existsById(id)) {
            // Deleta o paciente se existir
            pr.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            // Retorna um erro se o paciente não existe
            return ResponseEntity.notFound().build();
        }
    }
}
