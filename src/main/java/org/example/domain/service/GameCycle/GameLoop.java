package org.example.domain.service.GameCycle;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.example.domain.entity.character.Enemy;
import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.game.Room;
import org.example.domain.entity.item.PickupService;
import org.example.domain.service.gameSaver.GameService;
import org.example.domain.service.generation.CellType;
import org.example.domain.service.generation.Randomizer;
import org.example.domain.statistics.StatisticsService;
import org.example.presentation.сombatDeathService.Combat;
import org.example.presentation.сombatDeathService.Death;
import org.example.presentation.controller.GameController;
import org.example.presentation.input.InputHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.domain.service.generation.ThingsGenerator.generateThingsForLevel;

public class GameLoop {

    private GameState state;
    private InputHandler inputHandler;
    private StatisticsService statisticsService;
    private GameService gameService;
    private GameController gameController;
    private Combat combat;
    private Death death;

    public GameLoop(GameState state, InputHandler inputHandler, StatisticsService statisticsService, GameService gameService) {
        this.state = state;
        this.inputHandler = inputHandler;
        this.statisticsService = statisticsService;
        this.gameService = gameService;
        this.gameController = new GameController(inputHandler, statisticsService);
        this.combat = new Combat();
        this.death = new Death();
    }

    public boolean gameRun() throws IOException {
        while (true) {
            state.fogOfWar.update(state.player.coordinate, state.map, state.rooms);
            gameController.render(state.screen, state.player, state.map, state.enemies, state.foods, state.scrolls, state.elixirs, state.weapons,
                    state.treasures, state.fogOfWar, state.currentLevel.getExit(), state.logs,
                    state.showBackpack, state.currentLevelIndex);

            state.screen.refresh();

            KeyStroke key = inputHandler.getPlayerMovement(state.screen);
            if (key == null) continue;

            if (key.getKeyType() == KeyType.Escape) {
                statisticsService.updateStatistics(state.player, state.currentLevelIndex + 1);
                gameService.saveGame(state.player, state.currentLevelIndex, state.currentLevel, state.enemies, state.foods,
                        state.scrolls, state.elixirs, state.weapons, state.treasures, state.fogOfWar);
                return false;
            }

            Character charr = key.getCharacter();
            if (charr == null) continue;
            char c = charr;

            if (c == 'q' && state.showBackpack) {
                state.showBackpack = false;
                continue;
            }

            int NewX = state.player.coordinate.getX();
            int NewY = state.player.coordinate.getY();

            if (c == 'w') NewY--;
            if (c == 's') NewY++;
            if (c == 'a') NewX--;
            if (c == 'd') NewX++;

            if (inputHandler.isBackpackToggle(c)) {
                state.showBackpack = true;
                continue;
            }

            if (inputHandler.isFoodMenu(c)) {
                inputHandler.handleFoodMenu(state.screen, state.player, state.logs, gameService, state.currentLevelIndex, state.currentLevel, state.enemies,
                        state.foods, state.scrolls, state.elixirs, state.weapons, state.treasures, state.fogOfWar, gameController);
                continue;
            }

            if (inputHandler.isScrollMenu(c)) {
                inputHandler.handleScrollMenu(state.screen, state.player, state.logs, gameService, state.currentLevelIndex, state.currentLevel, state.enemies,
                        state.foods, state.scrolls, state.elixirs, state.weapons, state.treasures, state.fogOfWar, gameController);
                continue;
            }

            if (inputHandler.isElixirMenu(c)) {
                inputHandler.handleElixirMenu(state.screen, state.player, state.logs, gameService, state.currentLevelIndex, state.currentLevel, state.enemies, state.foods, state.scrolls, state.elixirs, state.weapons, state.treasures, state.fogOfWar, gameController);
                continue;
            }

            if (inputHandler.isWeaponMenu(c)) {
                inputHandler.handleWeaponMenu(state.screen, state.player, state.logs, gameService, state.currentLevelIndex, state.currentLevel, state.enemies, state.foods, state.scrolls, state.elixirs, state.weapons, state.treasures, state.fogOfWar, gameController, state.currentLevel.getCells());
                continue;
            }

            state.player.updateEffects();

            Coordinate nextCoord = new Coordinate(NewX, NewY);
            CellType nextCell = state.currentLevel.getCell(nextCoord);

            if (nextCoord.equals(state.currentLevel.getExit())) {
                statisticsService.updateStatistics(state.player, state.currentLevelIndex + 1);
                state.screen.clear();

                state.currentLevelIndex++;
                if (state.currentLevelIndex >= state.levels.size()) {
                    statisticsService.updateStatistics(state.player, state.currentLevelIndex);
                    statisticsService.endGame();
                    gameService.deleteSave();
                    return true;
                }

                state.currentLevel = state.levels.get(state.currentLevelIndex);
                state.rooms = new ArrayList<>(state.currentLevel.getRooms().values());
                state.map = state.currentLevel.getCells();

                Room newStartRoom = state.rooms.get(0);
                List<Coordinate> newStartCoords = new ArrayList<>();
                for (int y1 = newStartRoom.getLeft().getY() + 1; y1 < newStartRoom.getRight().getY(); y1++) {
                    for (int x1 = newStartRoom.getLeft().getX() + 1; x1 < newStartRoom.getRight().getX(); x1++) {
                        Coordinate coord = new Coordinate(x1, y1);
                        if (state.currentLevel.getCell(coord) == CellType.FLOOR) {
                            newStartCoords.add(coord);
                        }
                    }
                }
                Coordinate newStartCoord = newStartCoords.get(Randomizer.random(0, newStartCoords.size() - 1));
                state.player.coordinate.setX(newStartCoord.getX());
                state.player.coordinate.setY(newStartCoord.getY());

                state.foods.clear();
                state.scrolls.clear();
                state.elixirs.clear();
                state.weapons.clear();
                state.enemies.clear();

                generateThingsForLevel(state.rooms, state.currentLevel, state.foods, state.scrolls, state.elixirs, state.weapons, state.enemies,
                        state.map, state.player.coordinate, state.treasures, state.currentLevelIndex);

                for (Enemy enemy : state.enemies.values()) {
                    enemy.setLevelIndex(state.currentLevelIndex);
                }
                state.fogOfWar.reset();
                gameService.saveGame(state.player, state.currentLevelIndex, state.currentLevel, state.enemies, state.foods,
                        state.scrolls, state.elixirs, state.weapons, state.treasures, state.fogOfWar);
                continue;
            }

            if (nextCell == CellType.FLOOR || nextCell == CellType.DOOR || nextCell == CellType.CORIDOR) {

                PickupService.tryPickup(nextCoord, state.player, state.foods, state.scrolls, state.elixirs, state.weapons, state.treasures, state.logs, statisticsService);

                Enemy monster = state.player.enemyCollision(NewX, NewY, state.enemies);
                if (monster != null) {

                    if (!state.player.isSleeping()) {
                        combat.playerAttack(state.player, monster, state.logs);
                        statisticsService.onHitDealt();
                        combat.enemyAttack(state.player, monster, state.logs);
                        statisticsService.onHitTaken();
                    } else {
                        state.player.setSleeping(false);
                    }

                    if (death.checkEnemyDeath(monster, state.logs, state.treasures, statisticsService)) {
                        state.enemies.values().remove(monster);
                    }

                    if (death.checkPlayerDeath(state.player, state.screen, statisticsService, gameService)) {
                        statisticsService.updateStatistics(state.player, state.currentLevelIndex + 1);
                        return false;
                    }

                    gameService.saveGame(state.player, state.currentLevelIndex, state.currentLevel, state.enemies, state.foods,
                            state.scrolls, state.elixirs, state.weapons, state.treasures, state.fogOfWar);

                } else {
                    if (!state.player.isSleeping()) {
                        state.logs[1] = "";
                        state.player.coordinate.setX(NewX);
                        state.player.coordinate.setY(NewY);
                        statisticsService.onStep();
                        gameService.saveGame(state.player, state.currentLevelIndex, state.currentLevel, state.enemies, state.foods,
                                state.scrolls, state.elixirs, state.weapons, state.treasures, state.fogOfWar);
                    } else {
                        state.player.setSleeping(false);
                    }
                }

                if (inputHandler.isMovementKey(c)) {
                    for (Enemy enemy : state.enemies.values()) {
                        enemy.playerPos = state.player.coordinate;
                        enemy.step();
                    }
                }
            }
        }
    }
}
