package org.example.datalayer.mapper.statistics;

import org.example.datalayer.dto.statistics.AttemptStatsDTO;
import org.example.domain.statistics.AttemptStatistics;

public class AttemptStatsMapper {

    public AttemptStatsDTO toDto(AttemptStatistics stats) {
        if (stats == null) return null;

        AttemptStatsDTO dto = new AttemptStatsDTO();
        dto.setPlayerName(stats.getPlayerName());
        dto.setTreasures(stats.getTreasures());
        dto.setLevel(stats.getLevel());
        dto.setEnemiesKilled(stats.getEnemiesKilled());
        dto.setFoodEaten(stats.getFoodEaten());
        dto.setElixirsDrunk(stats.getElixirsDrunk());
        dto.setScrollsRead(stats.getScrollsRead());
        dto.setHitsDealt(stats.getHitsDealt());
        dto.setHitsTaken(stats.getHitsTaken());
        dto.setStepsTaken(stats.getStepsTaken());

        return dto;
    }

    public AttemptStatistics toEntity(AttemptStatsDTO dto) {
        if (dto == null) return null;

        AttemptStatistics stats = new AttemptStatistics();
        stats.setPlayerName(dto.getPlayerName());
        stats.setTreasures(dto.getTreasures());
        stats.setLevel(dto.getLevel());
        stats.setEnemiesKilled(dto.getEnemiesKilled());
        stats.setFoodEaten(dto.getFoodEaten());
        stats.setElixirsDrunk(dto.getElixirsDrunk());
        stats.setScrollsRead(dto.getScrollsRead());
        stats.setHitsDealt(dto.getHitsDealt());
        stats.setHitsTaken(dto.getHitsTaken());
        stats.setStepsTaken(dto.getStepsTaken());

        return stats;
    }
}