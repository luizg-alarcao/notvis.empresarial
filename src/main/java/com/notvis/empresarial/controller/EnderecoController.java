package com.notvis.empresarial.controller;

import com.notvis.empresarial.domain.embeddables.Endereco;
import com.notvis.empresarial.integrations.viacep.ViaCepResponse;
import com.notvis.empresarial.services.CepService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Cria um endpoint(Porta de entrada da API): GET /enderecos/cep/{cep}
//Quando acessar o endereço com um CEP, ele consulta o ViaCEP e devolve os dados do endereço.
@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private CepService cepService;

    @GetMapping("/cep/{cep}")
    public ResponseEntity<Endereco> buscarEndereco(@PathVariable String cep) {
        ViaCepResponse dados = cepService.buscarEnderecoPorCep(cep);
        Endereco endereco = cepService.converterViaCepParaEndereco(dados, cep);
        return ResponseEntity.ok(endereco);
    }
}