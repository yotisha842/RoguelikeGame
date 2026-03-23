package org.example.domain.service.GameCycle;

import com.googlecode.lanterna.screen.Screen;
import org.example.datalayer.dto.GameSaveDTO;
import org.example.domain.entity.character.Enemy;
import org.example.domain.entity.character.Player;
import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.game.Room;
import org.example.domain.entity.item.*;
import org.example.domain.fogOfWar.FogOfWar;
import org.example.domain.service.generation.CellType;
import org.example.domain.service.generation.Generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameState {
    Player player;
    int currentLevelIndex;
    Generation currentLevel;
    Map<Coordinate, Enemy> enemies;
    List<Food> foods;
    List<Scroll> scrolls;
    List<Elixir> elixirs;
    List<Weapon> weapons;
    List<Treasure> treasures;
    FogOfWar fogOfWar;
    List<Room> rooms;
    Map<Coordinate, CellType> map;

    String[] logs = {"", ""};
    public boolean showBackpack = false;

    public Screen screen;
    public GameSaveDTO loadedSave;
    public List<Generation> levels = new ArrayList<>();

    public Player getPlayer() {
        return player;
    }

    public Map<Coordinate, Enemy> getEnemies() {
        return enemies;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public List<Scroll> getScrolls() {
        return scrolls;
    }

    public List<Elixir> getElixirs() {
        return elixirs;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public List<Treasure> getTreasures() {
        return treasures;
    }

    public FogOfWar getFogOfWar() {
        return fogOfWar;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Map<Coordinate, CellType> getMap() {
        return map;
    }
}
