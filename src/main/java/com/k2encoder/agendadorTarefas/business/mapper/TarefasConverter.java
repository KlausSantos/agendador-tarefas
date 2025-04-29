package com.k2encoder.agendadorTarefas.business.mapper;

import com.k2encoder.agendadorTarefas.business.dto.TarefasDTO;
import com.k2encoder.agendadorTarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefasConverter {

    TarefasEntity paraTarefaEntity (TarefasDTO dto);
    TarefasDTO paraTarefaDTO (TarefasEntity entity);
}
