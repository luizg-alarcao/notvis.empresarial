package com.notvis.empresarial.controller;

import com.notvis.empresarial.services.ClienteService;
import com.notvis.empresarial.web.dto.ClienteRequest;
import com.notvis.empresarial.web.dto.ClienteResponse;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

//Essa classe é responsável por controlar as ações relacionadas aos clientes (CRUD)
//POST /api/clientes (criar)
//GET /api/clientes (listar paginado)
//GET /api/clientes/{id} (buscar por id)
//PUT /api/clientes/{id} (atualizar)
//DELETE /api/clientes/{id} (excluir)
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity<ClienteResponse> criar(@Valid @RequestBody ClienteRequest req) {
        var resp = service.criar(req);
        return ResponseEntity.created(URI.create("/api/clientes/" + resp.getId())).body(resp);
    }

    @GetMapping
    public Page<ClienteResponse> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "nome,asc") String sort) {

        var parts = sort.split(",");
        var pageable = PageRequest.of(page, size, Sort.by(
                        (parts.length > 0 ? parts[0] : "nome"))
                .ascending());

        if (parts.length == 2 && parts[1].equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(parts[0]).descending());
        }

        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    public ClienteResponse buscar(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ClienteResponse atualizar(@PathVariable UUID id,
                                     @Valid @RequestBody ClienteRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
