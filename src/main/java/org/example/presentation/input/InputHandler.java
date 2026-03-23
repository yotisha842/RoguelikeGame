package org.example.presentation.input;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import org.example.domain.entity.character.Enemy;
import org.example.domain.entity.character.Player;
import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.item.*;
import org.example.domain.fogOfWar.FogOfWar;
import org.example.domain.service.gameSaver.GameService;
import org.example.domain.service.generation.CellType;
import org.example.domain.service.generation.Generation;
import org.example.presentation.controller.GameController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class InputHandler {

    public KeyStroke getPlayerMovement(Screen screen) throws IOException {
        return screen.pollInput();
    }

    public boolean isUnequipWeapon(char c) {
        return c == '0';
    }

    public boolean isMovementKey(char c) {
        return c == 'w' || c == 's' || c == 'a' || c == 'd';
    }

    public boolean isBackpackToggle(char c) {
        return c == 'b';
    }

    public boolean isBackpackClose(char c) {
        return c == 'q';
    }

    public boolean isFoodMenu(char c) {
        return c == 'j';
    }

    public boolean isScrollMenu(char c) {
        return c == 'e';
    }

    public boolean isElixirMenu(char c) {
        return c == 'k';
    }

    public boolean isWeaponMenu(char c) {
        return c == 'h';
    }

    public void handleFoodMenu(Screen screen, Player player, String[] logs, GameService gameService,
                               int currentLevelIndex, Generation currentLevel, Map<Coordinate, Enemy> enemies, List<Food> foods,
                               List<Scroll> scrolls, List<Elixir> elixirs, List<Weapon> weapons, List<Treasure> treasures, FogOfWar fogOfWar,
                               GameController gameController) throws IOException {
        logs[1] = "";
        String message = gameController.handleFoodMenu(screen, player);
        if (message != null) logs[0] = message;
        screen.clear();
        screen.refresh();
        gameService.saveGame(player, currentLevelIndex, currentLevel, enemies, foods,
                scrolls, elixirs, weapons, treasures, fogOfWar);
    }

    public void handleScrollMenu(Screen screen, Player player, String[] logs, GameService gameService,
                               int currentLevelIndex, Generation currentLevel, Map<Coordinate, Enemy> enemies, List<Food> foods,
                               List<Scroll> scrolls, List<Elixir> elixirs, List<Weapon> weapons, List<Treasure> treasures, FogOfWar fogOfWar,
                                 GameController gameController) throws IOException {
        logs[1] = "";
        String message = gameController.handleScrollMenu(screen, player);
        if (message != null) logs[0] = message;
        screen.clear();
        screen.refresh();
        gameService.saveGame(player, currentLevelIndex, currentLevel, enemies, foods,
                scrolls, elixirs, weapons, treasures, fogOfWar);
    }

    public void handleElixirMenu(Screen screen, Player player, String[] logs, GameService gameService,
                                 int currentLevelIndex, Generation currentLevel, Map<Coordinate, Enemy> enemies, List<Food> foods,
                                 List<Scroll> scrolls, List<Elixir> elixirs, List<Weapon> weapons, List<Treasure> treasures, FogOfWar fogOfWar,
                                 GameController gameController) throws IOException{
        logs[1] = "";
        String message = gameController.handleElixirMenu(screen, player);
        if (message != null) logs[0] = message;
        screen.clear();
        screen.refresh();
        gameService.saveGame(player, currentLevelIndex, currentLevel, enemies, foods,
                scrolls, elixirs, weapons, treasures, fogOfWar);
    }

    public void handleWeaponMenu(Screen screen, Player player, String[] logs, GameService gameService,
                                 int currentLevelIndex, Generation currentLevel, Map<Coordinate, Enemy> enemies, List<Food> foods,
                                 List<Scroll> scrolls, List<Elixir> elixirs, List<Weapon> weapons, List<Treasure> treasures, FogOfWar fogOfWar,
                                 GameController gameController, Map<Coordinate, CellType> map) throws IOException {
        logs[1] = "";
        String message = gameController.handleWeaponMenu(screen, player, weapons, map, foods, scrolls, elixirs);
        if (message != null) logs[0] = message;
        screen.clear();
        screen.refresh();
        gameService.saveGame(player, currentLevelIndex, currentLevel, enemies, foods,
                scrolls, elixirs, weapons, treasures, fogOfWar);
    }


    public int selectFromInventory(Screen screen) throws IOException {
        while (true) {
            KeyStroke key = screen.readInput();
            if (key.getKeyType() == KeyType.Escape) {
                return -1;
            }
            if (key.getCharacter() != null) {
                char c = key.getCharacter();
                if (c >= '1' && c <= '9') {
                    return c - '1';
                }

                if (c == '0') {
                    return 10;
                }
            }
        }
    }

    public String handleMenuInput(Screen screen, String[] options, int selected) throws IOException {
        KeyStroke key = screen.pollInput();
        if (key == null) return null;

        if (key.getKeyType() == KeyType.ArrowUp) {
            return "UP";
        } else if (key.getKeyType() == KeyType.ArrowDown) {
            return "DOWN";
        } else if (key.getKeyType() == KeyType.Enter) {
            return options[selected];
        } else if (key.getKeyType() == KeyType.Escape) {
            return "Exit";
        }

        return null;
    }
}
