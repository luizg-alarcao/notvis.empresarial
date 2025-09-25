package com.notvis.empresarial.services;

import com.notvis.empresarial.domain.Produto;
import com.notvis.empresarial.domain.enums.TipoProduto;
import com.notvis.empresarial.repository.ProdutoRepository;
import com.notvis.empresarial.web.dto.ProdutoRequest;
import com.notvis.empresarial.web.dto.ProdutoResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

//Regras que implementamos aqui:
//Normalizar codigo: trim().toUpperCase().
//Unicidade de codigo (case-insensitive).
//PECA: quantidadeEstoque obrigatória (>= 0).
//SERVICO: quantidadeEstoque será forçada para null (ou 0).
//Atualização preserva unicidade: não pode colidir com outro id.

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;

    @Transactional
    public ProdutoResponse criar(ProdutoRequest req) {
        var codigoNorm = normalizarCodigo(req.codigo());
        validarNegocio(req.tipoProduto(), req.quantidadeEstoque(), codigoNorm, null);

        var entity = toEntity(req, codigoNorm);
        var salvo = repository.save(entity);
        return toResponse(salvo);
    }

    public Page<ProdutoResponse> listar(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    public ProdutoResponse buscarPorId(UUID id) {
        var p = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));
        return toResponse(p);
    }

    @Transactional
    public ProdutoResponse atualizar(UUID id, ProdutoRequest req) {
        var existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));

        var codigoNorm = normalizarCodigo(req.codigo());
        validarNegocio(req.tipoProduto(), req.quantidadeEstoque(), codigoNorm, id);

        existente.setNome(req.nome());
        existente.setDescricao(req.descricao());
        existente.setTipoProduto(req.tipoProduto());
        existente.setCodigo(codigoNorm);
        existente.setPreco(req.preco());

        if (req.tipoProduto() == TipoProduto.PECA) {
            existente.setQuantidadeEstoque(req.quantidadeEstoque() == null ? 0 : req.quantidadeEstoque());
        } else {
            existente.setQuantidadeEstoque(null); // serviço não controla estoque
        }

        existente.setAtivo(req.ativo() == null ? Boolean.TRUE : req.ativo());

        var salvo = repository.save(existente);
        return toResponse(salvo);
    }

    @Transactional
    public void excluir(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Produto não encontrado.");
        }
        repository.deleteById(id);
    }

    // Regras/Helpers

    private void validarNegocio(TipoProduto tipo, Integer estoque, String codigoNorm, UUID idEdicao) {
        // codigo único (case-insensitive)
        repository.findByCodigoIgnoreCase(codigoNorm).ifPresent(outro -> {
            if (idEdicao == null || !outro.getId().equals(idEdicao)) {
                throw new IllegalArgumentException("Código já cadastrado para outro produto.");
            }
        });

        if (tipo == TipoProduto.PECA) {
            if (estoque == null || estoque < 0) {
                throw new IllegalArgumentException("Para PECA, quantidadeEstoque deve ser informada (>= 0).");
            }
        }
        // Para SERVICO, estoque é ignorado
    }

    private String normalizarCodigo(String codigo) {
        return codigo == null ? null : codigo.trim().toUpperCase();
    }

    private Produto toEntity(ProdutoRequest req, String codigoNorm) {
        var builder = Produto.builder()
                .nome(req.nome())
                .descricao(req.descricao())
                .tipoProduto(req.tipoProduto())
                .codigo(codigoNorm)
                .preco(req.preco())
                .ativo(req.ativo() == null ? Boolean.TRUE : req.ativo());

        if (req.tipoProduto() == TipoProduto.PECA) {
            builder.quantidadeEstoque(req.quantidadeEstoque() == null ? 0 : req.quantidadeEstoque());
        } else {
            builder.quantidadeEstoque(null);
        }

        return builder.build();
    }

    private ProdutoResponse toResponse(Produto p) {
        return new ProdutoResponse(
                p.getId(), p.getNome(), p.getDescricao(), p.getTipoProduto(),
                p.getCodigo(), p.getPreco(), p.getQuantidadeEstoque(), p.getAtivo(),
                p.getCriadoEm(), p.getAtualizadoEm()
        );
    }
}
