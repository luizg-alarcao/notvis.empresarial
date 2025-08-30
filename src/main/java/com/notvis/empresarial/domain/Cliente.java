package com.notvis.empresarial.domain;


import com.notvis.empresarial.domain.embeddables.Endereco;
import com.notvis.empresarial.domain.enums.TipoPessoa;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

//Essa classe é o modelo de cliente
//O jakarta irá criar os dados e as tabelas no Database
//Jakarta está fazendo as validações dos campos
@Entity
@Table(name = "clientes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cliente {

    @Id @GeneratedValue
    private UUID id;

    @NotBlank
    @Size(min = 2, max = 120, message = "O campo nome deve conter no minimo 2 e no maximo 120 caracteres.")
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPessoa tipoPessoa;

    @Column(unique = true)
    private String cpfCnpj;

    @Email
    private String email;

    private String telefone;

    @Embedded
    private Endereco endereco;
}
