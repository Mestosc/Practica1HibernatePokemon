import model.Adestrador;
import model.Pokedex;
import model.Pokemon;
import services.AdestradorService;
import services.PokedexService;
import services.PokemonService;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Aquello que requiera introducir informacion debera ser descomentado previamente para el uso lo hice así por comodidad

        // Inicialización de los servicios para interactuar con la base de datos
        PokedexService pokedexService = new PokedexService();
        AdestradorService adestradorService = new AdestradorService();
        PokemonService pokemonService = new PokemonService();

        // Creación de un array de objetos Pokedex con datos iniciales
        Pokedex[] pokedex = {
                new Pokedex("Bulbasaur", new BigDecimal("6.90"), "Planta/Veneno"),
                new Pokedex("Charmander", new BigDecimal("8.50"), "Fuego"),
                new Pokedex("Squirtle", new BigDecimal("9.00"), "Agua"),
                new Pokedex("Pikachu", new BigDecimal("6.00"), "Eléctrico"),
                new Pokedex("Jigglypuff", new BigDecimal("5.50"), "Normal/Hada"),
                new Pokedex("Meowth", new BigDecimal("4.20"), "Normal"),
                new Pokedex("Psyduck", new BigDecimal("19.60"), "Agua"),
                new Pokedex("Machop", new BigDecimal("19.50"), "Lucha"),
                new Pokedex("Geodude", new BigDecimal("20.00"), "Roca/Tierra"),
                new Pokedex("Eevee", new BigDecimal("6.50"), "Normal")
        };
        // === ARRAY DE ADESTRADORES ===
        // Creación de un array de objetos Adestrador con datos iniciales
        Adestrador[] adestradores = {
                new Adestrador("O", Date.valueOf("2006-03-22")),
                new Adestrador("Emni", Date.valueOf("1998-07-09"))
        };

        System.out.println("Insertando datos");
        // Bucle para insertar cada entrada de Pokedex en la base de datos
        for (Pokedex pokedexEntry : pokedex) {
            System.out.println("Insertando entrada " + pokedexEntry + " na tabla pokedex");
            pokedexService.crearEntrada(pokedexEntry);
        }
        // Bucle para insertar cada Adestrador en la base de datos
        for (Adestrador adestrador : adestradores) {
            System.out.println("Insertando adestrador " + adestrador + " en tabla adestrador");
            adestradorService.crearAdestrador(adestrador);
        }

        // Obtención de todas las entradas de Pokedex y Adestradores de la base de datos
        List<Pokedex> entradasPokedex = pokedexService.listarPokedex();
        List<Adestrador> adestradores1 = adestradorService.obtenerAdestradores();

        // === ARRAY DE POKEMON ===
        // Creación de un array de objetos Pokemon con datos iniciales, relacionándolos con Pokedex y Adestradores existentes
        Pokemon[] pokemons = {
                new Pokemon("Bulby", Date.valueOf("2020-03-10"), entradasPokedex.get(0), adestradores1.get(0)),
                new Pokemon("Flamy", Date.valueOf("2021-07-21"), entradasPokedex.get(1), adestradores1.get(0)),
                new Pokemon("Shell", Date.valueOf("2022-02-14"), entradasPokedex.get(2), adestradores1.get(1)),
                new Pokemon("Sparky", Date.valueOf("2023-05-03"), entradasPokedex.get(3), adestradores1.get(0)),
                new Pokemon("Jiggly", Date.valueOf("2021-10-10"), entradasPokedex.get(4), adestradores1.get(1)),
                new Pokemon("Meow", Date.valueOf("2019-06-06"), entradasPokedex.get(5), adestradores1.get(0)),
                new Pokemon("Duckster", Date.valueOf("2020-12-01"), entradasPokedex.get(6), adestradores1.get(1)),
                new Pokemon("Chop", Date.valueOf("2022-08-20"), entradasPokedex.get(7), adestradores1.get(0)),
                new Pokemon("Rocky", Date.valueOf("2023-03-03"), entradasPokedex.get(8), adestradores1.get(1)),
                new Pokemon("Eon", Date.valueOf("2021-01-01"), entradasPokedex.get(9), adestradores1.get(0)),
                new Pokemon("Charmy", Date.valueOf("2020-09-15"), entradasPokedex.get(1), adestradores1.get(1)),
                new Pokemon("Geo", Date.valueOf("2018-11-30"), entradasPokedex.get(8), adestradores1.get(0))
        };

        // Bucle para insertar cada Pokemon en la base de datos
        for (Pokemon pokemon : pokemons) {
            System.out.println("Insertando en tabla pokemon o pokemon " + pokemon);
            pokemonService.crearPokemon(pokemon);
        }
        System.out.println("Insercion de datos finalizada");

        // Impresión de todas las entradas de Pokedex y Adestradores
        for (Pokedex entradaPokedex : entradasPokedex) {
            System.out.println(entradaPokedex);
        }

        for (Adestrador adestrador : adestradores1) {
            System.out.println(adestrador);
        }

        // --- Operaciones con Pokedex ---
        // Lectura de entradas específicas de Pokedex por ID
        Pokedex pokedexEntry1 = pokedexService.leerEntradaPokedex(10L);
        Pokedex pokedexEntry2 = pokedexService.leerEntradaPokedex(4L);

        // Serialización de dos entradas de Pokedex a un archivo
        pokedexService.serializarEntradasPokedex("pokedexSerializada.dat",pokedexEntry1,pokedexEntry2);
        System.out.println("Mostrando datos serializados");
        // Deserialización de entradas de Pokedex desde un archivo
        ArrayList<Pokedex> entradasSerializadas = pokedexService.leerEntradasSerializadas("pokedexSerializada.dat",2);

        // Modificación de los pesos de las entradas deserializadas
        entradasSerializadas.getFirst().setPeso(new BigDecimal("4.50"));
        entradasSerializadas.get(1).setPeso(new BigDecimal("5.00"));

        System.out.println("Listando entradas serializadas");
        // Impresión de las entradas deserializadas y modificadas
        for (Pokedex entrada : entradasSerializadas) {
            System.out.println(entrada);
        }

        // Actualización de las entradas de Pokedex en la base de datos con los nuevos pesos
        pokedexService.actualizarEntrada(entradasSerializadas.getFirst());
        pokedexService.actualizarEntrada(entradasSerializadas.get(1));
        // Fin Cosas Pokedex

        // --- Operaciones con Adestrador ---
        // Obtención de Adestradores por ID
        Adestrador adestrador1 = adestradorService.obtenerAdestradorId(1L);
        Adestrador adestrador2 = adestradorService.obtenerAdestradorId(2L);

        // Serialización de Adestradores a un archivo XML
        adestradorService.toXML("adestradores.xml",adestrador1,adestrador2);

        // Modificación de los datos de los Adestradores
        adestrador2.setNacemento(Date.valueOf("1999-09-01"));
        adestrador1.setNome("Om");
        // Actualización de los Adestradores en la base de datos
        adestradorService.actualizarAdestrador(adestrador1);
        adestradorService.actualizarAdestrador(adestrador2);

        System.out.println("Listando datos de tablas");
        // Obtención y listado de todos los Pokemon
        List<Pokemon> pokemons1 = pokemonService.obtenerTodosPokemon();
        for (Pokemon pokemon : pokemons1) {
            System.out.println(pokemon);
        }
        // Serialización de todos los Pokemon a un archivo XML
        pokemonService.toXML("pokemons.xml",pokemons1);
        // Listado de todas las entradas de Pokedex
        for (Pokedex pokedexEntry : pokedexService.listarPokedex()) {
            System.out.println(pokedexEntry);
        }
        // Lectura de Adestradores desde un archivo XML
        ArrayList<Adestrador> adestradorsXML = adestradorService.leerXML("adestradores.xml");
        // Actualización de los Adestradores leídos desde el XML en la base de datos
        for (Adestrador adestrador : adestradorsXML) {
            adestradorService.actualizarAdestrador(adestrador);
        }
    }
}