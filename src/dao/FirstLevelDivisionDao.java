package dao;

import javafx.collections.ObservableList;
import sample.FirstLevelDivision;



public interface FirstLevelDivisionDao {

    ObservableList<FirstLevelDivision> getAllDivisions();
    FirstLevelDivision getById(int id);
    int save(FirstLevelDivision division);
    int update(FirstLevelDivision division);
    void delete(int id);

}
