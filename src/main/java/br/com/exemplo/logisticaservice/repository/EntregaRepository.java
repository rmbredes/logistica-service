package br.com.exemplo.logisticaservice.repository;

import br.com.exemplo.logisticaservice.entity.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Operações de persistência das entregas.
 */
public interface EntregaRepository
        extends JpaRepository<Entrega, Long> {

    /**
     * Permite descobrir se o pedido já possui uma entrega.
     */
    Optional<Entrega> findByPedidoId(Long pedidoId);
}