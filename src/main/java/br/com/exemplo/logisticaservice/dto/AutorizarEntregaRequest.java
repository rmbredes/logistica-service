package br.com.exemplo.logisticaservice.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Dados enviados pelo monólito para autorizar uma entrega.
 */
public record AutorizarEntregaRequest(

        @NotNull(message = "O identificador do evento é obrigatório")
        UUID eventoPagamentoId,

        @NotNull(message = "O identificador do pagamento é obrigatório")
        Long pagamentoId,

        @NotNull(message = "O identificador do pedido é obrigatório")
        Long pedidoId
) {
}