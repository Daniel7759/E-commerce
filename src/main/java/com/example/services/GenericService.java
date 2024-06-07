package com.example.services;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, ID> {
    T insert(T entity);
    T update(T entity);
    void delete(ID id, Class<T> entityClass);
    T findById(ID id, Class<T> entityClass);
    List<T> findAll();
}
