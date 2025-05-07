package com.k2encoder.agendadorTarefas.business.mapper;

import com.k2encoder.agendadorTarefas.business.dto.TarefasDTO;
import com.k2encoder.agendadorTarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefasConverter {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "dataEvento", target = "dataEvento")
    @Mapping(source = "dataCriacao", target = "dataCriacao")
    TarefasEntity paraTarefaEntity(TarefasDTO dto);
    TarefasDTO paraTarefaDTO(TarefasEntity entity);
    List<TarefasEntity> paraListaTarefaEntity(List<TarefasDTO> dtos);
    List<TarefasDTO> paraListaTarefaDTO(List<TarefasEntity> entities);
}
