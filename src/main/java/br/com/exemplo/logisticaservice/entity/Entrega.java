package br.com.exemplo.logisticaservice.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

/**
 * Representa uma entrega autorizada pela logística.
 */
@Entity
@Table(
        name = "entregas",
        uniqueConstraints = {
                /*
                 * Um mesmo pedido não pode criar duas entregas.
                 * Essa restrição ajuda a garantir idempotência.
                 */
                @UniqueConstraint(
                        name = "uk_entregas_pedido_id",
                        columnNames = "pedido_id"
                )
        }
)
public class Entrega {

    /** Identificador interno do logistica-service. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Identificador do evento de pagamento recebido pelo monólito.
     *
     * Não é chave estrangeira: pertence a outra aplicação.
     */
    @Column(name = "evento_pagamento_id", nullable = false)
    private UUID eventoPagamentoId;

    /** Identificador do pagamento no pagamento-service. */
    @Column(name = "pagamento_id", nullable = false)
    private Long pagamentoId;

    /** Identificador do pedido no monólito. */
    @Column(name = "pedido_id", nullable = false)
    private Long pedidoId;

    /** Estado atual da entrega. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEntrega status;

    /** Momento em que a logística autorizou a entrega. */
    @Column(name = "autorizada_em", nullable = false)
    private Instant autorizadaEm;

    /** Construtor exigido pelo JPA. */
    protected Entrega() {
    }

    /**
     * Cria uma entrega já autorizada.
     */
    public Entrega(
            UUID eventoPagamentoId,
            Long pagamentoId,
            Long pedidoId
    ) {
        this.eventoPagamentoId = eventoPagamentoId;
        this.pagamentoId = pagamentoId;
        this.pedidoId = pedidoId;
        this.status = StatusEntrega.AUTORIZADA;
        this.autorizadaEm = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public UUID getEventoPagamentoId() {
        return eventoPagamentoId;
    }

    public Long getPagamentoId() {
        return pagamentoId;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public StatusEntrega getStatus() {
        return status;
    }

    public Instant getAutorizadaEm() {
        return autorizadaEm;
    }
}