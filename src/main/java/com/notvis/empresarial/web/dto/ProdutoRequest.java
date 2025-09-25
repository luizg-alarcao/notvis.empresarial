package com.notvis.empresarial.web.dto;

import com.notvis.empresarial.domain.enums.TipoProduto;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

//Porque uso de dto? Não expor Entity direto; controlar contrato da API; validar a entrada.
public record ProdutoRequest(
        @NotBlank @Size(min = 2, max = 120) String nome,
        @Size(max = 500) String descricao,
        @NotNull TipoProduto tipoProduto,
        @NotBlank @Size(max = 50) String codigo,
        @NotNull @DecimalMin(value = "0.01") @Digits(integer = 13, fraction = 2) BigDecimal preco,
        @PositiveOrZero Integer quantidadeEstoque,
        Boolean ativo
) { }
