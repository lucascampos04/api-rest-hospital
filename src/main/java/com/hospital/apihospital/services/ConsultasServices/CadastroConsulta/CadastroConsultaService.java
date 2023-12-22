package com.hospital.apihospital.services.ConsultasServices.CadastroConsulta;

import com.hospital.apihospital.Model.DTO.MarcaConsultaDTO;
import com.hospital.apihospital.Model.DTO.UsersDTO;
import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.Model.Entity.MarcaConsultaEntity;
import com.hospital.apihospital.Model.Enum.RoleEnum;
import com.hospital.apihospital.Model.Repository.MarcaConsultaRepository;
import com.hospital.apihospital.Model.Repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CadastroConsultaService {

    @Autowired
    private MarcaConsultaRepository marcaConsultaRepository;

    @Autowired
    private UsersRepository usersRepository;
    @Transactional
    public ResponseEntity<String> cadastrarConsulta(@Valid MarcaConsultaDTO marcaConsultaDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de validação");
        }

        try {
            MarcaConsultaEntity marcaConsultaEntity = new MarcaConsultaEntity();
            marcaConsultaEntity.setTipoConsulta(marcaConsultaDTO.getTipoConsulta());
            marcaConsultaEntity.setData(marcaConsultaDTO.getData());
            marcaConsultaEntity.setValor(marcaConsultaDTO.getValor());

            if (marcaConsultaDTO.getCadastrarUsersId() != null) {
                CadastrarUsers usuario = usersRepository.findById(marcaConsultaDTO.getCadastrarUsersId()).orElse(null);
                if (usuario != null) {
                    marcaConsultaEntity.setCadastrarUsers(usuario);

                    if (RoleEnum.FUNCIONARIO.equals(usuario.getRole())) {
                        double valorConsulta = marcaConsultaDTO.getValor();
                        usuario.setSalario(usuario.getSalario() - valorConsulta);
                        usersRepository.save(usuario);
                    }
                }
            }

            marcaConsultaRepository.save(marcaConsultaEntity);

            return ResponseEntity.status(HttpStatus.CREATED).body("Consulta cadastrada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar consulta");
        }
    }

}
