package model;

import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "pokemon")
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "nacemento")
    private Date nacemento;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "pokedexentry",referencedColumnName = "id")
    private Pokedex pokedexEntry;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // Esto es desde la clase en la que estoy no desde la clase atributo a la que corresponde el campo
    @JoinColumn(name = "adestrador",referencedColumnName = "id")
    private Adestrador adestrador;

    public Pokemon() {}

    /**
     * Creacion de una entrada para la tabla de pokemon
     * @param nome El nombre del pokemon
     * @param nacemento la fecha de nacimiento del mismo
     * @param pokedexEntry la entrada de la pokedex con toda su informacion
     * @param adestrador el entrenador de ese pokemon
     */
    public Pokemon(String nome, Date nacemento, Pokedex pokedexEntry, Adestrador adestrador) {
        this.nome = nome;
        this.nacemento = nacemento;
        this.pokedexEntry = pokedexEntry;
        this.adestrador = adestrador;
    }

    public Adestrador getAdestrador() {
        return adestrador;
    }

    public void setAdestrador(Adestrador adestrador) {
        this.adestrador = adestrador;
    }

    public Pokedex getPokedexEntry() {
        return pokedexEntry;
    }

    public void setPokedexEntry(Pokedex pokedexEntry) {
        this.pokedexEntry = pokedexEntry;
    }

    public Date getNacemento() {
        return nacemento;
    }

    public void setNacemento(Date nacemento) {
        this.nacemento = nacemento;
    }

    public String getNome() {
        return nome;
    }
    public Long getId() {
        return id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", nacemento=" + nacemento +
                ", pokedexEntry=" + pokedexEntry +
                ", adestrador=" + adestrador +
                '}';
    }
}
