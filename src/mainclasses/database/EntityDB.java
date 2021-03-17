package mainclasses.database;

import auxiliar.DatabaseConnection;
import mainclasses.entity.Company;
import mainclasses.entity.Entity;
import mainclasses.entity.School;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Grupo Individual Sprint 3 2021 - Carlos Masana
 * Clase EntityDB: Simula una base de datos con objetos de tipo Entidad
 */
public class EntityDB {

    // ArrayList con entidades
    private final ArrayList<Entity> LISTA_ENTIDADES = new ArrayList<Entity>();

    // Constructor vacío
    public EntityDB() {
        this.getEntitiesTable();
    }

    // Métodos para realizar operaciones básicas en nuestro ArrayList

    /**
     * Añadir entidad al array list
     * @param ent Objeto de la clase entidad
     */
    public void addEntity(Entity ent) {
        LISTA_ENTIDADES.add(ent);
    }

    /**
     * Elimina una entidad del ArrayList
     * @param posicion posición de la entidad dentro del ArrayList
     */
    public void removeEntity(int posicion) {
        LISTA_ENTIDADES.remove(posicion);
    }

    /**
     * Obtiene una entidad desde el ArrayList
     * @param posicion posición de la entidad dentro del ArrayList
     * @return devuelve un objeto de la clase Entity
     */
    public Entity getEntityFromDB(int posicion) {
        return LISTA_ENTIDADES.get(posicion);
    }

    /**
     * Obtiene el tamaño del ArrayList de Entidades
     * @return devuelve un entero con el tamaño del ArrayList de tipo Entity
     */
    public int sizeEntityDB() {
        return LISTA_ENTIDADES.size();
    }


    /**
     * Transforma ArrayList a array de Entidades
     */
    public Entity[] listEntities() {
        return LISTA_ENTIDADES.toArray(new Entity[sizeEntityDB()]);
    }


    /**
     * Permite cargar un ResultSet con los datos de la bbdd en el arraylist de esta clase
     */
    private void getEntitiesTable() {
        String sql = "SELECT id, entityname, city, phone, cif, territorialid FROM ENTITIES";

        // Try-with-resources Statement: Se realiza el close() automaticamente
        try(Statement stmt = DatabaseConnection.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                if (rs.getString("cif") == null) {
                    School school = new School();
                    school.setIdEntity(rs.getInt("id"));
                    school.setNombre(rs.getString("entityname"));
                    school.setPoblacion(rs.getString("city"));
                    school.setTelefono(rs.getInt("phone"));
                    school.setCodTerritorial(rs.getString("territorialid"));

                    this.addEntity(school);

                } else {
                    Company company = new Company();
                    company.setIdEntity(rs.getInt("id"));
                    company.setNombre(rs.getString("entityname"));
                    company.setPoblacion(rs.getString("city"));
                    company.setTelefono(rs.getInt("phone"));
                    company.setCif(rs.getString("cif"));

                    this.addEntity(company);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


