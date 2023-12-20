package com.hospital.apihospital.controller.RegistrosPacientes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.apihospital.Model.DTO.UsersDTO;
import com.hospital.apihospital.Model.Repository.UsersRepository;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/v1/pacientes")
public class UsersController {

    @Autowired
    private UsersRepository pr;

    private boolean isNumeric(String str) {
        if (str == null) {
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
    public List<UsersDTO> listarPacientes() {
        List<CadastrarUsers> pacientes = pr.findAll();
        List<UsersDTO> usersDTOS = pacientes.stream()
                .map(UsersDTO::fromEntity)
                .collect(Collectors.toList());
        return usersDTOS;
    }

    /**
     * Obtém informações de um paciente pelo ID fornecido no corpo da solicitação.
     *
     * @param pesquisarPorIdRequest O objeto PesquisaPorIdRequest contendo o ID do
     *                              paciente a ser pesquisado.
     * @return Os dados do paciente se encontrado, ou um erro se não encontrado.
     */
    @PostMapping("/buscarpaciente")
    public ResponseEntity<?> getPacienteByIdPost(@RequestBody UsersDTO pesquisarPorIdRequest) {
        Long id = pesquisarPorIdRequest.getId();
        Optional<CadastrarUsers> pacienteOptional = pr.findById(id);
        if (pacienteOptional.isPresent()) {
            CadastrarUsers CadastrarUsers = pacienteOptional.get();
            UsersDTO usersDTO = UsersDTO.fromEntity(CadastrarUsers);
            return ResponseEntity.ok(usersDTO);
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
    public ResponseEntity<?> getPacientesByIdGet(@PathVariable Long id) {
        Optional<CadastrarUsers> pacienteOptional = pr.findById(id);

        if (pacienteOptional.isPresent()) {
            CadastrarUsers CadastrarUsers = pacienteOptional.get();
            UsersDTO usersDTO = UsersDTO.fromEntity(CadastrarUsers);
            return ResponseEntity.ok(usersDTO);
        } else {
            System.out.println("Nada encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado com o ID fornecido.");
        }
    }

    /**
     * Cadastra um novo paciente.
     *
     * @param usersDTO O objeto PacienteDTO contendo informações do paciente a
     *                    ser cadastrado.
     * @param result      O objeto BindingResult para validação.
     * @return Uma resposta com a mensagem de sucesso ou erro.
     */
    @PostMapping("/addpaciente")
    public ResponseEntity<String> cadastrar(@Valid @RequestBody UsersDTO usersDTO, BindingResult result,
                                            HttpServletRequest httpServletRequest) {
        if (result.hasErrors()) {
            // Lida com erros de validação
            StringBuilder errorMensagem = new StringBuilder("Erro de validação: ");
            result.getAllErrors().forEach(error -> {
                errorMensagem.append(error.getDefaultMessage()).append("; ");
            });
            return ResponseEntity.badRequest().body(errorMensagem.toString());
        }

        // Validação de RG e CPF
        boolean rgExiste = pr.existsByRg(usersDTO.getRg());
        boolean cpfExiste = pr.existsByCpf(usersDTO.getCpf());

        if (rgExiste && cpfExiste) {
            // Se ambos RG e CPF existirem, retorne um erro
            return ResponseEntity.badRequest().body("Erro: RG e CPF não podem ser iguais.");
        }

        if (rgExiste || cpfExiste) {
            // Se RG ou CPF existirem, retorne um erro
            return ResponseEntity.badRequest().body("Erro: RG e CPF já estão registrados no banco de dados.");
        }

        String genero = usersDTO.getGenero();

        if (!"Masculino".equalsIgnoreCase(genero) && !"Feminino".equalsIgnoreCase(genero)) {
            return ResponseEntity.badRequest().body("Erro: Gênero inválido. Escolha 'Masculino' ou 'Feminino'.");
        }

        try {
            // Cria um novo paciente e o salva no banco de dados
            CadastrarUsers paciente = new CadastrarUsers();
            paciente.setNome(usersDTO.getNome());
            paciente.setCpf(usersDTO.getCpf());
            paciente.setRg(usersDTO.getRg());
            paciente.setGenero(usersDTO.getGenero());
            paciente.setDataNascimento(usersDTO.getDataNascimento());
            paciente.setPlano_paciente(usersDTO.getPlano_paciente());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
            String dataRegistroFormatada = sdf.format(new Date());
            Date dataRegistro = sdf.parse(dataRegistroFormatada);

            paciente.setDataRegistro(dataRegistro);

            CadastrarUsers pacienteSave = pr.save(paciente);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Acess-Control-Allow-Origin", "http://localhost:5173");

            return ResponseEntity
                    .ok("Paciente cadastrado com sucesso! ID: " + pacienteSave.getId() + " Data do registro : "
                            + pacienteSave.getDataRegistro() + " PLano : " + paciente.getPlano_paciente());
        } catch (Exception e) {
            // Lida com erros inesperados
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar o paciente: " + e.getMessage());
        }
    }

    @PutMapping("/atualizar-paciente/{id}")
    public ResponseEntity<String> atualizarpaciente(@PathVariable Long id, @Valid @RequestBody UsersDTO usersDTO,
            BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder erroMsg = new StringBuilder("Erro de validação: ");
            result.getAllErrors().forEach(error -> {
                erroMsg.append(error.getDefaultMessage()).append("; ");
            });
            return ResponseEntity.badRequest().body(erroMsg.toString());
        }

        Optional<CadastrarUsers> pacienteOptional = pr.findById(id);

        if (pacienteOptional.isPresent()) {
            CadastrarUsers pacienteExistente = pacienteOptional.get();

            pacienteExistente.setNome(usersDTO.getNome());
            pacienteExistente.setCpf(usersDTO.getCpf());
            pacienteExistente.setRg(usersDTO.getRg());
            pacienteExistente.setGenero(usersDTO.getGenero());
            pacienteExistente.setDataNascimento(usersDTO.getDataNascimento());
            pacienteExistente.setPlano_paciente(usersDTO.getPlano_paciente());

            CadastrarUsers pacienteAtualizado = pr.save(pacienteExistente);

            return ResponseEntity.ok("Paciente atualizado com sucesso ID : " + pacienteAtualizado.getId());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado com o ID fornecido");
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
        try {
            pr.deleteById(id);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Controlador para excluir pacientes com campos nulos.
     */
    @DeleteMapping("/freekill")
    public ResponseEntity<String> deletePacientesComCamposNulos() {
        try {
            List<CadastrarUsers> pacientes = pr.findAll();
            for (CadastrarUsers paciente : pacientes) {
                pr.deleteAll();
            }
            pr.deleteAll(pacientes);

            return ResponseEntity.ok("Todos os pacientes e registros relacionados foram excluídos com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao excluir todos os pacientes: " + e.getMessage());
        }
    }
}