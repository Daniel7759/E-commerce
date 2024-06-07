package com.example.services;

import com.example.models.MarcaEntity;
import com.example.repositories.MarcaRepository;
import org.springframework.stereotype.Service;

@Service
public class MarcaService extends GenericServicesImpl<MarcaEntity, Long, MarcaRepository>{

    public MarcaService(MarcaRepository repository) {
        super(repository);
    }
}
