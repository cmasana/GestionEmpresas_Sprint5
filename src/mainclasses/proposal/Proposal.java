package mainclasses.proposal;

import mainclasses.entity.Entity;

import java.util.Date;

/**
 * Grupo Individual Sprint 3 2020 - Carlos Masana -
 *
 * Clase Proposal: Define los atributos y métodos de la clase Proposal
 */
public class Proposal {
    private String name;
    private String description;
    private Date startDate;
    private Entity entity;

    public Proposal(String name, String description, Date startDate, Entity entity) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "Título: " + name + " | " +
                "Descripción: " + description + " | " +
                "Fecha de inicio: " + startDate + " | " +
                "Entidad asociada: " + entity.getNombre() + " | " ;
    }

    // Getters & Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

}
