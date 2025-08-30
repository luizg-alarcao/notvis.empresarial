package com.notvis.empresarial.services;

import com.notvis.empresarial.domain.embeddables.Endereco;
import com.notvis.empresarial.integrations.viacep.ViaCepResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//Essa é a classe que vai consultar o endereço pelo cep, integrando com a API ViaCep.
@Service
public class CepService {
    public ViaCepResponse buscarEnderecoPorCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://viacep.com.br/ws/" + cep + "/json";

        ResponseEntity<ViaCepResponse> response = restTemplate.getForEntity(url, ViaCepResponse.class);
        return response.getBody();
    }

    public Endereco converterViaCepParaEndereco(ViaCepResponse dados, String cepDigitado) {
        return Endereco.builder()
                .cep(cepDigitado)
                .logradouro(dados.getLogradouro())
                .bairro(dados.getBairro())
                .localidade(dados.getLocalidade())
                .uf(dados.getUf())
                .numero("") //Essa parte deverá ser preenchida pelo utilizador
                .complemento("") //Opcional
                .build();
    }
}
