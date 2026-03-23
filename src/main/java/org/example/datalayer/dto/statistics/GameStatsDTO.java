package org.example.datalayer.dto.statistics;

import java.util.List;

public class GameStatsDTO {
    private List<AttemptStatsDTO> attempts;

    public GameStatsDTO() {}

    public GameStatsDTO(List<AttemptStatsDTO> attempts) {
        this.attempts = attempts;
    }

    public List<AttemptStatsDTO> getAttempts() { return attempts; }
}