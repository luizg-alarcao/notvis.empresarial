package com.notvis.empresarial.domain.embeddables;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

//A API ViaCep vai pegar dados do CEP digitado e irá preencher os campos da classe "Endereco"
@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Endereco {

    @NotBlank(message = "Esse campo não pode ficar vázio!")
    @Pattern(regexp = "\\d{8}", message = "O CEP deve conter 8 dígitos (ex: 87200000)")
    private String cep;

    @NotBlank(message = "Esse campo não pode ficar vázio!")
    @Size(max = 120, message = "Esse campo não pode ter mais de 120 caracteres.")
    private String logradouro;

    @NotBlank(message = "Esse campo não pode ficar vázio!")
    @Size(max = 10, message = "Esse campo não pode ter mais de 10 caracteres.")
    private String numero;

    @Size(max = 50, message = "Esse campo não pode ter mais de 50 caracteres.")
    private String complemento;


    @NotBlank(message = "Esse campo não pode ficar vázio!")
    @Size(max = 50, message = "Esse campo não pode ter mais de 50 caracteres.")
    private String bairro;

    @NotBlank(message = "Esse campo não pode ficar vázio!")
    @Size(max = 50, message = "Esse campo não pode ter mais de 50 caracteres.")
    private String localidade; //Cidade

    @NotBlank(message = "Esse campo não pode ficar vázio!")
    @Size(max = 2, message = "Esse campo deve conter apenas as siglas do seu estado, ex: 'PR'.")
    private String uf; //Estado

}
