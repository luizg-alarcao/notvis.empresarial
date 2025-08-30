package com.notvis.empresarial.integrations.viacep;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Essa classe fica responsável em preencher o endereço com os dados entregue do ViaCep
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViaCepResponse {
    private String logradouro;
    private String bairro;
    private String localidade; //Cidade
    private String uf; //Estado
}
