package org.example.domain.entity.item;

import org.example.domain.entity.character.Player;
import org.example.domain.entity.game.Coordinate;
import java.util.List;

public class ItemChanger {

    public static void PersFoodChanger(Player player, Food food, List<Food> foods) {
        if (player.getBackpack().addItem(food)) {
            foods.remove(food);
        } else {
            Coordinate oldPlayerPos = new Coordinate(player.coordinate.getX(), player.coordinate.getY());
            player.coordinate.setX(food.getPosition().getX());
            player.coordinate.setY(food.getPosition().getY());
            food.setPosition(oldPlayerPos);
        }
    }

    public static void PersScrollChanger(Player player, Scroll scroll, List<Scroll> scrolls) {
        if (player.getBackpack().addItem(scroll)) {
            scrolls.remove(scroll);
        } else {
            Coordinate oldPlayerPos = new Coordinate(player.coordinate.getX(), player.coordinate.getY());
            player.coordinate.setX(scroll.getPosition().getX());
            player.coordinate.setY(scroll.getPosition().getY());
            scroll.setPosition(oldPlayerPos);
        }
    }

    public static void PersPotionChanger(Player player, Elixir elixir, List<Elixir> elixirs) {
        if (player.getBackpack().addItem(elixir)) {
            elixirs.remove(elixir);
        } else {
            Coordinate oldPlayerPos = new Coordinate(player.coordinate.getX(), player.coordinate.getY());
            player.coordinate.setX(elixir.getPosition().getX());
            player.coordinate.setY(elixir.getPosition().getY());
            elixir.setPosition(oldPlayerPos);
        }
    }

    public static void PersWeaponChanger(Player player, Weapon weapon, List<Weapon> weapons) {
        if (player.getBackpack().addItem(weapon)) {
            weapons.remove(weapon);
        } else {
            Coordinate oldPlayerPos = new Coordinate(player.coordinate.getX(), player.coordinate.getY());
            player.coordinate.setX(weapon.getPosition().getX());
            player.coordinate.setY(weapon.getPosition().getY());
            weapon.setPosition(oldPlayerPos);
        }
    }
}
