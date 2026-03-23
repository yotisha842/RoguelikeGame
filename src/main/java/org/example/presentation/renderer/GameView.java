package org.example.presentation.renderer;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import org.example.domain.entity.character.Enemy;
import org.example.domain.entity.character.Player;
import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.item.*;
import org.example.domain.service.generation.CellType;
import org.example.domain.fogOfWar.FogOfWar;

import java.util.Map;

public class GameView {

    public void drawMapWithFog(Screen screen, Map<Coordinate, CellType> map, FogOfWar fog) {
        for (int y = 0; y < 200; y++) {
            for (int x = 0; x < 200; x++) {
                int status = fog.getVisibility(x, y);

                if (status == FogOfWar.UNEXPLORED) {
                    screen.setCharacter(x, y, new TextCharacter(' '));
                    continue;
                }
                else if (status == FogOfWar.EXPLORED) {
                    CellType type = map.get(new Coordinate(x, y));
                    if (type == CellType.WALL) {
                        screen.setCharacter(x, y, new TextCharacter('#'));
                    } else {
                        screen.setCharacter(x, y, new TextCharacter(' '));
                    }
                }
                else if (status == FogOfWar.VISIBLE) {
                    // ← рисуем всё
                    CellType type = map.get(new Coordinate(x, y));
                    if (type != null) {
                        char ch = '.';
                        if (type == CellType.WALL) ch = '#';
                        else if (type == CellType.FLOOR || type == CellType.CORIDOR) ch = '.';
                        else if (type == CellType.DOOR) ch = '.';
                        screen.setCharacter(x, y, new TextCharacter(ch));
                    } else {
                        screen.setCharacter(x, y, new TextCharacter(' '));
                    }
                }
            }
        }
    }

    public void drawEntity(Screen screen, Coordinate pos, char symbol) {
        screen.setCharacter(pos.getX(), pos.getY(), new TextCharacter(symbol));
    }

    public void drawEntity(Screen screen, Coordinate pos, char symbol, TextColor color) {
        screen.setCharacter(pos.getX(), pos.getY(), new TextCharacter(symbol, color, TextColor.ANSI.DEFAULT));
    }

    public void drawPlayer(Screen screen, Player player) {
        drawEntity(screen, player.coordinate, 'P', TextColor.ANSI.CYAN);
    }

    public void drawEnemy(Screen screen, Enemy enemy) {
        char symbol = enemy.getSymbol();
        TextColor color = TextColor.ANSI.WHITE;

        switch (enemy.GetSymbol()) {
            case 'z':
                color = TextColor.ANSI.GREEN;
                break;
            case 'v':
                color = TextColor.ANSI.RED;
                break;
            case 'g':
                color = TextColor.ANSI.WHITE;
                break;
            case 'O':
                color = TextColor.ANSI.YELLOW;
                break;
            case 's':
                color = TextColor.ANSI.WHITE;
                break;
        }

        drawEntity(screen, enemy.position, symbol, color);
    }

    public void drawFood(Screen screen, Food food) {
        drawEntity(screen, food.getPosition(), 'F');
    }

    public void drawScroll(Screen screen, Scroll scroll) {
        drawEntity(screen, scroll.getPosition(), 'S');
    }

    public void drawElixir(Screen screen, Elixir elixir) {
        drawEntity(screen, elixir.getPosition(), 'E');
    }

    public void drawWeapon(Screen screen, Weapon weapon) {
        drawEntity(screen, weapon.getPosition(), 'W');
    }

    public void drawTreasure(Screen screen, Treasure treasure) {
        screen.setCharacter(treasure.getPosition().getX(), treasure.getPosition().getY(), new TextCharacter('T'));
    }

    public void drawExit(Screen screen, Coordinate exitCoord) {
        if (exitCoord != null) {
            drawEntity(screen, exitCoord, 'X');
        }
    }
}