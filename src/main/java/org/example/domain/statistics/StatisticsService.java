package org.example.domain.statistics;

import org.example.datalayer.repository.StatisticsRepository;
import org.example.domain.entity.character.Player;

import java.io.IOException;
import java.util.List;

public class StatisticsService {
    private final StatisticsRepository statsRepository;
    private GameStatistics gameStatistics;

    public StatisticsService() {
        this.statsRepository = new StatisticsRepository();
        this.gameStatistics = statsRepository.loadStatistics().orElse(new GameStatistics());
    }

    public void startNewGame(String playerName) {
        gameStatistics.startNewAttempt(playerName);
    }

    public void endGame() {
        gameStatistics.endCurrentAttempt();
        try {
            statsRepository.saveStatistics(gameStatistics);
        } catch (IOException e) {
            System.err.println("Failed to save statistics: " + e.getMessage());
        }
    }

    public void updateStatistics(Player player, int currentLevel) {
        AttemptStatistics current = gameStatistics.getCurrentAttempt();
        current.setLevel(currentLevel);
        current.setTreasures(player.getTotalTreasures());
    }

    public void onEnemyKilled() {
        gameStatistics.addEnemyKilled();
    }

    public void onFoodEaten() {
        gameStatistics.addFoodEaten();
    }

    public void onElixirDrunk() {
        gameStatistics.addElixirDrunk();
    }

    public void onScrollRead() {
        gameStatistics.addScrollRead();
    }

    public void onHitDealt() {
        gameStatistics.addHitDealt();
    }

    public void onHitTaken() {
        gameStatistics.addHitTaken();
    }

    public void onStep() {
        gameStatistics.addStep();
    }

    public void updateTreasures(int treasures) {
        gameStatistics.updateTreasures(treasures);
    }
    public List<AttemptStatistics> getLeaderboard() {
        return gameStatistics.getLeaderboard();
    }

    public List<AttemptStatistics> getTopAttempts(int limit) {
        return gameStatistics.getTopAttempts(limit);
    }
}