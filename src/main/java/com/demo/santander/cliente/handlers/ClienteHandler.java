package com.demo.santander.cliente.handlers;

import static com.demo.santander.cliente.utilitarios.Constantes.CAMPO;
import static com.demo.santander.cliente.utilitarios.Constantes.ESPACIO;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.demo.santander.cliente.entities.Cliente;
import com.demo.santander.cliente.services.ClienteService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ClienteHandler {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private Validator validator;

	public Mono<ServerResponse> listarClientes(ServerRequest request) {
		return ServerResponse.ok().body(clienteService.findAll(), Cliente.class);
	}

	public Mono<ServerResponse> obtenerXId(ServerRequest request) {
		String id = request.pathVariable("id");
		return clienteService.findById(id).flatMap(cliente -> ServerResponse.ok().bodyValue(cliente))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> crearCliente(ServerRequest request) {
		Mono<Cliente> cliente = request.bodyToMono(Cliente.class);

		return cliente.flatMap(p -> {
			Errors errors = new BeanPropertyBindingResult(p, Cliente.class.getName());
			validator.validate(p, errors);

			if (errors.hasErrors()) {
				return Flux.fromIterable(errors.getFieldErrors())
						.map(fieldError -> CAMPO + fieldError.getField() + ESPACIO + fieldError.getDefaultMessage())
						.collectList().flatMap(errorMensaje -> ServerResponse.badRequest().contentType(APPLICATION_JSON)
								.bodyValue(errorMensaje));
			} else {

				return clienteService.save(p)
						.flatMap(clientedb -> ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(clientedb));
			}
		});
	}

	public Mono<ServerResponse> actualizarCliente(ServerRequest request) {
		String id = request.pathVariable("id");
		Mono<Cliente> clienteMono = request.bodyToMono(Cliente.class);

		return clienteService.findById(id).zipWith(clienteMono, (dbCliente, newCliente) -> {
			dbCliente.setNombre(newCliente.getNombre());
			dbCliente.setEmail(newCliente.getEmail());
			dbCliente.setTipoDocumento(newCliente.getTipoDocumento());
			dbCliente.setNroDocumento(newCliente.getNroDocumento());
			return dbCliente;
		}).flatMap(clienteService::save).flatMap(cliente -> ServerResponse.ok().bodyValue(cliente))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> eliminarCliente(ServerRequest request) {
		String id = request.pathVariable("id");
		Mono<Cliente> clienteDb = clienteService.findById(id);

		return clienteDb.flatMap(p -> clienteService.delete(p).then(ServerResponse.noContent().build()))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
}
