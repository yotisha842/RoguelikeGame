package org.example.domain.entity.item;

import org.example.domain.entity.character.Player;
import org.example.domain.entity.game.Coordinate;
import org.example.domain.statistics.StatisticsService;

import java.util.List;

public class PickupService {

    public static boolean tryPickup(Coordinate nextCoord, Player player, List<Food> foods, List<Scroll> scrolls, List<Elixir> elixirs,
            List<Weapon> weapons, List<Treasure> treasures, String[] logs, StatisticsService statisticsService) {


        for (int i = 0; i < foods.size(); i++) {
            Food food = foods.get(i);
            if (nextCoord.equals(food.getPosition())) {
                logs[1] = "";
                ItemChanger.PersFoodChanger(player, food, foods);
                logs[0] = "Picked up " + food.getFoodType() + " +" + food.getHealthIncrease() + " HP";
                return true;
            }
        }

        for (int i = 0; i < scrolls.size(); i++) {
            Scroll scroll = scrolls.get(i);
            if (nextCoord.equals(scroll.getPosition())) {
                logs[1] = "";
                ItemChanger.PersScrollChanger(player, scroll, scrolls);
                logs[0] = "Picked up scroll of " + scroll.getScrollType() + " +" + scroll.getValue();
                return true;
            }
        }

        for (int i = 0; i < elixirs.size(); i++) {
            Elixir elixir = elixirs.get(i);
            if (nextCoord.equals(elixir.getPosition())) {
                logs[1] = "";
                ItemChanger.PersPotionChanger(player, elixir, elixirs);
                logs[0] = "Picked up elixir of " + elixir.getElixirType() + " +" + elixir.getValue();
                return true;
            }
        }

        for (int i = 0; i < weapons.size(); i++) {
            Weapon weapon = weapons.get(i);
            if (nextCoord.equals(weapon.getPosition())) {
                logs[1] = "";
                ItemChanger.PersWeaponChanger(player, weapon, weapons);
                logs[0] = "Picked up weapon " + weapon.getWeaponType() + " +" + weapon.getValue() + " DMG";
                return true;
            }
        }

        for (int i = 0; i < treasures.size(); i++) {
            Treasure treasure = treasures.get(i);
            if (nextCoord.equals(treasure.getPosition())) {
                logs[1] = "";
                player.addTreasures(treasure.getValue());
                statisticsService.updateTreasures(player.getTotalTreasures());
                logs[0] = "Picked up " + treasure.getValue() + " treasures!";
                treasures.remove(i);
                return true;
            }
        }
        return false;
    }
}
