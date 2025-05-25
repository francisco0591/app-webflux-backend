package com.demo.santander.cliente.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.demo.santander.cliente.entities.Cliente;

public interface ClienteRepository extends ReactiveMongoRepository<Cliente, String>{
   
}
