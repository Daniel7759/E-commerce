package com.example.services;

import com.example.exceptions.ResourceNotFoundException;
import com.example.repositories.Identifable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class GenericServicesImpl<T, ID, R extends JpaRepository<T, ID>> implements GenericService<T, ID>{

    protected final R repository;

    public GenericServicesImpl(R repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public T insert(T entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public T update(T entity) {
        if (entity instanceof Identifable){
            Identifable<ID> identifable = (Identifable<ID>) entity;
            if (!repository.existsById(identifable.getId())){
                String entityName = entity.getClass().getSimpleName();
                throw new ResourceNotFoundException(entityName+" not found with id: " + identifable.getId());
            }
        }
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void delete(ID id, Class<T> entityClass) {
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException(entityClass.getSimpleName()+" not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public T findById(ID id, Class<T> entityClass) {
        return repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(entityClass.getSimpleName()+" not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}
