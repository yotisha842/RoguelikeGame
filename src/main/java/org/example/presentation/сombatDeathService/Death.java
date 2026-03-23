package org.example.presentation.сombatDeathService;

import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.item.Treasure;
import com.googlecode.lanterna.screen.Screen;
import org.example.domain.entity.character.Enemy;
import org.example.domain.entity.character.Player;
import org.example.domain.service.gameSaver.GameService;
import org.example.domain.statistics.StatisticsService;

import java.io.IOException;
import java.util.List;
public class Death {
    public boolean checkEnemyDeath(Enemy monster, String[] logs, List<Treasure> treasures, StatisticsService statisticsService) throws IOException {
        if (monster.hp <= 0) {
            statisticsService.onEnemyKilled();
            int treasureValue = monster.calculateTreasureValue();
            logs[1] = "";
            logs[0] = "Enemy dead, you get " + treasureValue + " treasures";

            Treasure treasure = new Treasure(
                    new Coordinate(monster.position.getX(), monster.position.getY()),
                    treasureValue
            );
            treasures.add(treasure);
            return true;
        }
        return false;
    }

    public boolean checkPlayerDeath(Player player, Screen screen, StatisticsService statisticsService, GameService gameService) throws IOException  {
        if (player.getHp() <= 0) {
            statisticsService.endGame();
            gameService.deleteSave();
            screen.clear();
            screen.newTextGraphics().putString(10, 10, "YOU DIED!");
            screen.newTextGraphics().putString(10, 12, "Press any key to return to main menu...");
            screen.refresh();
            screen.readInput();
            return true;
        }
        return false;
    }
}