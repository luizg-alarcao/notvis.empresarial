package com.notvis.empresarial.domain;

import com.notvis.empresarial.domain.enums.Cargo;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

// Essa classe representa um funcionário da empresa.
// Por enquanto é só cadastro, depois expandiremos para autenticação.
@Entity
@Table(name = "funcionarios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Funcionario {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    @Size(min = 2, max = 120, message = "O nome deve ter entre 2 e 120 caracteres.")
    private String nome;

    @NotBlank
    @Size(min = 11, max = 11, message = "O CPF deve ter 11 dígitos.")
    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cargo cargo;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "O salário deve ser maior que zero.")
    private BigDecimal salario;

    @NotNull
    private LocalDate dataAdmissao;

    @Column(nullable = false)
    private boolean ativo = true;
}
//Parei por aqui...