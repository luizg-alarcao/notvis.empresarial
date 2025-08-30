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
    UUID id;
    String nome;
    TipoPessoa tipoPessoa;
    String cpfCnpj;
    String email;
    String telefone;
    Endereco endereco;
}