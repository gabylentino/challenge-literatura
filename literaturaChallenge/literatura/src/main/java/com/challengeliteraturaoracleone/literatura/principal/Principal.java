package com.challengeliteraturaoracleone.literatura.principal;

import com.challengeliteraturaoracleone.literatura.model.Autor;
import com.challengeliteraturaoracleone.literatura.model.InfoLibroAutor;
import com.challengeliteraturaoracleone.literatura.model.Libro;
import com.challengeliteraturaoracleone.literatura.model.Resultado;
import com.challengeliteraturaoracleone.literatura.repository.AutorRepository;
import com.challengeliteraturaoracleone.literatura.repository.LibroRepository;
import com.challengeliteraturaoracleone.literatura.service.ConsumoApi;
import com.challengeliteraturaoracleone.literatura.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private ConsumoApi consumoApi = new ConsumoApi();
    private Scanner input = new Scanner(System.in);
    private final String direccion_URL = "https://gutendex.com/books/?page=1&search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    public String textoBusqueda;

    private LibroRepository repositoriolibro; // acceso CRUD
    private AutorRepository repositorioautor;

    public Principal(LibroRepository repositoriolibro, AutorRepository repositorioautor) {// acceso CRUD
        this.repositorioautor = repositorioautor;
        this.repositoriolibro = repositoriolibro;

    }

    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ***********************************************************************************
                                                    Bienvenido!
                                               Que deseas hacer hoy?
                    ***********************************************************************************       
                                            1 - Buscar Libros por TÍtulo
                                            2 - Buscar Autor por Nombre
                                            3 - Listar Libros Registrados
                                            4 - Listar Autores Registrados
                                            5 - Listar Autores Vivos
                                            6 - Listar Libros por Idioma
                                            7 - Buscar Libros por TÍtulo BULK
                                            8 - Top 10 Libros más Buscados
                                            9 - Generar Estadísticas
                                        
                                            0 - Salir
                    ***********************************************************************************
                    """;

            while (opcion != 0) {
                System.out.println(menu);
                try {
                    opcion = Integer.valueOf(input.nextLine());
                    switch (opcion) {
                        case 1:
                            BusquedaInicial();
                            break;
                        case 2:
                            buscarAutorPorNombre();
                            break;
                        case 3:
                            listarLibrosRegistrados();
                            break;
                        case 4:
                            listarAutoresRegistrados();
                            break;
                        case 5:
                            listarAutoresVivos();
                            break;
                        case 6:
                            listarLibrosPorIdioma();
                            break;
                        case 7:
                            BusquedaInicialArray();
                            break;
                        case 8:
                            top10Libros();
                            break;
                        case 9:
                            generarEstadisticas();
                            break;
                        
                        case 0:
                            System.out.println(
                                    );
                            break;
                        default:
                            System.out.println("Opción no válida!");
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Opción no válida: " + e.getMessage());
                }


            }
        }
    }

    private void listarLibrosPorIdioma() {
        List <String> idiomas= repositoriolibro.EncontrarlosIdimoas();
        System.out.println("lista de abrevitauras de idiomas existentes en la base");
        for ( String i :idiomas) {
            System.out.println(i);
        }

        System.out.println("Escribe la abreviatura del idioma ");
        var idioma = input.nextLine();
        List<Libro> libros = repositorioautor.buscarPorIdima(idioma);
        System.out.println(libros);
    }

    private void BusquedaInicial() {
        System.out.println("Escribe el titulo del libro que quieres buscar");
        var textoBusqueda = input.nextLine();

        System.out.println(direccion_URL + textoBusqueda.replace(" ", "+"));
        var json = consumoApi.obtenerDatos(direccion_URL + textoBusqueda.replace(" ", "+"));

        Resultado resultados = conversor.obtenerDatos(json, Resultado.class);

        if (json.isEmpty() || !json.contains("\"count\":0,\"next\":null,\"previous\":null,\"results\":[]")) {


            Optional<Libro> datosLibro = resultados.resultados().stream()
                    .filter(libros -> libros.getTitulo().toLowerCase()
                            .contains(textoBusqueda.toLowerCase())).findFirst();

            if (datosLibro.isPresent()) {

                System.out.println("libro Encontrado");
                List<Libro> libros= new ArrayList<>();
                System.out.println(libros);



                Arrays autor = datosLibro.get().autores();
                for (Autor autor1 ::autor ) {
                    System.out.println("nombre:" + autor1.nombre());
                    System.out.println("Nacimientos:" + autor1.nacimiento());
                    System.out.println("Fallecimiento:" + autor1.deceso());
                    var edad = autor1.nacimiento().intValue() - autor1.deceso().intValue();
                    System.out.println("Edad del Autor" + edad);
                }

                try {
                    List<Libro> librosEncontrados = datosLibro.stream()
                            .map(libros -> new Libro(libros)).collect(Collectors.toList());
                    Autor autorLibro = datosLibro.stream()
                            .flatMap(libros -> libros.autores().stream()
                                    .map(autor2 -> new Autor(autor2))).collect(Collectors.toList())
                            .stream().findFirst().get();
                    Optional<Autor> buscarAutorenBD = repositorioautor.findByNombreContainsIgnoreCase(datosLibro.get().autores().stream()
                            .map(autor2 -> autor2.nombre())
                            .collect(Collectors.joining()));
                    Optional<Libro> buscarLibroBD = repositorioautor.buscarLibroPorNombre(textoBusqueda);
                    if (buscarLibroBD.isPresent()) {
                        System.out.println(" El libro ya esta en la base de datos");
                    } else {
                        Autor autorsalvarBase;
                        if (buscarAutorenBD.isPresent()) {
                            autorsalvarBase = buscarAutorenBD.get();
                            System.out.println(" El autor ya esta en la base de datos");
                        } else {
                            autorsalvarBase = autorLibro;
                            repositorioautor.save(autorsalvarBase);
                        }
                        autorsalvarBase.setLibro(librosEncontrados);
                        repositorioautor.save(autorsalvarBase);


                    }


                } catch (Exception e) {
                    System.out.println("Warning! " + e.getMessage());
                }
            } else {
                System.out.println("Libro no encontrado!");
            }

        }
    }
    public void guardarLibroAutor(InfoLibroAutor infoLibroAutor ) {
        if (infoLibroAutor != null) {
            System.out.println("en el metodo"+infoLibroAutor.getAutor());
            repositorioautor.save(infoLibroAutor.getAutor());
            infoLibroAutor.getLibro().setAutor(infoLibroAutor.getAutor());
            repositoriolibro.save(infoLibroAutor.getLibro());
            System.out.println("en el metodo"+infoLibroAutor.getLibro());
        }
    }

    public void BusquedaInicialArray() {
        System.out.println("Escribe el titulo del libro que quieres buscar");
        var textoBusqueda = input.nextLine();
        // textoBusqueda = "A Room with a View";
        System.out.println(direccion_URL + textoBusqueda.replace(" ", "+"));
        var json = consumoApi.obtenerDatos(direccion_URL + textoBusqueda.replace(" ", "+"));

        Resultado librosEncontrados = conversor.obtenerDatos(json, Resultado.class);

        if (json.isEmpty() || !json.contains("\"count\":0,\"next\":null,\"previous\":null,\"results\":[]")) {


            System.out.println(librosEncontrados);
            List<Libro> librosParaBase = new ArrayList<>();
            librosParaBase = librosEncontrados.resultados().stream()
                    .map(Libro::new)
                    .collect(Collectors.toList());
            // System.out.println(librosParaBase);
            for (Libro libros : librosParaBase) {
                System.out.println(libros);
            }
            List<Autor> autorParaBase = new ArrayList<>();
            autorParaBase = librosEncontrados.resultados().stream()
                    .flatMap(libros -> libros.autores().stream()
                            .map(autor -> new Autor(autor)))
                    .collect(Collectors.toList());
            for (Autor autorBD : autorParaBase) {
                //  System.out.println(autorBD);
            }

            List<InfoLibroAutor> listaLibroAutores = new ArrayList<>();


            for (int i = 0; i < librosParaBase.size(); i++) {
                InfoLibroAutor infoLibroAutor = new InfoLibroAutor(librosParaBase.get(i), autorParaBase.get(i));
                listaLibroAutores.add(infoLibroAutor);
            }
            //  listaLibroAutores.forEach(System.out::println);

            for (InfoLibroAutor libroAutor : listaLibroAutores) {
                System.out.println( libroAutor.getAutor().getNombre());
                System.out.println( libroAutor.getLibro().getTitulo());
                Optional<Autor> buscarAutorenBD = repositorioautor.findByNombreContainsIgnoreCase(libroAutor.getAutor().getNombre());
                Optional<Libro> buscarLibroBD = repositoriolibro.findByTituloContainsIgnoreCase(libroAutor.getLibro().getTitulo());

                try {
                    if (buscarLibroBD.isPresent()) {
                        System.out.println(" El libro ya esta en la base de datos");
                    } else {

                        if (buscarAutorenBD.isPresent()) {
                            Libro libronuevo;
                            libronuevo = libroAutor.getLibro();
                            libronuevo.setAutor(buscarAutorenBD.get());
                            System.out.println(libronuevo);
                            repositoriolibro.save(libronuevo);
                            System.out.println(" El autor ya sera en la base de datos");
                        } else {
                            //  System.out.println("autor no presente");
                            Autor autornuevo= libroAutor.getAutor();
                            Libro libronuevo = libroAutor.getLibro();
                            //  System.out.println("22222222222222"+libronuevo);
                            // System.out.println("11111111111111"+autornuevo);
                            InfoLibroAutor infoLibroAutor = new InfoLibroAutor(libronuevo, autornuevo);
                            guardarLibroAutor(infoLibroAutor);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Atencion! " + e.getMessage());
                }
//

            }
        } else {
            System.out.println("Libro no encontrado!");
        }
    }


    // metodo desahabilitado
    public void BusquedaLibros() {
//        textoBusqueda = "quijote";
        System.out.println(direccion_URL + textoBusqueda.replace(" ", "+"));
        var json = consumoApi.obtenerDatos(direccion_URL + textoBusqueda.replace(" ", "+"));

        Resultado librosEncontrados = conversor.obtenerDatos(json, Resultado.class);
        System.out.println(librosEncontrados);
        List<Libro> librosParaBase = new ArrayList<>();
        librosParaBase = librosEncontrados.resultados().stream()
                .map(Libro::new)
                .collect(Collectors.toList());
        System.out.println(librosParaBase);
        for (Libro libros : librosParaBase) {
            System.out.println(libros);
        }
        List<Autor> autorParaBase = new ArrayList<>();
        autorParaBase = librosEncontrados.resultados().stream()
                .flatMap(libros -> libros.autores().stream()
                        .map(autor -> new Autor(autor)))
                .collect(Collectors.toList());
        for (Autor autor : autorParaBase) {
            System.out.println(autor);
        }


    }

    public void buscarAutorPorNombre() {
        System.out.println("Escribe el nombre del autor");
        var nombreAutor = input.nextLine();

        Optional<Autor> autorBuscado = repositorioautor.findByNombreContainsIgnoreCase(nombreAutor);
        if (autorBuscado.isPresent()) {
            System.out.println(autorBuscado.get());
        }

    }

    public void listarLibrosRegistrados() {
        System.out.println("Lista de libros registrados");
        List<Libro> libros = repositorioautor.buscarTodosLosLibros();
        System.out.println(libros);

    }

    public void listarAutoresRegistrados() {
        System.out.println("Lista de autores registrados");
        List<Autor> autores = repositorioautor.findAll();
        System.out.println(autores);

    }

    public void listarAutoresVivos() {
        System.out.println("Por favor escribe el año en cuestion");
        var anio = input.nextInt();
        List<Autor> autores = repositorioautor.buscarAutoresVivos(anio);
        System.out.println(autores);
        for (Autor autor :autores)
        {
            System.out.println("nombre:"+autor.getNombre());
            var edad=anio-autor.getNacimiento();
            System.out.println("tenia esta "+edad+" en ese año");


        }
    }




    public void top10Libros () {
        System.out.println("""
        TOP 10 LIBROS MÁS BUSCADOS 
                            """);
        List<Libro> libros = repositoriolibro.findTop10ByOrderByDescargasDesc();
        System.out.println();
        libros.forEach(l -> System.out.println(

                "\nTítulo: " + l.getTitulo() +
                        "\nAutor: " + l.getAutor().getNombre() +
                        "\nNúmero de descargas: " + l.getDescargas()));
    }
    public void generarEstadisticas () {
        System.out.println("""
               GENERAR ESTADÍSTICAS 
                     """);
        var json = consumoApi.obtenerDatos(direccion_URL);
        var datos = conversor.obtenerDatos(json, Resultado.class);
        IntSummaryStatistics estadisticas = datos.resultados().stream()
                .filter(libros -> libros.descargas()>0)
                .collect(Collectors.summarizingInt(Libro::descargas));
//
        Integer media = (int) estadisticas.getAverage();

        System.out.println("Media de descargas: " + media);
        System.out.println("Máxima de descargas: " + estadisticas.getMax());
        System.out.println("Mínima de descargas: " + estadisticas.getMin());
        System.out.println("Total registros para calcular las estadísticas: " + estadisticas.getCount());

    }
}

