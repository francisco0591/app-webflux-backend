package com.demo.santander.cliente.routers;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.demo.santander.cliente.handlers.ClienteHandler;

@Configuration
public class RouterFunctionConfig {

	@Bean
	public RouterFunction<ServerResponse> routes(ClienteHandler handler) {

		return route(GET("/api/v1/clientes"), handler::listarClientes)
				.andRoute(GET("/api/v1/cliente/{id}"), handler::obtenerXId)
				.andRoute(POST("/api/v1/cliente"), handler::crearCliente)
				.andRoute(PUT("/api/v1/cliente/{id}"), handler::actualizarCliente)
				.andRoute(DELETE("/api/v1/cliente/{id}"), handler::eliminarCliente);

	}
}
