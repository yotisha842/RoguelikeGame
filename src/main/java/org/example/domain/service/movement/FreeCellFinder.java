package org.example.domain.service.movement;

import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.item.Elixir;
import org.example.domain.entity.item.Food;
import org.example.domain.entity.item.Scroll;
import org.example.domain.entity.item.Weapon;
import org.example.domain.service.generation.CellType;

import java.util.List;
import java.util.Map;

public class FreeCellFinder {
    public static Coordinate findFreeAdjacentCell(Coordinate playerPos, Map<Coordinate, CellType> map,
                                                  List<Food> foods, List<Scroll> scrolls,
                                                  List<Elixir> elixirs, List<Weapon> weapons) {


        int[][] directions = {{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};


        for (int i = 0; i < directions.length; i++) {
            int newX = playerPos.getX() + directions[i][0];
            int newY = playerPos.getY() + directions[i][1];

            Coordinate candidate = new Coordinate(newX, newY);

            CellType cellType = map.get(candidate);


            if (cellType == CellType.FLOOR || cellType == CellType.DOOR || cellType == CellType.CORIDOR) {

                boolean flag = false;
                for (int j = 0; j < foods.size(); j++) {
                    Food food = foods.get(j);
                    if (food.getPosition().getX() == candidate.getX() && food.getPosition().getY() == candidate.getY()) {
                        flag = true;
                        break;
                    }
                }

                for (int j = 0; j < scrolls.size(); j++) {
                    Scroll scroll = scrolls.get(j);
                    if (scroll.getPosition().getX() == candidate.getX() && scroll.getPosition().getY() == candidate.getY()) {
                        flag = true;
                        break;
                    }
                }

                for (int j = 0; j < elixirs.size(); j++) {
                    Elixir elixir = elixirs.get(j);
                    if (elixir.getPosition().getX() == candidate.getX() && elixir.getPosition().getY() == candidate.getY()) {
                        flag = true;
                        break;
                    }
                }

                for (int j = 0; j < weapons.size(); j++) {
                    Weapon weapon = weapons.get(j);
                    if (weapon.getPosition().getX() == candidate.getX() && weapon.getPosition().getY() == candidate.getY()) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    return candidate;
                }
            }
        }
        return null;
    }
}