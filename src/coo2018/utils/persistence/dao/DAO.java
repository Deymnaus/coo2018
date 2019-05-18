package coo2018.utils.persistence.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;


public abstract class DAO<T> {
  protected Connection connect = null;
   
  public DAO(){
  }
   
  /**
  * Méthode de création
  * @param obj
  * @return boolean 
 * @throws Exception 
  */
  public abstract boolean create(T obj) throws Exception;

  /**
  * Méthode pour effacer
  * @param obj
  * @return boolean 
  */
  public abstract boolean delete(T obj) throws Exception;

  /**
  * Méthode de mise à jour
  * @param obj
  * @return boolean
  */
  public abstract boolean update(T obj);

  /**
  * Méthode de recherche des informations
  * @param id
  * @return T
  */
  public abstract T find(String id);
  
  public abstract List<T> findAll() throws Exception;
  
}