package com.demo.santander.cliente.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

	@Id
	private String id;

	@NotBlank
	@NotNull
	private String nombre;
	private String email;
	@NotBlank
	@NotNull
	private String tipoDocumento;
	@NotBlank
	@NotNull
	private String nroDocumento;

}
