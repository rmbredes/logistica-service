package br.com.exemplo.logisticaservice.dto;

import br.com.exemplo.logisticaservice.entity.Entrega;
import br.com.exemplo.logisticaservice.entity.StatusEntrega;

import java.time.Instant;
import java.util.UUID;

/**
 * Representação devolvida pela API de logística.
 */
public record EntregaResponse(
        Long id,
        UUID eventoPagamentoId,
        Long pagamentoId,
        Long pedidoId,
        StatusEntrega status,
        Instant autorizadaEm
) {

    /**
     * Converte a entidade persistida para o contrato HTTP.
     */
    public static EntregaResponse from(Entrega entrega) {
        return new EntregaResponse(
                entrega.getId(),
                entrega.getEventoPagamentoId(),
                entrega.getPagamentoId(),
                entrega.getPedidoId(),
                entrega.getStatus(),
                entrega.getAutorizadaEm()
        );
    }
}