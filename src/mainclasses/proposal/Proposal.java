package mainclasses.proposal;

import mainclasses.entity.Entity;

import java.util.Date;

/**
 * Grupo Individual Sprint 3 2020 - Carlos Masana -
 *
 * Clase Proposal: Define los atributos y métodos de la clase Proposal
 */
public class Proposal {
    private int idProposal;
    private String title;
    private String description;
    private Date startDate;
    private Entity entity;

    public Proposal() {

    }

    public Proposal(int idProposal, String title, String description, Date startDate, Entity entity) {
        this.idProposal = idProposal;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "Título: " + title + " | " +
                "Descripción: " + description + " | " +
                "Fecha de inicio: " + startDate + " | " +
                "Entidad asociada: " + entity.getNombre() + " | " ;
    }

    // Getters & Setters


    public int getIdProposal() {
        return idProposal;
    }

    public void setIdProposal(int idProposal) {
        this.idProposal = idProposal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
