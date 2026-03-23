package org.example.datalayer.mapper.statistics;

import org.example.datalayer.dto.statistics.GameStatsDTO;
import org.example.datalayer.dto.statistics.AttemptStatsDTO;
import org.example.domain.statistics.GameStatistics;
import org.example.domain.statistics.AttemptStatistics;

import java.util.List;
import java.util.stream.Collectors;

public class GameStatsMapper {

    private final AttemptStatsMapper attemptMapper = new AttemptStatsMapper();

    public GameStatsDTO toDto(GameStatistics stats) {
        if (stats == null) return null;

        List<AttemptStatsDTO> attemptDTOs = stats.getAttempts().stream()
                .map(attemptMapper::toDto)
                .collect(Collectors.toList());

        return new GameStatsDTO(attemptDTOs);
    }

    public GameStatistics toEntity(GameStatsDTO dto) {
        if (dto == null) return null;

        GameStatistics stats = new GameStatistics();

        List<AttemptStatistics> attempts = dto.getAttempts().stream()
                .map(attemptMapper::toEntity)
                .collect(Collectors.toList());

        stats.setAttempts(attempts);

        return stats;
    }
}