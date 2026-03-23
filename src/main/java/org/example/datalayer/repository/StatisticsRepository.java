package org.example.datalayer.repository;

import org.example.datalayer.dto.statistics.GameStatsDTO;
import org.example.datalayer.mapper.statistics.GameStatsMapper;
import org.example.datalayer.util.JsonManager;
import org.example.domain.statistics.GameStatistics;
import org.example.domain.statistics.AttemptStatistics;

import java.io.IOException;
import java.util.Optional;

public class StatisticsRepository {
    private final JsonManager jsonManager;
    private final GameStatsMapper statsMapper;
    private static final String STATS_FILE = "game_stats.json";

    public StatisticsRepository() {
        this.jsonManager = new JsonManager("saves");
        this.statsMapper = new GameStatsMapper();
    }

    public void saveStatistics(GameStatistics statistics) throws IOException {
        GameStatsDTO dto = statsMapper.toDto(statistics);
        jsonManager.saveToFile(STATS_FILE, dto);
    }

    public Optional<GameStatistics> loadStatistics() {
        try {
            if (!jsonManager.fileExists(STATS_FILE)) {
                return Optional.of(new GameStatistics());
            }

            GameStatsDTO dto = jsonManager.loadFromFile(STATS_FILE, GameStatsDTO.class);
            GameStatistics stats = statsMapper.toEntity(dto);

            return Optional.of(stats);

        } catch (IOException e) {
            System.err.println("Error loading statistics: " + e.getMessage());
            return Optional.of(new GameStatistics());
        }
    }

    public void addAttempt(AttemptStatistics attempt) throws IOException {
        GameStatistics stats = loadStatistics().orElse(new GameStatistics());
        stats.getAttempts().add(attempt);
        saveStatistics(stats);
    }
}