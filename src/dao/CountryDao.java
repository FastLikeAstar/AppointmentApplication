package dao;

import javafx.collections.ObservableList;
import sample.Country;



/**
 * This interface follows the Data Access Object (DAO) Pattern for the Countries
 *  entity in the provided Entity Relationship Diagram (ERD).
 */
public interface CountryDao {

    ObservableList<Country> getAllCountries();
    Country getById(int id);
    int save(Country country);
    int update(Country country);
    void delete(int id);

}
