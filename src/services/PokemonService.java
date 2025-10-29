package services;

import config.HibernateConfig;
import model.Adestrador;
import model.Pokedex;
import model.Pokemon;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class PokemonService {
    public void crearPokemon(String nome, Date nacemento, Pokedex pokedexEntry, Adestrador adestrador) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Pokemon pokemon = new Pokemon(nome,nacemento,pokedexEntry,adestrador);
            session.save(pokemon);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Fallo al crear un pokemon");
        }
    }
    public void crearPokemon(Pokemon pokemon) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction trans = session.beginTransaction();
            session.save(pokemon);
            trans.commit();
        }
    }
    public void crearPokemon(String nome, Date nacemento, Long pokedexEntryID, Long adestradorID) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Pokemon pokemon = new Pokemon(nome,nacemento,session.get(Pokedex.class,pokedexEntryID),session.get(Adestrador.class,adestradorID));
            session.save(pokemon);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Fallo al crear un pokemon");
        }
    }
    public void borrarPokemon(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Pokemon pokemon = session.get(Pokemon.class,id);
            session.remove(pokemon);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Ha habido un error borrando al pokemon");
        }
    }
    public Pokemon obtenerPokemonId(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.get(Pokemon.class,id);
        } catch (Exception e) {
            System.out.println("Fallo al encontrar un pokemon");
            return null;
        }
    }
    public Pokemon obtenerPokemonNombre(String nombre) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.get(Pokemon.class,nombre);
        } catch (Exception e) {
            System.out.println("Fallo al encontrar un pokemon");
            return null;
        }
    }
    public void actualizarPokemon(Pokemon pokemon) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(pokemon);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error en la actualizacion de un pokemon");
        }
    }
    public List<Pokemon> obtenerTodosPokemon() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("from Pokemon",Pokemon.class).getResultList();
        } catch (Exception e) {
            System.out.println("Fallo al obtener los pokemon");
            return null;
        }
    }
    public void toXML(String fichero,List<Pokemon> pokemons) {
        try (FileWriter writer = new FileWriter(fichero)) {
            XMLStreamWriter xml = XMLOutputFactory.newInstance().createXMLStreamWriter(writer);
            xml.writeStartDocument("1.0");
            xml.writeStartElement("pokemons");
            for (Pokemon pokemon : pokemons) {
                xml.writeStartElement("pokemon");

                xml.writeStartElement("id");
                xml.writeCharacters(Long.toString(pokemon.getId()));
                xml.writeEndElement();

                xml.writeStartElement("nome");
                xml.writeCharacters(pokemon.getNome());
                xml.writeEndElement();

                xml.writeStartElement("nacemento");
                xml.writeCharacters(pokemon.getNacemento().toString());
                xml.writeEndElement();

                xml.writeStartElement("pokedexEntry");
                xml.writeCharacters(pokemon.getPokedexEntry().getId().toString());
                xml.writeEndElement();

                xml.writeStartElement("adestrador");
                xml.writeCharacters(pokemon.getAdestrador().getId().toString());
                xml.writeEndElement();

                xml.writeEndElement();
            }
            xml.writeEndElement();
        } catch (IOException e) {
            System.out.println("Error en insercion " + e.getMessage());
        } catch (XMLStreamException e) {
            System.out.println("Error en la escritura del XML " + e.getMessage());
        }
    }
    public void toXML(String fichero,Pokemon...pokemons) {
        try (FileWriter writer = new FileWriter(fichero)) {
            XMLStreamWriter xml = XMLOutputFactory.newInstance().createXMLStreamWriter(writer);
            xml.writeStartDocument("1.0");
            xml.writeStartElement("pokemons");
            for (Pokemon pokemon : pokemons) {
                xml.writeStartElement("pokemon");

                xml.writeStartElement("id");
                xml.writeCharacters(Long.toString(pokemon.getId()));
                xml.writeEndElement();

                xml.writeStartElement("nome");
                xml.writeCharacters(pokemon.getNome());
                xml.writeEndElement();

                xml.writeStartElement("nacemento");
                xml.writeCharacters(pokemon.getNacemento().toString());
                xml.writeEndElement();

                xml.writeStartElement("pokedexEntry");
                xml.writeCharacters(pokemon.getPokedexEntry().getId().toString());
                xml.writeEndElement();

                xml.writeStartElement("adestrador");
                xml.writeCharacters(pokemon.getAdestrador().getId().toString());
                xml.writeEndElement();

                xml.writeEndElement();
            }
            xml.writeEndElement();
            xml.writeEndDocument();
        } catch (IOException e) {
            System.out.println("Error en insercion " + e.getMessage());
        } catch (XMLStreamException e) {
            System.out.println("Error en la escritura del XML " + e.getMessage());
        }
    }
}
