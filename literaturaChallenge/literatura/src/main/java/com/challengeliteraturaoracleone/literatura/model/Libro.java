package com.challengeliteraturaoracleone.literatura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String titulo;

    //    private String autores;
    private String idiomas;
    private Integer descargas;
    private String categoria;
    private String medio;
    @ManyToOne
    private Autor autor;

    public Libro(Libro libros) {
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    public Libro(DatosLibro datosLibro) {

        this.titulo = datosLibro.titulo();
        if (datosLibro.idiomas().size() > 1) {
            String strIdiomas = null;
            for (String idioma : datosLibro.idiomas()) {
                strIdiomas = strIdiomas + "," + idioma;
            }
            this.idiomas = strIdiomas;
        } else {
            this.idiomas = datosLibro.idiomas().toString().replace("[", "").replace("]", "");
        }

        this.descargas = multiplicar(datosLibro.descargas());
        this.categoria = DevolverCategoria(datosLibro.categoria());
//                this.autores = String.valueOf(libroRecord.autores().stream()
//                .map(autor -> autor.nombre()).collect(Collectors.toList())).replace("[", "").replace("]", "");
        //this.categoria = DevolverCategoria(libroRecord.categoria());
        this.medio = datosLibro.medio();

    }
    Libro (){}
    private String DevolverCategoria(ArrayList<String> meme) {

        // Palabras a contar
        String palabraFiccion = "fiction";
        String palabraDrama = "drama";
        String palabraComedia = "comedy";

        // Contadores
        int contadorFiccion = 0;
        int contadorDrama = 0;
        int contadorComedia = 0;
        int contador=0;
        String categoriaFinal= null;

        // Contar las palabras en la lista
        for (String frase : meme) {
            if (frase.toLowerCase().contains(palabraFiccion)) {
                contadorFiccion++;
                contador++;
            }
            if (frase.toLowerCase().contains(palabraDrama)) {
                contadorDrama++;
                contador++;
            }
            if (frase.toLowerCase().contains(palabraComedia)) {
                contadorComedia++;
                contador++;
            }
        }
        if ( contadorFiccion > contadorComedia && contadorFiccion > contadorDrama){categoriaFinal = "Ficcion";}
        if ( contadorComedia > contadorFiccion && contadorComedia> contadorDrama){categoriaFinal = "Comedia";}
        if ( contadorDrama > contadorComedia && contadorDrama > contadorFiccion){categoriaFinal = "Drama";}
        String valores ="ficcion"+contadorFiccion+"drama"+contadorDrama+"comedia"+contadorComedia;
        if (categoriaFinal == null) {  categoriaFinal = "Otra";
        }
        return categoriaFinal;

    }
    public Integer multiplicar (int numero)
    {
        int Resultado=0;
        Resultado = 100023*numero;
        return Resultado;
    }


    @Override
    public String toString() {
        return "LibroBD{" +
                "Id=" + Id +
                ", titulo='" + titulo + '\'' +

                ", idiomas='" + idiomas + '\'' +
                ", descargas=" + descargas +
                ", categoria='" + categoria + '\'' +
                ", medio='" + medio + '\'' +
                '}';
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMedio() {
        return medio;
    }

    public void setMedio(String medio) {
        this.medio = medio;
    }

    public Arrays autores() {
        return autores();
    }

    public int descargas() {
        return descargas;
    }
}
