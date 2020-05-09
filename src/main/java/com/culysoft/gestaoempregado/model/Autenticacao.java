package com.culysoft.gestaoempregado.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.culysoft.gestaoempregado.enumeracao.Roles;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Quitumba Culyssander
 */
@Data
@EqualsAndHashCode(callSuper = false, of = {"utilizador"})
@Entity
@Table(name = "autenticacao")
public class Autenticacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    private boolean estado;
    @Column(name = "utilizador", nullable = false, unique = true)
    @NotEmpty(message = "O utilizador da Autenticação é obrigatório")
    private String utilizador;
    @Column(name = "palavra_passe", nullable = false)
    @NotEmpty(message = "A palavra passe da Autenticação é obrigatório")
    private String palavraPasse;
    @Enumerated(EnumType.STRING)
    private Roles perfil;
    @Column(name = "data_hora_criacao", insertable = false, updatable = false)
    private LocalDateTime dataHoraDeCriacao;
    @Column(name = "ultima_autenticacao")
    private LocalDateTime ultimaAutenticacao;
}
