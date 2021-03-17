package mainclasses.database;

import auxiliar.DatabaseConnection;

import mainclasses.entity.Company;
import mainclasses.entity.School;
import mainclasses.proposal.Proposal;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 * Grupo Individual Sprint 3 2021 - Carlos Masana
 * Clase ProposalDB: Simula una base de datos con las propuestas
 */
public class ProposalDB {
    // ArrayList que simula la base de datos
    private final ArrayList<Proposal> listaPropuestas = new ArrayList<Proposal>();

    // Constructor vacío
    public ProposalDB() {
        this.getProposalsTable();
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

    /**
     * Permite transformar un arraylist en un array 2d de Strings
     * (es necesario para cargar los datos del arraylist en el JTable)
     * @return devuelve un array de Strings
     */
    public String[][] listProposalsObject() {
        String[][] array = new String[sizeProposalDB()][5];

        for (int i = 0; i < sizeProposalDB(); i ++) {
            array[i][0] = String.valueOf(getProposalFromDB(i).getIdProposal());
            array[i][1] = getProposalFromDB(i).getTitle();
            array[i][2] = getProposalFromDB(i).getDescription();
            array[i][3] = String.valueOf(getProposalFromDB(i).getStartDate());
            array[i][4] = String.valueOf(getProposalFromDB(i).getEntity());
        }

        return array;
    }

    /**
     * Permite cargar un ResultSet con los datos de la bbdd en el arraylist de esta clase
     */
    private void getProposalsTable() {
        String sql = "SELECT p.idproposal, p.title, p.description, p.startdate, e.id, e.entityname, e.city, e.phone, e.cif, e.territorialid " +
                     "FROM PROPOSALS AS p " + "INNER JOIN ENTITIES AS e " +
                     "ON p.identity = e.id " +
                     "WHERE status = 'active'" +
                     "ORDER BY p.idproposal ASC";

        String title, description, entityName, city, territorialId, cif;
        int idProposal, phone, idSchool, idCompany;
        Date date;


        // Try-with-resources Statement: Se realiza el close() automaticamente
        try(Statement stmt = DatabaseConnection.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            Proposal proposal;
            School school;
            Company company;

            while (rs.next()) {
                // Si el cif es nulo, la entidad es una escuela
                if (rs.getString("cif") == null) {
                    /*
                    Con el inner join podemos obtener un ResultSet más completo y mostrar así el objeto de School
                    en la JTable
                     */
                    idProposal = rs.getInt("idproposal");
                    title = rs.getString("title");
                    description = rs.getString("description");
                    date = rs.getDate("startdate");

                    idSchool = rs.getInt("id");
                    entityName = rs.getString("entityname");
                    city = rs.getString("city");
                    phone = rs.getInt("phone");
                    territorialId = rs.getString("territorialid");

                    school = new School(idSchool, entityName, city, phone, territorialId);
                    proposal = new Proposal(idProposal, title, description, date, school);

                    this.addProposal(proposal);
                } else {
                    idProposal = rs.getInt("idproposal");
                    title = rs.getString("title");
                    description = rs.getString("description");
                    date = rs.getDate("startdate");

                    idCompany = rs.getInt("id");
                    entityName = rs.getString("entityname");
                    city = rs.getString("city");
                    phone = rs.getInt("phone");
                    cif = rs.getString("cif");

                    company = new Company(idCompany, entityName, city, phone, cif);
                    proposal = new Proposal(idProposal, title, description, date, company);

                    this.addProposal(proposal);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
