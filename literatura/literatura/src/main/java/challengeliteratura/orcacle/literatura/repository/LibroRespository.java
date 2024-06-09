package challengeliteratura.orcacle.literatura.repository;

import challengeliteratura.orcacle.literatura.model.Libro;

import java.util.List;
import java.util.Optional;

public interface LibroRespository {
    void save(Libro libro);

    List<Libro> findAll();

    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);

    List<Libro> findTop10ByOrderBytotalDescargas();
}
