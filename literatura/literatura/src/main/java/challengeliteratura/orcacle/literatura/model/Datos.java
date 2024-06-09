package challengeliteratura.orcacle.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(
        @JsonAlias("results") List<DatosLibro> resultado
) {
    public boolean isPresent() {
        return false;
    }

    public Arrays resultados() {
        return null;
    }
}
