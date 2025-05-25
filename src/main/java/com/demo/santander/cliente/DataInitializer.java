package com.demo.santander.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.demo.santander.cliente.entities.Cliente;
import com.demo.santander.cliente.repositories.ClienteRepository;

import reactor.core.publisher.Flux;

@Configuration
public class DataInitializer {

	@Autowired
	private ClienteRepository clienteRepository;

	@Bean
	public ApplicationRunner initData() {
		return args -> {
			clienteRepository.deleteAll()
					.thenMany(Flux.just(
							Cliente.builder().nombre("Andres Sosa").email("andres@hotmail.com").tipoDocumento("DNI")
									.nroDocumento("4563476").build(),
							Cliente.builder().nombre("Patricias Fernandez").email("patricia@hotmail.com").tipoDocumento("DNI")
									.nroDocumento("8123234").build(),
							Cliente.builder().nombre("Luis Soto").email("luis@gmail.com").tipoDocumento("RUC")
									.nroDocumento("4565676").build())
							.flatMap(clienteRepository::save))
					.subscribe(cliente -> System.out.println("📦 Insertado: " + cliente));
		};
	}
}
