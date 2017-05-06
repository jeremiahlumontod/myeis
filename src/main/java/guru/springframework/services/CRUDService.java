package guru.springframework.services;

import java.util.List;

/**
 * Created by jeremiahlumontod on 4/23/17.
 */

public interface CRUDService<T> {
    List<?> listAll();
    T getById(Integer id);
    T saveOrUpdate(T domainObject);
    void delete(Integer id);
}

