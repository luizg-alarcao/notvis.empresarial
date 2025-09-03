package com.notvis.empresarial.web.dto;

import com.notvis.empresarial.domain.embeddables.Endereco;
import com.notvis.empresarial.domain.enums.TipoPessoa;
import lombok.*;

import java.util.UUID;

//Essa classe é um DTO de saída
//ela serve para devolver os dados de um cliente quando sua API é chamada.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteResponse {
    private UUID id;
    private String nome;
    private TipoPessoa tipoPessoa;
    private String cpfCnpj;
    private String email;
    private String telefone;
    private Endereco endereco;
}