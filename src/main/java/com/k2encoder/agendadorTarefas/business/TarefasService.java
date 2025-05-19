package com.k2encoder.agendadorTarefas.business;

import com.k2encoder.agendadorTarefas.business.dto.TarefasDTO;
import com.k2encoder.agendadorTarefas.business.mapper.TarefasConverter;
import com.k2encoder.agendadorTarefas.business.mapper.TarefasUpdateConverter;
import com.k2encoder.agendadorTarefas.infrastructure.entity.TarefasEntity;
import com.k2encoder.agendadorTarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.k2encoder.agendadorTarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.k2encoder.agendadorTarefas.infrastructure.repository.TarefasRepository;
import com.k2encoder.agendadorTarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;
    private final TarefasUpdateConverter tarefasUpdateConverter;

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

    public List<TarefasDTO> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return tarefasConverter.paraListaTarefaDTO(
                tarefasRepository.findByDataEventoBetween(dataInicial, dataFinal));
    }

    public List<TarefasDTO> buscaTarefasPorEmail(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        return tarefasConverter.paraListaTarefaDTO(
                tarefasRepository.findByEmailUsuario(email)
        );
    }

    public void deletaTarefaPorID(String id) {
        try {
            tarefasRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao deletar, ID inexistente: " + id, e.getCause());
        }
    }

    public TarefasDTO alteraStatus(StatusNotificacaoEnum status, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Tarefa inexistente, id: " + id));
            entity.setStatusNotificacaoEnum(status);
            return tarefasConverter.paraTarefaDTO(tarefasRepository.save(entity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa: " + e.getCause());
        }
    }

    public TarefasDTO updateTarefas(TarefasDTO dto, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Tarefa inexistente, id: " + id));
            tarefasUpdateConverter.updateTarefas(dto, entity);
            return tarefasConverter.paraTarefaDTO(tarefasRepository.save(entity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao atualizar a tarefa: " + e.getCause());
        }
    }
}
