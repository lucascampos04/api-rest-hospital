package com.hospital.apihospital.controller.rh.RegisterFuncionarios;

import com.hospital.apihospital.Model.DTO.CadastrarFuncionarioDto;
import com.hospital.apihospital.Model.DTO.PacienteDTO;
import com.hospital.apihospital.Model.Entity.CadastrarFuncionario;
import com.hospital.apihospital.Model.Entity.CadastrarPaciente;
import com.hospital.apihospital.Model.Repository.FuncionarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/rh/funcionarios")
public class FuncionarioController {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping
    public List<CadastrarFuncionarioDto> listarFuncionario() {
        List<CadastrarFuncionario> funcionario = funcionarioRepository.findAll();
        List<CadastrarFuncionarioDto> funcionarioDtos = funcionario.stream()
                .map(CadastrarFuncionarioDto::fromEntity)
                .collect(Collectors.toList());
        return funcionarioDtos;
    }

    @PostMapping("/add/funcionarios")
    public ResponseEntity<String> cadastrar(@Valid @RequestBody CadastrarFuncionarioDto funcionarioDTO, BindingResult result,
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
        boolean rgExiste = funcionarioRepository.existsByRg(funcionarioDTO.getRg());
        boolean cpfExiste = funcionarioRepository.existsByCpf(funcionarioDTO.getCpf());

        if (rgExiste && cpfExiste) {
            // Se ambos RG e CPF existirem, retorne um erro
            return ResponseEntity.badRequest().body("Erro: RG e CPF não podem ser iguais.");
        }

        if (rgExiste || cpfExiste) {
            // Se RG ou CPF existirem, retorne um erro
            return ResponseEntity.badRequest().body("Erro: RG e CPF já estão registrados no banco de dados.");
        }

        String genero = funcionarioDTO.getGenero();

        if (!"Masculino".equalsIgnoreCase(genero) && !"Feminino".equalsIgnoreCase(genero)) {
            return ResponseEntity.badRequest().body("Erro: Gênero inválido. Escolha 'Masculino' ou 'Feminino'.");
        }

        try {
            // Cria um novo paciente e o salva no banco de dados
            CadastrarFuncionario funcionario = new CadastrarFuncionario();
            funcionario.setNome(funcionarioDTO.getNome());
            funcionario.setCpf(funcionarioDTO.getCpf());
            funcionario.setRg(funcionarioDTO.getRg());
            funcionario.setGenero(funcionarioDTO.getGenero());
            funcionario.setDataNascimento(funcionarioDTO.getDataNascimento());
            funcionario.setCargo(funcionarioDTO.getCargo());


            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
            String dataRegistroFormatada = sdf.format(new Date());
            Date dataRegistro = sdf.parse(dataRegistroFormatada);

            funcionario.setDataRegistro(dataRegistro);

            CadastrarFuncionario pacienteSave = funcionarioRepository.save(funcionario);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Acess-Control-Allow-Origin", "http://localhost:5173");

            return ResponseEntity
                    .ok("Paciente cadastrado com sucesso! ID: " + pacienteSave.getId() + " Data do registro : "
                            + pacienteSave.getDataRegistro() + " PLano : " + funcionario.getCargo());
        } catch (Exception e) {
            // Lida com erros inesperados
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar o paciente: " + e.getMessage());
        }
    }
}
