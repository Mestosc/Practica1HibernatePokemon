# Practica 1 Hibernate: Pokemon
¡Bienvenido a la Pokédex con Hibernate! trainer Este proyecto es una aplicación de consola que utiliza Hibernate para gestionar una base de datos de Pokémon, entrenadores y entradas de la Pokédex. 

## 🚀 Funcionalidades Clave

*   **Gestión de Entidades:** 📝 Crea, lee, actualiza y elimina (CRUD) Pokémon, entrenadores y entradas de la Pokédex.
*   **Relaciones:** 🔗 Maneja relaciones entre las entidades, como la asignación de un Pokémon a un entrenador o la vinculación de un Pokémon a una entrada de la Pokédex.
*   **Serialización:** 💾 Serializa y deserializa objetos de la Pokédex a archivos `.dat`.
*   **Exportación a XML:** 📄 Exporta datos de entrenadores a archivos `.xml`.
*   **Configuración de Hibernate:** ⚙️ Utiliza un archivo de configuración de Hibernate (`properties.xml`) para gestionar la conexión a la base de datos.

## 🔧 ¿Cómo Funciona?

El proyecto sigue una arquitectura de tres capas:

1.  **Capa de Modelo:** 📦 Define las entidades de la aplicación (`Adestrador`, `Pokedex`, `Pokemon`) utilizando anotaciones de JPA.
2.  **Capa de Servicios:** 🛠️ Contiene la lógica de negocio para cada entidad (`AdestradorService`, `PokedexService`, `PokemonService`).
3.  **Capa de Configuración:** ⚙️ Configura la conexión a la base de datos y la sesión de Hibernate (`HibernateConfig`).

El archivo `Main.java` actúa como punto de entrada de la aplicación, donde se inicializan los servicios y se realizan las operaciones de ejemplo.

## 🏁 Empezando

Para ejecutar el proyecto, sigue estos pasos:

1.  **Configura la Base de Datos:** 💾 Asegúrate de tener una base de datos PostgreSQL en funcionamiento y actualiza el archivo `resources/properties.xml` con tus credenciales.
2.  **Ejecuta la Aplicación:** ▶️ Ejecuta el método `main` en la clase `Main.java`.

## 🛠️ Tecnologías Utilizadas

*   **Java:** ☕ El lenguaje de programación principal.
*   **Hibernate:** 🐘 El framework de mapeo objeto-relacional (ORM) para la persistencia de datos.
*   **PostgreSQL:** 🐘 La base de datos utilizada para almacenar los datos.

Y alguna cosa más