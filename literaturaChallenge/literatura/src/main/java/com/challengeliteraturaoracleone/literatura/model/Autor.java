package com.challengeliteraturaoracleone.literatura.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="autores")

public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String nombre;
    private Integer nacimiento;
    private Integer deceso;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;


    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        for (Libro books : libros)
        {libros.forEach(l -> l.setAutor(this));
            this.libros=libros;
        }}

    Autor() {
    }

    public Autor(DatosAutor datosAutor) {

        this.nombre = datosAutor.nombre();
        this.nacimiento = datosAutor.nacimiento();
        this.deceso = datosAutor.deceso();
    }


    @Override
    public String toString() {
        return  "******************* " +
                     "Autor " +
                "******************* " +
                "  nombre='" + nombre + '\'' +
                ", nacimiento=" + nacimiento +
                ", deceso=" + deceso +
                "******************* ";
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Integer nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Integer getDeceso() {
        return deceso;
    }

    public void setDeceso(Integer deceso) {
        this.deceso = deceso;
    }

    public Number deceso() {
    }

    public Number nacimiento() {
    }
}
