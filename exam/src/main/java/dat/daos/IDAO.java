package dat.daos;

import dat.dtos.TripDTO;
import dat.enums.TripCategory;

import java.util.List;

public interface IDAO<T> {

    T create(T dto);

    List<T> getAll();

    T getById(int id);

    T update(int id, T dto);

    boolean delete(int id);

    List<T> getTripsByCategory(TripCategory category);

}
