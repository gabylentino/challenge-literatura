package com.challengeliteraturaoracleone.literatura.model;

public class InfoLibroAutor {
    private Libro libro;
    private Autor autor;

    public InfoLibroAutor(Libro libro, Autor autor) {
    }

    public void infoLibroAutor(Libro libro, Autor autor) {
        this.libro = libro;
        this.autor=autor;
    }


    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return "infoLibroAutor{" +
                "libro=" + libro +
                ", autor=" + autor +
                '}';
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }


}
