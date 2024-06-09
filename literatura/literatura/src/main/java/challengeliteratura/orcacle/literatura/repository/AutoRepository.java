package challengeliteratura.orcacle.literatura.repository;

import challengeliteratura.orcacle.literatura.model.Autor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutoRepository {
    Optional<Autor> findByNombre(String nombre);

    @Query("SELECT s FROM Autor s WHERE s.fechaNac <= :año AND s.fechaFall >= :año")
    List<Autor> autoresVivosEnDeterminadoAnio(int anio);

    void save(Autor autor);
}
