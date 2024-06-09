package challengeliteratura.orcacle.literatura.principal;


import challengeliteratura.orcacle.literatura.model.Autor;
import challengeliteratura.orcacle.literatura.model.Datos;
import challengeliteratura.orcacle.literatura.model.DatosLibro;
import challengeliteratura.orcacle.literatura.model.Libro;
import challengeliteratura.orcacle.literatura.repository.AutoRepository;
import challengeliteratura.orcacle.literatura.repository.LibroRespository;
import challengeliteratura.orcacle.literatura.service.ConsumoApi;
import challengeliteratura.orcacle.literatura.service.ConvierteDatos;


import java.util.*;


public class Principal {
    private ConsumoApi consumoApi = new ConsumoApi();
    private Scanner teclado = new Scanner(System.in);
    private final String URL_BASE = "http://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRespository repositorio;
    private AutoRepository autorRepositorio;
    private List<Libro> libro;

    public Principal(LibroRespository repositorio, AutoRepository autorRepositorio) {
        this.repositorio = repositorio;
        this.autorRepositorio = autorRepositorio;
    }


    public void muestraElMenu() {

        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                ***********************************************************************************
                                                Bienvenido!
                                           Que deseas hacer hoy?
                ***********************************************************************************       
                                        1 - Buscar Libros por Titulo
                                        2 - Buscar Autor por Nombre
                                        3 - Mostrar todos los libros del sitio
                                        4 - Mostrar libros buscados
                                        5 - Listar Autores Vivos
                                        6 - Top 10 Libros más Buscados
                                        7 - Estadisticas de la pagina
                                        0 - Salir
                                        
                ***********************************************************************************
                """;

            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion) {
                case 1:
                    LibroTitulo();
                    break;
                case 2:
                    buscarAutor();
                    break;
                case 3:
                    mostrarLibros();
                    break;
                case 4:
                    librosBuscados();
                    break;
                case 5:
                    listarAutoresVivos();
                    break;
                case 6:
                    top10();
                    break;

                case 0:
                    System.out.println(
                    );
                    break;
                default:
                    System.out.println("Opción no válida!");
                    break;
            }


        }
    }

    private Libro LibroTitulo() {
        System.out.println("Que libro deseas leer hoy?");
        var busqueda = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + busqueda.replace(" ", "+") );
        var resultadoBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibro> libroBuscado = resultadoBusqueda.resultado().stream()
                .filter(l -> l.titulo().toUpperCase().contains(busqueda.toUpperCase()))
                .findFirst();
        if(libroBuscado.isPresent()){
            System.out.println("Libro encontrado!!");
            var libroDato = libroBuscado.get();
            Libro libro = new Libro(libroDato);

            // Verificar si el autor ya existe en la base de datos
            Autor autor = new Autor(libroDato.autor().get(0));
            Optional<Autor> autorExistente = autorRepositorio.findByNombre(autor.getNombre());
            if (autorExistente.isPresent()) {
                libro.setAutor(autorExistente.get());
            } else {
                autorRepositorio.save(autor);
                libro.setAutor(autor);
            }
            // Verificar si el libro ya existe en la base de datos
            Optional<Libro> libroExistente = repositorio.findByTituloContainsIgnoreCase(libro.getTitulo());
            if (libroExistente.isPresent()) {
                System.out.println(libroExistente.get());
            } else {
                repositorio.save(libro);
                System.out.println(libro);
              return libro;
            }

        } else {
            System.out.println("No encontramos el libro que pediste!!");
        }


        return null;
    }


    private void buscarAutor() {
        System.out.println("Que autor deseas buscar?");
        var busqueda = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + busqueda.replace(" ", "+"));
        var resultadoBusqueda = conversor.obtenerDatos(json, Datos.class);

        if (resultadoBusqueda.isPresent()) {
            System.out.println("Autor encontrado!");
            System.out.println(resultadoBusqueda);

        } else {
            System.out.println("No encontramos el autor solicitado!");
        }
    }

    private void mostrarLibros() {
        System.out.println("Bienvenidos a nuestra biblioteca virtual!!");
        var json = consumoApi.obtenerDatos(URL_BASE );
        System.out.println(json);
    }

    private void librosBuscados() {
        libro = repositorio.findAll();
        System.out.println(libro);
        
    }

    private void listarAutoresVivos() {
        System.out.println("Especifique el año que quieres buscar: ");
        var anio = teclado.nextLine();
        var resultado = autorRepositorio.autoresVivosEnDeterminadoAnio(Integer.parseInt(anio));
        if(resultado.isEmpty()) {
            System.out.println("No se encontraron resultados");
        } else {
            System.out.println("Autores vivos en el año "+anio+":");
            List<Autor> autoresDelAño = resultado;
            autoresDelAño.forEach(System.out::println);
        }
    }



    private void top10() {
        System.out.println("Este es nuestro top 10 del mes:");
        List<Libro> toplibro = repositorio.findTop10ByOrderBytotalDescargas();
        System.out.println(toplibro);
    }



}
