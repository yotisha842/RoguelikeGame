package org.example.domain.service.GameCycle;

import org.example.domain.entity.character.Enemy;
import org.example.domain.service.generation.Randomizer;
import org.example.domain.service.generation.CellType;
import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.game.Room;
import org.example.domain.entity.character.Player;
import org.example.domain.fogOfWar.FogOfWar;
import org.example.domain.service.generation.Generation;
import java.util.*;

import static org.example.domain.service.generation.ThingsGenerator.generateThingsForLevel;

public class GameInitialize {

    public void initializeGameState(GameState state) {
        if (state.loadedSave != null) {
            loadSavedGame(state);
        } else {
            createNewGame(state);
        }
    }

    private void loadSavedGame(GameState state) {

        state.player = Game.getGameService().restorePlayer(state.loadedSave);
        state.currentLevelIndex = state.loadedSave.getCurrentLevelIndex();

        for (int i = 0; i < 21; i++) {
            state.levels.add(new Generation());
        }

        state.currentLevel = Game.getGameService().restoreGeneration(state.loadedSave);
        state.levels.set(state.currentLevelIndex, state.currentLevel);

        state.map = state.currentLevel.getCells();
        state.rooms = new ArrayList<>(state.currentLevel.getRooms().values());

        state.foods = Game.getGameService().restoreFoods(state.loadedSave);
        state.scrolls = Game.getGameService().restoreScrolls(state.loadedSave);
        state.elixirs = Game.getGameService().restoreElixirs(state.loadedSave);
        state.weapons = Game.getGameService().restoreWeapons(state.loadedSave);
        state.treasures = Game.getGameService().restoreTreasures(state.loadedSave);
        state.enemies = Game.getGameService().restoreEnemies(state.loadedSave, state.map, state.foods, state.scrolls, state.elixirs, state.weapons, state.treasures, state.player.coordinate, state.rooms, state.currentLevel.getExit());
        for (Enemy enemy : state.enemies.values()) {
            enemy.setLevelIndex(state.currentLevelIndex);
        }
        state.fogOfWar = Game.getGameService().restoreFogOfWar(state.loadedSave);
    }

    private void createNewGame(GameState state) {
        for (int i = 0; i < 21; i++) {
            state.levels.add(new Generation());
        }

        state.currentLevelIndex = 0;
        state.currentLevel = state.levels.get(state.currentLevelIndex);
        state.rooms = new ArrayList<>(state.currentLevel.getRooms().values());
        Room startRoom = state.rooms.get(0);
        state.map = state.currentLevel.getCells();

        List<Coordinate> startFloorCoords = new ArrayList<>();
        for (int y = startRoom.getLeft().getY() + 1; y < startRoom.getRight().getY(); y++) {
            for (int x = startRoom.getLeft().getX() + 1; x < startRoom.getRight().getX(); x++) {
                Coordinate coord = new Coordinate(x, y);
                if (state.currentLevel.getCell(coord) == CellType.FLOOR) {
                    startFloorCoords.add(coord);
                }
            }
        }

        Coordinate startCoord = startFloorCoords.get(Randomizer.random(0, startFloorCoords.size() - 1));
        state.player = new Player(startCoord.getX(), startCoord.getY(), 100, 9);

        state.enemies = new HashMap<>();
        state.foods = new ArrayList<>();
        state.scrolls = new ArrayList<>();
        state.elixirs = new ArrayList<>();
        state.weapons = new ArrayList<>();
        state.treasures = new ArrayList<>();
        state.fogOfWar = new FogOfWar();

        generateThingsForLevel(state.rooms, state.currentLevel, state.foods, state.scrolls, state.elixirs, state.weapons, state.enemies, state.map, state.player.coordinate, state.treasures, state.currentLevelIndex);
    }
}