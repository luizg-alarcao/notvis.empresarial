package com.notvis.empresarial.controller;

import com.notvis.empresarial.services.ProdutoService;
import com.notvis.empresarial.web.dto.ProdutoRequest;
import com.notvis.empresarial.web.dto.ProdutoResponse;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

//Rotas
//POST /api/produtos — cria.
//GET /api/produtos — lista paginado.
//GET /api/produtos/{id} — busca por id.
//PUT /api/produtos/{id} — atualiza.
//DELETE /api/produtos/{id} — exclui.

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;

    @PostMapping
    public ResponseEntity<ProdutoResponse> criar(@Valid @RequestBody ProdutoRequest req) {
        var resp = service.criar(req);
        return ResponseEntity.created(URI.create("/api/produtos/" + resp.id())).body(resp);
    }

    @GetMapping
    public Page<ProdutoResponse> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "nome,asc") String sort) {

        Sort.Direction dir = Sort.Direction.ASC;
        String prop = "nome";
        var parts = sort.split(",");
        if (parts.length > 0 && !parts[0].isBlank()) prop = parts[0];
        if (parts.length > 1 && parts[1].equalsIgnoreCase("desc")) dir = Sort.Direction.DESC;

        var pageable = PageRequest.of(page, size, Sort.by(dir, prop));
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    public ProdutoResponse buscar(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ProdutoResponse atualizar(@PathVariable UUID id, @Valid @RequestBody ProdutoRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
