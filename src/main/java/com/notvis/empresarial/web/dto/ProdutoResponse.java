package com.notvis.empresarial.web.dto;

import com.notvis.empresarial.domain.enums.TipoProduto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ProdutoResponse(
        UUID id,
        String nome,
        String descricao,
        TipoProduto tipoProduto,
        String codigo,
        BigDecimal preco,
        Integer quantidadeEstoque,
        Boolean ativo,
        OffsetDateTime criadoEm,
        OffsetDateTime atualizadoEm
) { }
