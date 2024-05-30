package com.challengeliteraturaoracleone.literatura;

import com.challengeliteraturaoracleone.literatura.principal.Principal;
import com.challengeliteraturaoracleone.literatura.repository.AutorRepository;
import com.challengeliteraturaoracleone.literatura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {
	@Autowired// acceso CRUD ( inyeccion de dependencias)
	private AutorRepository repositorioAutor;// acceso CRUD
	@Autowired
	private LibroRepository repositorioLibro;// acceso CRUD
	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal= new Principal(repositorioLibro,repositorioAutor );
		//principal.BusquedaInicialArray();
		principal.muestraMenu();
//		principal.BusquedaLibros();

	}
}