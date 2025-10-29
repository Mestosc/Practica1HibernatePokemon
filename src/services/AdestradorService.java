package services;

import config.HibernateConfig;
import model.Adestrador;
import org.hibernate.Session;
import org.hibernate.Transaction;


import javax.xml.namespace.QName;
import javax.xml.stream.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AdestradorService {
    public void crearAdestrador(String nome, Date nacemento) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Adestrador adestrador = new Adestrador(nome,nacemento);
            session.save(adestrador);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Problema al crear adestrador " + e.getMessage());
        }
    }
    public void toXML(List<Adestrador> adestradors,String nombreFichero) {
        try (FileWriter writer = new FileWriter(nombreFichero)) {
            XMLStreamWriter xml = XMLOutputFactory.newDefaultFactory().createXMLStreamWriter(writer);
            xml.writeStartDocument("1.0");
            xml.writeStartDocument("Adestradores");
            for (Adestrador adestrador : adestradors) {
                xml.writeStartDocument("id");
                xml.writeCharacters(String.valueOf(adestrador.getId()));
                xml.writeEndElement();
                xml.writeStartElement("nome");
                xml.writeCharacters(adestrador.getNome());
                xml.writeEndElement();
                xml.writeStartElement("nacemento");
                xml.writeCharacters(adestrador.getNacemento().toString());
                xml.writeEndElement();
            }
            xml.writeEndElement();
            xml.writeEndDocument();
        } catch (IOException e) {
            System.out.println("Error na escritura");
        } catch (XMLStreamException e) {
            System.out.println("Fallo al parsear el XML");
        }
    }
    public void toXML(String nombreFichero,Adestrador...adestradors) {
        try (FileWriter writer = new FileWriter(nombreFichero)) {
            XMLStreamWriter xml = XMLOutputFactory.newDefaultFactory().createXMLStreamWriter(writer);
            xml.writeStartDocument("1.0");
            xml.writeStartElement("adestradores");
            for (Adestrador adestrador : adestradors) {
                xml.writeStartElement("adestrador");

                xml.writeStartElement("id");
                xml.writeCharacters(String.valueOf(adestrador.getId()));
                xml.writeEndElement();

                xml.writeStartElement("nome");
                xml.writeCharacters(adestrador.getNome());
                xml.writeEndElement();

                xml.writeStartElement("nacemento");
                xml.writeCharacters(adestrador.getNacemento().toString());
                xml.writeEndElement();

                xml.writeEndElement();
            }
            xml.writeEndElement();
            xml.writeEndDocument();
        } catch (IOException e) {
            System.out.println("Error na escritura");
        } catch (XMLStreamException e) {
            System.out.println("Fallo al parsear el XML");
        }
    }
    public void crearAdestrador(Adestrador adestrador) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(adestrador);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error en la creacion do adestrador");
        }

    }
    public void actualizarAdestrador(Adestrador adestrador) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(adestrador);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error en actualizar adestradores");
        }
    }
    public void eliminarAdestrador(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Adestrador adestrador = session.get(Adestrador.class,id);
            if (adestrador!=null) {
                session.delete(adestrador);
            } else {
                System.out.println("Non existe adestrador con este ID");
            }
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error en proceso de eliminacion");
        }
    }
    public void eliminarAdestrador(Adestrador adestrador) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(adestrador);
            transaction.commit();
        }
    }
    public List<Adestrador> obtenerAdestradores() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("from Adestrador", Adestrador.class).getResultList();
        } catch (Exception e) {
            System.out.println("Erro en obtener adestradores");
            return null;
        }
    }
    public Adestrador obtenerAdestradorId(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.get(Adestrador.class,id);
        } catch (Exception e) {
            System.out.println("Error en obtener el adestrador");
        }
        return null;
    }
    public ArrayList<Adestrador> leerXML(String fichero) {
        ArrayList<Adestrador> adestradors = new ArrayList<>();
        String elementoActual = null;
        try (FileReader reader = new FileReader(fichero)) {
            XMLStreamReader xmlReader = XMLInputFactory.newDefaultFactory().createXMLStreamReader(reader);
            Adestrador adestrador = null;
            while (xmlReader.hasNext()) {
                int elemento = xmlReader.next();
                switch (elemento) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        String nombreElemento = xmlReader.getLocalName();
                        if (nombreElemento.equals("adestrador")) {
                           adestrador = new Adestrador();
                        } else {
                            elementoActual = nombreElemento;
                        }
                    }
                    case XMLStreamConstants.CHARACTERS -> {
                        if (adestrador==null || elementoActual==null) {
                            continue;
                        }
                        switch (elementoActual) {
                            case "id" -> {
                                adestrador.setId(Long.valueOf(xmlReader.getText()));
                            }
                            case "nome" -> {
                                adestrador.setNome(xmlReader.getText());
                            }
                            case "nacemento" -> {
                                adestrador.setNacemento(Date.valueOf(xmlReader.getText()));
                            }
                        }
                    }
                    case XMLStreamConstants.END_ELEMENT -> {
                        if (xmlReader.getName().equals(QName.valueOf("adestrador"))) {
                            adestradors.add(adestrador);
                        }
                    }
                }
            }
        } catch (XMLStreamException e) {
            System.out.println("Error en relacion al XML " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("Fichero no encontrado " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Problema Input Output");
        }
        return adestradors;
    }
}
