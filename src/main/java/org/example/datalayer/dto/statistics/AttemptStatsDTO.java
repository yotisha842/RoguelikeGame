package org.example.datalayer.dto.statistics;

public class AttemptStatsDTO {
    private String playerName;
    private int treasures;
    private int level;
    private int enemiesKilled;
    private int foodEaten;
    private int elixirsDrunk;
    private int scrollsRead;
    private int hitsDealt;
    private int hitsTaken;
    private int stepsTaken;

    public AttemptStatsDTO() {}

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }
    public int getTreasures() { return treasures; }
    public void setTreasures(int treasures) { this.treasures = treasures; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getEnemiesKilled() { return enemiesKilled; }
    public void setEnemiesKilled(int enemiesKilled) { this.enemiesKilled = enemiesKilled; }

    public int getFoodEaten() { return foodEaten; }
    public void setFoodEaten(int foodEaten) { this.foodEaten = foodEaten; }

    public int getElixirsDrunk() { return elixirsDrunk; }
    public void setElixirsDrunk(int elixirsDrunk) { this.elixirsDrunk = elixirsDrunk; }

    public int getScrollsRead() { return scrollsRead; }
    public void setScrollsRead(int scrollsRead) { this.scrollsRead = scrollsRead; }

    public int getHitsDealt() { return hitsDealt; }
    public void setHitsDealt(int hitsDealt) { this.hitsDealt = hitsDealt; }

    public int getHitsTaken() { return hitsTaken; }
    public void setHitsTaken(int hitsTaken) { this.hitsTaken = hitsTaken; }

    public int getStepsTaken() { return stepsTaken; }
    public void setStepsTaken(int stepsTaken) { this.stepsTaken = stepsTaken; }
}