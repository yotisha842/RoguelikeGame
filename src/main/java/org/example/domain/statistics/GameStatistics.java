package org.example.domain.statistics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GameStatistics {
    private List<AttemptStatistics> attempts;
    private AttemptStatistics currentAttempt;

    public GameStatistics() {
        this.attempts = new ArrayList<>();
        this.currentAttempt = new AttemptStatistics();
    }

    public void startNewAttempt(String playerName) {
        this.currentAttempt = new AttemptStatistics();
        this.currentAttempt.setPlayerName(playerName);
    }

    public void endCurrentAttempt() {
        if (currentAttempt.getLevel() == 0) {
            currentAttempt.setLevel(1);
        }
        attempts.add(currentAttempt);
    }

    public List<AttemptStatistics> getLeaderboard() {
        return attempts.stream()
                .sorted(Comparator.comparingInt(AttemptStatistics::getTreasures).reversed())
                .collect(Collectors.toList());
    }

    public List<AttemptStatistics> getTopAttempts(int limit) {
        return getLeaderboard().stream().limit(limit).collect(Collectors.toList());
    }
    public void addEnemyKilled() {
        currentAttempt.setEnemiesKilled(currentAttempt.getEnemiesKilled() + 1);
    }

    public void addFoodEaten() {
        currentAttempt.setFoodEaten(currentAttempt.getFoodEaten() + 1);
    }

    public void addElixirDrunk() {
        currentAttempt.setElixirsDrunk(currentAttempt.getElixirsDrunk() + 1);
    }

    public void addScrollRead() {
        currentAttempt.setScrollsRead(currentAttempt.getScrollsRead() + 1);
    }

    public void addHitDealt() {
        currentAttempt.setHitsDealt(currentAttempt.getHitsDealt() + 1);
    }

    public void addHitTaken() {
        currentAttempt.setHitsTaken(currentAttempt.getHitsTaken() + 1);
    }

    public void addStep() {
        currentAttempt.setStepsTaken(currentAttempt.getStepsTaken() + 1);
    }

    public void updateTreasures(int treasures) {
        currentAttempt.setTreasures(treasures);
    }

    public List<AttemptStatistics> getAttempts() { return attempts; }
    public void setAttempts(List<AttemptStatistics> attempts) { this.attempts = attempts; }

    public AttemptStatistics getCurrentAttempt() { return currentAttempt; }
}