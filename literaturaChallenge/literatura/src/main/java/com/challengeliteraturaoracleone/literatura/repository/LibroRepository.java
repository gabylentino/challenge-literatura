package com.challengeliteraturaoracleone.literatura.repository;

import com.challengeliteraturaoracleone.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);

    List<Libro> findTop10ByOrderByDescargasDesc();

    @Query("SELECT DISTINCT l.idiomas FROM LibroBD l")
    List<String> EncontrarlosIdimoas();

}
