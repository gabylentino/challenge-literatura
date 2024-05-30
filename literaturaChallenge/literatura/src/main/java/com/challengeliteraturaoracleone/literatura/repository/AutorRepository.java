package com.challengeliteraturaoracleone.literatura.repository;

import com.challengeliteraturaoracleone.literatura.model.Autor;
import com.challengeliteraturaoracleone.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository  extends JpaRepository<Autor,Long> {
    Optional<Autor> findByNombreContainsIgnoreCase(String nombreAutor);
    @Query("SELECT l FROM LibroBD l JOIN l.autor a WHERE l.titulo LIKE %:nombre%")
    Optional<Libro> buscarLibroPorNombre(@Param("nombre") String nombre);

    @Query("SELECT l FROM LibroBD l JOIN l.autor a")
    List<Libro> buscarTodosLosLibros();

    @Query("SELECT a FROM AutorBD a WHERE a.deceso > :fecha AND a.nacimiento < :fecha")
    List<Autor> buscarAutoresVivos(@Param ("fecha") Integer fecha);
    @Query("SELECT l FROM LibroBD l JOIN l.autor a WHERE l.idiomas = :idioma")
    List<Libro> buscarPorIdima(@Param ("idioma") String idioma);

}
