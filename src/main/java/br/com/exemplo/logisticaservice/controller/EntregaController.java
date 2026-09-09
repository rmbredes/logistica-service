package br.com.exemplo.logisticaservice.controller;

import br.com.exemplo.logisticaservice.dto.AutorizarEntregaRequest;
import br.com.exemplo.logisticaservice.dto.EntregaResponse;
import br.com.exemplo.logisticaservice.service.EntregaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoints REST do logistica-service.
 */
@RestController
@RequestMapping("/entregas")
public class EntregaController {

    private final EntregaService entregaService;

    public EntregaController(
            EntregaService entregaService
    ) {
        this.entregaService = entregaService;
    }

    /**
     * Autoriza uma entrega.
     *
     * Usamos 200 OK tanto na criação quanto na repetição porque
     * o resultado final é o mesmo: a entrega está autorizada.
     */
    @PostMapping
    public ResponseEntity<EntregaResponse> autorizar(
            @Valid @RequestBody AutorizarEntregaRequest request
    ) {
        return ResponseEntity.ok(
                EntregaResponse.from(
                        entregaService.autorizar(request)
                )
        );
    }

    /**
     * Consulta a entrega relacionada a um pedido.
     */
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<EntregaResponse> buscarPorPedido(
            @PathVariable Long pedidoId
    ) {
        return ResponseEntity.ok(
                EntregaResponse.from(
                        entregaService.buscarPorPedido(pedidoId)
                )
        );
    }
}