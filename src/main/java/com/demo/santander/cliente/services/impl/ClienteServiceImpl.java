package com.demo.santander.cliente.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.santander.cliente.entities.Cliente;
import com.demo.santander.cliente.repositories.ClienteRepository;
import com.demo.santander.cliente.services.ClienteService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public Flux<Cliente> findAll() {
		return this.clienteRepository.findAll();
	}

	@Override
	public Mono<Cliente> findById(String id) {
		return this.clienteRepository.findById(id);
	}

	@Override
	public Mono<Cliente> save(Cliente cliente) {
		return this.clienteRepository.save(cliente);
	}

	@Override
	public Mono<Void> delete(Cliente cliente) {
		return this.clienteRepository.delete(cliente);
	}

}
