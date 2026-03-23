package org.example.domain.service.gameSaver;

import org.example.datalayer.dto.GameSaveDTO;
import org.example.datalayer.repository.SaveRepository;
import org.example.domain.entity.character.Enemy;
import org.example.domain.entity.character.Player;
import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.game.Room;
import org.example.domain.entity.item.*;
import org.example.domain.service.generation.CellType;
import org.example.domain.service.generation.Generation;
import org.example.domain.fogOfWar.FogOfWar;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameService {
    private final SaveRepository saveRepository;

    public GameService() {
        this.saveRepository = new SaveRepository();
    }

    public void saveGame(Player player,
                         int currentLevelIndex,
                         Generation currentLevel,
                         Map<Coordinate, Enemy> enemies,
                         List<Food> foods,
                         List<Scroll> scrolls,
                         List<Elixir> elixirs,
                         List<Weapon> weapons,
                         List<Treasure> treasures,
                         FogOfWar fogOfWar) {
        try {
            saveRepository.saveGame(player, currentLevelIndex, currentLevel, enemies,
                    foods, scrolls, elixirs, weapons, treasures, fogOfWar);
        } catch (IOException e) {
            System.err.println("Failed to save game: " + e.getMessage());
        }
    }

    public Optional<GameSaveDTO> loadGame() {
        Optional<GameSaveDTO> loaded = saveRepository.loadGame();
        loaded.ifPresent(save -> {
        });
        return loaded;
    }

    public void deleteSave() {
        saveRepository.deleteSave();
    }

    public Player restorePlayer(GameSaveDTO save) {
        if (save == null) return null;
        return saveRepository.restorePlayer(save);
    }

    public Generation restoreGeneration(GameSaveDTO save) {
        if (save == null) return null;
        return saveRepository.restoreGeneration(save);
    }

    public Map<Coordinate, Enemy> restoreEnemies(GameSaveDTO save,
                                                 Map<Coordinate, CellType> map,
                                                 List<Food> foods,
                                                 List<Scroll> scrolls,
                                                 List<Elixir> elixirs,
                                                 List<Weapon> weapons,
                                                 List<Treasure> treasures,
                                                 Coordinate playerPos,
                                                 List<Room> rooms,
                                                 Coordinate exitCoord) {
        if (save == null) return new java.util.HashMap<>();
        return saveRepository.restoreEnemies(save, map, foods, scrolls, elixirs, weapons,
                treasures, playerPos, rooms, exitCoord);
    }

    public List<Food> restoreFoods(GameSaveDTO save) {
        if (save == null) return new java.util.ArrayList<>();
        return saveRepository.restoreFoods(save);
    }

    public List<Scroll> restoreScrolls(GameSaveDTO save) {
        if (save == null) return new java.util.ArrayList<>();
        return saveRepository.restoreScrolls(save);
    }

    public List<Elixir> restoreElixirs(GameSaveDTO save) {
        if (save == null) return new java.util.ArrayList<>();
        return saveRepository.restoreElixirs(save);
    }

    public List<Weapon> restoreWeapons(GameSaveDTO save) {
        if (save == null) return new java.util.ArrayList<>();
        return saveRepository.restoreWeapons(save);
    }

    public List<Treasure> restoreTreasures(GameSaveDTO save) {
        if (save == null) return new java.util.ArrayList<>();
        return saveRepository.restoreTreasures(save);
    }

    public FogOfWar restoreFogOfWar(GameSaveDTO save) {
        if (save == null) return new FogOfWar();
        return saveRepository.restoreFogOfWar(save);
    }
}