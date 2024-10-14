package ma.projet.dao;

import java.util.List;


public interface IDao <T>{
    boolean create (T o);
    T findById(int id);
    List<T> findAll();
    
}
