package com.notvis.empresarial.services;

import com.notvis.empresarial.domain.Cliente;
import com.notvis.empresarial.domain.enums.TipoPessoa;
import com.notvis.empresarial.repository.ClienteRepository;
import com.notvis.empresarial.web.dto.ClienteRequest;
import com.notvis.empresarial.web.dto.ClienteResponse;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

//Essa classe contém as regras de negócio de cliente
//contem as validações.
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional
    public ClienteResponse criar(ClienteRequest req) {
        validarDocumento(req.getTipoPessoa(), req.getCpfCnpj());
        if (req.getCpfCnpj() != null && repository.existsByCpfCnpj(somenteDigitos(req.getCpfCnpj()))) {
            throw new IllegalArgumentException("CPF/CNPJ já cadastrado.");
        }
        var entity = toEntity(req);
        var salvo = repository.save(entity);
        return toResponse(salvo);
    }

    public Page<ClienteResponse> listar(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    public ClienteResponse buscarPorId(UUID id) {
        var c = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado."));
        return toResponse(c);
    }

    @Transactional
    public ClienteResponse atualizar(UUID id, ClienteRequest req) {
        validarDocumento(req.getTipoPessoa(), req.getCpfCnpj());

        var existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado."));

        if (req.getCpfCnpj() != null) {
            var doc = somenteDigitos(req.getCpfCnpj());
            repository.findByCpfCnpj(doc).ifPresent(outro -> {
                if (!outro.getId().equals(id)) {
                    throw new IllegalArgumentException("CPF/CNPJ já cadastrado para outro cliente.");
                }
            });
            existente.setCpfCnpj(doc);
        } else {
            existente.setCpfCnpj(null);
        }

        existente.setNome(req.getNome());
        existente.setTipoPessoa(req.getTipoPessoa());
        existente.setEmail(req.getEmail());
        existente.setTelefone(req.getTelefone());
        existente.setEndereco(req.getEndereco());

        var salvo = repository.save(existente);
        return toResponse(salvo);
    }

    @Transactional
    public void excluir(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Cliente não encontrado.");
        }
        repository.deleteById(id);
    }

    private void validarDocumento(TipoPessoa tipo, String cpfCnpj) {
        if (cpfCnpj == null || cpfCnpj.isBlank()) return; // documento opcional
        var doc = somenteDigitos(cpfCnpj);

        if (tipo == TipoPessoa.Fisica && doc.length() != 11) {
            throw new IllegalArgumentException("CPF deve ter 11 dígitos (somente números).");
        }
        if (tipo == TipoPessoa.Juridica && doc.length() != 14) {
            throw new IllegalArgumentException("CNPJ deve ter 14 dígitos (somente números).");
        }
    }

    private String somenteDigitos(String s) {
        return s.replaceAll("\\D", "");
    }

    private Cliente toEntity(ClienteRequest req) {
        var doc = req.getCpfCnpj() == null ? null : somenteDigitos(req.getCpfCnpj());
        return Cliente.builder()
                .nome(req.getNome())
                .tipoPessoa(req.getTipoPessoa())
                .cpfCnpj(doc)
                .email(req.getEmail())
                .telefone(req.getTelefone())
                .endereco(req.getEndereco())
                .build();
    }

    private ClienteResponse toResponse(Cliente c) {
        return ClienteResponse.builder()
                .id(c.getId())
                .nome(c.getNome())
                .tipoPessoa(c.getTipoPessoa())
                .cpfCnpj(c.getCpfCnpj())
                .email(c.getEmail())
                .telefone(c.getTelefone())
                .endereco(c.getEndereco())
                .build();
    }
}
