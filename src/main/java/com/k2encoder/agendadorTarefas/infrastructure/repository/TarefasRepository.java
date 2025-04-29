package com.k2encoder.agendadorTarefas.infrastructure.repository;

import com.k2encoder.agendadorTarefas.infrastructure.entity.TarefasEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefasRepository extends MongoRepository<TarefasEntity, String> {
}
