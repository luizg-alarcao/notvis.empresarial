package com.notvis.empresarial.web.dto;


import com.notvis.empresarial.domain.embeddables.Endereco;
import com.notvis.empresarial.domain.enums.TipoPessoa;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Essa classe não representa o cliente no Database
//ela representa o cliente na entrada da API, quando alguem envia os dados para cadastrar
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor //Incluido dps.
public class ClienteRequest {
    @NotBlank @Size(min = 2, max = 120)
    String nome;

    @NotNull
    TipoPessoa tipoPessoa;

    String cpfCnpj;

    @Email
    String email;

    String telefone;

    @NotNull
    Endereco endereco;
}
