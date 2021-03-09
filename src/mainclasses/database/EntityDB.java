package mainclasses.database;

import mainclasses.entity.Company;
import mainclasses.entity.Entity;
import mainclasses.entity.School;

import java.util.ArrayList;

/**
 * Grupo Individual Sprint 3 2021 - Carlos Masana
 * Clase EntityDB: Simula una base de datos con objetos de tipo Entidad
 */
public class EntityDB {

    // ArrayList con entidades
    private static final ArrayList<Entity> LISTA = new ArrayList<Entity>();

    // Constructor
    public EntityDB() {
        LISTA.add(new School("IES Montsià", "Amposta",977700043, "43006101"));
        LISTA.add(new School("IES Eduardo Blanco Amor", "Ourense",988219843, "32008941"));
        LISTA.add(new Company("Aluminis Jovi SL", "La Ràpita",977744089, "B43437276"));
        LISTA.add(new Company("Cabreiroá SA", "Verín",988590015, "A32004194"));
    }

    // Getter
    public ArrayList<Entity> getLISTA() {
        return LISTA;
    }
}


