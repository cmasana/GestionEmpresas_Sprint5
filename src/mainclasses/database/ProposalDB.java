package mainclasses.database;

import mainclasses.proposal.Proposal;
import java.util.ArrayList;

/**
 * Grupo Individual Sprint 3 2021 - Carlos Masana
 * Clase ProposalDB: Simula una base de datos con las propuestas
 */
public class ProposalDB {
    // ArrayList que simula la base de datos
    private static ArrayList<Proposal> listaPropuestas;

    // Constructor vacío
    public ProposalDB() {
        listaPropuestas = new ArrayList<Proposal>();
    }


    // Métodos para realizar operaciones básicas en nuestro ArrayList

    /**
     * Añadir propuesta al array list
     * @param proposal Objeto de la clase Proposal
     */
    public void addProposal(Proposal proposal) {
        listaPropuestas.add(proposal);
    }

    /**
     * Elimina una propuesta del ArrayList
     * @param posicion posición de la propuesta dentro del ArrayList
     */
    public void removeProposal(int posicion) {
        listaPropuestas.remove(posicion);
    }

    /**
     * Obtiene una propuesta desde el ArrayList
     * @param posicion posición de la propuesta dentro del ArrayList
     * @return devuelve un objeto de la clase Proposal
     */
    public Proposal getProposalFromDB(int posicion) {
        return listaPropuestas.get(posicion);
    }

    /**
     * Obtiene el tamaño del ArrayList de propuestas
     * @return devuelve un entero con el tamaño del ArrayList de tipo Proposal
     */
    public int sizeProposalDB() {
        return listaPropuestas.size();
    }
}
