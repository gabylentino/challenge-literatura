package challengeliteratura.orcacle.literatura.model;

import jakarta.persistence.*;
@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne
    private Autor autor;
    private String idioma;
    private Integer download_count;

    public Libro() {
    }

    public Libro(DatosLibro datosLibros) {
        this.titulo = datosLibros.titulo();
        this.idioma = datosLibros.idiomas().get(0);
        this.download_count = datosLibros.totalDescargas();
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Integer download_count) {
        this.download_count = download_count;
    }

    @Override
    public String toString() {
        return   " Libro=" + titulo +
                ", Autor='" + autor.getNombre() + '\'' +
                ", Idioma=" + idioma +
                ", Descargas=" + download_count;
    }
}

