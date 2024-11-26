package com.github.alvader01.Model.DAO;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T,K> extends Closeable {
    T save(T entity);
    T update(T entity);
    T delete(T entity) throws SQLException;
    T findById(K key);
    List<T> findAll();
}
