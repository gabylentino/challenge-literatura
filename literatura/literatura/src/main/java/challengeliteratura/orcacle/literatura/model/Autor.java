package challengeliteratura.orcacle.literatura.model;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "autores")
public class Autor {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long Id;
        @Column(unique = true)
        private String nombre;
        private String fechaNac;
        private String fechaFall;
        @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private List<Libro> libros;

        public Autor() {
        }

        public Autor(DatosAutor datosAutor) {
            this.nombre = datosAutor.nombre();
            this.fechaNac = datosAutor.fechaNac();
            this.fechaFall = datosAutor.fechaFall();
        }

        public long getId() {
            return Id;
        }

        public void setId(long id) {
            Id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getFechaNac() {
            return fechaNac;
        }

        public void setFechaNac(String fechaNac) {
            this.fechaNac = fechaNac;
        }

        public String getFechaFall() {
            return fechaFall;
        }

        public void setFechaFall(String fechaFall) {
            this.fechaFall = fechaFall;
        }

        public List<Libro> getLibros() {
            return libros;
        }

        public void setLibros(List<Libro> libros) {
            this.libros = libros;
        }

        @Override
        public String toString() {
            return "Autor{" +
                    "Id=" + Id +
                    ", nombre='" + nombre + '\'' +
                    ", fechaNac='" + fechaNac + '\'' +
                    ", fechaFall='" + fechaFall + '\'' +
                    ", libros=" + libros +
                    '}';
        }
    }

