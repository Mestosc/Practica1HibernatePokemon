package services;

import config.HibernateConfig;
import model.Pokedex;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
            //e.printStackTrace();
            System.out.println("Error al crear una entrada " + e.getMessage());
        }
    }
    public void serializarEntradasPokedex(String fichero,Pokedex...pokedexEntries) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fichero))) {
            objectOutputStream.writeInt(pokedexEntries.length);
            for (Pokedex entry : pokedexEntries) {
                objectOutputStream.writeObject(entry);
            }
        } catch (IOException f) {
            System.out.println("Probel " + f.getMessage());
        }
    }
    public ArrayList<Pokedex> leerEntradasSerializadas(String fichero,int cantidadLeer) {
        ArrayList<Pokedex> entradasPokedex = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fichero))) {
            int longitud = objectInputStream.readInt();
            for (int i = 0;i<Math.min(longitud,cantidadLeer);i++) {
                entradasPokedex.add((Pokedex) objectInputStream.readObject());
            }
        } catch (IOException e) {
            System.out.println("Cosas problemas " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Otros problemas " + e.getMessage());
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
    public void actualizarEntrada(Pokedex pokedex) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(pokedex);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Fallo " + e.getMessage());
        }
    }
    public void eliminarEntrada(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Pokedex pokedex = session.get(Pokedex.class,id);
            if (pokedex!=null) {
                session.delete(pokedex);
            }
            transaction.commit();;
        }
    }
}
