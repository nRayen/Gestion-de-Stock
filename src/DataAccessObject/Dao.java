package DataAccessObject;

import java.util.List;

import Stock.Fournisseur;

public interface Dao<T> {
    Fournisseur get(int id);

    List<T> getAll();

    void save(T t);

    void update(T t, String[] params);

    void delete(T t);
}
