package com.hospital.apihospital.services.ConsultasServices.CadastroConsulta;

import com.hospital.apihospital.Model.DTO.MarcaConsultaDTO;
import com.hospital.apihospital.Model.Entity.CadastrarUsers;
import com.hospital.apihospital.Model.Entity.MarcaConsultaEntity;
import com.hospital.apihospital.Model.Enum.RoleEnum;
import com.hospital.apihospital.Model.Repository.MarcaConsultaRepository;
import com.hospital.apihospital.Model.Repository.UsersRepository;
import com.hospital.apihospital.services.DescontoEmPlanos.PlanoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

@Service
public class CadastroConsultaService {

    private final MarcaConsultaRepository marcaConsultaRepository;

    private final UsersRepository usersRepository;
    private final PlanoService planoService;
    public CadastroConsultaService(MarcaConsultaRepository marcaConsultaRepository, UsersRepository usersRepository, PlanoService planoService) {
        this.marcaConsultaRepository = marcaConsultaRepository;
        this.usersRepository = usersRepository;
        this.planoService = planoService;
    }

    @Transactional
    public ResponseEntity<String> cadastrarConsulta(@Valid MarcaConsultaDTO marcaConsultaDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de validação");
        }

        try {
            CadastrarUsers usuario = usuarioAssociado(marcaConsultaDTO);

            double valorConsulta = marcaConsultaDTO.getValor();
            double valorComDesconto = planoService.calcularValorComDesconto(usuario, valorConsulta);
            MarcaConsultaEntity marcaConsultaEntity = criarConsultaEntity(marcaConsultaDTO, valorComDesconto);
            marcaConsultaRepository.save(marcaConsultaEntity);
            descontarValorConsulta(usuario, valorConsulta);

            return ResponseEntity.status(HttpStatus.CREATED).body("Consulta cadastrada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar consulta");
        }
    }

    private MarcaConsultaEntity criarConsultaEntity(MarcaConsultaDTO marcaConsultaDTO, double valorConsulta) {
        MarcaConsultaEntity marcaConsultaEntity = new MarcaConsultaEntity();
        marcaConsultaEntity.setTipoConsulta(marcaConsultaDTO.getTipoConsulta());
        marcaConsultaEntity.setData(marcaConsultaDTO.getData());
        marcaConsultaEntity.setValor(valorConsulta);

        CadastrarUsers usuario = usuarioAssociado(marcaConsultaDTO);
        if (usuario != null) {
            marcaConsultaEntity.setCadastrarUsers(usuario);
        }

        return marcaConsultaEntity;
    }

    private CadastrarUsers usuarioAssociado(MarcaConsultaDTO marcaConsultaDTO) {
        if (marcaConsultaDTO.getCadastrarUsersId() != null) {
            return usersRepository.findById(marcaConsultaDTO.getCadastrarUsersId()).orElse(null);
        }
        return null;
    }

    private void descontarValorConsulta(CadastrarUsers usuario, double valorConsulta) {
        if (usuario != null && RoleEnum.FUNCIONARIO.equals(usuario.getRole())) {
            usuario.setSalario(usuario.getSalario() - valorConsulta);
            usersRepository.save(usuario);
        }
    }


}
