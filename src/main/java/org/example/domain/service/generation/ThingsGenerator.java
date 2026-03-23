package org.example.domain.service.generation;

import org.example.domain.entity.character.Enemy;
import org.example.domain.entity.item.Effect;
import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.game.Room;
import org.example.domain.entity.item.Elixir;
import org.example.domain.entity.item.Food;
import org.example.domain.entity.item.Scroll;
import org.example.domain.entity.item.Weapon;
import org.example.domain.entity.item.Treasure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ThingsGenerator {

    public static void generateThingsForLevel (List<Room> rooms,
                                               Generation currentLevel,
                                               List<Food> foods,
                                               List<Scroll> scrolls,
                                               List<Elixir> elixirs,
                                               List<Weapon> weapons,
                                               Map<Coordinate, Enemy> enemies,
                                               Map<Coordinate, CellType> map,
                                               Coordinate playerCoord,
                                               List <Treasure> treasures,
                                               int levelIndex) {

        for (int roomId = 1; roomId < 9; roomId++) {
            Room room = rooms.get(roomId);
            List<Coordinate> roomFloorCoords = new ArrayList<>();
            for (int y = room.getLeft().getY() + 1; y < room.getRight().getY(); y++) {
                for (int x = room.getLeft().getX() + 1; x < room.getRight().getX(); x++) {
                    Coordinate coord = new Coordinate(x, y);
                    if (currentLevel.getCell(coord) == CellType.FLOOR) {
                        roomFloorCoords.add(coord);
                    }
                }
            }

            List<Coordinate> occupied = new ArrayList<>();

            int maxFood = 4 - (levelIndex/10);
            int foodCount = Randomizer.random(1, maxFood);
            for (int i = 0; i < foodCount; i++) {
                Coordinate foodPos;
                int attempts = 0;
                do {
                    foodPos = roomFloorCoords.get(Randomizer.random(0, roomFloorCoords.size() - 1));
                    attempts++;
                    if (attempts > roomFloorCoords.size()) break;
                } while (occupied.contains(foodPos));

                if (foodPos != null) {
                    Food.FoodType[] foodTypes = {Food.FoodType.Apple, Food.FoodType.Meat, Food.FoodType.Cheese};
                    Food.FoodType type = foodTypes[Randomizer.random(0, foodTypes.length - 1)];
                    int health = 0;
                    switch (type) {
                        case Apple:
                            health = 10;
                            break;
                        case Meat:
                            health = 20;
                            break;
                        case Cheese:
                            health = 15;
                            break;
                    }
                    foods.add(new Food(foodPos, health, type));
                    occupied.add(foodPos);
                }
            }

            int maxScroll = (levelIndex < 2) ? 2 : 1;
            int scrollCount = Randomizer.random(0, maxScroll);
            for (int i = 0; i < scrollCount; i++) {
                Coordinate scrollPos;
                int attempts = 0;
                do {
                    scrollPos = roomFloorCoords.get(Randomizer.random(0, roomFloorCoords.size() - 1));
                    attempts++;
                    if (attempts > roomFloorCoords.size()) break;
                } while (occupied.contains(scrollPos));

                if (scrollPos != null) {
                    Scroll.ScrollType[] types = {Scroll.ScrollType.strength, Scroll.ScrollType.dexterity, Scroll.ScrollType.max_hp};
                    Scroll.ScrollType type = types[Randomizer.random(0, types.length - 1)];
                    scrolls.add(new Scroll(scrollPos, type));
                    occupied.add(scrollPos);
                }
            }

            int maxElixir = (levelIndex < 2) ? 2 : 1;
            int elixirCount = Randomizer.random(0, maxElixir);
            for (int i = 0; i < elixirCount; i++) {
                Coordinate elixirPos;
                int attempts = 0;
                do {
                    elixirPos = roomFloorCoords.get(Randomizer.random(0, roomFloorCoords.size() - 1));
                    attempts++;
                    if (attempts > roomFloorCoords.size()) break;
                } while (occupied.contains(elixirPos));

                if (elixirPos != null) {
                    Effect.Type[] types = {Effect.Type.STRENGTH, Effect.Type.DEXTERITY, Effect.Type.MAX_HP};
                    Effect.Type type = types[Randomizer.random(0, types.length - 1)];
                    int value = (type == Effect.Type.MAX_HP) ? 15 : 10;
                    int duration = 3; // на 3 хода
                    elixirs.add(new Elixir(elixirPos, type, value, duration));
                    occupied.add(elixirPos);
                }
            }

            int maxWeapon = (levelIndex < 1) ? 2 : 1;
            int weaponCount = Randomizer.random(0, maxWeapon);
            for (int i = 0; i < weaponCount; i++) {
                Coordinate weaponPos;
                int attempts = 0;
                do {
                    weaponPos = roomFloorCoords.get(Randomizer.random(0, roomFloorCoords.size() - 1));
                    attempts++;
                    if (attempts > roomFloorCoords.size()) break;
                } while (occupied.contains(weaponPos));


                if (weaponPos != null) {
                    Weapon.WeaponType[] types = {Weapon.WeaponType.Axe, Weapon.WeaponType.Spear, Weapon.WeaponType.Great_Sword, Weapon.WeaponType.Sword,
                    Weapon.WeaponType.Double_Axe, Weapon.WeaponType.Mace, Weapon.WeaponType.Greatest_Sword, Weapon.WeaponType.Dagger, Weapon.WeaponType.Nunchucks,
                    Weapon.WeaponType.Secret_Weapon};
                    Weapon.WeaponType type = types[Randomizer.random(0, types.length - 1)];
                    weapons.add(new Weapon(weaponPos, type));
                    occupied.add(weaponPos);
                }
            }

            int maxMonster = 2 + Math.round((float) levelIndex / 10);
            int monsterCount = Randomizer.random(1, maxMonster);

            for (int i = 0; i < monsterCount; i++) {
                Coordinate monsterPos;
                int attempts = 0;
                do {
                    monsterPos = roomFloorCoords.get(Randomizer.random(0, roomFloorCoords.size() - 1));
                    attempts++;
                    if (attempts > roomFloorCoords.size()) break;
                } while (occupied.contains(monsterPos));

                if (monsterPos != null) {
                    char[] monsterSymbols = {'z', 'v', 'g', 'O', 's', 'm'};
                    char randomSymbol = monsterSymbols[Randomizer.random(0, monsterSymbols.length - 1)];
                    Enemy monster = new Enemy(randomSymbol, enemies, map, monsterPos, foods, scrolls, elixirs, weapons, playerCoord, rooms, treasures, currentLevel.getExit());
                    monster.setLevelIndex(levelIndex);
                    enemies.put(monsterPos, monster);
                    occupied.add(monsterPos);
                }
            }
        }
    }
}
