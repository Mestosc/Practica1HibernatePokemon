package services;

import config.HibernateConfig;
import model.Pokedex;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para realizar operaciones CRUD y alguna otra cosa en la Pokedex
 * @version 1.0
 * @author Oscar
 * @see model.Pokedex
 */
public class PokedexService {
    public void crearEntrada(String nome, BigDecimal peso, String misc) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Pokedex pokedex = new Pokedex();
            pokedex.setNome(nome);
            pokedex.setPeso(peso);
            pokedex.setMisc(misc);
            session.save(pokedex);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error al crear una entrada " + e.getMessage());
        }
    }

    /**
     * Serializar entradas de pokedex
     * @param fichero el fichero en el que quiero serializarlas
     * @param pokedexEntries las entradas que quiero serializar una detras de otra
     */
    public void serializarEntradasPokedex(String fichero,Pokedex...pokedexEntries) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fichero))) {
            objectOutputStream.writeInt(pokedexEntries.length);
            for (Pokedex entry : pokedexEntries) {
                objectOutputStream.writeObject(entry);
            }
        } catch (IOException f) {
            System.out.println("Fallo actualizando entradas de Pokedex " + f.getMessage());
        }
    }

    /**
     * El nombre del archivo serializado en este caso para las entradas de Pokedex
     * @param fichero el fichero que quiero leer
     * @param cantidadLeer la cantidad de datos a obtener
     * @return una lista con las entradas de Pokedex, que queria leer
     */
    public ArrayList<Pokedex> leerEntradasSerializadas(String fichero,int cantidadLeer) {
        ArrayList<Pokedex> entradasPokedex = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fichero))) {
            int longitud = objectInputStream.readInt();
            for (int i = 0;i<Math.min(longitud,cantidadLeer);i++) {
                entradasPokedex.add((Pokedex) objectInputStream.readObject());
            }
        } catch (IOException e) {
            System.out.println("Problemas de I/O al leer serializado " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Clase no encontrada " + e.getMessage());
        }
        return entradasPokedex;
    }
    public void crearEntrada(Pokedex entradaPokedex) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(entradaPokedex);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error en la creacion da entrada na pokedex");
        }
    }
    public Pokedex leerEntradaPokedex(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.get(Pokedex.class, id);
        } catch (Exception e) {
            System.out.println("Error al leer el gato " + e.getMessage());
            return null;
        }
    }
    public List<Pokedex> listarPokedex() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("from Pokedex", Pokedex.class).getResultList();
        } catch (Exception e) {
            System.out.println("Error al leer el gato " + e.getMessage());
            return null;
        }
    }

    /**
     * Actualizar entrada de la pokedex
     * @param pokedex le paso un objeto que represente esa entrada con los datos que quiero cambiados
     */
    public void actualizarEntrada(Pokedex pokedex) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(pokedex);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Fallo al actualizar entrada " + e.getMessage());
        }
    }

    /**
     * Eliminar entrada de la base de datos mediante su identificador unico
     * @param id el identificador unico de la base de datos
     */
    public void eliminarEntrada(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Pokedex pokedex = session.get(Pokedex.class,id);
            if (pokedex!=null) {
                session.delete(pokedex);
            } else {
                System.out.println("No existe la entrada en la base de datos omitiendo...");
            }
            transaction.commit();;
        }
    }
}
