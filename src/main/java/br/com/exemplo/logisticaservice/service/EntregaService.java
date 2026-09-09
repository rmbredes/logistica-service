package br.com.exemplo.logisticaservice.service;

import br.com.exemplo.logisticaservice.dto.AutorizarEntregaRequest;
import br.com.exemplo.logisticaservice.entity.Entrega;
import br.com.exemplo.logisticaservice.repository.EntregaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Executa a autorização de entregas.
 */
@Service
public class EntregaService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(EntregaService.class);

    private final EntregaRepository entregaRepository;

    public EntregaService(
            EntregaRepository entregaRepository
    ) {
        this.entregaRepository = entregaRepository;
    }

    /**
     * Autoriza uma entrega ou devolve a entrega já existente.
     *
     * Isso torna a operação idempotente: repetir a mesma solicitação
     * para o mesmo pedido não cria outro registro.
     */
    @Transactional
    public Entrega autorizar(
            AutorizarEntregaRequest request
    ) {
        return entregaRepository
                .findByPedidoId(request.pedidoId())
                .map(entregaExistente -> {
                    LOGGER.info(
                            "Entrega já autorizada: entregaId={}, pedidoId={}",
                            entregaExistente.getId(),
                            entregaExistente.getPedidoId()
                    );

                    return entregaExistente;
                })
                .orElseGet(() -> criarEntrega(request));
    }

    /**
     * Cria efetivamente uma nova entrega.
     */
    private Entrega criarEntrega(
            AutorizarEntregaRequest request
    ) {
        Entrega entrega = new Entrega(
                request.eventoPagamentoId(),
                request.pagamentoId(),
                request.pedidoId()
        );

        Entrega salva =
                entregaRepository.save(entrega);

        LOGGER.info(
                "Entrega autorizada: entregaId={}, pagamentoId={}, pedidoId={}",
                salva.getId(),
                salva.getPagamentoId(),
                salva.getPedidoId()
        );

        return salva;
    }

    /**
     * Consulta uma entrega pelo pedido.
     */
    @Transactional(readOnly = true)
    public Entrega buscarPorPedido(
            Long pedidoId
    ) {
        return entregaRepository
                .findByPedidoId(pedidoId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Entrega não encontrada para o pedido: "
                                        + pedidoId
                        )
                );
    }
}