package com.culysoft.gestaoempregado.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"codigo", "nome", "telefone", "email"})
@Entity
@Table(name = "empregado")
public class Empregado {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	@NotEmpty(message = "O nome do Empregado é obrigatório")
	private String nome;
	private String morada;
	@NotEmpty(message = "O telefone do Empregado é obrigatório")
	private String telefone;
	@NotEmpty(message = "O email do Empregado é obrigatório")
	@Email(message = "O email do Empregado não é valido.")
	private String email;
	@NotEmpty(message = "O cargo do Empregado é obrigatório")
	private String cargo;
	private double salario;
	@Column(name = "data_criacao", insertable = false, updatable = false)
	private LocalDateTime dataDeCriacao;
	@OneToOne
    @JoinColumn(name = "autenticacao_codigo")
    private Autenticacao autenticacao;
    @ManyToOne
    @JoinTable(name = "empregado_criado", joinColumns = @JoinColumn(name = "empregado_pai"), 
            inverseJoinColumns = @JoinColumn(name = "empregado_filho", table = "empregado"))
    private Empregado empregadoPai;
}