package com.k2encoder.agendadorTarefas.business.mapper;

import com.k2encoder.agendadorTarefas.business.dto.TarefasDTO;
import com.k2encoder.agendadorTarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TarefasUpdateConverter {

    void updateTarefas(TarefasDTO dto, @MappingTarget TarefasEntity entity);
}
