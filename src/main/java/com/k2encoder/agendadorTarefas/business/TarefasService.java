package com.k2encoder.agendadorTarefas.business;

import com.k2encoder.agendadorTarefas.business.dto.TarefasDTO;
import com.k2encoder.agendadorTarefas.business.mapper.TarefasConverter;
import com.k2encoder.agendadorTarefas.infrastructure.entity.TarefasEntity;
import com.k2encoder.agendadorTarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.k2encoder.agendadorTarefas.infrastructure.repository.TarefasRepository;
import com.k2encoder.agendadorTarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefa(String token, TarefasDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefasConverter.paraTarefaEntity(dto);

        return tarefasConverter.paraTarefaDTO(
                tarefasRepository.save(entity)
        );
    }
}
