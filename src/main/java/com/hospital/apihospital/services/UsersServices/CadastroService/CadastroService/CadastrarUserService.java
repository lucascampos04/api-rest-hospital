package com.hospital.apihospital.services.UsersServices.CadastroService.CadastroService;

import com.hospital.apihospital.Model.DTO.UsersDTO;
import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.Model.Enum.CargoEnum;
import com.hospital.apihospital.Model.Enum.RoleEnum;
import com.hospital.apihospital.Model.Repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

/**
 * Serviço para cadastrar usuários e funcionários.
 */
@Service
public class CadastrarUserService {

    private final UsersRepository usersRepository;

    public CadastrarUserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    /**
     * Realiza o cadastro de um paciente ou funcionário.
     *
     * @param usersDTO Objeto contendo os dados do usuário ou funcionário.
     * @param result   Objeto que contém os resultados da validação do Spring.
     * @return ResponseEntity contendo uma mensagem de sucesso ou erro.
     */
    @Transactional
    public ResponseEntity<String> cadastrarPaciente(@Valid @RequestBody UsersDTO usersDTO, BindingResult result) {
        try {
            CadastrarUsers userEntity = new CadastrarUsers();

            ResponseEntity<String> validationResult = validateDataOfUserORemployees(usersDTO);
            ResponseEntity<String> validationErros = handlingErros(result);
            ResponseEntity<String> validationDateNascimento = validationDataNascimentoOfEmployees(usersDTO);
            ResponseEntity<String> validationData = validateDataEmployees(usersDTO);

            if (validationErros != null) {
                return validationErros;
            }

            if (validationData != null){
                return validationData;
            }

            if (validationDateNascimento != null){
                return validationDateNascimento;
            }

            if (validationResult != null) {
                return validationResult;
            }

            CadastrarUsers user = getCadastrarUsers(usersDTO);


            CadastrarUsers userSave = usersRepository.save(user);

            return ResponseEntity.ok().body("Paciente cadastrado com sucesso\nID : " + userSave.getId() + " ROLE : " + userSave.getRole());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Obtém um objeto CadastrarUsers a partir dos dados do DTO.
     *
     * @param usersDTO Objeto contendo os dados do usuário ou funcionário.
     * @return Objeto CadastrarUsers.
     * @throws ParseException Exceção lançada em caso de erro na formatação da data.
     */
    private CadastrarUsers getCadastrarUsers(UsersDTO usersDTO) throws ParseException {
        CadastrarUsers user = new CadastrarUsers();
        user.setNome(usersDTO.getNome());
        user.setCpf(usersDTO.getCpf());
        user.setRg(usersDTO.getRg());
        user.setGenero(usersDTO.getGenero());
        user.setCargo(usersDTO.getCargo());
        user.setPlano_paciente(usersDTO.getPlanoPaciente());
        user.setDataNascimento(usersDTO.getDataNascimento());
        user.setEmail(usersDTO.getEmail());
        user.setRole(usersDTO.getRole());
        user.setSalario(usersDTO.getSalario());
        user.setTelefone(usersDTO.getTelefone());
        user.setDataRegistro(getCurrentDateInBrasilia());

        if (usersDTO.getCargo() != null){
            switch (usersDTO.getCargo()){
                case MEDICO:
                case GERENTE:
                case ADMINISTRATIVO:
                case ENFERMEIRO:
                case FAXINEIRO:
                case OPERARIO:
                    user.setRole(RoleEnum.FUNCIONARIO);
                    break;
                default:
                    user.setRole(RoleEnum.PACIENTE);
            }
        } else {
            user.setRole(RoleEnum.PACIENTE);
        }
        return user;
    }


    /**
     * Valida dados específicos de funcionários, como cargo e salário.
     *
     * @param usersDTO Objeto contendo os dados do funcionário.
     * @return ResponseEntity contendo uma mensagem de sucesso ou erro.
     */
    private ResponseEntity<String> validateDataEmployees(UsersDTO usersDTO) {
        CargoEnum cargo = usersDTO.getCargo();
        Double salario = usersDTO.getSalario();

        // validando cargo e salario.
        if (cargo != null) {
            switch (cargo) {
                case GERENTE:
                    if (salario < 5000.0) {
                        return ResponseEntity.badRequest().body("Erro: Gerentes devem ter um salário mínimo de 5000.0.");
                    }
                    break;
                case MEDICO:
                    if (salario < 10000.0) {
                        return ResponseEntity.badRequest().body("Erro: Médicos devem ter um salário mínimo de 10000.0.");
                    }
                    break;
                case ADMINISTRATIVO:
                    if (salario < 3000.0) {
                        return ResponseEntity.badRequest().body("Erro: Administradores e funcionários relacionados ao RH devem ter um salário mínimo de 3000.0.");
                    }
                    break;
                case ENFERMEIRO:
                    if (salario < 2500.0) {
                        return ResponseEntity.badRequest().body("Erro: Enfermeiros devem ter um salário mínimo de 2500.0.");
                    }
                    break;
                case FAXINEIRO:
                    if (salario < 2000.0) {
                        return ResponseEntity.badRequest().body("Erro: Faxineiros devem ter um salário mínimo de 2000.0.");
                    }
                    break;
                case OPERARIO:
                    if (salario < 2000.0) {
                        return ResponseEntity.badRequest().body("Erro: Operários devem ter um salário mínimo de 2000.0.");
                    }
                    break;
                default:
                    return ResponseEntity.notFound().build();
            }
        }
        return null;
    }

    /**
     * Valida dados comuns a usuários e funcionários.
     *
     * @param usersDTO Objeto contendo os dados do usuário ou funcionário.
     * @return ResponseEntity contendo uma mensagem de sucesso ou erro.
     */
    private ResponseEntity<String> validateDataOfUserORemployees(UsersDTO usersDTO) {
        if (usersRepository.existsByRg(usersDTO.getRg())) {
            return ResponseEntity.badRequest().body("Rg já registrado. Tente Novamente");
        }

        if (usersDTO.getRg().length() > 15) {
            return ResponseEntity.badRequest().body("Erro: O RG não pode ter mais de 15 caracteres.");
        }

        if (usersRepository.existsByCpf(usersDTO.getCpf())) {
            return ResponseEntity.badRequest().body("CPF já registrado. Tente Novamente");
        }

        if (usersDTO.getCpf().length() > 20) {
            return ResponseEntity.badRequest().body("Erro: O CPF não pode ter mais de 20 caracteres.");
        }

        if (usersRepository.existsByEmail(usersDTO.getEmail())) {
            return ResponseEntity.badRequest().body("EMAIL já registrado. Tente Novamente");
        }

        if (usersDTO.getNome().matches(".*\\d+.*")){
            return ResponseEntity.badRequest().body("Erro: O nome não pode conter números");
        }


        if (!"Masculino".equalsIgnoreCase(usersDTO.getGenero()) && !"Feminino".equalsIgnoreCase(usersDTO.getGenero())) {
            return ResponseEntity.badRequest().body("Erro: Gênero inválido. Escolha 'Masculino' ou 'Feminino'.");
        }
        return null;
    }

    /**
     * Valida a data de nascimento de funcionários.
     *
     * @param usersDTO Objeto contendo os dados do funcionário.
     * @return ResponseEntity contendo uma mensagem de sucesso ou erro.
     */
    private ResponseEntity<String> validationDataNascimentoOfEmployees(UsersDTO usersDTO) {
        try {
            Date dataNascimento = usersDTO.getDataNascimento();

            Calendar calendarLimit = Calendar.getInstance();
            calendarLimit.set(2007, Calendar.JANUARY, 1);

            Calendar calendarNascimento = Calendar.getInstance();
            calendarNascimento.setTime(dataNascimento);

            if (calendarNascimento.after(calendarLimit)) {
                return ResponseEntity.badRequest().body("Erro: Não é possível inserir um funcionário menor de idade");
            }
            return null;
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /**
     * Valida a data de registro de users.
     *
     * @return dataRegistro contendo a data de inserção formatada.
     * @throws ParseException Exceção lançada em caso de erro na formatação da data.
     */
    private @NotBlank(message = "A data de registro é obrigatória") Date getCurrentDateInBrasilia() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        String dataRegistroFormatada = sdf.format(new Date());
        Date dataRegistro = sdf.parse(dataRegistroFormatada);
        return dataRegistro;
    }

    /**
     * Manipula os erros de validação do Spring e retorna uma ResponseEntity com mensagens de erro, se houver.
     *
     * @param result Resultado da validação do Spring.
     * @return ResponseEntity contendo mensagens de erro, se houver.
     */
    private ResponseEntity<String> handlingErros(BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMensagem = new StringBuilder("Erro de validação: ");
            result.getAllErrors().forEach(error -> {
                errorMensagem.append(error.getDefaultMessage()).append("; ");
            });
            return ResponseEntity.badRequest().body(errorMensagem.toString());
        }
        return null;
    }




}
