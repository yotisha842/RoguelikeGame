package org.example.datalayer.dto;

import java.util.List;
import java.util.Map;

public class GameSaveDTO {
    private PlayerDTO player;
    private int currentLevelIndex;
    private GenerationDTO currentLevel;
    private List<EnemyDTO> enemies;
    private List<FoodDTO> foods;
    private List<ScrollDTO> scrolls;
    private List<ElixirDTO> elixirs;
    private List<WeaponDTO> weapons;
    private List<TreasureDTO> treasures;
    private FogOfWarDTO fogOfWar;

    public GameSaveDTO() {}

    public GameSaveDTO(PlayerDTO player, int currentLevelIndex, GenerationDTO currentLevel,
                       List<EnemyDTO> enemies, List<FoodDTO> foods, List<ScrollDTO> scrolls,
                       List<ElixirDTO> elixirs, List<WeaponDTO> weapons, List<TreasureDTO> treasures,
                       FogOfWarDTO fogOfWar) {
        this.player = player;
        this.currentLevelIndex = currentLevelIndex;
        this.currentLevel = currentLevel;
        this.enemies = enemies;
        this.foods = foods;
        this.scrolls = scrolls;
        this.elixirs = elixirs;
        this.weapons = weapons;
        this.treasures = treasures;
        this.fogOfWar = fogOfWar;
    }

    public PlayerDTO getPlayer() { return player; }
    public void setPlayer(PlayerDTO player) { this.player = player; }

    public int getCurrentLevelIndex() { return currentLevelIndex; }
    public GenerationDTO getCurrentLevel() { return currentLevel; }

    public List<EnemyDTO> getEnemies() { return enemies; }
    public void setEnemies(List<EnemyDTO> enemies) { this.enemies = enemies; }

    public List<FoodDTO> getFoods() { return foods; }
    public void setFoods(List<FoodDTO> foods) { this.foods = foods; }

    public List<ScrollDTO> getScrolls() { return scrolls; }
    public void setScrolls(List<ScrollDTO> scrolls) { this.scrolls = scrolls; }

    public List<ElixirDTO> getElixirs() { return elixirs; }
    public void setElixirs(List<ElixirDTO> elixirs) { this.elixirs = elixirs; }

    public List<WeaponDTO> getWeapons() { return weapons; }
    public void setWeapons(List<WeaponDTO> weapons) { this.weapons = weapons; }

    public List<TreasureDTO> getTreasures() { return treasures; }
    public void setTreasures(List<TreasureDTO> treasures) { this.treasures = treasures; }

    public FogOfWarDTO getFogOfWar() { return fogOfWar; }
    public void setFogOfWar(FogOfWarDTO fogOfWar) { this.fogOfWar = fogOfWar; }
}