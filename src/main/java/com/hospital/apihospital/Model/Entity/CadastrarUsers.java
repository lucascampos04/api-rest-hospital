package com.hospital.apihospital.Model.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hospital.apihospital.Model.Enum.CargoEnum;
import com.hospital.apihospital.Model.Enum.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cadastrarUsers")
@Getter
@Setter
@NoArgsConstructor
public class CadastrarUsers implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome")
    @NotBlank(message = "O nome é obrigatório")
    @Pattern(regexp = "^[^0-9]+$", message = "O nome não deve conter números")
    private String nome;

    @Column(name = "password")
    @NotBlank(message = "A senha é obrigatoria")
    private String password;

    @Column(name = "password_antiga")
    private String passwordBefore;

    @Column(name = "rg", length = 15, unique = true)
    @NotBlank(message = "O RG é obrigatório")
    private String rg;

    @Column(name = "cpf", length = 19, unique = true)
    @NotBlank(message = "O CPF é obrigatório")
    private String cpf;

    @Column(name = "data_nascimento")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotBlank(message = "A data de nascimento é obrigatorio")
    private Date dataNascimento;

    @Column(name = "genero", length = 30)
    @NotBlank(message = "Genero obrigatorio")
    @Pattern(regexp = "^[^0-9]+$", message = "O o genero não deve conter números")
    private String genero;

    @Column(name = "plano_paciente", length = 50)
    @NotBlank
    private String plano_paciente;

    @Column(name = "email")
    @NotBlank
    private String email;

    @Column(name = "telefone")
    @NotBlank
    private String telefone;

    @Column(name = "data_registro")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotBlank(message = "A data de registro é obrigatória")
    private Date dataRegistro;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private String dataRegistroFormatada;

    @Column(name = "cargo")
    @Enumerated(EnumType.STRING)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CargoEnum cargo;

    @Column(name = "salario")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double salario;

    @Column(name = "role", length = 20)
    @NotBlank(message = "A role é obrigatória")
    @Pattern(regexp = "^[^0-9]+$", message = "O role não deve conter números")
    private RoleEnum role;

    @OneToMany(mappedBy = "cadastrarUsers", cascade = CascadeType.ALL)
    private List<MarcaConsultaEntity> marcaConsultas;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if (getRole() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + getRole().name()));
        }

        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
