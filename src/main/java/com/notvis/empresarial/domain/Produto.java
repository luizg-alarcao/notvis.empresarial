package com.notvis.empresarial.domain;

import com.notvis.empresarial.domain.enums.TipoProduto;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "produtos",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_produtos_codigo", columnNames = "codigo")
        }
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Produto {

    @Id @GeneratedValue
    private UUID id;

    @NotBlank
    @Size(min = 2, max = 150)
    @Column(nullable = false, length = 150)
    private String nome;

    @Size(max = 500)
    @Column(length = 500)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_produto", nullable = false, length = 20)
    private TipoProduto tipoProduto;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String codigo; // código interno/de fabricante (único)

    @NotNull
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    @Digits(integer = 13, fraction = 2)
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal preco;

    @PositiveOrZero
    private Integer quantidadeEstoque; // para PECA obrigatório (> = 0); para SERVICO pode ser null/0

    @Builder.Default
    @Column(nullable = false)
    private Boolean ativo = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime criadoEm;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime atualizadoEm;
}
