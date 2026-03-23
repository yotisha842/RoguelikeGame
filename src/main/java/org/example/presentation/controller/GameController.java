package org.example.presentation.controller;

import com.googlecode.lanterna.screen.Screen;
import org.example.domain.entity.character.Enemy;
import org.example.domain.entity.character.Player;
import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.item.*;
import org.example.domain.service.generation.CellType;
import org.example.presentation.input.InputHandler;
import org.example.domain.fogOfWar.FogOfWar;
import org.example.presentation.renderer.GameView;
import org.example.presentation.renderer.InventoryView;
import org.example.presentation.renderer.UIRenderer;
import org.example.domain.statistics.StatisticsService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.example.domain.service.movement.FreeCellFinder;

public class GameController {
    private final InputHandler inputHandler;
    private final GameView gameView;
    private final InventoryView inventoryView;
    private final UIRenderer uiRenderer;

    private StatisticsService statisticsService;

    public GameController(InputHandler inputHandler, StatisticsService statisticsService) {
        this.inputHandler = inputHandler;
        this.gameView = new GameView();
        this.inventoryView = new InventoryView();
        this.uiRenderer = new UIRenderer();
        this.statisticsService = statisticsService;
    }

    public String handleFoodMenu(Screen screen, Player player) throws IOException {
        while (true) {
            screen.clear();
            screen.refresh();

            Item[] foodSlots = player.getBackpack().getSlots("food");
            inventoryView.showFoodOptions(screen, foodSlots);

            int choice = inputHandler.selectFromInventory(screen);
            if (choice == -1) break;

            if (choice >= 0 && choice < foodSlots.length && foodSlots[choice] != null) {
                Item item = player.getBackpack().removeItem("food", choice);
                if (item instanceof Food) {
                    ((Food) item).ItemFunction(player);
                    statisticsService.onFoodEaten();
                    return "Ate " + ((Food) item).getFoodType() + "! HP: " + player.getHp();
                }
            }
            break;
        }
        screen.refresh();
        return null;
    }

    public String handleScrollMenu(Screen screen, Player player) throws IOException {
        while (true) {
            screen.clear();
            screen.refresh();

            Item[] scrollSlots = player.getBackpack().getSlots("scroll");
            inventoryView.showScrollOptions(screen, scrollSlots);

            int choice = inputHandler.selectFromInventory(screen);
            if (choice == -1) break;

            if (choice >= 0 && choice < scrollSlots.length && scrollSlots[choice] != null) {
                Item item = player.getBackpack().removeItem("scroll", choice);
                if (item instanceof Scroll) {
                    ((Scroll) item).ItemFunction(player);
                    statisticsService.onScrollRead();
                    return "Used scroll of " + ((Scroll) item).getScrollType();
                }
            }
            break;
        }
        screen.refresh();
        return null;
    }

    public String handleElixirMenu(Screen screen, Player player) throws IOException {
        while (true) {
            screen.clear();
            screen.refresh();

            Item[] elixirSlots = player.getBackpack().getSlots("elixir");
            inventoryView.showElixirOptions(screen, elixirSlots);

            int choice = inputHandler.selectFromInventory(screen);
            if (choice == -1) break;

            if (choice >= 0 && choice < elixirSlots.length && elixirSlots[choice] != null) {
                Item item = player.getBackpack().removeItem("elixir", choice);
                if (item instanceof Elixir) {
                    ((Elixir) item).ItemFunction(player);
                    statisticsService.onElixirDrunk();
                    return "Used elixir of " + ((Elixir) item).getElixirType();
                }
            }
            break;
        }
        screen.refresh();
        return null;
    }

    public String handleWeaponMenu(Screen screen, Player player, List<Weapon> weaponsOnGround,
                                   Map<Coordinate, CellType> map, List<Food> foods,
                                   List<Scroll> scrolls, List<Elixir> elixirs) throws IOException {
        while (true) {
            screen.clear();
            screen.refresh();

            Item[] weaponSlots = player.getBackpack().getSlots("weapon");
            inventoryView.showWeaponOptions(screen, weaponSlots);

            int choice = inputHandler.selectFromInventory(screen);
            if (choice == -1) break;

            if (choice == 10) {
                Weapon oldWeapon = player.getEquippedWeapon();
                if (oldWeapon != null) {
                    player.unequipWeapon();

                    if (player.getBackpack().addItem(oldWeapon)) {
                        return "Unequipped " + oldWeapon.getWeaponType() + " (placed in backpack)";
                    } else {
                        Coordinate dropPos = FreeCellFinder.findFreeAdjacentCell(player.coordinate, map, foods, scrolls, elixirs, weaponsOnGround);

                        if (dropPos != null) {
                            oldWeapon.setPosition(dropPos);
                            weaponsOnGround.add(oldWeapon);
                            return "Unequipped and dropped " + oldWeapon.getWeaponType();
                        } else {
                            return "Unequipped " + oldWeapon.getWeaponType() + " - disappeared (no space)";
                        }
                    }
                } else {
                    return "No weapon to unequip.";
                }
            }

            if (choice >= 0 && choice < weaponSlots.length && weaponSlots[choice] != null) {
                Item item = player.getBackpack().removeItem("weapon", choice);
                if (item instanceof Weapon) {
                    Weapon newWeapon = (Weapon) item;

                    Weapon oldWeapon = player.getEquippedWeapon();
                    Coordinate dropPos = null;

                    if (oldWeapon != null) {
                        player.unequipWeapon();

                            dropPos = FreeCellFinder.findFreeAdjacentCell(player.coordinate, map, foods, scrolls, elixirs, weaponsOnGround);

                            if (dropPos != null) {
                                oldWeapon.setPosition(dropPos);
                                weaponsOnGround.add(oldWeapon);
                            }
                    }

                    player.equipWeapon(newWeapon);

                    String message = "Equipped " + newWeapon.getWeaponType();
                    if (oldWeapon != null) {
                        if (dropPos != null) {
                            message += ", dropped " + oldWeapon.getWeaponType();
                        } else {
                            message += ", old " + oldWeapon.getWeaponType() + " disappeared (no space)";
                        }
                    }
                    return message;
                }
            }
            break;
        }
        return null;
    }

    public void render(Screen screen, Player player, Map<Coordinate, CellType> map,
                       Map<Coordinate, Enemy> enemies, List<Food> foods, List<Scroll> scrolls,
                       List<Elixir> elixirs, List<Weapon> weapons, List<Treasure> treasures, FogOfWar fogOfWar, Coordinate exitCoord,
                       String[] logs, boolean showBackpack, int currenLevel) {

        gameView.drawMapWithFog(screen, map, fogOfWar);

        gameView.drawPlayer(screen, player);

        for (Enemy enemy : enemies.values()) {
            if (fogOfWar.isVisible(enemy.position.getX(), enemy.position.getY())) {
                gameView.drawEnemy(screen, enemy);
            }
        }

        for (Food food : foods) {
            if (fogOfWar.isVisible(food.getPosition().getX(), food.getPosition().getY())) {
                gameView.drawFood(screen, food);
            }
        }

        for (Scroll scroll : scrolls) {
            if (fogOfWar.isVisible(scroll.getPosition().getX(), scroll.getPosition().getY())) {
                gameView.drawScroll(screen, scroll);
            }
        }

        for (Elixir elixir : elixirs) {
            if (fogOfWar.isVisible(elixir.getPosition().getX(), elixir.getPosition().getY())) {
                gameView.drawElixir(screen, elixir);
            }
        }

        for (Weapon weapon : weapons) {
            if (fogOfWar.isVisible(weapon.getPosition().getX(), weapon.getPosition().getY())) {
                gameView.drawWeapon(screen, weapon);
            }
        }

        for (Treasure treasure : treasures) {
            if (fogOfWar.isVisible(treasure.getPosition().getX(), treasure.getPosition().getY())) {
                gameView.drawTreasure(screen, treasure);
            }
        }

        if (exitCoord != null && fogOfWar.isVisible(exitCoord.getX(), exitCoord.getY())) {
            gameView.drawExit(screen, exitCoord);
        }

        uiRenderer.drawGameUI(screen, player, logs, currenLevel, showBackpack);
        uiRenderer.drawBackpackInfo(screen, player, showBackpack);
    }
}
